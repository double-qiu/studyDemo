package cn.didadu.sample.nio;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * 管理异步连接
 * Created by jinggg on 15/12/28.
 */
public class ConnectAsync {

    public static void main (String [] argv) throws Exception {
        String host = "localhost";
        int port = 1234;
        InetSocketAddress addr = new InetSocketAddress (host, port);

        SocketChannel sc = SocketChannel.open( );
        sc.configureBlocking (false);
        System.out.println("initiating connection");
        sc.connect(addr);

        while ( ! sc.finishConnect( )) {
            doSomethingUseful( );
        }
        System.out.println("connection established");
    }

    private static void doSomethingUseful( )
    {
        System.out.println ("doing something useless");
    }

}
