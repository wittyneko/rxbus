package cn.wittyneko.rxbus;

import rx.Observable;

/**
 * RxBus接口
 * Created by wittytutu on 16-12-22.
 */

public interface EventObservable {

    Observable<EventBusMsg> toObservable();

    <T> Observable<T> toObservable(Class<T> type);
}
