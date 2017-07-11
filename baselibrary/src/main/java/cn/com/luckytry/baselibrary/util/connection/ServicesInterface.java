package cn.com.luckytry.baselibrary.util.connection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by 魏兴 on 2017/6/28.
 */

public interface ServicesInterface {
    @GET("?utm_campaign=baidu&utm_medium=organic&utm_source=baidu&utm_content=homepage&utm_term=")
    Call<ResponseBody> getHtmlString();
}
