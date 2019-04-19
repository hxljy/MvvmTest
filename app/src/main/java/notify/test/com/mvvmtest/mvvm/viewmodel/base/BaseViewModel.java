package notify.test.com.mvvmtest.mvvm.viewmodel.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleObserver;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import notify.test.com.mvvmtest.mvvm.data.Status;

/**
 * Created by hxl on 2019/2/19
 */

public class BaseViewModel extends AndroidViewModel implements LifecycleObserver {

//    protected M mModel;

    /**
     * 数据请求状态
     */
    public final ObservableField<Status> STATUS = new ObservableField<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

//    public BaseViewModel(Application application, M model) {
//        super(application);
//        this.mModel = model;
//    }


}
