package com.ningtaifu.homework03.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public class NettyHeaderResposeFilter implements HttpResponseFilter{

    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("NettyHeaderResposeFilter","hello NettyHeaderResposeFilter");
    }
}
