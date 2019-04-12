package notify.test.com.mvvmtest.mvvm.view.activity;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import notify.test.com.mvvmtest.R;
import notify.test.com.mvvmtest.databinding.ActivityWeatherBinding;
import notify.test.com.mvvmtest.mvvm.model.bean.Weather;
import notify.test.com.mvvmtest.mvvm.view.activity.base.BaseActivity;
import notify.test.com.mvvmtest.mvvm.view.adapter.WeatherAdapter;
import notify.test.com.mvvmtest.mvvm.viewmodel.WeatherViewModel;

public class WeatherActivity extends BaseActivity<ActivityWeatherBinding, WeatherViewModel> {


    WeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_weather;
    }

    @Override
    public void initView() {
        mBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        weatherAdapter = new WeatherAdapter(R.layout.item_rv_weather, null);
        mBinding.rv.setAdapter(weatherAdapter);
    }

    @Override
    public void initData() {
        mViewModel.weatherData.observe(this, new Observer<List<Weather>>() {
            @Override
            public void onChanged(@Nullable List<Weather> weathers) {
                weatherAdapter.addData(weathers);
            }
        });
        mViewModel.newData.observe(this, new Observer<List<Weather>>() {
            @Override
            public void onChanged(@Nullable List<Weather> weathers) {
                weatherAdapter.setNewData(weathers);
            }
        });


        mViewModel.getWeather();

    }
}
