package org.practice;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.net.HostAndPort;
import io.vertx.core.net.SocketAddress;
import io.vertx.httpproxy.*;
import jakarta.inject.Inject;

public class SimpleReverseProxyServer extends AbstractVerticle {


//    @Inject
//    Vertx vertx;

    public void start(){
        Vertx vertx = Vertx.vertx();
        int PORT = 8088;
        HttpClientOptions options = new HttpClientOptions()
                .setSsl(true)
                .setTrustAll(true);
        HttpClient httpClient = vertx.createHttpClient(options);
        ProxyOptions proxyOptions = new ProxyOptions();
        proxyOptions.setSupportWebSocket(true);
        HttpProxy proxy = HttpProxy.reverseProxy(proxyOptions, httpClient);
        proxy.addInterceptor(new SetAuthorityInterceptor(443, "api.restful-api.dev"));
        proxy.origin(443, "api.restful-api.dev");


        vertx.createHttpServer()
                .webSocketHandler(event -> event.writeTextMessage("""
                        { "timestamp": "%d" }""".formatted(System.currentTimeMillis())))
                .requestHandler(proxy)
                .listen(PORT).onSuccess(c -> {
                    System.out.println("sucess");
                }).onFailure(e -> {
                    e.printStackTrace();
                    System.out.println("failure");
                });
        ;
        System.out.printf("HTTP server started on port %d%n", PORT);
    }
}
