package com.ningtaifu.homework03.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface HeaderRequstFilter {
    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);
}
