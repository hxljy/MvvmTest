package notify.test.com.mvvmtest.mvvm.model.api.helper;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import notify.test.com.mvvmtest.utils.log.Log;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by hxl on 2017/1/6
 */

class OkHttpClientHelper {
    private OkHttpClient mClient;
    private final static long TIMEOUT = 10;  //超时时间

    private static OkHttpClientHelper clientHelper;

    public static OkHttpClientHelper getInstance() {
        if (clientHelper == null) {
            synchronized (OkHttpClientHelper.class) {
                if (clientHelper == null) {
                    clientHelper = new OkHttpClientHelper();
                }
            }
        }
        return clientHelper;
    }


    //获取OKHttpClicent对象
    OkHttpClient getOkHttpClient() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        if (mClient == null) {
            mClient = new OkHttpClient.Builder()
//                    .addInterceptor(new HeaderInterceptor())
//                    .addInterceptor(interceptor)
                    .addInterceptor(new LoggingInterceptor())
                    //设置请求读写的超时时间
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
//                    .retryOnConnectionFailure(true)
                    .build();
        }
        return mClient;
    }



    private class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Log.i("hxl【发送请求】", "-----------------------------------请求开始-----------------------------------");
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();

            long t1 = System.nanoTime();//请求发起的时间

            String method = request.method();
            if ("POST".equals(method)) {
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                    Log.i("hxl【发送请求】", String.format("%s 发送请求 %s on %s%n%s %nRequestParams:{%s}",
                            method, request.url(), chain.connection(), request.headers(), sb.toString()));
                }
            } else {
                Log.i("hxl【发送请求】", String.format("%s 发送请求 %s on %s%n%s", method, request.url(), chain.connection(), request.headers()));
            }


            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间

            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024 * 1024);
            String value = responseBody.string();


            //log过多 换行输出
            int strLength = value.length();
            int start = 0;
            int LOG_MAXLENGTH = 2000;
            int end = LOG_MAXLENGTH;
            Log.i("hxl【接收响应】", String.format("【状态码】 %s %s", response.code(), request.url()));
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.i("hxl【接收响应】", String.format("【返回json】 %s【返回json】",
                            value.substring(start, end)));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.i("hxl【接收响应】", String.format("【返回json】 %s【返回json结束】%n响应时间 %.1fms",
                            value.substring(start, strLength), (t2 - t1) / 1e6d));
                    break;
                }
            }
            Log.i("hxl【接收响应】", "-----------------------------------请求结束-----------------------------------");
            return response;
        }
    }
}
