package com.example.ding.testble;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ding.testble.agreementholp.E0Holper;
import com.example.ding.testble.agreementholp.F8Holper;
import com.example.ding.testble.agreementholp.OneEHolper;
import com.example.ding.testble.agreementholp.UpgradeHolper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements UpgradeHolper.NewUpgradeCallBack{
    private Dialog dialog;
    private ImageView mTransalteImageView;
    private TextView mBleState;
    private Button mButton;
    private Button mJinRu;
    private Button mTuiChu;
    private Button mEFButton;
    private Button mF8Button;
    private Button mFileUpgrade;
    private byte[] mBinBytes;
    private TextView mViewById;
    private LinearLayout mUpgradeALL;
    private LinearLayout mOverALL;
    private Button mFinishButton;
    private RotateAnimation myAlphaAnimation;
    private TranslateAnimation animation;
    private TextView mUpgradeResult;
    private Button mF9Button;
    private String mAddress;
    private String mName;
    private LinearLayout mAgainALL;
    private Button mAgainButton;
    private Button mCancleButton;
    private  UpgradeHolper upgradeHolper;
    private Button mMusicButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_main);
        ScreenManager.getScreenManager().pushActivity(this);
        ButterKnife.bind(this);
        initView();
        MyApplication.sMainActivity=this;
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastMap.BleStateAction);
        filter.setPriority(Integer.MAX_VALUE);
        registerReceiver(bleStateReceiver, filter);
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(BroadCastMap.LockStateAction);
        filter1.setPriority(Integer.MAX_VALUE);
        registerReceiver(lockuLtimatelyState, filter1);
        Intent intent = getIntent();
        mAddress = intent.getStringExtra("address");
        mName = intent.getStringExtra("name");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bleStateReceiver);
        unregisterReceiver(lockuLtimatelyState);
        Intent intent = new Intent(MainActivity.this,BleService.class);
        stopService(intent);
        BleAndLockState.BLEADRESS="";
        BleAndLockState.type=0;




    }

    private void initView() {
        mBleState = (TextView)findViewById(R.id.ble_state);
        mButton = (Button)findViewById(R.id.button);
        mJinRu = (Button)findViewById(R.id.jinru);
        mTuiChu = (Button)findViewById(R.id.tuichu);
        mEFButton = (Button)findViewById(R.id.ef_button);
        mF8Button = (Button)findViewById(R.id.f8_button);
        mFileUpgrade = (Button)findViewById(R.id.file_upgrade);
        mF9Button = (Button)findViewById(R.id.empty_mileage);
        mMusicButton = (Button)findViewById(R.id.music_upgrade);
        mButton.setEnabled(true);
        mJinRu.setEnabled(false);
        mTuiChu.setEnabled(false);
        mEFButton.setEnabled(false);
        mF8Button.setEnabled(false);
        mFileUpgrade.setEnabled(false);
        mMusicButton.setEnabled(false);
        mF9Button.setEnabled(false);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,BleService.class);
                startService(intent1);
                BleAndLockState.BLEADRESS=mAddress;
                BleAndLockState.BLENAME=mName;
                mBleState.setText("蓝牙连接中");
            }
        });

        mJinRu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,F1AndF3Activity.class);
                startActivity(intent);
            }
        });
        mTuiChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shangchuanbianhao();
            }
        });

        mEFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                E0Holper e0Holper= new E0Holper(MainActivity.this, new E0Holper.EFBackCallBack() {
                    @Override
                    public void isOK(Boolean result) {
                    }
                });
            }
        });
        mF8Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F8Holper f8Holper= new F8Holper(MainActivity.this, new F8Holper.F8BackCallBack() {
                    @Override
                    public void isOK(Boolean result) {
                        if(result){
                            ToastUtil.showShortToast("上传时间成功");
                        }else {
                            ToastUtil.showShortToast("上传时间失败");
                        }
                    }
                });
            }
        });
        mFileUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BleAndLockState.type=1;
                if(TextUtils.isEmpty(BleAndLockState.bin_adress)){
                    Intent intent1 = new Intent(MainActivity.this, MyFileActivity.class);
                    MainActivity.this.startActivityForResult(intent1,100);
                }else{
                    mBinBytes=null;
                    mBinBytes=getbyte( BleAndLockState.bin_adress);
                    upgradeHolper = new UpgradeHolper(mBinBytes, MainActivity.this, MainActivity.this);
                    upgradeHolper.startUpgrade();
                }
            }
        });
        mMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BleAndLockState.type=2;
                if(TextUtils.isEmpty(BleAndLockState.mp3_adress)){
                    Intent intent1 = new Intent(MainActivity.this, MyFileActivity.class);
                    MainActivity.this.startActivityForResult(intent1,200);
                }else{
                    mBinBytes=null;
                    mBinBytes=getbyte( BleAndLockState.mp3_adress);
                    upgradeHolper = new UpgradeHolper(mBinBytes, MainActivity.this, MainActivity.this);
                    upgradeHolper.startUpgrade();
                }





            }
        });

        mF9Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(MainActivity.this,F9Activity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==100){
          BleAndLockState.bin_adress = data.getStringExtra("data");
            if(!TextUtils.isEmpty(BleAndLockState.bin_adress)){
                    mBinBytes=null;
                    mBinBytes=getbyte( BleAndLockState.bin_adress);
                upgradeHolper = new UpgradeHolper(mBinBytes, this, this);
                upgradeHolper.startUpgrade();
            }

        }else if(resultCode==RESULT_OK&&requestCode==1){
            String vcu=data.getStringExtra("data");
            OneEHolper oneEHolper = new OneEHolper(MainActivity.this, new OneEHolper.OneEBackCallBack() {
                @Override
                public void isOK(Boolean result) {
                        if(result){
                            ToastUtil.showShortToast("上传编号成功");
                        }else{
                            ToastUtil.showShortToast("上传编号失败");
                        }
                }
            },vcu);
        }else if(resultCode==RESULT_OK&&requestCode==200){
            BleAndLockState.mp3_adress = data.getStringExtra("data");
            if(!TextUtils.isEmpty(BleAndLockState.mp3_adress)){

                    mBinBytes=null;
                    mBinBytes=getbyte( BleAndLockState.mp3_adress);

                upgradeHolper = new UpgradeHolper(mBinBytes, this, this);
                upgradeHolper.startUpgrade();
            }

        }



    }



    @Override
    public void startUpgrade() {
        getDialog();
        mUpgradeALL.setVisibility(View.VISIBLE); //开始上传文件
        mOverALL.setVisibility(View.GONE);
        mAgainALL.setVisibility(View.GONE);
    }

    @Override
    public void upgradeCourse(int allDataSize, int succeedSize) {
        float weight=309 * Screen.getScreenWidth(MainActivity.this) / 720;
        float start = (succeedSize-1) * (weight/allDataSize); //图片开始移动  同时数字开始变化，如果断点续传的时候数字和车不是从断点的地方开始，可以尝试将stopcount改成serialNumber
        float end = succeedSize * (weight/allDataSize);
        String s = DataTreatingUtils.parsePercent((double) end / weight);//将数字转换成百分比
        mViewById.setText(s);
        setTranslateAnimation(mTransalteImageView, start, end);
    }

    @Override
    public void endUpgrade() {
        mUpgradeALL.setVisibility(View.GONE);
        mOverALL.setVisibility(View.VISIBLE);
        mAgainALL.setVisibility(View.GONE);
        mUpgradeResult.setText("升级成功");
        dialog.setCanceledOnTouchOutside(true);
       //  BleAndLockState.BLEADRESS="";
    }

    @Override
    public void defeated() {
        upgradeHolper.endUpgrade();
        mUpgradeALL.setVisibility(View.GONE);
        mOverALL.setVisibility(View.GONE);
        mAgainALL.setVisibility(View.VISIBLE);
        mUpgradeResult.setText("升级失败");
        if(dialog!=null){
            dialog.setCanceledOnTouchOutside(true);
        }
    }



    /**
     * 图片平移动画
     */

    public void setTranslateAnimation(ImageView image, float start, float end) {
        animation = new TranslateAnimation(start, end, 0, 0);
        animation.setFillAfter(true);//停在结束的地方
        image.setAnimation(animation);
        image.startAnimation(animation);
    }

    /**
     * 弹出升级的dialog
     */
    public void getDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View layout = inflater.inflate(R.layout.layout_dialog, null);
        if(dialog==null){
            dialog = new android.support.v7.app.AlertDialog.Builder(this).create();
            dialog.show();
        }
        dialog.setCancelable(false);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        mTransalteImageView = (ImageView) layout.findViewById(R.id.layout_dialog_imageview);
        mViewById = (TextView) layout.findViewById(R.id.layout_dialog_textview);
        mUpgradeALL = (LinearLayout) layout.findViewById(R.id.layout_dialog_upgrade_autolinearlayout);
        mUpgradeResult = (TextView)layout.findViewById(R.id.upgrade_result);
        mOverALL = (LinearLayout) layout.findViewById(R.id.layout_dialog_over_autolinearlayout);
        mFinishButton = (Button) layout.findViewById(R.id.layout_dialog_over_button);
        mAgainALL = (LinearLayout)layout.findViewById(R.id.layout_dialog_again_autolinearlayout);
        mAgainButton = (Button)layout.findViewById(R.id.layout_dialog_again_button);
        mCancleButton = (Button)layout.findViewById(R.id.layout_dialog_cancle_button);
        params.width = 689 * Screen.getScreenWidth(this) / 720;
        params.height = 500 * Screen.getScreenHeight(this) / 720;
        dialog.getWindow().setAttributes(params);
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false); //设置点击外部dialog不消失
        dialog.show();
        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mCancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(BleAndLockState.type==1){
                   if(TextUtils.isEmpty(BleAndLockState.bin_adress)){
                       Intent intent1 = new Intent(MainActivity.this, MyFileActivity.class);
                       MainActivity.this.startActivityForResult(intent1,100);
                   }else{
                       mBinBytes=null;
                       mBinBytes=getbyte( BleAndLockState.bin_adress);
                       upgradeHolper = new UpgradeHolper(mBinBytes, MainActivity.this, MainActivity.this);
                       upgradeHolper.startUpgrade();
                   }

               }else if(BleAndLockState.type==2){
                   if(TextUtils.isEmpty(BleAndLockState.mp3_adress)){
                       Intent intent1 = new Intent(MainActivity.this, MyFileActivity.class);
                       MainActivity.this.startActivityForResult(intent1,200);
                   }else{
                       mBinBytes=null;
                       mBinBytes=getbyte( BleAndLockState.mp3_adress);
                       upgradeHolper = new UpgradeHolper(mBinBytes, MainActivity.this, MainActivity.this);
                       upgradeHolper.startUpgrade();
                   }
               }

            }
        });
    }

    /**
     * 弹出上传编号的dialog
     */
    public void shangchuanbianhao() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View layout = inflater.inflate(R.layout.one_e, null);

         final AlertDialog di = new android.support.v7.app.AlertDialog.Builder(this).create();
        di.show();
        di.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        WindowManager.LayoutParams params = di.getWindow().getAttributes();
        params.width = 689 * Screen.getScreenWidth(this) / 720;
        di.getWindow().setAttributes(params);
        di.setContentView(layout);
        di.show();
        di.setCanceledOnTouchOutside(false); //设置点击外部dialog不消失
        final EditText shuru = (EditText) layout.findViewById(R.id.one_e_shuru);

        Button shangchuan = (Button) layout.findViewById(R.id.one_e_shuru_shangchuan);
        Button zhaopian = (Button) layout.findViewById(R.id.one_e_paizhao_shangchuan);

        shangchuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(!TextUtils.isEmpty(shuru.getText().toString())){
                    di.setCanceledOnTouchOutside(true);
                    OneEHolper oneEHolper = new OneEHolper(MainActivity.this, new OneEHolper.OneEBackCallBack() {
                        @Override
                        public void isOK(Boolean result) {
                            if(result){
                                ToastUtil.showShortToast("上传编号成功");
                                di.dismiss();
                            }else{
                                ToastUtil.showShortToast("上传编号失败");
                            }
                        }
                    },shuru.getText().toString());
                }else{
                   Toast.makeText(MainActivity.this,"您还没有输入内容",Toast.LENGTH_SHORT).show();
               }
            }
        });
        zhaopian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                di.setCanceledOnTouchOutside(true);
                di.dismiss();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, WeChatCaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 1);
            }
        });

    }







