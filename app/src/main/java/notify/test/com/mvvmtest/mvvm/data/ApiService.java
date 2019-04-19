package notify.test.com.mvvmtest.mvvm.data;


import io.reactivex.Observable;
import notify.test.com.mvvmtest.bean.ZhihuData;
import notify.test.com.mvvmtest.bean.ZhihuStoryDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/9/6.
 * -
 */

public interface ApiService {

    @GET("api/4/news/latest")
    Call<ZhihuData> getLatestNews();

    @GET("/api/4/news/before/{date}")
    Observable<String> getTheDaily(@Path("date") int date);

    @GET("api/4/news/{id}")
    Call<ZhihuStoryDetail> getZhiHuStoryDetail(@Path("id") String id);

}
