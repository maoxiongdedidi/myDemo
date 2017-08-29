package com.example.ding.testble.agreement;

import java.nio.ByteOrder;

/**
 * Created by 丁赵来 on 2017/7/14.
 */

public class F9 extends Struct {
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
    public final  M2cprotocol.ble_packetHeader packetHeader =inner(new M2cprotocol.ble_packetHeader());
    public final Struct.Signed64 message_id= new Signed64();//8
    public final M2cprotocol.Cloud_SN_t       cloud_SN = inner(new M2cprotocol.Cloud_SN_t     ());//8
    public final Unsigned32 cmd= new Unsigned32();//里程
    public final Unsigned32 para1=new Unsigned32();//cmd=2时：0表示关闭自动大灯 1开启自动大灯    cmd==0是次字段表示里程值（清空里程就是0，其他数字就是具体的里程值）
    public final Unsigned32 para2= new Unsigned32();//开启自动大灯时，开灯的光感值0-40
    public final Unsigned32 para3=new Unsigned32();//开启自动大灯时，关灯的光感值40-100
    public final Unsigned32 para4= new Unsigned32();
    public final Unsigned16 check_digi1t = new Unsigned16();//2  校验位


    public F9() {
        packetHeader.packetHeaderOne.set((short) 255);
        packetHeader.packetHeaderTwo.set((short) 204);
        packetHeader.gallery.set((short) 2);
        packetHeader.pId.set((short) 249);
        packetHeader.arbitrarily.set((short) 1);
        message_id.set(((System.currentTimeMillis()/256)*256)+2);
        cloud_SN.VCU_SN.sn.set("V10000");
        cloud_SN.TBox_SN.sn.set("T10000");
        packetHeader.data_length.set(Short.valueOf((this.size()-(packetHeader.size()))+""));//固定值

    }
}
