package cn.didadu.sample.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by jinggg on 15/12/21.
 */
public class ChannelCopy {

    public static void main (String [] argv)
            throws IOException{
        ReadableByteChannel source = Channels.newChannel (System.in);
        WritableByteChannel dest = Channels.newChannel(System.out);

        channelCopy2 (source, dest);
        source.close( );
        dest.close();
    }


    private static void channelCopy1 (ReadableByteChannel src,
            WritableByteChannel dest)
            throws IOException {

        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read (buffer) != -1) {
            buffer.flip( );
            dest.write(buffer);
            buffer.compact();
        }

        buffer.flip();

        while (buffer.hasRemaining( )) {
            dest.write (buffer);
        }
    }

    private static void channelCopy2 (ReadableByteChannel src,
            WritableByteChannel dest)
            throws IOException{
        ByteBuffer buffer = ByteBuffer.allocateDirect (16 * 1024);

        while (src.read (buffer) != -1) {
            buffer.flip( );
            while (buffer.hasRemaining( )) {
                dest.write (buffer);
            }
            buffer.clear( );
        }
    }

}
