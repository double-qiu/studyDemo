package cn.didadu.sample.concurrentDesign.io.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * Created by jinggg on 16/3/25.
 */
public class NioEchoClient {

    private Selector selector;

    public void init(InetAddress ip, int port) throws IOException{
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);

        this.selector = SelectorProvider.provider().openSelector();

        channel.connect(new InetSocketAddress(ip,port));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void working() throws Exception{
        while (true){
            if(!selector.isOpen()){
                break;
            }
            selector.select();

            Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()){
                SelectionKey key = ite.next();
                ite.remove();
                if(key.isConnectable()){
                    connect(key);
                }else if(key.isReadable()){
                    read(key);
                }
            }
        }
    }

    public void connect(SelectionKey key) throws IOException{
        SocketChannel channel = (SocketChannel) key.channel();

        if(channel.isConnectionPending()){
            channel.finishConnect();
        }

        channel.configureBlocking(false);
        channel.write(ByteBuffer.wrap(new String("hello server!\r\n").getBytes()));
        channel.register(this.selector,SelectionKey.OP_READ);
    }

    public void read(SelectionKey key) throws IOException{
        SocketChannel channel = (SocketChannel) key.channel();

        ByteBuffer buffer = ByteBuffer.allocate(100);
        channel.read(buffer);

        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("客户端收到消息：" + msg);
        channel.close();
        key.selector().close();

    }

    public static void main(String[] args) throws Exception {
        for(int i = 0; i < 20; i++){
            NioEchoClient client = new NioEchoClient();
            client.init(InetAddress.getLocalHost(),8000);
            client.working();
        }

    }

}
