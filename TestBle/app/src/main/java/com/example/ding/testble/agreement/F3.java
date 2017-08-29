package com.example.ding.testble.agreement;


import java.nio.ByteOrder;


/**
 * Created by 韩飞虎 on 2017/2/16.
 * 灯光控制
 */
public class   F3 extends Struct {
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
    public final  M2cprotocol.ble_packetHeader packetHeader =inner(new M2cprotocol.ble_packetHeader());
    public final Struct.Signed64 message_id= new Signed64();//8
    public final M2cprotocol.Cloud_SN_t       cloud_SN = inner(new M2cprotocol.Cloud_SN_t     ());//8
    public final M2cprotocol.lamp_info_t lamp_info_t = inner(new M2cprotocol.lamp_info_t());//8
    public final Unsigned16 check_digi1t = new Unsigned16();//2  校验位
    public F3(){
        packetHeader.packetHeaderOne.set((short) 255);
        packetHeader.packetHeaderTwo.set((short) 204);
        packetHeader.pId.set((short) 243);
        packetHeader.gallery.set((short) 2);
        packetHeader.arbitrarily.set((short) 1);
        message_id.set(((System.currentTimeMillis()/256)*256)+2);
        cloud_SN.VCU_SN.sn.set("V10000");
        cloud_SN.TBox_SN.sn.set("T10000");
        packetHeader.data_length.set(Short.valueOf((this.size()-(packetHeader.size()))+""));//固定值
    }

}
