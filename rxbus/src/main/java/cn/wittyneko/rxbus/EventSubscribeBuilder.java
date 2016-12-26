package cn.wittyneko.rxbus;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 订阅者构建
 * Created by wittytutu on 16-12-22.
 */

public class EventSubscribeBuilder {

    private EventObservable mRxBus;

    private FragmentLifecycleProvider mFragLifecycleProvider;
    private ActivityLifecycleProvider mActLifecycleProvider;
    private FragmentEvent mFragmentEndEvent;
    private ActivityEvent mActivityEndEvent;

    private int code = EventBusMsg.EMPTY;
    private String uri = EventBusMsg.EMPTY_URI;

    private Scheduler observeOn = Schedulers.immediate();
    private Action1<? super EventBusMsg> onNext;
    private Action1<Throwable> onError;

    public EventSubscribeBuilder(EventObservable rxBus, FragmentLifecycleProvider provider) {
        this.mRxBus = rxBus;
        this.mFragLifecycleProvider = provider;
    }

    public EventSubscribeBuilder(EventObservable rxBus, ActivityLifecycleProvider provider) {
        this.mRxBus = rxBus;
        this.mActLifecycleProvider = provider;
    }

    public EventSubscribeBuilder setCode(int code) {
        this.code = code;
        return this;
    }

    public EventSubscribeBuilder setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public EventSubscribeBuilder setEndEvent(FragmentEvent event) {
        this.mFragmentEndEvent = event;
        return this;
    }

    public EventSubscribeBuilder setEndEvent(ActivityEvent event) {
        this.mActivityEndEvent = event;
        return this;
    }

    public EventSubscribeBuilder observeOn(Scheduler observeOn) {
        this.observeOn = observeOn;
        return this;
    }

    /**
     * 接受消息
     *
     * @param action
     * @param <T>
     * @return
     */
    public <T extends EventBusMsg> EventSubscribeBuilder onNext(Action1<T> action) {
        this.onNext = (Action1<EventBusMsg>) action;
        return this;
    }

    public EventSubscribeBuilder onError(Action1<Throwable> action) {
        this.onError = action;
        return this;
    }

    public Observable<EventBusMsg> toObservable() {
        Observable<EventBusMsg> eventObservable = null;

        if (mActLifecycleProvider != null && mRxBus != null) {
            eventObservable = mRxBus.toObservable()
                    .compose(mActivityEndEvent == null ?
                            mActLifecycleProvider.<EventBusMsg>bindToLifecycle() : // 绑定生命周期
                            mActLifecycleProvider.<EventBusMsg>bindUntilEvent(mActivityEndEvent))
                    .filter(new EventFilter(code, uri)) //过滤 根据 code 和 uri 判断返回事件
                    .observeOn(observeOn);
        } else if (mFragLifecycleProvider != null && mRxBus != null) {
            eventObservable = mRxBus.toObservable()
                    .compose(mFragmentEndEvent == null ?
                            mFragLifecycleProvider.<EventBusMsg>bindToLifecycle() :
                            mFragLifecycleProvider.<EventBusMsg>bindUntilEvent(mFragmentEndEvent))
                    .filter(new EventFilter(code, uri))
                    .observeOn(observeOn);
        }
        return eventObservable;
    }

    public void create() {
        toObservable().subscribe(new EventNext(onNext, onError));
    }
}
