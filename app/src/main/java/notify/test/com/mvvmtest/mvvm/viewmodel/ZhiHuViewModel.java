package notify.test.com.mvvmtest.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

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

/**
 * Created by hxl on 2019/2/19
 */

public class ZhiHuViewModel extends BaseViewModel {

    int index = 20190301;

    private MutableLiveData<List<ZhihuData.StoriesBean>> zhihuList = new MutableLiveData<>();
//    public MutableLiveData<List<Weather>> newData = new MutableLiveData<>();
//    private List<Weather> list = new ArrayList<>();
//    private int page = 1;

    public ZhiHuViewModel(@NonNull Application application) {
        super(application);
    }


    public void getData() {
        DataRepository.getZhiHuList(index)
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

                        if ((zhihuData.getStories() == null || zhihuData.getStories().size() == 0) && index == 20190301) {

                        }
                        zhihuList.setValue(zhihuData.getStories());
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

    public void refresh() {
        index = 20190301;
        getData();
    }

    public void loadMore() {
        index++;
        getData();
    }


    public MutableLiveData<List<ZhihuData.StoriesBean>> getZhihuList() {
        return zhihuList;
    }
}
