package com.example.ding.testble.agreement;

import java.nio.ByteOrder;

/**
 * Created by 丁赵来 on 2017/7/11.
 */

public class OneE extends Struct {
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
    public final  M2cprotocol.ble_packetHeader packetHeader =inner(new M2cprotocol.ble_packetHeader());
    public final Unsigned8 msgType = new Unsigned8();//5
    public final Struct.Signed64 message_id= new Signed64();
    public final M2cprotocol.Cloud_SN_t   cloud_SN = inner(new M2cprotocol.Cloud_SN_t());//8
    public final M2cprotocol.SN_t VCU_SN= inner(new M2cprotocol.SN_t());
    public final Struct.Unsigned16 check_digit = new Struct.Unsigned16();//2  校验位

    public OneE() {
        packetHeader.packetHeaderOne.set((short) 255);
        packetHeader.packetHeaderTwo.set((short) 204);
        packetHeader.pId.set((short) 48);
        packetHeader.arbitrarily.set((short) 1);
        packetHeader.gallery.set((short) 2);
        packetHeader.data_length.set(Short.valueOf((this.size()-packetHeader.size())+""));//固定值
        message_id.set(((System.currentTimeMillis()/256)*256)+2);
        cloud_SN.VCU_SN.sn.set("V10000");
        cloud_SN.TBox_SN.sn.set("T10000");
    }
}
