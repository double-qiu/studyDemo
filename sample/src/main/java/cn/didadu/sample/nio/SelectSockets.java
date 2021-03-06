package cn.didadu.sample.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Created by jinggg on 15/12/30.
 */
public class SelectSockets {

    public static int PORT_NUMBER = 1234;

    public static void main(String[] argv) throws Exception {
        new SelectSockets().go();
    }

    public void go() throws Exception {
        int port = PORT_NUMBER;
        System.out.println("Listening on port " + port);

        // Allocate an unbound server socket channel
        ServerSocketChannel serverChannel = ServerSocketChannel.open();

        // Get the associated ServerSocket to bind it with
        ServerSocket serverSocket = serverChannel.socket();

        // Create a new Selector for use below
        Selector selector = Selector.open();

        // Set the port the server channel will listen to
        serverSocket.bind(new InetSocketAddress(port));

        // Set nonblocking mode for the listening socket
        serverChannel.configureBlocking(false);

        // Register the ServerSocketChannel with the Selector
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // This may block for a long time. Upon returning, the
            // selected set contains keys of the ready channels.
            int n = selector.select();

            if (n == 0) {
                continue; // nothing to do
            }

            // Get an iterator over the set of selected keys
            Iterator it = selector.selectedKeys().iterator();

            // Look at each key in the selected set
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();

                // Is a new connection coming in?
                if (key.isAcceptable()) {
                    ServerSocketChannel server =
                            (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();
                    registerChannel(selector, channel,
                            SelectionKey.OP_READ);
                    sayHello(channel);
                }

                // Is there data to read on this channel?
                if (key.isReadable()) {
                    readDataFromSocket(key);
                }
                // Remove key from selected set; it's been handled
                it.remove();
            }

        }
    }

    protected void registerChannel(Selector selector,
            SelectableChannel channel, int ops) throws Exception {
        if (channel == null) {
            return; // could happen
        }

        // Set the new channel nonblocking
        channel.configureBlocking(false);
        // Register it with the selector
        channel.register(selector, ops);
    }

    // Use the same byte buffer for all channels. A single thread is
    // servicing all the channels, so no danger of concurrent acccess.
    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    protected void readDataFromSocket(SelectionKey key) throws Exception {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;
        buffer.clear(); // Empty buffer

        while ((count = socketChannel.read(buffer)) > 0) {
            buffer.flip(); // Make buffer readable

            while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }
            buffer.clear(); // Empty buffer
        }

        if (count < 0) {
            socketChannel.close();
        }
    }

    private void sayHello(SocketChannel channel) throws Exception {
        buffer.clear();
        buffer.put("Hi there!\r\n".getBytes());
        buffer.flip();
        channel.write(buffer);
    }
}
