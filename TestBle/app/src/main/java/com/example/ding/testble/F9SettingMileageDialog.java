package com.example.ding.testble;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.ding.testble.agreementholp.F9Holper;

/**
 * Created by 丁赵来 on 2017/7/25.
 */

public class F9SettingMileageDialog extends Activity {
    private EditText mEdittext;
    private Button mViewById;
    private String TAG=this.getClass().getName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_mileage);
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
    }

    private void initView() {

        mEdittext = (EditText)findViewById(R.id.setting_mileage_specific);
        mViewById = (Button)findViewById(R.id.setting_mileage_affirm);
        mViewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEdittext.getText().length()>0){
                    Integer integer = Integer.valueOf(mEdittext.getText().toString());
                    if(integer>0){
                        F9Holper f9Holper= new F9Holper(F9SettingMileageDialog.this, new F9Holper.F9BackCallBack() {
                            @Override
                            public void isOK(Boolean result) {
                                if(result){
                                    ToastUtil.showShortToast("里程设置完成");
                                    finish();
                                }else{
                                    ToastUtil.showShortToast("里程设置失败");
                                }
                            }
                        },0,integer ,0,0,0);
                    }else{
                        ToastUtil.showShortToast("输入的数字必须大于0");
                    }
                }else{
                    ToastUtil.showShortToast("您还没有输入具体的里程数字");
                }

            }
        });

    }
}
