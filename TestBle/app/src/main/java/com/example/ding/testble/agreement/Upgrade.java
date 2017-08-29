package com.example.ding.testble.agreement;



/**
 * Created by ding on 2017/3/6.
 */
//  2017/3/6 更新固件返回的值协议
public class Upgrade extends Struct {
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
    public final M2cprotocol.packetHeader packetHeader=inner(new M2cprotocol.packetHeader());//4
    public final Unsigned8 pId = new Unsigned8();//协议编号
    public final Unsigned8 word= new Unsigned8();//命令字
    public final Unsigned8 ack_para = new Unsigned8();//1：成功，0：失败
    public final Unsigned16 check_digi1t = new Unsigned16();//2  校验位
}
