package org.practice;

import io.vertx.core.Future;
import io.vertx.core.net.HostAndPort;
import io.vertx.httpproxy.ProxyContext;
import io.vertx.httpproxy.ProxyInterceptor;
import io.vertx.httpproxy.ProxyResponse;

public class SetAuthorityInterceptor implements ProxyInterceptor {
    private String authority;
    int port;

    public SetAuthorityInterceptor(int port, String host) {
       authority = host;
       this.port = port;
    }

    @Override
    public Future<ProxyResponse> handleProxyRequest(ProxyContext context) {
        context.request().setAuthority(HostAndPort.authority(authority, port));
        return context.sendRequest();
    }
}