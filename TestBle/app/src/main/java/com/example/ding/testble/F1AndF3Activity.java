package com.example.ding.testble;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ding.testble.agreementholp.F1Holper;
import com.example.ding.testble.agreementholp.F3Holper;

import java.util.ArrayList;

public class F1AndF3Activity extends AppCompatActivity {
    private Button mOpenButton;
    private Button mCloseButton;
    private Button mJinRu;
    private Button mF3OpenButton;
    private Button mF3CloseButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f1);
        ScreenManager.getScreenManager().pushActivity(this);
        initView();
        initData();
    }



    private void initView() {
        mOpenButton = (Button)findViewById(R.id.f1_and_f3_open_button);
        mCloseButton = (Button)findViewById(R.id.f1_and_f3_close_button);
        mJinRu = (Button)findViewById(R.id.f1_and_f3_jinru);
        mF3OpenButton = (Button)findViewById(R.id.f1_and_f3_open_light_button);
        mF3CloseButton = (Button)findViewById(R.id.f1_and_f3_close_light_button);
        mF3OpenButton.setEnabled(false);
        mF3CloseButton.setEnabled(false);
    }
    private void initData() {
        mOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F1Holper f1Holper = new F1Holper(2, F1AndF3Activity.this, new F1Holper.F1CallBack() {
                    @Override
                    public void result(boolean res) {
                        if(res){
                            ToastUtil.showShortToast("开锁成功");
                        }else {
                            ToastUtil.showShortToast("开锁失败");
                        }
                    }
                });
            }
        });
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F1Holper f1Holper = new F1Holper(1, F1AndF3Activity.this, new F1Holper.F1CallBack() {
                    @Override
                    public void result(boolean res) {
                        if(res){
                            ToastUtil.showShortToast("关锁成功");
                        }else {
                            ToastUtil.showShortToast("关锁失败");
                        }
                    }
                });
            }
        });
        mJinRu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F1Holper f1Holper = new F1Holper(4, F1AndF3Activity.this, new F1Holper.F1CallBack() {
                    @Override
                    public void result(boolean res) {
                        if(res){
                            ToastUtil.showShortToast("进入娱乐模式成功");
                            mF3OpenButton.setEnabled(true);
                            mF3CloseButton.setEnabled(true);
                        }else {
                            ToastUtil.showShortToast("进入娱乐模式失败");
                        }
                    }
                });
            }
        });
        mF3OpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> list = new ArrayList<Integer>();
                list.add(1); list.add(1); list.add(1); list.add(1);
                list.add(1); list.add(1); list.add(1); list.add(1);
                F3Holper f3Holper = new F3Holper(F1AndF3Activity.this, new F3Holper.F3BackCallBack() {
                    @Override
                    public void isOK(Boolean result) {
                        if(result){
                            ToastUtil.showShortToast("开灯成功");
                        }else {
                            ToastUtil.showShortToast("开灯失败");
                        }
                    }
                },list);
            }
        });


        mF3CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> list = new ArrayList<Integer>();
                list.add(0); list.add(0); list.add(0); list.add(0);
                list.add(0); list.add(0); list.add(0); list.add(0);
                F3Holper f3Holper = new F3Holper(F1AndF3Activity.this, new F3Holper.F3BackCallBack() {
                    @Override
                    public void isOK(Boolean result) {
                        if(result){
                            ToastUtil.showShortToast("关灯成功");
                        }else {
                            ToastUtil.showShortToast("关灯失败");
                        }
                    }
                },list);
            }
        });
    }
}
