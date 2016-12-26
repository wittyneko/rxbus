package cn.wittyneko.rxbus;

import rx.subjects.ReplaySubject;
import rx.subjects.SerializedSubject;

/**
 * RxJava代替EventBus的方案
 * 缓存多个发送事件
 *
 * @author wittytutu
 * @create 2016-12-22
 **/
public class RxBusReplay extends AbsRxBus {

    private static volatile RxBusReplay rxBus;

    protected RxBusReplay() {
        subject = new SerializedSubject<>(ReplaySubject.create());
    }

    public static RxBusReplay getInstance() {
        if (rxBus == null) {
            synchronized (RxBusReplay.class) {
                if (rxBus == null) {
                    rxBus = new RxBusReplay();
                }
            }
        }
        return rxBus;
    }
}
