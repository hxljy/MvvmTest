package notify.test.com.mvvmtest.mvvm.model;

import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by hxl on 2019/2/19
 */

public class WeatherModel {

    private MutableLiveData<List<String>> weather;




    public MutableLiveData<List<String>> getWeather() {
        if (weather == null) {
            // TODO: 2017/11/16 Cache
            weather = new MutableLiveData<>();
        }
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        List<String> list=new ArrayList<>();
                        list.add("111");
                        list.add("222");
                        list.add("333");
                        list.add("444");
                        list.add("555");
                        list.add("111");
                        list.add("222");
                        list.add("333");
                        list.add("444");
                        list.add("555");
                        weather.setValue(list);
                    }
                });

        return weather;
    }
}
