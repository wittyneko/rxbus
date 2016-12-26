package cn.wittyneko.rxbus;

import android.util.Log;

import rx.functions.Action1;
import rx.plugins.RxJavaObservableExecutionHook;
import rx.plugins.RxJavaPlugins;

/**
 * RxBus默认订阅处理
 * Created by wittytutu on 16-12-22.
 */
public class EventNext implements Action1<EventBusMsg> {

    static final RxJavaObservableExecutionHook hook = RxJavaPlugins.getInstance().getObservableExecutionHook();

    private Action1<? super EventBusMsg> onNext;
    private Action1<Throwable> onError;

    public EventNext(Action1<? super EventBusMsg> onNext) {
        this.onNext = onNext;
    }

    public EventNext(Action1<? super EventBusMsg> onNext, Action1<Throwable> onError) {
        this.onNext = onNext;
        this.onError = onError;
    }

    @Override
    public void call(EventBusMsg events) {
        // 捕获异常处理
        // RxJava默认的异常处理会执行onError无法再接收消息
        try {
            onNext.call(events);
            //Log.e("next", " -> " + events.getUri() + events.getContent());
        } catch (Throwable e) {
            //Log.e("error", " -> " + events.getUri() + events.getContent());
            if (onError != null) {
                //e.fillInStackTrace();
                if (e != null)
                    onError.call(e);
                else
                    onError.call(new NullPointerException("RxBus onError"));
            } else {
                if (e != null)
                    hook.onSubscribeError(e);
                else
                    hook.onSubscribeError(new NullPointerException("RxBus onError"));
            }
        }
    }
}