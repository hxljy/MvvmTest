package notify.test.com.mvvmtest.mvvm.data;

/**
 * 数据请求状态
 */
public class Status {
    private State state;
    private Object msg;

    public Status(State state) {
        this.state = state;
    }

    public Status(State state, Object msg) {
        this.state = state;
        this.msg = msg;
    }

    public State getState() {
        return state;
    }

    public Object getMsg() {
        return msg;
    }

    public enum State {
        /**
         * 正在加载中
         */
        LOADING,

        /**
         * 加载成功
         */
        SUCCESS,

        /**
         * 内容为空
         */
        EMPTY,

        /**
         * 加载失败
         */
        ERROR,
        
        /**
         * 刷新
         */
        REFRESH,

    }
}
