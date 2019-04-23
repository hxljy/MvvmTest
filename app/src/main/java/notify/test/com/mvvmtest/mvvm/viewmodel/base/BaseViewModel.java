package notify.test.com.mvvmtest.mvvm.viewmodel.base;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import notify.test.com.mvvmtest.mvvm.data.Status;

/**
 * Created by hxl on 2019/2/19
 */

public class BaseViewModel extends AndroidViewModel implements LifecycleObserver {

//    protected M mModel;

    /**
     * 数据请求状态
     */
//    public final ObservableField<Status> STATUS = new ObservableField<>();
    private MutableLiveData<Status> status = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    //    public BaseViewModel(Application application, M model) {
//        super(application);
//        this.mModel = model;
//    }


}
