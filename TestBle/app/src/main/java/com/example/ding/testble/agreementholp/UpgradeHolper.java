package com.example.ding.testble.agreementholp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

import com.example.ding.testble.BleService;
import com.example.ding.testble.BroadCastMap;
import com.example.ding.testble.DataTreatingUtils;
import com.example.ding.testble.ToastUtil;
import com.example.ding.testble.agreement.M2cprotocol;
import com.example.ding.testble.agreement.Struct;
import com.example.ding.testble.black.FileContentResponse;
import com.example.ding.testble.black.FileResponse;
import com.example.ding.testble.black.MessageResponse;
import com.example.ding.testble.black.MessageSend;
import com.example.ding.testble.ble.FileUtils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by 丁赵来 on 2017/7/5.
 */

public class UpgradeHolper {
    public static int arbitrarily=1;
    public byte[] correctData;
    public int packetOrder;
    private byte[] fileContentByteData;
    public HashMap<Integer,byte[]> map = new HashMap<>();
    private Context mContext;
    public  int sendOrder=1;//需要发送的表示
    public  int isHopeful=1;//期待收到的回复
    public NewUpgradeCallBack cb;
    public UpgradeHolper(byte[] fileContentByteData, Context context, NewUpgradeCallBack cb) {
        this.fileContentByteData = fileContentByteData;
        mContext = context;
        this.cb=cb;
    }
    public     int flag=0;
    private Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            flag=flag+1;
            if(flag>4){
                mContext.unregisterReceiver(upgradeBackAction);
               cb.defeated();
            }else {
                ToastUtil.showShortToast("第"+ flag+"次重发");
                startUpgrade();//如果出现格式化flash的需求，只需要在此方法中添加一个参数即可
            }
        }
    };
    public void startUpgrade(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastMap.UpgradeBackAction);
        filter.setPriority(Integer.MAX_VALUE);
        try {
            mContext.unregisterReceiver(upgradeBackAction);
            mContext.registerReceiver(upgradeBackAction, filter);
        }catch (Exception e){
            mContext.registerReceiver(upgradeBackAction, filter);
        }
        upgradeData();
    }

    Runnable mSecondHandshakeRunnable = new Runnable() {
        @Override
        public void run() {
            MessageSend mMessageSend = new MessageSend();
            byte[] hexBytes = correctData;
            FileResponse fileResponse = new FileResponse();
            fileResponse.setByteBuffer(ByteBuffer.wrap(hexBytes).order(ByteOrder.LITTLE_ENDIAN),0);
            mMessageSend.is_resume_broken.set(Short.valueOf(fileResponse.is_resume_broke+""));
            mMessageSend.first_block_sn.set(Integer.valueOf(fileResponse.first_block_sn+ ""));
            mMessageSend.block_size.set(Integer.valueOf(fileResponse.block_size+ ""));
            mMessageSend.all_block_nbr.set(map.size());
            byte[] bytes2 = new byte[mMessageSend.size()];
            mMessageSend.getByteBuffer().get(bytes2);
            byte[] checkSum1 = DataTreatingUtils.jiaoyan(bytes2);
            bytes2[bytes2.length-2]=checkSum1[0];
            bytes2[bytes2.length-1]=checkSum1[1];
            isHopeful=34;
            BleService.send(bytes2);

        }
    };

    public  void upgradeData(){
        switch (sendOrder){
            case 1:
                M2cprotocol.ble_packetHeader packeHeader= new M2cprotocol.ble_packetHeader(38,1,1);
                byte[] bytes = new byte[packeHeader.size()];
                packeHeader.getByteBuffer().get(bytes);
                // byte[] bytes1 = map.get(0);
                byte[] dataAndHeader= new byte[36];//截取前36位
                System.arraycopy(fileContentByteData, 0, dataAndHeader, 0, 36);
                byte[] checkSum = DataTreatingUtils.jiaoyan(DataTreatingUtils.byteMerger(bytes, dataAndHeader));
                byte[] allData = DataTreatingUtils.byteMerger(DataTreatingUtils.byteMerger(bytes, dataAndHeader), checkSum);
                isHopeful=33;
                BleService.send(allData);
                cb.startUpgrade();
                break;
            case 2:
                mHandler.postDelayed(mSecondHandshakeRunnable,5000);//延时5秒开始升级
                break;
            case 3:
                TestHax testHax = new TestHax();
                testHax.block_sn.set(packetOrder);
                testHax.block_len.set((short) (map.get(packetOrder).length));
                M2cprotocol.ble_packetHeader packeHeader1= new M2cprotocol.ble_packetHeader((map.get(packetOrder).length+testHax.size()+2),arbitrarily,3);
                byte[] bytes3 = new byte[packeHeader1.size()];
                packeHeader1.getByteBuffer().get(bytes3);
                byte[] content=new byte[testHax.size()];
                testHax.getByteBuffer().get(content);
                byte[] aaa = DataTreatingUtils.byteMerger(bytes3, content);
                byte[] checkNum3 = DataTreatingUtils.jiaoyan(DataTreatingUtils.byteMerger(aaa,map.get(packetOrder)));
                byte[] allData3=DataTreatingUtils.byteMerger(DataTreatingUtils.byteMerger(aaa,map.get(packetOrder)),checkNum3);
                isHopeful=35;

                BleService.send(allData3);
                Log.e("=======发送的信息", Arrays.toString(allData3));
                if(arbitrarily>255){
                    arbitrarily=1;
                }else{
                    arbitrarily=arbitrarily+1;
                }

                break;
            case 4:
                M2cprotocol.ble_packetHeader ble_packetHeader= new M2cprotocol.ble_packetHeader(2,1,4);
                byte[] bytes4 = new byte[ble_packetHeader.size()];
                ble_packetHeader.getByteBuffer().get(bytes4);
                byte[] jiaoyan = DataTreatingUtils.jiaoyan(bytes4);
                byte[] alldata = DataTreatingUtils.byteMerger(bytes4, jiaoyan);

                BleService.send(alldata);
                isHopeful=36;
                break;
        }
        if(sendOrder!=2){
            mHandler.postDelayed(mRunnable,1500);
        }else{
            mHandler.postDelayed(mRunnable,7000);
        }

    }


    private BroadcastReceiver upgradeBackAction = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] allData = intent.getByteArrayExtra("data");
            Integer integer = getPId(DataTreatingUtils.bytes2hex02(allData));
            if(integer==isHopeful){
                flag=0;
                correctData=allData;
                mHandler.removeCallbacks(mRunnable);
                switch (integer){
                    case 33:
                        FileResponse fileResponse = new FileResponse();
                        fileResponse.setByteBuffer(ByteBuffer.wrap(allData).order(ByteOrder.LITTLE_ENDIAN),0);
                        packetOrder = Integer.valueOf(fileResponse.first_block_sn+ "");
                        Integer blockSize = Integer.valueOf(fileResponse.block_size + "");
                        //开始对文件进行处理
                        map.clear();
                        FileUtils fileUtils = new FileUtils(mContext);
                        map= fileUtils.initData(fileContentByteData,blockSize);
                        sendOrder=2;
                        upgradeData();
                        break;
                    case 34:
                        MessageResponse mMessageResponse = new MessageResponse();
                        mMessageResponse.setByteBuffer(ByteBuffer.wrap(allData),0);
                        String aaa="传输确认"+mMessageResponse.is_transfer_confirm_OK;
                        Integer confimOK = Integer.valueOf(mMessageResponse.is_transfer_confirm_OK + "");
                        if(confimOK==1){
                            Log.e("====传输确认",aaa);
                            sendOrder=3;
                            upgradeData();
                        }else{
                            sendOrder=1;
                            upgradeData();
                        }
                        break;
                    case 35:
                        FileContentResponse fileContentResponse = new FileContentResponse();
                        fileContentResponse.setByteBuffer(ByteBuffer.wrap(allData).order(ByteOrder.LITTLE_ENDIAN),0);
                        Integer isRevOK = Integer.valueOf(fileContentResponse.is_rev_OK + "");
                        if(isRevOK==1||isRevOK==0){
                            cb.upgradeCourse(map.size(),packetOrder);
                            packetOrder = Integer.valueOf(fileContentResponse.want_block_sn+ "");
                            Log.e("=======收到的信息baooxiu",packetOrder+"");
                            if(packetOrder==map.size()){
                                sendOrder=4;
                                upgradeData();
                            }else{
                                sendOrder=3;
                                upgradeData();

                                // mHandler.post(mRunnableFileContent);
                            }
                        }else if(isRevOK==2){
                            sendOrder=1;
                            upgradeData();
                        }
                        break;
                    case 36:
                        MessageResponse mMessageResponse1 = new MessageResponse();
                        mMessageResponse1.setByteBuffer(ByteBuffer.wrap(allData),0);
                        String aaa1="传输确认"+mMessageResponse1.is_transfer_confirm_OK;
                        Log.e("=======传输确认",aaa1);
                        if(aaa1.equals("传输确认1")){
                            cb.endUpgrade();
                            mContext.unregisterReceiver(upgradeBackAction);
                        }
                        break;
                }
            }else{

            }


        }
    };

    public interface NewUpgradeCallBack{
        void startUpgrade();
        void upgradeCourse(int allDataSize, int succeedSize);
        void endUpgrade();
        void defeated();
    }







    public static  class TestHax extends Struct {

        public boolean isPacked() {
            System.out.println("");
            return true; // MyStruct is packed.
        }
        @Override
        public ByteOrder byteOrder() {
            return ByteOrder.LITTLE_ENDIAN;
        }
        public final Unsigned32 block_sn= new Unsigned32();
        public final Unsigned8 block_len=new Unsigned8();

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
