package com.ningtaifu.homework02;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OkHttpUtils {

    // 缓存客户端实例
    public static OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1,TimeUnit.SECONDS)
            .build();

    // GET 调用

    /**
     *
     * @param url
     * @param hashMapHeader  用于接收Header 参数
     * @return
     * @throws IOException
     */
    public static String getAsString(String url, HashMap<String,String> hashMapHeader) throws IOException {
        Request.Builder requestBuilder= new Request.Builder();
       //加入过滤条件
        if (hashMapHeader!=null){
            for (String keyString :hashMapHeader.keySet()){
                requestBuilder.addHeader(keyString,hashMapHeader.get(keyString));
            }
        }
        Request request = requestBuilder.url(url)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String data = response.body().string();
            return data;
        } catch (Exception exception) {
            return "请求失败！";
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        String url = "http://localhost:8080";
        String url = "http://127.0.0.1:8808";//访问api 网关
        HashMap<String,String> hashMapHeader=new HashMap<>();
        hashMapHeader.put("token","1111");
        String text = OkHttpUtils.getAsString(url,hashMapHeader);
        System.out.println("url: " + url + " ; response: \n" + text);
        // 清理资源
        OkHttpUtils.client = null;
    }

}
