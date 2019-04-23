package notify.test.com.mvvmtest.mvvm.view.adapter;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;

import java.util.List;

import notify.test.com.mvvmtest.bean.ZhihuData;
import notify.test.com.mvvmtest.mvvm.view.adapter.base.BaseBindAdapter;
import notify.test.com.mvvmtest.mvvm.view.adapter.base.BaseBindHolder;

/**
 * Created by hxl on 2019/2/20
 */

public class ZhiHuAdapter extends BaseBindAdapter<ZhihuData.StoriesBean> {

    public ZhiHuAdapter(int layoutResId, @Nullable List<ZhihuData.StoriesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseBindHolder helper, ZhihuData.StoriesBean s) {
        ViewDataBinding binding=helper.getBinding();
        binding.setVariable(BR.data,s);
        binding.executePendingBindings();

    }
}
