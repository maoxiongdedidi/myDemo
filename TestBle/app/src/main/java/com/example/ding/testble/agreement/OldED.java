package com.example.ding.testble.agreement;


/**
 * Created by 韩飞虎 on 2017/3/4.
 */
public class OldED extends Struct {
    /*  public ByteOrder byteOrder() {
      return ByteOrder.LITTLE_ENDIAN;
  }*/
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }

    public final  M2cprotocol.packetHeader packetHeader =inner(new M2cprotocol.packetHeader());
    public final Struct.Unsigned8 pId = new Struct.Unsigned8();//1
    public final Struct.Signed64 message_id= new Signed64();
    public final M2cprotocol.SN_t VCU_SN =inner(new M2cprotocol.SN_t());//8
    public final Struct.Unsigned16 check_digit = new Struct.Unsigned16();//2  校验位


    public OldED(){
        pId.set((short) 224);//固定值
        packetHeader.packet1.set((short) 255);//固定值
        packetHeader.packet2.set((short) 204);//固定值
        message_id.set(System.currentTimeMillis());

        packetHeader.packet_length.set(Short.valueOf((this.size()-(packetHeader.size()+2))+""));//固定值
    }
}
