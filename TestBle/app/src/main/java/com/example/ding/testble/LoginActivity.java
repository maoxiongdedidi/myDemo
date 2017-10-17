package com.example.ding.testble;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.example.ding.testble.callback.HttpRuselt;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import java.util.HashMap;
public class LoginActivity extends AppCompatActivity {
    private EditText mphone;
    private EditText mpassword;
    private Button mLogin_button;
    private LinearLayout mARL;

    private MyApplication mApplicationContext;
    private String mClientId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ScreenManager.getScreenManager().pushActivity(this);
        mApplicationContext = (MyApplication) getApplicationContext();
        initView();
        click();

    }

    private void initView() {

        mphone = (EditText) findViewById(R.id.phone_edittext);
        mpassword = (EditText) findViewById(R.id.password_edittext);
        mLogin_button = (Button) findViewById(R.id.login_button);

        mARL = (LinearLayout) findViewById(R.id.login_autoRelativeLayout);

            addDrawable(mphone, R.mipmap.sign_in_phone_white);
            addDrawable(mpassword, R.mipmap.sign_in_password_white);
            mphone.setTextColor(0xff7f8388);
            mphone.setHintTextColor(0xffcccccc);
            mpassword.setTextColor(0xff7f8388);
            mpassword.setHintTextColor(0xffcccccc);
        Screen.addLayoutListener(this,mARL,mLogin_button);
    }


    public void click() {
        mLogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginJudge();

            }
        });
        mARL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApplicationContext.hideInputMethod(LoginActivity.this);
            }
        });
    }

    //添加图片
    public void addDrawable(EditText m, int i) {
        Drawable drawable1 = getResources().getDrawable(i);
        drawable1.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        m.setCompoundDrawables(drawable1, null, null, null);//
    }

    /**
     * 登录的判断
     */
    public void loginJudge() {

        mHandler.postDelayed(mRunnable, 6000);//登录6秒超时

        if(NetUtils.isNetworkConnected(this)){
            if(NetUtils.isMobileConnected(this)||NetUtils.isWifiConnected(this)){
                Constant.username=mphone.getText().toString();
                Constant.password=mpassword.getText().toString();
                HashMap<String,String> map = new HashMap<>();
                map.put("username", Constant.username);
                map.put("password",Constant.password);
                MyOkHttp.getInstance().getStringJson(Constant.LOGIN, map, LoginActivity.this, new HttpRuselt() {
                    @Override
                    public void setString(String s) {
                        mHandler.removeCallbacks(mRunnable);//登录6秒超时
                        Gson gson = new Gson();
                        LoginJson loginJson = gson.fromJson(s, LoginJson.class);
                        if(loginJson.isSuccess()){
                            if(loginJson.getData().equals("0")){
                                Intent intent = new Intent(LoginActivity.this,BleListActivity.class);
                                startActivity(intent);
                                Intent intent1 = new Intent(LoginActivity.this,AppManamerService.class);
                                startService(intent1);

                                finish();
                            }else{
                                ToastUtil.showShortToast("账号或密码错误");
                            }
                        }else{
                            ToastUtil.showShortToast("账号或密码错误");
                        }
                    }
                    @Override
                    public void seterr(Exception e) {

                    }
                });
            }else
                ToastUtil.showShortToast("当前网络不可用");
        }else
            ToastUtil.showShortToast("当前网络未连接");

    }

    Handler mHandler = new Handler() {
    };
    Runnable mRunnable =  new Runnable() {
        @Override
        public void run() {
            ToastUtil.showShortToast("登录超时");
            OkHttpUtils.getInstance().cancelTag(this);
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}