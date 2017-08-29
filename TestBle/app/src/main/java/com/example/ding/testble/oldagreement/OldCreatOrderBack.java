package com.example.ding.testble.oldagreement;

import com.example.ding.testble.agreement.M2cprotocol;
import com.example.ding.testble.agreement.Struct;

/**
 * Created by 丁赵来 on 2017/7/8.
 */

public class OldCreatOrderBack extends Struct {
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
    public final  M2cprotocol.ble_packetHeader packetHeader =inner(new M2cprotocol.ble_packetHeader());
    public final Struct.Unsigned8 para= new Unsigned8();//0,shibai ,1成功
    public final Unsigned16 check_digi1t = new Unsigned16();//2  校验位
}
