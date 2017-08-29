package com.example.ding.testble.agreement;


import java.nio.ByteOrder;


/**
 * Created by 韩飞虎 on 2017/3/4.
 */
public class EF extends Struct {

      public ByteOrder byteOrder() {
      return ByteOrder.LITTLE_ENDIAN;
  }
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
  public final  M2cprotocol.ble_packetHeader packetHeader =inner(new M2cprotocol.ble_packetHeader());
    public final Signed64 message_id= new Signed64();//8
  //13+14=27
  public final M2cprotocol.Cloud_SN_t       cloud_SN = inner(new M2cprotocol.Cloud_SN_t     ());//8
    //27+5
    public final M2cprotocol.ver_info_t             TBox_boot_ver = inner(new M2cprotocol.ver_info_t     ());//8
  //27+5+5
  public final M2cprotocol.ver_info_t             TBox_app_ver = inner(new M2cprotocol.ver_info_t     ());//8
  //27+5+5+5
    public final M2cprotocol.ver_info_t             VCU_boot_ver = inner(new M2cprotocol.ver_info_t     ());//8//需要用到的（检查是不是自己车的vcu发过来的东西）
  //27+5+5+5+5
    public final M2cprotocol.ver_info_t             VCU_app_ver = inner(new M2cprotocol.ver_info_t     ());//8
    /**
     * 当前模式
     */
    public final Unsigned8 mode = new Unsigned8();//1：防盗模式，2：开锁模式，3：驾驶。4.娱乐，5.升级 1
    /**
     * 音量
     */
    public final Unsigned8 voice = new Unsigned8();
    /**
     * 灯光状态
     */
    public final M2cprotocol.lamp_info_t lamp_info_t = inner(new M2cprotocol.lamp_info_t());
    /**
     * 限速
     */
    public final M2cprotocol.speedctl_info_t speedctl_info_t = inner(new M2cprotocol.speedctl_info_t());
    public final Struct.Unsigned16 check_digit = new Struct.Unsigned16();//2  校验位


}
