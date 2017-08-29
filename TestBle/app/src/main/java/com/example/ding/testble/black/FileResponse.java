package com.example.ding.testble.black;



import com.example.ding.testble.agreement.M2cprotocol;
import com.example.ding.testble.agreement.Struct;

import java.nio.ByteOrder;

/**
 * Created by 丁赵来 on 2017/6/21.
 */

public class FileResponse extends Struct {
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }

    @Override
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }

    public final M2cprotocol.ble_packetHeader mBle_packetHeader=inner(new M2cprotocol.ble_packetHeader());//6个字节
    public final  Unsigned8 is_can_IAP=new Unsigned8();//是否升级
    public final Unsigned8 is_resume_broke= new Unsigned8();//续传1，新传0
    public final Unsigned32 first_block_sn= new Unsigned32();//首次传的序号
    public final Unsigned16 block_size= new Unsigned16();//每个包的大小
    public final Unsigned16 checksum= new Unsigned16();//校验位
}
