package cn.wittyneko.rxbus;

import rx.functions.Func1;

/**
 * RxBus默认事件过滤处理
 * Created by wittytutu on 16-12-22.
 */

public class EventFilter implements Func1<EventBusMsg, Boolean> {

    int code = EventBusMsg.EMPTY;
    String uri = EventBusMsg.EMPTY_URI;

    public EventFilter(int code, String uri) {
        this.code = code;
        this.uri = uri;
    }

    @Override
    public Boolean call(EventBusMsg events) {
        //Log.e(getClass().getName(), "event:"+ events.code + "," + events.uri);
        //Log.e(getClass().getName(), "filter:"+ event + "," + uri);
        return events.code == code && events.uri.equals(uri);
    }
}
