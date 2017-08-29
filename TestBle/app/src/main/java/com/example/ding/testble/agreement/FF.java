package com.example.ding.testble.agreement;


import java.nio.ByteOrder;


/**
 * Created by 韩飞虎 on 2017/2/16.
 */
public class FF extends Struct {
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
    public final  M2cprotocol.ble_packetHeader packetHeader =inner(new M2cprotocol.ble_packetHeader());

    public final Signed64 message_id= new Signed64();//8
    public final M2cprotocol.Cloud_SN_t       cloud_SN = inner(new M2cprotocol.Cloud_SN_t     ());
    public final Unsigned8 ack_para = new Unsigned8();//1：成功，0：失败
    public final Unsigned8 result_reason = new Unsigned8();//1：成功，0：失败
    public final Struct.Unsigned16 check_digit = new Struct.Unsigned16();//2  校验位

}