package cn.wittyneko.rxbus.sample;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import cn.wittyneko.rxbus.EventBusMsg;
import cn.wittyneko.rxbus.RxBus;
import cn.wittyneko.rxbus.sample.databinding.ActivityMainBinding;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends RxAppCompatActivity {

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
        initListener();
    }

    private void initView(){
        mBinding.mainBtn.setText(getClass().getSimpleName() + " send");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(mBinding.frm1.getId(), Fragment1.newInstance(null, null), "frm1");
        transaction.add(mBinding.frm2.getId(), Fragment2.newInstance(null, null), "frm2");
        transaction.commit();
    }

    private void initListener(){

        mBinding.mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 发送消息
                RxBus.getInstance().send("main", "来自Activity数据");
            }
        });

        // 接收MainActivity消息
        RxBus.getInstance().with(this)
                .setUri("main")
                .observeOn(AndroidSchedulers.mainThread())
                .onNext(new Action1<EventBusMsg<String>>() {
                    @Override
                    public void call(EventBusMsg<String> msg) {
                        mBinding.mainTv.setText(msg.getContent());
                        Toast.makeText(getApplication(), "收到：" + msg.getUri() + "，消息：" + msg.getContent(), Toast.LENGTH_SHORT).show();
                    }
                })
                .create();

        // 接收Blank1Fragment消息
        RxBus.getInstance().with(this)
                .setUri("frm1")
                .observeOn(AndroidSchedulers.mainThread())
                .onNext(new Action1<EventBusMsg<String>>() {
                    @Override
                    public void call(EventBusMsg<String> msg) {
                        mBinding.mainTv.setText(msg.getContent());
                        Toast.makeText(getApplication(), "收到：" + msg.getUri() + "，消息：" + msg.getContent(), Toast.LENGTH_SHORT).show();
                    }
                })
                .create();

        // 接收Blank2Fragment消息
        RxBus.getInstance().with(this)
                .setUri("frm2")
                .observeOn(AndroidSchedulers.mainThread())
                .onNext(new Action1<EventBusMsg<String>>() {
                    @Override
                    public void call(EventBusMsg<String> msg) {
                        mBinding.mainTv.setText(msg.getContent());
                        Toast.makeText(getApplication(), "收到：" + msg.getUri() + "，消息：" + msg.getContent(), Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
    }
}
