package notify.test.com.mvvmtest.mvvm.data;


import io.reactivex.Observable;
import notify.test.com.mvvmtest.mvvm.data.helper.RetrofitHelper;
import retrofit2.Retrofit;

/**
 * created by hxl on 2017/9/6
 * -
 */
public class DataRepository {

    private static final ApiService apiService = createService(ApiService.class);

    private static <T> T createService(Class<T> c) {
        Retrofit mRetrofit = RetrofitHelper.getInstance().getRetrofit();
        return mRetrofit.create(c);
    }


    public static Observable<String> getZhiHuList(int date) {
        return apiService.getTheDaily(date);
    }









}
