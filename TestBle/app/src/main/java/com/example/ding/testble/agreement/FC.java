package com.example.ding.testble.agreement;

import java.nio.ByteOrder;

/**
 * Created by 丁赵来 on 2017/6/27.
 */

public class FC extends Struct {
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
    public final  M2cprotocol.ble_packetHeader packetHeader =inner(new M2cprotocol.ble_packetHeader());
    public final M2cprotocol.Cloud_SN_t       cloud_SN = inner(new M2cprotocol.Cloud_SN_t     ());//28
    public final Unsigned8 ack_para = new Unsigned8();
    public final Struct.Unsigned16 check_digit = new Struct.Unsigned16();//2  校验位
}
