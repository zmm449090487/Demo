package api;

import bean.Demo;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    public String url = "https://www.wanandroid.com/";
    @GET("project/list/1/json?cid=294")
    Observable<Demo> getData();



    public String uploadingUrl = "http://yun918.cn/";
    @Multipart
    @POST("study/public/file_upload.php")
    Call<ResponseBody> getRetrofit(@Part("key")RequestBody requestBody, @Part MultipartBody.Part multipartBody);
}
