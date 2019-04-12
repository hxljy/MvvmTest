package notify.test.com.mvvmtest.mvvm.view.activity.base;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.ParameterizedType;

import notify.test.com.mvvmtest.mvvm.viewmodel.base.BaseViewModel;

/**
 * Created by hxl on 2019/2/19
 */

public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {

    /**
     * ViewDataBinding
     */
    protected DB mBinding;

    /**
     * instance in subclass; 自动销毁
     */
    protected VM mViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置DataBinding
        mBinding = DataBindingUtil.setContentView(this, setLayoutId());
        Class<VM> entityClass = (Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        mViewModel = ViewModelProviders.of(this).get(entityClass);
        getLifecycle().addObserver((LifecycleObserver) mViewModel);

        initView();
        initData();
    }

    public abstract int setLayoutId();
    public abstract void initView();
    public abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mBinding = null;
        //移除LifecycleObserver
        if (mViewModel != null) {
            getLifecycle().removeObserver((LifecycleObserver) mViewModel);
        }
        this.mViewModel = null;
    }
}
