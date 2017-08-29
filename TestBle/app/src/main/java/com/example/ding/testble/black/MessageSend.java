package com.example.ding.testble.black;



import com.example.ding.testble.agreement.M2cprotocol;
import com.example.ding.testble.agreement.Struct;

import java.nio.ByteOrder;


/**
 * Created by 丁赵来 on 2017/6/22.
 */

public class MessageSend extends Struct {
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
    @Override
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }
    public final  M2cprotocol.ble_packetHeader mBle_packetHeader=inner(new M2cprotocol.ble_packetHeader());//6个字节
    public final  Unsigned32 all_block_nbr=new Unsigned32();//是否传输
    public final Unsigned8 is_resume_broken= new Unsigned8();//续传1，新传2
    public final Unsigned32 first_block_sn=new Unsigned32();//首次传的序号
    public final Unsigned16 block_size=new Unsigned16();//单次包最大长度
    public final Unsigned16 checksum= new Unsigned16();//校验位

    public MessageSend() {
        mBle_packetHeader.packetHeaderOne.set((short) 255);
        mBle_packetHeader.packetHeaderTwo.set((short) 204);
        mBle_packetHeader.data_length.set((short) 13);
        mBle_packetHeader.arbitrarily.set((short) 1);
        mBle_packetHeader.gallery.set((short) 2);
        mBle_packetHeader.pId.set((short) 2);
    }
}
