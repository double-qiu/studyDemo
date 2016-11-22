package cn.didadu.sample.nio;

import java.nio.CharBuffer;

/**
 * 填充、释放缓冲区
 * Created by jinggg on 15/12/14.
 */
public class BufferFillDrain {

    private static int index = 0;
    private static String [] strings = {
            "A random string value",
            "The product of an infinite number of monkeys",
            "Hey hey we're the Monkees",
            "Opening act for the Monkees: Jimi Hendrix",
            "'Scuse me while I kiss this fly", // Sorry Jimi ;-)
            "Help Me! Help Me!"
    };


    public static void main(String[] argv) throws Exception {

        CharBuffer buff = CharBuffer.allocate(100);

        System.out.println(buff.isDirect());

        while(fillBuffer(buff)){
            buff.flip();
            drainBuffer(buff);
            buff.clear();
        }
    }

    /**
     * 写buffer
     * @param buffer
     * @return
     */
    private static boolean fillBuffer (CharBuffer buffer){

        if(index >= strings.length){
            return false;
        }

        String string = strings [index++];

        for (int i = 0; i < string.length(); i++) {
            buffer.put(string.charAt(i));
        }

        return true;
    }

    /**
     * 读buffer
     * @param buffer
     */
    private static void drainBuffer (CharBuffer buffer){
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }

        System.out.println ("");
    }

}
