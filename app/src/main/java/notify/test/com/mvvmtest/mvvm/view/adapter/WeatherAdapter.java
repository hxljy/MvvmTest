package notify.test.com.mvvmtest.mvvm.view.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;

import com.android.databinding.library.baseAdapters.BR;

import java.util.List;

import notify.test.com.mvvmtest.mvvm.model.bean.Weather;
import notify.test.com.mvvmtest.mvvm.view.adapter.base.BaseBindAdapter;
import notify.test.com.mvvmtest.mvvm.view.adapter.base.BaseBindHolder;

/**
 * Created by hxl on 2019/2/20
 */

public class WeatherAdapter extends BaseBindAdapter<Weather> {

    public WeatherAdapter(int layoutResId, @Nullable List<Weather> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseBindHolder helper, Weather weather) {
        ViewDataBinding binding=helper.getBinding();
        binding.setVariable(BR.weather,weather);
        binding.executePendingBindings();


    }
}
