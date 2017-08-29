package com.example.ding.testble.black;



import com.example.ding.testble.agreement.M2cprotocol;
import com.example.ding.testble.agreement.Struct;

import java.nio.ByteOrder;


/**
 * Created by 丁赵来 on 2017/6/23.
 */

public class FileContentSend extends Struct {
    public static int dataSize;
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }
    public final  M2cprotocol.ble_packetHeader mBle_packetHeader=inner(new M2cprotocol.ble_packetHeader());//6个字节
    public final Unsigned32 block_sn= new Unsigned32();
    public final Unsigned8 block_len=new Unsigned8();
   public  FileData fileData;
    public final Unsigned16 checkNum = new Unsigned16();
    public FileData mFileData;
    public FileContentSend(int dataLength,int packetOrder, int  itrarily) {
      this.dataSize=dataLength;
        fileData=inner(new FileData());
        mBle_packetHeader. packetHeaderOne.set((short) 255);
        mBle_packetHeader. packetHeaderTwo.set((short) 204);
        mBle_packetHeader. data_length.set((short) packetOrder);
        mBle_packetHeader.arbitrarily.set((short) itrarily);
        mBle_packetHeader. gallery.set((short) 2);//通道
        mBle_packetHeader. pId.set((short) 3);//命令字
    }


    public static class FileData extends Struct{
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned8 data=new Unsigned8(FileContentSend.dataSize*8);
    }


}
