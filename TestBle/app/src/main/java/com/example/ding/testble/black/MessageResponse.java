package com.example.ding.testble.black;


import com.example.ding.testble.agreement.M2cprotocol;
import com.example.ding.testble.agreement.Struct;

/**
 * Created by 丁赵来 on 2017/6/21.
 */

public class MessageResponse extends Struct {
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
    public final  M2cprotocol.ble_packetHeader mBle_packetHeader=inner(new M2cprotocol.ble_packetHeader());//6个字节
    public final  Unsigned8 is_transfer_confirm_OK=new Unsigned8();//是否传输
    public final Unsigned16 checksum= new Unsigned16();//校验位
}
