package com.example.ding.testble.agreement;

/**
 * Created by 丁赵来 on 2017/7/19.
 */

public class OldEF extends Struct{

    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
    public final  M2cprotocol.packetHeader packetHeader =inner(new M2cprotocol.packetHeader());
    public final Unsigned8 pId = new Unsigned8();//5
    public final Signed64 message_id= new Signed64();
    public final M2cprotocol.SN_t VCU_SN =inner(new M2cprotocol.SN_t());//8
    /**
     * 当前模式
     */
    public final Unsigned8 mode = new Unsigned8();//0：防盗模式，1：开锁模式，2：娱乐模式。 1
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
    public final Unsigned16 check_digit = new Unsigned16();//2  校验位

}
