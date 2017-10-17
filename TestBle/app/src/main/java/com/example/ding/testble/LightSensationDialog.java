package com.example.ding.testble;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.ding.testble.agreementholp.F9Holper;

/**
 * Created by 丁赵来 on 2017/7/25.
 */

public class LightSensationDialog extends Activity {
    private SeekBar mOpen;
    private SeekBar mClose;
    private Button mCancle;
    private Button mAgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_sensation_setting);
        ScreenManager.getScreenManager().pushActivity(this);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.alpha = 0.9f; // 透明度
        lp.width= (int) (0.9* Screen.getScreenWidth(this));
        dialogWindow.setAttributes(lp);
        initView();
        initData();
    }



    private void initView() {
        mOpen = (SeekBar)findViewById(R.id.open_sensation);
        mClose = (SeekBar)findViewById(R.id.close_sensation);
        mCancle = (Button)findViewById(R.id.cancle);
        mAgress = (Button)findViewById(R.id.agress);
    }
    private void initData() {
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mOpen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress()>=40){
                    ToastUtil.showShortToast("开灯光感值最大为40");
                    seekBar.setProgress(39);
                }else{
                    ToastUtil.showShortToast("当前值为："+seekBar.getProgress());
                }
            }
        });
        mClose.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress()<40){
                    ToastUtil.showShortToast("关灯光感值最小为40");
                    seekBar.setProgress(40);
                }else if(seekBar.getProgress()>=40&&seekBar.getProgress()<100){
                    ToastUtil.showShortToast("当前值为："+seekBar.getProgress());
                }else if(seekBar.getProgress()==100){
                    ToastUtil.showShortToast("关灯光感值最大为99");
                    seekBar.setProgress(99);
                }
            }
        });
        mAgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F9Holper f9Holper = new F9Holper(LightSensationDialog.this, new F9Holper.F9BackCallBack() {
                    @Override
                    public void isOK(Boolean result) {
                        if (result) {
                            ToastUtil.showShortToast("设置完成");
                            finish();
                        } else {
                            ToastUtil.showShortToast("设置失败");
                        }
                    }
                }, 2, 1, mOpen.getProgress(), mClose.getProgress(), 0);
            }
        });
    }
}
