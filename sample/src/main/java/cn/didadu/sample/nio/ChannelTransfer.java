package cn.didadu.sample.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

/**
 * Created by jinggg on 15/12/22.
 */
public class ChannelTransfer {

    public static void main (String [] argv)
            throws Exception{

        String target = "target.txt";
        String source = "blahblah.txt";

        FileOutputStream fos = new FileOutputStream(target);
        FileInputStream fis = new FileInputStream (source);
        FileChannel channel = fis.getChannel( );

        channel.transferTo (0, channel.size( ), Channels.newChannel(fos));

        channel.close( );
        fis.close( );
        fos.close();
    }

}
