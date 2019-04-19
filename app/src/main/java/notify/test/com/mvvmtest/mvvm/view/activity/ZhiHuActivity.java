package notify.test.com.mvvmtest.mvvm.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import notify.test.com.mvvmtest.R;
import notify.test.com.mvvmtest.bean.ZhihuData;
import notify.test.com.mvvmtest.databinding.ActivityWeatherBinding;
import notify.test.com.mvvmtest.mvvm.view.activity.base.BaseActivity;
import notify.test.com.mvvmtest.mvvm.view.adapter.ZhiHuAdapter;
import notify.test.com.mvvmtest.mvvm.viewmodel.ZhiHuViewModel;
import notify.test.com.mvvmtest.utils.log.Log;

public class ZhiHuActivity extends BaseActivity<ActivityWeatherBinding, ZhiHuViewModel> {


    private ZhiHuAdapter zhiHuAdapter;
    private View errorView, emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_weather;
    }

    @Override
    public ViewModelProvider.Factory setFactory() {
        return null;
    }

    @Override
    public void initView() {
        mBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        zhiHuAdapter = new ZhiHuAdapter(R.layout.item_rv_weather, null);
        mBinding.rv.setAdapter(zhiHuAdapter);

        emptyView = getLayoutInflater().inflate(R.layout.layout_empty_view, (ViewGroup) mBinding.rv.getParent(), false);
        errorView = getLayoutInflater().inflate(R.layout.tips_loading_failed, (ViewGroup) mBinding.rv.getParent(), false);
        errorView.findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        zhiHuAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mViewModel.loadMore();
            }
        }, mBinding.rv);
        mBinding.srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                zhiHuAdapter.setEnableLoadMore(false);
                mViewModel.refresh();
            }
        });
    }

    @Override
    public void initData() {
        mViewModel.getZhihuList().observe(this, new Observer<List<ZhihuData.StoriesBean>>() {
            @Override
            public void onChanged(@Nullable List<ZhihuData.StoriesBean> storiesBeans) {
                zhiHuAdapter.addData(storiesBeans);
            }
        });
        mViewModel.STATUS.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Log.e(mViewModel.STATUS.get());
            }
        });

        mBinding.srl.setRefreshing(true);
        mViewModel.getData();
    }
}
