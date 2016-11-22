package cn.didadu.sample.buffer;

import java.awt.*;

/**
 * Created by jinggg on 16/2/1.
 */
public class BufferMovingCircle extends NoBufferMovingCircle{

    Graphics doubleBuffer = null;

    public void init(){
        super.init();
        doubleBuffer = screenImage.getGraphics();
    }

    public void paint(Graphics g){
        doubleBuffer.setColor(Color.white);
        doubleBuffer.fillRect(0,0,200,100);
        drawCircle(doubleBuffer);
        g.drawImage(screenImage,0,0,this);
    }

}
