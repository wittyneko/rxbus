package cn.wittyneko.rxbus.sample;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import cn.wittyneko.rxbus.EventBusMsg;
import cn.wittyneko.rxbus.RxBus;
import cn.wittyneko.rxbus.sample.databinding.FragmentBlankBinding;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends RxFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FragmentBlankBinding mBinding;


    public Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_blank, container, false);
        initView();
        initListener();
        return mBinding.getRoot();
    }

    private void initView(){
        mBinding.sendBtn.setText(getClass().getSimpleName() + " send");
    }

    private void initListener(){

        // 接收MainActivity消息
        RxBus.getInstance().with(this)
                .setUri("main")
                .observeOn(AndroidSchedulers.mainThread())
                .onNext(new Action1<EventBusMsg<String>>() {
                    @Override
                    public void call(EventBusMsg<String> msg) {
                        mBinding.msgTv.setText(msg.getContent());
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
                        mBinding.msgTv.setText(msg.getContent());
                    }
                })
                .create();

        mBinding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 发送消息
                RxBus.getInstance().send("frm2", "来自Fragment2数据");
            }
        });
    }

}
