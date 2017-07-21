package cn.com.luckytry.baselibrary.util.connection;

import java.io.IOException;

import cn.com.luckytry.baselibrary.util.LUtil;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 魏兴 on 2017/6/28.
 */

public class NetWorkUtil {

    private static NetWorkUtil intance;
    public ServicesInterface service;

    private void initRetrofit(){
       Retrofit retrofit = new Retrofit.Builder()
               //设置OKHttpClient,如果不设置会提供一个默认的
               .client(getOkHttpClient())
               //设置baseUrl
//               .baseUrl(Const.BASE_BRL)
               .baseUrl("http://192.168.167.234:8091/")
               .build();

        service = retrofit.create(ServicesInterface.class);

    }
    private NetWorkUtil(){
        initRetrofit();
    }
    public synchronized static NetWorkUtil getIntance(){

            if(intance == null){
                intance = new NetWorkUtil();
            }


        return intance;
    }

    public  void getData(final CallBack cal){
        Call<ResponseBody> call = service.getHtmlString();

        Call<ResponseBody> clone = call.clone();
        //异步请求
        clone.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccess()){
                    try {
                        cal.onResult(response.body().string());

                    } catch (IOException e) {
                        cal.onError(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
    private OkHttpClient getOkHttpClient() {
        //日志显示级别  已经是最高级别了
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BASIC;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

            @Override
            public void log(String message) {
                LUtil.e("MainRecyclerAdapter",message);
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        return httpClientBuilder.build();
    }
    public interface CallBack{
        String onResult(String result);
        void onError(Exception err);
    }

    public static ServicesInterface getService() {
        Retrofit retrofit = new Retrofit.Builder()
                //设置baseUrl
//               .baseUrl(Const.BASE_BRL)
                .baseUrl("http://192.168.167.234:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicesInterface services = retrofit.create(ServicesInterface.class);
        return services;
    }
}
