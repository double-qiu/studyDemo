package cn.didadu.sample.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

/**
 * Created by jinggg on 16/4/9.
 */
public class EchoServer extends AbstractVerticle{

    public static void main(String[] args){
        Vertx.vertx().createHttpServer().requestHandler(req ->
            req.response().end("<html><body><h1>Hello from vert.x!</h1></body></html>")
        ).listen(8080);
    }

}
