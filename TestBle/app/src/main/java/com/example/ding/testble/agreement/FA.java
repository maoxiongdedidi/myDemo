package com.example.ding.testble.agreement;

import java.nio.ByteOrder;


/**
 * Created by 韩飞虎 on 2017/3/4.
 */
public class FA extends Struct {
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }

    public final  M2cprotocol.ble_packetHeader packetHeader =inner(new M2cprotocol.ble_packetHeader());
    public final Struct.Unsigned8 messageType = new Struct.Unsigned8();//1
    public final Struct.Signed64 message_id= new Signed64();//8
    public final M2cprotocol.Cloud_SN_t       cloud_SN = inner(new M2cprotocol.Cloud_SN_t     ());//8
    public final Struct.Unsigned8 ack_para = new Struct.Unsigned8();// 成功 或者失败
    public final Struct.Unsigned8 ack_para1 = new Struct.Unsigned8();// 原因
    public final Struct.Unsigned16 check_digit = new Struct.Unsigned16();//2  校验位

}