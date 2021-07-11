package com.ningtaifu.homework03.inbound;

import com.ningtaifu.homework02.OkHttpUtils;
import com.ningtaifu.homework03.filter.HeaderRequstFilter;
import com.ningtaifu.homework03.filter.HttpResponseFilter;
import com.ningtaifu.homework03.filter.NettyHeaderRequstFilter;
import com.ningtaifu.homework03.filter.NettyHeaderResposeFilter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

import java.util.HashMap;

public class NettyOkhttpHandle extends ChannelInboundHandlerAdapter {


//    private List<String> hettpUrl=new ArrayList<>();
    private String okHttpUrl="http://127.0.0.1:8080";
    HeaderRequstFilter filterRequst=new NettyHeaderRequstFilter();
    HttpResponseFilter filterRespose=new NettyHeaderResposeFilter();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            //logger.info("channelRead流量接口请求开始，时间为{}", startTime);
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String uri = fullRequest.uri();
            //请求参数的 过滤器
            filterRequst.filter(fullRequest,ctx);
            //logger.info("接收到的请求url为{}", uri);
            if (uri.contains("/test")) {
                handlerTest(fullRequest, ctx,"url: test");
            } else {
                handlerTest(fullRequest, ctx,"url:others");
            }

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void handlerTest(FullHttpRequest fullRequest, ChannelHandlerContext ctx,String msg) {
        FullHttpResponse response = null;
        try {
            //过滤器 加入的key
            String keyHeader1="NettyHeaderRequstFilter";
            String keyHeader1Value= fullRequest.headers().get(keyHeader1);
            HashMap<String,String> hashMapHeader=new HashMap<>();
            hashMapHeader.put(keyHeader1,keyHeader1Value);

            String value = OkHttpUtils.getAsString(okHttpUrl,hashMapHeader);// body; // 对接上次作业的httpclient或者okhttp请求另一个url的响应数据
            //可以加入路由器

            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
            //返回数据的 过滤器
            filterRespose.filter(response);
            response.headers().set("msg",msg);
        } catch (Exception e) {
            System.out.println("处理出错:"+e.getMessage());
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderNames.KEEP_ALIVE);
                    ctx.write(response);
                }
                ctx.flush();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
