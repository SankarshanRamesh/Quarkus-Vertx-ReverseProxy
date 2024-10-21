package org.practice;

import io.vertx.core.Vertx;

public class DeployVerticle {

    public static void main(String a[]){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SimpleReverseProxyServer());
    }
}