public byte[] getbyte(String data1){
    byte[] mBytes=null;
    FileInputStream fis = null;
    try {
        fis = new FileInputStream(new File(data1));
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = fis.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        mBytes = outSteam.toByteArray();
        byte[] jiaoyan = DataTreatingUtils.jiaoyan(mBytes);
        int i = DataTreatingUtils.byte2int(jiaoyan);
        outSteam.close();
        fis.close();
        return mBytes;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }

}




    private BroadcastReceiver bleStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int bleState = BleAndLockState.bleState;
            if(bleState==1){
                mBleState.setText("蓝牙连接");
                mButton.setEnabled(false);
                mJinRu.setEnabled(true);
                mTuiChu.setEnabled(true);
                mEFButton.setEnabled(true);
                mF8Button.setEnabled(true);
                mFileUpgrade.setEnabled(true);
                mMusicButton.setEnabled(true);
                mF9Button.setEnabled(true);
            }else{
                mButton.setEnabled(true);
                mJinRu.setEnabled(false);
                mTuiChu.setEnabled(false);
                mEFButton.setEnabled(false);
                mF8Button.setEnabled(false);
                mFileUpgrade.setEnabled(false);
                mMusicButton.setEnabled(false);
                mF9Button.setEnabled(false);
                mBleState.setText("蓝牙未连接");
                if(dialog!=null){
                    dialog.dismiss();
                    dialog=null;
                }
            }
        }
    };
    private BroadcastReceiver lockuLtimatelyState = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int bleState = BleAndLockState.lockState;
            switch (bleState){
                case 1:
                    ToastUtil.showShortToast("进入防盗模式");
                    break;
                case 2:
                    ToastUtil.showShortToast("进入开锁模式");
                    break;
                case 3:
                    ToastUtil.showShortToast("进入驾驶模式");
                    break;
                case 4:
                    ToastUtil.showShortToast("进入娱乐模式");
                    break;
            }
        }
    };



}
