package notify.test.com.mvvmtest.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import notify.test.com.mvvmtest.mvvm.model.api.Status;
import notify.test.com.mvvmtest.mvvm.model.bean.Weather;
import notify.test.com.mvvmtest.mvvm.viewmodel.base.BaseViewModel;

/**
 * Created by hxl on 2019/2/19
 */

public class WeatherViewModel extends BaseViewModel {

    public MutableLiveData<List<Weather>> weatherData = new MutableLiveData<>();
    public MutableLiveData<List<Weather>> newData = new MutableLiveData<>();
    private List<Weather> list = new ArrayList<>();
    private int page = 1;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
    }


    public void getWeather() {

        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        STATUS.set(Status.LOADING);

                    }

                    @Override
                    public void onNext(Long aLong) {

                        Weather weather = new Weather();
                        weather.setName("sasa");
                        weather.setUrl("qqqqq");
                        list.add(weather);
                        list.add(weather);
                        list.add(weather);
                        list.add(weather);
                        list.add(weather);
                        list.add(weather);
                        weatherData.setValue(list);
                        STATUS.set(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        STATUS.set(Status.ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
