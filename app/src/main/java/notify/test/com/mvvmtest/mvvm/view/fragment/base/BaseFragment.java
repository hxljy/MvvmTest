package notify.test.com.mvvmtest.mvvm.view.fragment.base;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;

import notify.test.com.mvvmtest.mvvm.viewmodel.base.BaseViewModel;

/**
 * Created by hxl on 2019/2/19
 */

public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {


    private View mRootView;
    /**
     * ViewDataBinding
     */
    protected DB mBinding;
    /**
     * instance in subclass; 自动销毁
     */
    protected VM mViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mBinding = DataBindingUtil.inflate(inflater, setLayoutId(), container, false);
            mRootView = mBinding.getRoot();
            Class<VM> entityClass = (Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            mViewModel = ViewModelProviders.of(this).get(entityClass);
            getLifecycle().addObserver((LifecycleObserver) mViewModel);

            initView();
            initData();
        }
        return mRootView;
    }


    public abstract int setLayoutId();

    public abstract int initView();

    public abstract int initData();


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mBinding = null;
        this.mRootView = null;
        //移除LifecycleObserver
        if (mViewModel != null) {
            getLifecycle().removeObserver((LifecycleObserver) mViewModel);
        }
        this.mViewModel = null;
    }

}
