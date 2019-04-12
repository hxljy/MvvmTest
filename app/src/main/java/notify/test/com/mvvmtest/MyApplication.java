package notify.test.com.mvvmtest;

import android.app.Application;
import android.content.Context;

import notify.test.com.mvvmtest.utils.log.Log;


public class MyApplication extends Application {

    private static Context mContext;
    private static MyApplication instance;
    //控制日志打印
    public boolean mIsDebugMode = BuildConfig.DEBUG;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = MyApplication.this;
        mContext = getApplicationContext();
        initLog();
    }


    private void initLog() {
        // 设置日志开关
        Log.getInstanceLog().init();
        Log.getInstanceLog().setIsLog(mIsDebugMode);
        Log.getInstanceLog().setIsLogToSdcard(false);
    }

    public static Context getContext() {
        return mContext;
    }

    public static MyApplication getInstance() {
        return MyApplication.instance;
    }


}
