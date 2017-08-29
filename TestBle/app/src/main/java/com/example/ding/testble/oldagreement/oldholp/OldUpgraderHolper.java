package com.example.ding.testble.oldagreement.oldholp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.example.ding.testble.BleService;
import com.example.ding.testble.BroadCastMap;
import com.example.ding.testble.DataTreatingUtils;
import com.example.ding.testble.ToastUtil;
import com.example.ding.testble.agreement.M2cprotocol;
import com.example.ding.testble.agreement.Struct;
import com.example.ding.testble.agreementholp.UpgradeHolper;
import com.example.ding.testble.ble.FileUtils;
import com.example.ding.testble.oldagreement.OldCreatOrder;
import com.example.ding.testble.oldagreement.OldCreatOrderBack;
import com.example.ding.testble.oldagreement.OldReadNumberBack;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;



/**
 * Created by 丁赵来 on 2017/7/8.
 */

public class OldUpgraderHolper {
    public int packetOrder=0;//需要的包续
    public static int sendOrder=1;//需要发送的表示
    public static int isHopeful=1;//期待收到的回复
    public HashMap<Integer,byte[]> map = new HashMap<>();
    private byte[] fileContentByteData;
    private Context mContext;
    public OldUpgraderHolper(byte[] fileContentByteData, Context context) {
        this.fileContentByteData = fileContentByteData;
        mContext = context;
        FileUtils fileUtils = new FileUtils(mContext);
        map= fileUtils.initData(fileContentByteData,128);
    }
    private  int flag=0;
    private Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            flag=flag+1;
            if(flag>3){
                ToastUtil.showShortToast("升级失败");
            }else {
                startUpgrade();
            }
        }
    };
    //循环发送线程
    Runnable mCirculationRunnable= new Runnable() {
        @Override
        public void run() {
            M2cprotocol.ble_packetHeader packeHeader1= new M2cprotocol.ble_packetHeader();
            packeHeader1.packetHeaderOne.set((short) 255);
            packeHeader1. packetHeaderTwo.set((short) 204);
            packeHeader1.arbitrarily.set((short) 0);
            packeHeader1.gallery.set((short) 1);
            packeHeader1.pId.set((short) 2);
            packeHeader1.data_length.set((short) (map.get(packetOrder).length+4));
            byte[] packeHeader = packeHeader1.toBytes();
            TestHax testHax = new TestHax();
            testHax.block_sn.set(packetOrder);
            byte[] blockSN = testHax.toBytes();
            byte[] all = DataTreatingUtils.byteMerger(DataTreatingUtils.byteMerger(packeHeader, blockSN), map.get(packetOrder));
            byte[] checkSum = DataTreatingUtils.jiaoyan(all);
            byte[] allData = DataTreatingUtils.byteMerger(all, checkSum);
            isHopeful=34;
            BleService.send(allData);
        }
    };
    public void startUpgrade(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastMap.OldUpgradeBackAction);
        filter.setPriority(Integer.MAX_VALUE);
        try {
            mContext.unregisterReceiver(oldUpgradeBackAction);
            mContext.registerReceiver(oldUpgradeBackAction, filter);
        }catch (Exception e){
            mContext.registerReceiver(oldUpgradeBackAction, filter);
        }
        upgradeData();
    }

    private void upgradeData() {
        if(sendOrder==1){//创建文件
           /* OldCreatOrder oldCreatOrder= new OldCreatOrder();
            oldCreatOrder.goal.set((short) 1);//表示审计VCU
            oldCreatOrder.target.set((short) 1);//表示升级固件
            byte[] bytes = oldCreatOrder.toBytes();
            byte[] checkSum = DataTreatingUtils.jiaoyan(bytes);
            bytes[bytes.length-2]=checkSum[0];
            bytes[bytes.length-1]=checkSum[1];
            isHopeful=33;
            BleService.send(bytes);*/
            isHopeful=33;
            BleService.sendOrder("FFCC00040101010101D3");

        }else if(sendOrder==2){//写入文件
           if(packetOrder==0){
               mHandler.postDelayed(mCirculationRunnable,4000); //因为播放声音的缘故，需要等4秒再上传文件
           }else {
               mHandler.post(mCirculationRunnable);
           }
        }else if(sendOrder==3){//确认文件
            isHopeful=35;
            BleService.sendOrder("FFCC0002010301D1");
        }else if(sendOrder==4){//重启命令
            isHopeful=36;
            BleService.sendOrder("FFCC0002010401D2");
        }else if(sendOrder==6){//读取需要的包续
            isHopeful=38;
            BleService.sendOrder("FFCC0002010601D4");
        }
    }


    private BroadcastReceiver oldUpgradeBackAction = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] allData = intent.getByteArrayExtra("data");
            if(allData.length>2){
                Integer integer = getPId(DataTreatingUtils.bytes2hex02(allData));
                if(integer==isHopeful){
                    mHandler.removeCallbacks(mRunnable);
                    flag=0;
                    switch (isHopeful){
                        case 33:
                            OldCreatOrderBack ackCreatFile= new OldCreatOrderBack();
                            ackCreatFile.setByteBuffer(ByteBuffer.wrap(allData),0);
                            Integer para = Integer.valueOf(ackCreatFile.para + "");
                            if(para==0){
                                sendOrder=1;
                                upgradeData();
                            }else if(para==1){
                                sendOrder=2;
                                upgradeData();
                            }
                            break;
                        case 34:
                            OldCreatOrderBack ackWriteFile= new OldCreatOrderBack();
                            ackWriteFile.setByteBuffer(ByteBuffer.wrap(allData),0);
                            Integer isOk = Integer.valueOf(ackWriteFile.para + "");
                            if(isOk==0){
                                sendOrder=6;
                            }else if(isOk==1){
                               packetOrder=packetOrder+1;
                                if(packetOrder==map.size()){
                                    sendOrder=3;
                                }else{
                                    sendOrder=2;
                                }
                            }
                            upgradeData();
                            break;
                        case 35:
                            OldCreatOrderBack affirmFileBack= new OldCreatOrderBack();
                            affirmFileBack.setByteBuffer(ByteBuffer.wrap(allData),0);
                            Integer ackconFirmFile = Integer.valueOf(affirmFileBack.para + "");
                            if(ackconFirmFile==1){
                                sendOrder=4;
                                upgradeData();
                            }else if(ackconFirmFile==0){
                                sendOrder=6;
                                upgradeData();
                            }
                            break;
                        case 36:
                            OldCreatOrderBack restartVCUBack= new OldCreatOrderBack();
                            restartVCUBack.setByteBuffer(ByteBuffer.wrap(allData),0);
                            Integer restartVcu = Integer.valueOf(restartVCUBack.para + "");
                            if(restartVcu==1){
                                ToastUtil.showShortToast("升级成功，正在重启命令");
                            }
                            break;
                        case 38:
                            OldReadNumberBack oldReadNumberBack = new OldReadNumberBack();
                            oldReadNumberBack.setByteBuffer(ByteBuffer.wrap(allData),0);
                            packetOrder=Integer.valueOf(oldReadNumberBack.number+"");
                            if(packetOrder==map.size()){
                                sendOrder=3;
                            }else{
                                sendOrder=2;
                            }
                            upgradeData();
                            break;

                    }

                }


            }else{
                if(allData[0]==0x46){
                    mHandler.removeCallbacks(mRunnable);
                    flag=0;
                    sendOrder=6;
                    upgradeData();
                }
            }







        }
    };
    public static  class TestHax extends Struct {
        public boolean isPacked() {
            System.out.println("");
            return true; // MyStruct is packed.
        }
        public final Unsigned16 block_sn= new Unsigned16();
    }

    public Integer getPId(String s1){
        byte[] hexBytes = DataTreatingUtils.getHexBytes(DataTreatingUtils.replaceBlank(s1));//获取全部数据
        byte[] packHeader= new byte[6];
        System.arraycopy(hexBytes, 0, packHeader, 0, 6);//只要数据的前六位
        M2cprotocol.ble_packetHeader packeHeader= new M2cprotocol.ble_packetHeader();
        packeHeader.setByteBuffer(ByteBuffer.wrap(packHeader), 0);//将这些东西赋值给结构体
        //获取结构 体的命令字，以便进行判断需要执行什么操作
        Integer integer = Integer.valueOf(packeHeader.pId + "");
        return integer;
    };

}
