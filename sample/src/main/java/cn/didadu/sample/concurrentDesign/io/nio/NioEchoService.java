package cn.didadu.sample.concurrentDesign.io.nio;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jinggg on 16/3/24.
 */
public class NioEchoService {

    private Selector selector;

    private ExecutorService pool = Executors.newCachedThreadPool();

    public static Map<Socket,Long> time_stat = new HashMap<>(10240);

    private void startServer() throws Exception{

        //通过工厂方法获得一个Selector对象实例
        selector = SelectorProvider.provider().openSelector();

        //获得表示服务端的SocketChannel实例
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //设置为非阻塞模式
        ssc.configureBlocking(false);

        //绑定端口
        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
        ssc.socket().bind(isa);

        /**
         * 将ServerSocketChannel绑定到Selector上，并注册它感兴趣的时间为OP_ACCEPT
         * 当Selector发现ServerSocketChannel有新的客户端连接时，就会通知ServerSocketChannel进行处理
         */
        SelectionKey acceptKey = ssc.register(selector,SelectionKey.OP_ACCEPT);

        for(;;){
            /**
             * select()是一个阻塞方法，如果当前没有任何数据准备好，它就会等待
             * 一旦有数据可读，它就会返回，返回值是已经准备就绪的SelectionKey的数量
             */
            selector.select();

            // 获取准备好的SelectionKey，因为Selector同时为多个Channel服务，因此就绪的Channel可能有多个
            Set readyKey = selector.selectedKeys();
            Iterator i = readyKey.iterator();
            long e = 0;
            while(i.hasNext()){
                //根据迭代器获取SelectionKey
                SelectionKey sk = (SelectionKey) i.next();
                //将获取的SelectionKey移除，否则会处理相同的SelectionKey
                i.remove();

                if(sk.isAcceptable()){
                    /**
                     * 判断当前SelectionKey所代表的Channel是否在Acceptable状态
                     * 如果是就接收客户端的请求
                     */
                    doAccept(sk);
                }else if(sk.isValid() && sk.isReadable()){
                    /**
                     * 判断当前SelectionKey所代表的Channel是否已经可读
                     * 如果是就进行读任务
                     */
                    if(!time_stat.containsKey(((SocketChannel) sk.channel()).socket())){
                        time_stat.put(((SocketChannel) sk.channel()).socket(),System.currentTimeMillis());
                        doRead(sk);
                     }
                }else if(sk.isValid() && sk.isWritable()){
                    /**
                     * 判断当前SelectionKey所代表的Channel是否准备好可写
                     * 如果是就进行写任务
                     */
                    doWrite(sk);
                    e = System.currentTimeMillis();
                    long b = time_stat.remove(((SocketChannel) sk.channel()).socket());
                    System.out.println("spend:" + (b - e) + "ms");
                }
            }
        }
    }

    /**
     * 接收客户端请求
     * @param sk
     */
    private void doAccept(SelectionKey sk){
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try{
            //生成新的clientChannel，表示客户端和服务端通信的通道
            clientChannel = server.accept();
            //将clientChannel配置为非阻塞
            clientChannel.configureBlocking(false);

            //register this channel for reading
            /**
             * 将生成的新的clientChannel注册到selector上，并告诉Selector我现在对OP_READ操作感兴趣
             * 当Selector发现这个Channel已经准备好读时，就能给线程一个通知
             */
            SelectionKey clientKey = clientChannel.register(selector,SelectionKey.OP_READ);

            //allocate an echoclient instance and attach it to this sellection key.
            /**
             * 新建一个对象实例，一个EchoClient实例代表一个客户端
             * 将这个客户端作为附件添加到表示这个连接的SelectionKey上，这样在整个连接的处理过程中我们都可以共享这个EchoClient
             */
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);

            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connection from " + clientAddress.getHostAddress() + ".");
        }catch (Exception e){
            System.out.println("Failed to accept new client.");
            e.printStackTrace();
        }
    }

    private void doRead(SelectionKey sk){
        //通过SelectionKey获取当前客户端的channel
        SocketChannel channel = (SocketChannel) sk.channel();

        //准备8K的缓存
        ByteBuffer bb = ByteBuffer.allocate(8192);

        int len;
        try{
            //读取客户端发送的信息
            len = channel.read(bb);
            if(len < 0){
                //disconnect(sk);
                return;
            }
        }catch (Exception e){
            System.out.println("Failed to read from client");
            e.printStackTrace();
            //disconnect(sk);
            return;
        }
        //重置缓冲区，为数据读取做准备
        bb.flip();
        pool.execute(new HandleMsg(sk,bb));
    }

    private void doWrite(SelectionKey sk){
        /**
         * 通过SelectionKey获取channel以及客户端实例
         */
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoClient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();

        ByteBuffer bb = outq.getLast();
        try{
            int len = channel.write(bb);
            if(len == -1){
               // disconnect(sk);
                return;
            }
            if(bb.remaining() == 0){
                //如果全部发送完成，则移除这个缓存对象
                outq.removeLast();
            }
        }catch (Exception e){
            System.out.println("Failed to write to client.");
            e.printStackTrace();
            //disconnect(sk);
        }
        if(outq.size() == 0){
            //如果全部数据发送完成，则需要将写事件从感兴趣列表移除
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    /**
     * 客户端对象，封装了一个队列，保存在需要回复给这个客户端的所有信息，进行回复时只要从outq对象中弹出元素即可
     */
    class EchoClient{
        private LinkedList<ByteBuffer> outq;

        EchoClient(){
            outq = new LinkedList<>();
        }

        public LinkedList<ByteBuffer> getOutputQueue(){
            return outq;
        }

        public void enqueue(ByteBuffer bb){
            outq.addFirst(bb);
        }
    }

    class HandleMsg implements Runnable{

        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey sk, ByteBuffer bb){
            this.sk = sk;
            this.bb = bb;
        }

        @Override public void run() {
            //获取SelectionKey中的客户端实例
            EchoClient echoClient = (EchoClient) sk.attachment();
            //将接收到的数据压入队列
            echoClient.enqueue(bb);
            //在数据处理完成之后，就可以准备将结果回写到客户端，因此，重新注册感兴趣的消息事件
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            //强迫selector返回
            selector.wakeup();
        }
    }

    public static void main(String[] args) throws Exception {
        NioEchoService service = new NioEchoService();
        service.startServer();
    }

}
