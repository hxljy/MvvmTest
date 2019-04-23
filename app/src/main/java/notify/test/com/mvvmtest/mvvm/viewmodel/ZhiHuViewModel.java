package notify.test.com.mvvmtest.mvvm.viewmodel;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.databinding.ObservableInt;
import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import notify.test.com.mvvmtest.bean.ZhihuData;
import notify.test.com.mvvmtest.mvvm.data.DataRepository;
import notify.test.com.mvvmtest.mvvm.data.Status;
import notify.test.com.mvvmtest.mvvm.viewmodel.base.BaseViewModel;
import notify.test.com.mvvmtest.utils.log.Log;

/**
 * Created by hxl on 2019/2/19
 */

public class ZhiHuViewModel extends BaseViewModel {

    //    private int index = 20190301;
    public ObservableInt index = new ObservableInt(20190301);


    private MutableLiveData<List<ZhihuData.StoriesBean>> zhihuList = new MutableLiveData<>();
//    public MutableLiveData<List<Weather>> newData = new MutableLiveData<>();
//    private List<Weather> list = new ArrayList<>();
//    private int page = 1;

    public ZhiHuViewModel(@NonNull Application application) {
        super(application);
    }


    public void getData(final boolean isRefresh) {
        Log.e("getData-" + index.get());
        DataRepository.getZhiHuList(index.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();
                        ZhihuData zhihuData = gson.fromJson(s, ZhihuData.class);

                        //为空
                        if (zhihuData.getStories().size() == 0 && index.get() == 20190301) {
                            getStatus().setValue(new Status(Status.State.EMPTY));
                            return;
                        }
                        if (isRefresh) {
                            getStatus().setValue(new Status(Status.State.REFRESH));
                        }
                        zhihuList.setValue(zhihuData.getStories());
                        //是否到最后
                        if (index.get() == 20190310) {
                            getStatus().setValue(new Status(Status.State.SUCCESS, true));
                        } else {
                            getStatus().setValue(new Status(Status.State.SUCCESS, false));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getStatus().setValue(new Status(Status.State.ERROR, "错误了"));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void refresh() {
        index.set(20190301);
        getData(true);
    }

    public void loadMore() {
        index.set(index.get() + 1);
        getData(false);
    }


    public MutableLiveData<List<ZhihuData.StoriesBean>> getZhihuList() {
        return zhihuList;
    }
}
