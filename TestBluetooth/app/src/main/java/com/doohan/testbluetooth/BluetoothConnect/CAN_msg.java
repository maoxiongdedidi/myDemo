package com.doohan.testbluetooth.BluetoothConnect;



public class CAN_msg
{		
	public int id = 0;
	public byte data[] = new byte[8];
	public byte len = 0;
	public byte ch = 0;		
	public Common.CAN_FORMAT format = Common.CAN_FORMAT.STANDARD_FORMAT;
	public Common.CAN_FRAME type = Common.CAN_FRAME.DATA_FRAME;
	public static final int CAN_MSG_LEN = 16;
	public byte ex_data[] = null;
	
	private byte bytes[] = new byte[CAN_MSG_LEN];
	
	public CAN_msg(byte buf[])
	{
		id = 0;
		int offset = 4;
		id |= buf[3]; id <<= 8; 
		id |= buf[2]; id <<= 8; 
		id |= buf[1]; id <<= 8; 
		id |= buf[0]; 
		for(int i = 0 ; i < 8 ; i ++)
			data[i] = buf[offset ++];
		len = buf[offset ++];
		ch = buf[offset ++];
		if(buf[offset ++] == 0)
			format = Common.CAN_FORMAT.STANDARD_FORMAT;
		else
			format = Common.CAN_FORMAT.EXTENDED_FORMAT;
		
		if(buf[offset ++] == 0)
			type = Common.CAN_FRAME.DATA_FRAME;
		else
			type = Common.CAN_FRAME.REMOTE_FRAME;
		
		//检查是否是扩展型数据帧
		int checklen = (len & 0xFF);
		if(checklen == 254) //扩展型数据帧
		{
			int real_data_len = getIntValue(0);
			if(real_data_len != (buf.length - CAN_MSG_LEN))
				return;

			ex_data = new byte[real_data_len];
			
			for(int i = 0 ; i < real_data_len ; i ++)
				ex_data[i] = buf[offset ++];				
		}
	}
	
	public CAN_msg()
	{
		clearData();
	}		
	
	public byte [] getBytes()
	{
		int offset = 0;
		bytes[offset ++] = (byte) ((id & 0xFF));
		bytes[offset ++] = (byte) ((id & 0xFF00) >> 8);
		bytes[offset ++] = (byte) ((id & 0xFF0000) >> 16);
		bytes[offset ++] = (byte) ((id & 0xFF000000) >> 24);
		for(int i = 0 ; i < data.length ; i ++)
			bytes[offset ++] = data[i];
		bytes[offset ++] = len;
		bytes[offset ++] = ch;
		bytes[offset ++] = (byte) ((format == Common.CAN_FORMAT.STANDARD_FORMAT) ? 0 : 1);
		bytes[offset ++] = (byte) ((type == Common.CAN_FRAME.DATA_FRAME) ? 0 : 1);
		
		return bytes;
	}
	
	public void clearData()
	{
		for(int i = 0 ; i < data.length ; i ++)
			data[i] = 0;
	}
	
	public int getIntValue(int offset)
	{
		int ret = 0;
		if(offset > 4)
			return 0; 
		ret |= (data[offset + 3] & 0xFF); ret <<= 8;
		ret |= (data[offset + 2] & 0xFF); ret <<= 8;
		ret |= (data[offset + 1] & 0xFF); ret <<= 8;
		ret |= (data[offset + 0] & 0xFF);
		return ret;		
	}
	
	public byte getByteValue(int pos)
	{
		if(pos < 0 || pos > 7)
			return 0;
		
		return data[pos];
	}
	
	public short getShortValue(int offset)
	{
		short ret = 0;
		if(offset > 6)
			return 0;
		ret |= (data[offset + 1] & 0xFF); ret <<= 8;
		ret |= (data[offset + 0] & 0xFF);
		return ret;			
	}
	
	public long getLongValue()
	{
		long ret = 0;
		ret |= (data[7] & 0xFF); ret <<= 8;
		ret |= (data[6] & 0xFF); ret <<= 8;
		ret |= (data[5] & 0xFF); ret <<= 8;
		ret |= (data[4] & 0xFF); ret <<= 8;
		ret |= (data[3] & 0xFF); ret <<= 8;
		ret |= (data[2] & 0xFF); ret <<= 8;
		ret |= (data[1] & 0xFF); ret <<= 8;
		ret |= (data[0] & 0xFF);
		return ret;	
	}
}