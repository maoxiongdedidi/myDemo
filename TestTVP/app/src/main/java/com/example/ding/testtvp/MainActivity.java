package com.example.ding.testtvp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ding.testtvp.ble.BluetoothInterface;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements InformUserCallBack{

    private TextView mSheBeiBianHao;
    private TextView mSheBeiZhuangTai;
    private ImageView mSaoMiao;
    private TextView mLanYa;
    private Button mButton;
    private TextView mJingDu;
    private TextView mWeiDu;
    private TextView mWeiXing;
    private TextView mXinHao;
    private TextView mWaiZhi;
    private TextView mNeiZhi;
    private TextView mGengXin;
    private TextView mLiCheng;
    private TextView mYinPing;
    private LinearLayout mLanYaLL;
    private LinearLayout mShuJuLL;
    private BluetoothInterface mInstance;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_main);
        initView();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容,绑定车辆
                    initData(bundle.getString("result"));
                }
                break;
        }

    }

    private void initData(final String vcu) {
        OkHttpUtils.get().tag(this).url("http://121.41.47.71/doohan-carnet-web-1.0-SNAPSHOT/carnet/v1/carInfo1.json")

                .addHeader("Content-Type", "application/json")
               .addParams("vcuNo",vcu)
                .build().readTimeOut(10000).writeTimeOut(10000)
                .execute(new MyStringCallBack() {
                    @Override
                    public void onResponse(Call call, String s) {
                        super.onResponse(call, s);
                        Gson g= new Gson();
                        MyJson myJson = g.fromJson(s, MyJson.class);
                        if(myJson.isSuccess()){
                            mSheBeiBianHao.setText(vcu);

                            try {
                                MyJson.DataBean data = myJson.getData();
                                String vcuNo = data.getVcuNo();
                                mSheBeiBianHao.setText(vcuNo);
                                mSheBeiZhuangTai.setText("已注册");
                                if(data.getBluetoothAddress()!=null){
                                    mInstance = BluetoothInterface.getInstance(MainActivity.this, bleTreating(data.getBluetoothAddress()),MainActivity.this);
                                    mInstance.openBluetooth();
                                    mInstance.scanLeDevice(true);
                                }else{
                                    mLanYaLL.setVisibility(View.VISIBLE);
                                    mLanYa.setText("数据蓝牙地址为空");
                                    mButton.setClickable(false);

                                }


                                mShuJuLL.setVisibility(View.VISIBLE);
                                mJingDu.setText(data.getLongitude());
                                mWeiDu.setText(data.getLatitude());
                                mWeiXing.setText(data.getSatellitesNumber()+"");
                                mXinHao.setText(data.getGprsSignalStrength()+"");
                                mWaiZhi.setText(data.getExternalBatteryElectricity()+"");
                                mNeiZhi.setText(data.getTboxBatteryElectricity()+"");
                                mGengXin.setText(data.getUpdateTime());
                                mLiCheng.setText(data.getTotalMileage()+"");
                                mYinPing.setText(data.getAudioBluetoothState()+"");

                            }catch (Exception e){
                                mSheBeiBianHao.setText(vcu);
                                mSheBeiZhuangTai.setText("不存在");
                            }


                        }else {
                            Toast.makeText(MainActivity.this,myJson.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        super.onError(call, e);
                        String name = e.getClass().getName();
                        if (e.getClass() == SocketTimeoutException.class||e.getClass()==TimeoutException.class) {

                            Toast.makeText(MainActivity.this,"连接超时",Toast.LENGTH_SHORT).show();
                        } else if (e.getClass() == ConnectException.class) {

                            Toast.makeText(MainActivity.this,"服务器连接异常",Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(MainActivity.this,name,Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    /**
     * 将蓝牙地址转化成标准
     */
    public  String bleTreating(String s ){
        String newstr = "";
        int size = ((s.length())%2 == 0) ? ((s.length())/2):((s.length())/2+ 1);
        for(int i=0;i<size ;i++){
            int endIndex = (i+1)*2;
            if((i+1)==size){
                endIndex = s.length();
            }
            if(i==0){
                newstr += s.substring(i,endIndex);
            }else{
                newstr += ":"+s.substring(i*2, endIndex);
            }
        }
        return newstr;
    }

    private void initView() {
        mSheBeiBianHao = (TextView)findViewById(R.id.shebeibianhao_textview);
        mSheBeiZhuangTai = (TextView)findViewById(R.id.shebeizhuangtai_textview);
        mSaoMiao = (ImageView)findViewById(R.id.saomiao_imageview);
        mLanYa = (TextView)findViewById(R.id.lanya_type_textview);
        mButton = (Button)findViewById(R.id.button);
        mJingDu = (TextView)findViewById(R.id.jingdu_textview);
        mWeiDu = (TextView)findViewById(R.id.weidu_textview);
        mWeiXing = (TextView)findViewById(R.id.weixing_textview);
        mXinHao = (TextView)findViewById(R.id.xinhao_textview);
        mWaiZhi = (TextView)findViewById(R.id.waizhidianliang_textview);
        mNeiZhi = (TextView)findViewById(R.id.neizhidianliang_textview);
        mGengXin = (TextView)findViewById(R.id.gengxinshijian_textview);
        mLiCheng = (TextView)findViewById(R.id.licheng_textview);
        mYinPing = (TextView)findViewById(R.id.yinpinglanya_textview);
        mLanYaLL = (LinearLayout)findViewById(R.id.lanyazhuangtai_linearlayout);
        mLanYaLL.setVisibility(View.GONE);
        mShuJuLL = (LinearLayout)findViewById(R.id.shuju_linearlayout);
        mShuJuLL.setVisibility(View.GONE);

        mSaoMiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShuJuLL.setVisibility(View.GONE);
                mLanYaLL.setVisibility(View.GONE);
                mButton.setClickable(true);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, WeChatCaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInstance.disconnect();
                mLanYa.setText("蓝牙已断开");
            }
        });




    }











    @Override
    public void getAddress(String address) {
        Toast.makeText(this,address,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notGetAddress() {
        mLanYaLL.setVisibility(View.VISIBLE);
        mLanYa.setText("未找到该蓝牙设备");
        mButton.setClickable(false);
    }

    @Override
    public void bluetoothBreak() {
        mLanYaLL.setVisibility(View.VISIBLE);
        mLanYa.setText("与蓝牙断开连接");
        mButton.setClickable(false);
    }

    @Override
    public void bluetoothConnect() {
        mLanYaLL.setVisibility(View.VISIBLE);
        mLanYa.setText("数据蓝牙已连接");
        mButton.setClickable(true);



    }

    @Override
    public void receiveBluetoothData(String data, Context context) {

    }

    @Override
    public void connectBluetooth() {
        Toast.makeText(this,"正在连接蓝牙",Toast.LENGTH_SHORT).show();
    }
}
