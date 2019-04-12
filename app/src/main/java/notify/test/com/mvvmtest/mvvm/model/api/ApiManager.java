package notify.test.com.mvvmtest.mvvm.model.api;


import notify.test.com.mvvmtest.mvvm.model.api.helper.RetrofitHelper;
import retrofit2.Retrofit;

/**
 * created by hxl on 2017/9/6
 * -
 */
public class ApiManager {

    private static final ApiService apiService = createService(ApiService.class);

    private static <T> T createService(Class<T> c) {
        Retrofit mRetrofit = RetrofitHelper.getInstance().getRetrofit();
        return mRetrofit.create(c);
    }


}
