package cn.wittyneko.rxbus;


/**
 * 事件总线消息
 *
 * @param <T>
 */
public class EventBusMsg<T> {

    //默认事件 CODE 和 URI
    public static final int EMPTY = 0;
    public static final String EMPTY_URI = "";

    public int code = EMPTY;
    public String uri = EMPTY_URI;
    public T content;

    public EventBusMsg() {
    }

    public EventBusMsg(int code) {
        this.code = code;
    }

    public EventBusMsg(String uri) {
        this.uri = uri;
    }

    public EventBusMsg(T content) {
        this.content = content;
    }

    public EventBusMsg(int code, String uri) {
        this.code = code;
        this.uri = uri;
    }

    public EventBusMsg(int code, T content) {
        this.code = code;
        this.content = content;
    }

    public EventBusMsg(String uri, T content) {
        this.uri = uri;
        this.content = content;
    }

    public EventBusMsg(int code, String uri, T content) {
        this.code = code;
        this.uri = uri;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public EventBusMsg<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public EventBusMsg<T> setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public T getContent() {
        return content;
    }

    public EventBusMsg<T> setContent(T content) {
        this.content = content;
        return this;
    }

}