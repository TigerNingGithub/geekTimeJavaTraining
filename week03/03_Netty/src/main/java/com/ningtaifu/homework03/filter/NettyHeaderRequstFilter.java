package com.ningtaifu.homework03.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class NettyHeaderRequstFilter implements HeaderRequstFilter{
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("NettyHeaderRequstFilter", "hello NettyHeaderRequstFilter");
    }
}
