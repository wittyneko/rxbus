package cn.wittyneko.rxbus;

import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import rx.Observable;
import rx.subjects.Subject;

/**
 * RxJava代替EventBus的方案
 *
 * @author wittytutu
 * @create 2016-12-22
 **/
public abstract class AbsRxBus implements EventObservable {

    // PublishSubject 不缓存发送事件,
    // BehaviorSubject 缓存最近一个发送事件,
    // ReplaySubject 缓存多个发送事件
    protected Subject subject;

    public <T> void send(int code, T content) {
        send(code, EventBusMsg.EMPTY_URI, content);
    }

    public <T> void send(String uri, T content) {
        send(EventBusMsg.EMPTY, uri, content);
    }

    /**
     * 发送消息
     *
     * @param code    过滤编码
     * @param uri     过滤 uri
     * @param content 内容
     * @param <T>
     */
    public <T> void send(int code, String uri, T content) {
        EventBusMsg<T> event = new EventBusMsg<>();
        event.code = code;
        event.uri = uri;
        event.content = content;
        send(event);
    }

    /**
     * 发送消息
     *
     * @param o
     */
    public void send(EventBusMsg<?> o) {
        subject.onNext(o);
    }

    /**
     * 获取可订阅者
     *
     * @return
     */
    @Override
    public Observable<EventBusMsg> toObservable() {
        return toObservable(EventBusMsg.class);
    }

    @Override
    public <T> Observable<T> toObservable(Class<T> type) {
        return subject.ofType(type);
    }

    /**
     * 获取订阅者构建接收消息
     *
     * @param provider
     * @return
     */
    public EventSubscribeBuilder with(ActivityLifecycleProvider provider) {
        return new EventSubscribeBuilder(this, provider);
    }

    public EventSubscribeBuilder with(FragmentLifecycleProvider provider) {
        return new EventSubscribeBuilder(this, provider);
    }
}
