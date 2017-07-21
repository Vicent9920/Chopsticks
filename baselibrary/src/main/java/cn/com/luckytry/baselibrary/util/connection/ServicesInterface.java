package cn.com.luckytry.baselibrary.util.connection;

import cn.com.luckytry.baselibrary.mould.PassWord;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by 魏兴 on 2017/6/28.
 */

public interface ServicesInterface {
    @GET("?utm_campaign=baidu&utm_medium=organic&utm_source=baidu&utm_content=homepage&utm_term=")
    Call<ResponseBody> getHtmlString();

    @FormUrlEncoded
    @POST("co/key.do")
    Call<PassWord> getPassWord(@Field("id") int id);
}
