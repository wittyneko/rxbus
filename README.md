### 依懒库

```gradle
//RxJava
compile 'io.reactivex:rxjava:1.1.6'
compile 'io.reactivex:rxandroid:1.2.1'
//RxLifecycle管理Rx的生命周期
compile 'com.trello:rxlifecycle:0.6.1'
compile 'com.trello:rxlifecycle-components:0.6.1'
//compile 'com.trello:rxlifecycle-kotlin:0.6.1'
```

### 调用
[JitPack](https://jitpack.io/)导包方式：
```
compile 'com.github.wittyneko:rxbus:0.1.0'
```
[![](https://jitpack.io/v/wittyneko/rxbus.svg)](https://jitpack.io/#wittyneko/rxbus) [rxbus:0.1.0](https://jitpack.io/#wittyneko/rxbus/0.1.0)


发送消息
```java
RxBus.getInstance().send("main", "来自Activity数据");
```
接收消息
```java
RxBus.getInstance().with(this)
        .setCode(EventBusMsg.EMPTY) //过滤接收code
        .setUri("main") //过滤接收uri
        .observeOn(AndroidSchedulers.mainThread())
        .onNext(new Action1<EventBusMsg<String>>() {
            @Override
            public void call(EventBusMsg<String> msg) {
                 mBinding.mainTv.setText(msg.getContent());
                 Toast.makeText(getApplication(), "收到：" + msg.getUri() + "，消息：" + msg.getContent(), Toast.LENGTH_SHORT).show();
            }
       })
```
发送消息有多个实现，对应的接收通过`setCode`和`setUri`来进行过滤，默认code为0，uri为空字符串。

![send](http://upload-images.jianshu.io/upload_images/1845254-ff832aa74b7a4965.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 实现

实现很简单，无非就是建立可观察对象单例，接收的地方订阅消息接收。

![info](http://upload-images.jianshu.io/upload_images/1845254-6afe4f9183bcb19f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

具体的注释有说明，这里进行简单介绍。
- `AbsRxBus` 是抽象的实现方式，发送和接收的方法在这里实现。
- `RxBus`、`RxBusBehavior`和`RxBusReplsy`是具体的单例实现，区别在于没有订阅者消息缓存方式。
- `EventBusMsg` 消息对象，发送的消息最终会以一个EventBusMsg发送出去。
- `EventFilter` 消息过滤机制，过滤接收消息，如果想加入更多判断可修改此处实现。
- `EventNext` 消息接收，这里主要是为了防止catch后无法接收消息，没有什么特殊。
- `EventObservable` RxBus的接口定义
- `EventSubscribeBuilder` 构建订阅者，为了方便调用封装RxJava调用逻辑。
