package com.example.ding.testble.agreement;


import com.example.ding.testble.DataTreatingUtils;

/**
 * Created by 韩飞虎 on 2017/6/23.
 * FFAA 解析
 */
public class ReduceParse {
    public static int state=0;
    public static byte head1= (byte) 0xff;
    public static byte head2= (byte) 0xAA;
    public static byte[] datas;
    public static byte[]  checkDigit=new byte[2];
    public static int checkDigitDataIndex=0;
    public static int dataIndex=0;
    public  static  byte packetIndex;
    public static  byte dataLength;
    public static boolean isDataUse=false;
    public static void reduce(byte bytes,int bytesLenth,RedudeParseCallBack rpcb){
        switch (state){
            case 0:{
                if(bytes==head1){
                    state=1;
                }else{
                    cleanCache();
                }
                break;
            }
            case 1:{
                if(bytes==head2){
                    state=2;
                }else{
                    cleanCache();
                }
                break;
            }
            case 2:{
              //  System.out.println("包序");
                //跳过
                state=3;
                packetIndex=bytes;
                break;
            }
            case 3:{
                //System.out.println("包长");
                dataLength=bytes;
                datas=new byte[byte2int(new byte[]{bytes})];
                state=4;
                //验证这个长度
               /* if(Long.valueOf(bytesLenth-6)==Long.valueOf(ByteUtil.bytesToHexString(new byte[]{bytes}),16)){
                }*/
                break;
            }
            case 4:{
                //System.out.println("接收数据区");
                datas[dataIndex]=bytes;
                if(dataIndex==datas.length-1){
                    state=5;
                }
                dataIndex++;
                break;
            }
            case 5:{
               // System.out.println("接收数据区");
                checkDigit[checkDigitDataIndex]=bytes;
                checkDigitDataIndex++;
                if(checkDigitDataIndex==checkDigit.length){
                    int backCheckNum = byte2int(checkDigit);
                    byte[] frameHeader= new byte[4];
                    frameHeader[0]=head1;
                    frameHeader[1]=head2;
                    frameHeader[2]=packetIndex;
                    frameHeader[3]=dataLength;
                    int computationalCheckNum= byte2int(DataTreatingUtils.jiaoyan( DataTreatingUtils.byteMerger(frameHeader,datas)));

                    if(computationalCheckNum==backCheckNum){
                        byte[] bytes1 = DataTreatingUtils.byteMerger(frameHeader, datas);

                        rpcb.ok( DataTreatingUtils.byteMerger(bytes1, DataTreatingUtils.jiaoyan( DataTreatingUtils.byteMerger(frameHeader,datas))));
                        cleanCache();
                    }else{
                        rpcb.failed();
                        cleanCache();
                    }
                }
                break;
            }
        }
    }
   /* //byte 数组与 int 的相互转换
    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 ;
    }*/
   public static int byte2int(byte[] b) {
       int res = 0;
       int bLen = b.length;
       if (bLen < 5) {// int 最大到4个字节
           for (int i = 0; i < bLen; i++) {
               res += (b[i] & 0xFF) << (8 * i);
           }
       }
       return res;
   }
public interface RedudeParseCallBack{
    void  ok(byte[] allData);
    void  failed();
}


    public static void  cleanCache(){
      state=0;
        datas=null;
        dataIndex=0;
        checkDigitDataIndex=0;


    }
    public static int verson(byte[] datas){
        if(datas[4]==0x02){
            if(datas[5]==0x21||datas[5]==0x22||datas[5]==0x23||datas[5]==0x24){
                return 3;
            }
            return 2;//表示新版本的命令
        }else if(datas[4]==0x01){
            return 1;//表示老版本的升级
        } else {//以后返回可能会有0x04，那么就是第三个版本
            return 0;//表示老版本的命令
        }

    }



    public static boolean isOld(String s){
        byte[] hexBytes = DataTreatingUtils.getHexBytes(s);
        if(hexBytes[4]==0x02||hexBytes[4]==0x03){
            return false;
        }else {
            return true;
        }
    }
    public static byte getAgreementName(String s){
        byte[] hexBytes = DataTreatingUtils.getHexBytes(s);
       return isOld(s)?hexBytes[4]:hexBytes[5];
    }

   /* public static byte[] dataValidation(String s){
        byte[] bytes= DataTreatingUtils.getHexBytes(s);
        for (byte byt:bytes){
            ReduceParse.reduce(byt,bytes.length);
        }
        return can_be_use==null?null:can_be_use;
    }


*/
}
