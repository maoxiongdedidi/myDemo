package com.example.ding.testble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ding.testble.agreementholp.F9Holper;

public class F9Activity extends AppCompatActivity {
    private Button mF9Button;
    private Button mF9SettingButton;
    private Button mF9CloseButton;
    private Button mF9OpenButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f9);
        initView();
        initData();
    }

    private void initData() {
    }

    private void initView() {
        mF9Button = (Button)findViewById(R.id.f9_empty_mileage);
        mF9SettingButton = (Button)findViewById(R.id.f9_setting_mileage);
        mF9CloseButton = (Button)findViewById(R.id.f9_close_light);
        mF9OpenButton = (Button)findViewById(R.id.f9_open_light);
        mF9Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F9Holper f9Holper= new F9Holper(F9Activity.this, new F9Holper.F9BackCallBack() {
                    @Override
                    public void isOK(Boolean result) {
                        if(result){
                            ToastUtil.showShortToast("里程清空完成");
                        }else{
                            ToastUtil.showShortToast("里程清空失败");
                        }
                    }
                },0,0,0,0,0);
            }
        });
        mF9SettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(F9Activity.this,F9SettingMileageDialog.class);
                startActivity(intent);
            }
        });
        mF9CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F9Holper f9Holper= new F9Holper(F9Activity.this, new F9Holper.F9BackCallBack() {
                    @Override
                    public void isOK(Boolean result) {
                        if(result){
                            ToastUtil.showShortToast("关闭自动大灯完成");
                        }else{
                            ToastUtil.showShortToast("关闭自动大灯失败");
                        }
                    }
                },2,0,0,0,0);
            }
        });
        mF9OpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(F9Activity.this,LightSensationDialog.class);
                startActivity(intent);
            }
        });
    }
}
