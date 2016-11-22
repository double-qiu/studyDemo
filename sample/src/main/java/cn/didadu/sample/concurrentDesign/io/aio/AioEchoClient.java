package cn.didadu.sample.concurrentDesign.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by jinggg on 16/3/25.
 */
public class AioEchoClient {

    public static void main(String[] args) throws Exception{
        final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();

        client.connect(new InetSocketAddress(8000), null, new CompletionHandler<Void, Object>() {
            @Override public void completed(Void result, Object attachment) {

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override public void completed(Integer result, ByteBuffer attachment) {
                        buffer.flip();
                        System.out.println(new String(buffer.array()));
                        try {
                            client.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override public void failed(Throwable exc, ByteBuffer attachment) {

                    }
                });

            }

            @Override public void failed(Throwable exc, Object attachment) {

            }
        });

        Thread.sleep(100);
    }

}
