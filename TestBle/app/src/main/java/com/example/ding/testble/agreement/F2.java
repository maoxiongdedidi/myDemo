package com.example.ding.testble.agreement;


import java.nio.ByteOrder;


/**
 * Created by admin on 2017/1/6.
 */
public class F2 extends Struct {
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
    public final  M2cprotocol.ble_packetHeader packetHeader =inner(new M2cprotocol.ble_packetHeader());
    public final Struct.Signed64 message_id= new Signed64();
    public final M2cprotocol.Cloud_SN_t       cloud_SN = inner(new M2cprotocol.Cloud_SN_t     ());//8
    public final Unsigned8 para = new Unsigned8();//0：关闭声音。1~10：音量。1最小，100最高。
    public final Struct.Unsigned16 check_digit = new Struct.Unsigned16();//2  校验位
    public F2(){
        packetHeader.packetHeaderOne.set((short) 255);
        packetHeader.packetHeaderTwo.set((short) 204);
        packetHeader.pId.set((short) 242);
        packetHeader.gallery.set((short) 2);
        packetHeader.arbitrarily.set((short) 1);
        message_id.set(((System.currentTimeMillis()/256)*256)+2);
        cloud_SN.VCU_SN.sn.set("V10000");
        cloud_SN.TBox_SN.sn.set("T10000");
        packetHeader.data_length.set(Short.valueOf((this.size()-(packetHeader.size()))+""));//固定值
    }
}
