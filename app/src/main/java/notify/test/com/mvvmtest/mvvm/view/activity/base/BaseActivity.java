package notify.test.com.mvvmtest.mvvm.view.activity.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

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
        mViewModel = ViewModelProviders.of(this, setFactory()).get(entityClass);//viewmodel与view的lifecycle绑定起来
        getLifecycle().addObserver((LifecycleObserver) mViewModel);//viewmodel获取view的lifecycle

        initView();
        initData();
    }

    public abstract int setLayoutId();

    public abstract ViewModelProvider.Factory setFactory();

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
