package cn.wittyneko.rxbus;

import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * RxJava代替EventBus的方案
 * 不缓存发送事件
 *
 * @author wittytutu
 * @create 2016-12-22
 **/
public class RxBus extends AbsRxBus {

    private static volatile RxBus rxBus;

    protected RxBus() {
        subject = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }
}
