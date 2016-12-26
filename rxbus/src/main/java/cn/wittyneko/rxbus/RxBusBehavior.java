package cn.wittyneko.rxbus;

import rx.subjects.BehaviorSubject;
import rx.subjects.SerializedSubject;

/**
 * RxJava代替EventBus的方案
 * 缓存最近一个发送事件
 *
 * @author wittytutu
 * @create 2016-12-22
 **/
public class RxBusBehavior extends AbsRxBus {

    private static volatile RxBusBehavior rxBus;

    protected RxBusBehavior() {
        subject = new SerializedSubject<>(BehaviorSubject.create());
    }

    public static RxBusBehavior getInstance() {
        if (rxBus == null) {
            synchronized (RxBusBehavior.class) {
                if (rxBus == null) {
                    rxBus = new RxBusBehavior();
                }
            }
        }
        return rxBus;
    }
}
