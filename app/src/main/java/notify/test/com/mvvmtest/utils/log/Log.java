package notify.test.com.mvvmtest.utils.log;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import notify.test.com.mvvmtest.BuildConfig;
import notify.test.com.mvvmtest.MyApplication;

public class Log implements Constant {

    private static final String TAG = Log.class.getSimpleName();

    //    private static boolean IS_LOG = true;
    private static boolean IS_LOG = BuildConfig.DEBUG;
    private static boolean IS_LOG_TO_SDCARD = BuildConfig.DEBUG;

    // 日志的输出格式
    private static SimpleDateFormat LOG_TEXT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.ENGLISH);

    private static Log mInstance;

    // bufferwriter 缓存大小 1B
    private static final int WRITER_BUFFER_SIZE = 1024;
    private static FileWriter mFileWriter = null;
    private static BufferedWriter mBufferedWriter = null;
    private static ExecutorService mSingleThreadExecutor = null;

    public static Log getInstanceLog() {
        if (mInstance == null) {
            synchronized (Log.class) {
                if (mInstance == null) {
                    mInstance = new Log();
                }
            }
        }
        return mInstance;
    }

    public void init() {
        String logFilePath = getLogDirectoryPath();

        mSingleThreadExecutor = Executors.newSingleThreadExecutor();
        SimpleDateFormat logFilePrefix = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        File logFile = new File(logFilePath + File.separator + logFilePrefix.format(new Date()) + "Log.txt");

        try {
            mFileWriter = new FileWriter(logFile, true);
            mBufferedWriter = new BufferedWriter(mFileWriter, WRITER_BUFFER_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIsLog(boolean isLog) {
        IS_LOG = isLog;
    }

    public void setIsLogToSdcard(boolean isLogToSdcard) {
        IS_LOG_TO_SDCARD = isLogToSdcard;
    }

    public static void d(String tag, String msg) {
        printLog(D, tag, msg);
        if (IS_LOG_TO_SDCARD) writeLogToSDCard(tag, msg, 'd');
    }

    public static void v(String tag, String msg) {
        printLog(V, tag, msg);
        if (IS_LOG_TO_SDCARD) writeLogToSDCard(tag, msg, 'v');
    }

    public static void i(String tag, String msg) {
        printLog(I, tag, msg);
        if (IS_LOG_TO_SDCARD) writeLogToSDCard(tag, msg, 'i');
    }

    public static void w(String tag, String msg) {
        printLog(W, tag, msg);
        if (IS_LOG_TO_SDCARD) writeLogToSDCard(tag, msg, 'w');
    }

    public static void e(String msg) {
        printLog(E, "hxl", msg);
        if (IS_LOG_TO_SDCARD) writeLogToSDCard("hxl", msg, 'e');
    }

    public static void e(Object object) {
        printLog(E, "hxl", objectToString(object));
        if (IS_LOG_TO_SDCARD) writeLogToSDCard("hxl", objectToString(object), 'e');
    }

    public static void e(String tag, String msg) {
        printLog(E, tag, msg);
        if (IS_LOG_TO_SDCARD) writeLogToSDCard(tag, msg, 'e');
    }

    public static void json(String tag, String jsonFormat) {
        printLog(JSON, tag, jsonFormat);
        if (IS_LOG_TO_SDCARD) writeLogToSDCard(tag, jsonFormat, 'e');
    }

    private static String[] wrapperContent(String tagStr, String objectMsg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int index = 5;
        String className = stackTrace[index].getFileName();
        String methodName = stackTrace[index].getMethodName();
        int lineNumber = stackTrace[index].getLineNumber();
        String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        String tag = (tagStr == null ? className : tagStr);
        String msg = (objectMsg == null) ? NULL_TIPS : objectMsg;
        String headString = "[ (" + className + ":" + lineNumber + ")#" + methodNameShort + " ] ";
        return new String[]{tag, msg, headString};
    }

    private static void printLog(int type, String tagStr, String objectMsg) {
        if (!IS_LOG) {
            return;
        }
        String[] contents = wrapperContent(tagStr, objectMsg);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
            case A:
                BaseLog.printDefault(type, tag, headString + msg);
                break;
            case JSON:
                JsonLog.printJson(tag, msg, headString);
                break;
        }
    }

    private static void writeLogToSDCard(String tag, String msg, char level) {
        Date nowTime = new Date();
        String contentTime = LOG_TEXT_FORMAT.format(nowTime);
        final String writeMsg = contentTime + "  " + level + "  " + tag + "  " + msg + "\n";
        if (mSingleThreadExecutor == null) {
            return;
        }
        mSingleThreadExecutor.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    mBufferedWriter.write(writeMsg);
                    mBufferedWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void flushWriterBuffers() {
        try {
            mBufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void flushAndCloseWriterBuffers() {
        try {
            mBufferedWriter.flush();
            mBufferedWriter.close();
            mFileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将对象转化为String
     *
     * @param object
     * @return
     */
    public static <T> String objectToString(T object) {
        if (object == null) {
            return "Object{object is null}";
        }
        if (object.toString().startsWith(object.getClass().getName() + "@")) {
            StringBuilder builder = new StringBuilder(object.getClass().getSimpleName() + " { ");
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                boolean flag = false;
                for (String type : types) {
                    if (field.getType().getName().equalsIgnoreCase(type)) {
                        flag = true;
                        Object value = null;
                        try {
                            value = field.get(object);
                        } catch (IllegalAccessException e) {
                            value = e;
                        } finally {
                            builder.append(String.format("%s=%s, ", field.getName(),
                                    value == null ? "null" : value.toString()));
                            break;
                        }
                    }
                }
                if (!flag) {
                    builder.append(String.format("%s=%s, ", field.getName(), "Object"));
                }
            }
            return builder.replace(builder.length() - 2, builder.length() - 1, " }").toString();
        } else {
            return object.toString();
        }
    }

    // 基本数据类型
    private final static String[] types = {"int", "java.lang.String", "boolean", "char",
            "float", "double", "long", "short", "byte"};


    /**
     * 获取日志文件夹中的最新日志
     *
     * @return String 文件名不是觉得路径
     */
    public String getLatestLogFilePath() {
        File logDirFile = new File(getLogDirectoryPath());
        String[] listLogFiles = logDirFile.list();
        long des = -1;
        String tempLogFile = "";
        Date nowtime = new Date();
        Date logFileTime;

        // 获取最新的日志文件
        for (String logFile : listLogFiles) {
            Log.d("LogFile", logFile);
            SimpleDateFormat logFilePrefix = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                logFileTime = logFilePrefix.parse(logFile.substring(0, 10));
            } catch (Exception e) {
                logFileTime = null;
            }

            if (logFileTime != null) {
                long tempDes = nowtime.getTime() - logFileTime.getTime();
                if (des == -1 || tempDes <= des) {
                    des = tempDes;
                    tempLogFile = logFile;
                }
            }
        }
        return tempLogFile;
    }

    /**
     * 获取日志文件存储路径
     *
     * @return String 日志文件存储路径
     */
    public String getLogDirectoryPath() {
        return getDiskCacheDir("Log").getAbsolutePath();
    }

    /**
     * 获取 文件路径
     *
     * @param uniqueName 目录名
     * @return
     */
    public static File getDiskCacheDir(String uniqueName) {
        // getExternalCacheDir().getPath() = /storage/emulated/0/Android/data/com.huika.o2o.android.xmdd.debug/cache
        // Environment.getExternalStorageDirectory().getPath() = /storage/emulated/0
        // getFilesDir().getPath() = /data/data/com.huika.o2o.android.xmdd.debug/files
        // getCacheDir().getPath() = /data/data/com.huika.o2o.android.xmdd.debug/cache
        // 获取sd上的 Pictures Download DCIM 等目录
        // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        // sd卡是否可用
        final boolean state = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        File cacheDir = MyApplication.getInstance().getExternalCacheDir();
        final String cachePath = state && cacheDir != null ? cacheDir.getAbsolutePath() :
                MyApplication.getInstance().getCacheDir().getPath();
        File file = new File(cachePath + File.separator + uniqueName);
        if (!file.mkdirs()) {
            Log.i(TAG, uniqueName + "文件夹 已创建");
        }
        return file;
    }
}
