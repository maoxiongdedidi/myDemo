package com.example.ding.testble.agreement;


import java.io.Serializable;
import java.nio.ByteOrder;

/**
 * Created by admin on 2017/1/6.
 */
public class M2cprotocol {
    public static class SN_t extends Struct {

        public boolean isPacked() {

            return true; // MyStruct is packed.
        }

        /*    public ByteOrder byteOrder() {
                return ByteOrder.LITTLE_ENDIAN;
            }*/
        public final Struct.UTF8String sn= new UTF8String(14);
    }


    public static class Cloud_SN_t extends Struct {

        public boolean isPacked() {

            return true; // MyStruct is packed.
        }

        public final M2cprotocol.SN_t TBox_SN = inner(new M2cprotocol.SN_t());
        public final M2cprotocol.SN_t VCU_SN= inner(new M2cprotocol.SN_t());
    }


    public static class ver_info_t extends Struct {
        public ByteOrder byteOrder() {
            return ByteOrder.LITTLE_ENDIAN;
        }
        /*  public ByteOrder byteOrder() {return ByteOrder.BIG_ENDIAN;}*/
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned32 sw= new Unsigned32(); //软件版本
        public final Unsigned8 hw= new Unsigned8(); //硬件版本

    }

    public static class  rtc_time_t extends Struct{
        public ByteOrder byteOrder() {
            return ByteOrder.LITTLE_ENDIAN;
        }
        /*  public ByteOrder byteOrder() {return ByteOrder.BIG_ENDIAN;}*/
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned16 tm_year= new Unsigned16(); //年
        public final Unsigned8 tm_mon= new Unsigned8(); //月
        public final Unsigned8 tm_day= new Unsigned8(); //日
        public final Unsigned8 tm_hour= new Unsigned8(); //时
        public final Unsigned8 tm_min= new Unsigned8(); //分
        public final Unsigned8 tm_sec= new Unsigned8(); //秒

    }


    public static class VCU_info_t extends Struct {
        /*    public ByteOrder byteOrder() {return ByteOrder.BIG_ENDIAN;}*/
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned8 temperature_sens= new Unsigned8();
        public final Unsigned8 light_sens= new Unsigned8();
    }
    public static class IMU_info_t extends Struct {
        /*        public ByteOrder byteOrder() {return ByteOrder.BIG_ENDIAN;}*/
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned16 pitch= new Unsigned16();
        public final Unsigned16 roll= new Unsigned16();
        public final Unsigned16 yaw= new Unsigned16();
    }
    public static class alarm_info_t extends Struct {
        /* public ByteOrder byteOrder() {return ByteOrder.BIG_ENDIAN;}*/
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public  IMU_info_t IMU_sens=inner(new IMU_info_t());//67-73
        public final Unsigned8 light_sens= new Unsigned8();//73-74
        public final Unsigned8 driver_outlier_ID= new Unsigned8();//74-75
        public final Unsigned8 driver_outlier_value= new Unsigned8();
        public final Unsigned8 is_fence= new Unsigned8();
        public final Unsigned8 now_mode= new Unsigned8();
    }
    public static class GPS_info_t extends Struct {
        /*      public ByteOrder byteOrder() {return ByteOrder.BIG_ENDIAN;}*/
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned8 Lng_type= new Unsigned8();//36
        public final Float32 Lng= new Float32();//40
        public final Unsigned8 Lat_type= new Unsigned8();//41
        public final Float32 Lat= new Float32();//45
        public final Unsigned8 gps_effective= new Unsigned8();//
        public final Unsigned8 number_satellite= new Unsigned8();//
    }
    public static class GPRS_info_t extends Struct  {
        /*        public ByteOrder byteOrder() {return ByteOrder.BIG_ENDIAN;}*/
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned8 csq= new Unsigned8();
        public final UTF8String imsi= new UTF8String(15);


    }
    public static class MCU_info_t extends Struct implements Serializable {
        /*        public ByteOrder byteOrder() {return ByteOrder.BIG_ENDIAN;}*/
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned8 motor_lock= new Unsigned8();
        public final Unsigned8 motor_voltage= new Unsigned8();
        public final Unsigned8 motor_current= new Unsigned8();
        public final Unsigned8 mcu_ver_hw= new Unsigned8();
        public final Unsigned8 motor_state= new Unsigned8();
        public final Unsigned8 gear= new Unsigned8();
        public final Unsigned8 mcu_state= new Unsigned8();

        public Unsigned8 getMotor_lock() {
            return motor_lock;
        }

        public Unsigned8 getMotor_voltage() {
            return motor_voltage;
        }

        public Unsigned8 getMotor_current() {
            return motor_current;
        }

        public Unsigned8 getMcu_ver_hw() {
            return mcu_ver_hw;
        }

        public Unsigned8 getMotor_state() {
            return motor_state;
        }

        public Unsigned8 getGear() {
            return gear;
        }

        public Unsigned8 getMcu_state() {
            return mcu_state;
        }
    }
    public static class BMS_info_t extends Struct {
        /*        public ByteOrder byteOrder() {return ByteOrder.BIG_ENDIAN;}*/
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned8 battery_id_nbr= new Unsigned8();
        /**
         * 主电池电量
         */
        public final Unsigned8 power= new Unsigned8();
    }

    public static class BMS_id_info_t extends Struct {
        /*       public ByteOrder byteOrder() {return ByteOrder.BIG_ENDIAN;}*/
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned8 total_current= new Unsigned8();
        public final Unsigned8 total_voltage= new Unsigned8();
        public final Unsigned8 buff_power= new Unsigned8();
        public final Unsigned8 cycletimes= new Unsigned8();
        public final Unsigned8 power_per_cent= new Unsigned8();
        public final Unsigned8 ver_hw= new Unsigned8();
        public final Unsigned8 state= new Unsigned8();
        public final Unsigned8 temputer_nbr= new Unsigned8();
    }
    public static class ICL_info_t extends Struct {
        /*        public ByteOrder byteOrder() {return ByteOrder.BIG_ENDIAN;}*/
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned32 switch_status= new Unsigned32();
        public final Signed64 mileage_accumulation= new Signed64();
    }
    public static class amuse_info_t extends Struct {
        /*    public ByteOrder byteOrder() {return ByteOrder.BIG_ENDIAN;}*/
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned32 state= new Unsigned32();
        public final Unsigned32 bt_data_state= new Unsigned32();
        public final Unsigned32 bt_audio_state= new Unsigned32();
    }

    public static class packetHeader extends Struct {
        /*        public ByteOrder byteOrder() {return ByteOrder.BIG_ENDIAN;}*/
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned8  packet1= new Unsigned8();
        public final Unsigned8 packet2= new Unsigned8();
        public final Unsigned8  packet_sequence= new Unsigned8();
        public final Unsigned8 packet_length= new Unsigned8();
    }


    public static class lamp_info_t extends Struct {
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned8 hi_lamp= new Unsigned8();//远光灯
        public final Unsigned8 low_lamp= new Unsigned8();//近光灯
        public final Unsigned8 L_lamp= new Unsigned8();//左转向灯
        public final Unsigned8 R_lamp= new Unsigned8();//右转向灯
        public final Unsigned8 atmosphere_lamp= new Unsigned8();//氛围灯
        public final Unsigned8 one_key_lamp= new Unsigned8();//一键启动的
        public final Unsigned8 break_lamp= new Unsigned8();//刹车灯
        public final Unsigned8 hi_key_lamp= new Unsigned8();//H键灯
    }
    public static class check_digit extends Struct {
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned16 digit= new Unsigned16();//校验位

    }
    public static class speedctl_info_t extends Struct {
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned16 h_speed= new Unsigned16();//限速

    }

    /**
     * 播放声音
     */
    public static class audioplay_info_t extends Struct {
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }
        public final Unsigned8 cmd= new Unsigned8();//播放控制
        public final Unsigned8 loop= new Unsigned8();//播放文件编号
        public final Unsigned8 music_NO= new Unsigned8();//循环次数
        public final  Unsigned8 volume=new Unsigned8();

    }


    /**
     * ftp
     */
    public static class FTP_info_t extends Struct {
        public boolean isPacked() {
            return true; // MyStruct is packed.
        }


        public final UTF8String ftp_address= new UTF8String(20);//播放控制
        public final Unsigned16 port= new Unsigned16();//播放文件编号
        public final UTF8String user_name= new UTF8String(16);//循环次数
        public final UTF8String password= new UTF8String(16);//循环次数
        public final UTF8String dir= new UTF8String(20);//循环次数
        public final UTF8String filename= new UTF8String(15);//循环次数

    }



    public static class ble_packetHeader extends Struct{
    public boolean isPacked() {
        return true; // MyStruct is packed.
    }
    public final Unsigned8 packetHeaderOne= new Unsigned8();//包头1
    public final Unsigned8 packetHeaderTwo= new Unsigned8();//包头2
    public final Unsigned8 arbitrarily= new Unsigned8();//任意数字
    public final Unsigned8 data_length=new Unsigned8();//帧长
    public final Unsigned8 gallery=new Unsigned8();//通道
    public final Unsigned8 pId= new Unsigned8();//命令字

    public ble_packetHeader() {
    }

    public ble_packetHeader(int packetOrder, int  itrarily, int pid) {

      packetHeaderOne.set((short) 255);
        packetHeaderTwo.set((short) 204);
       data_length.set((short) packetOrder);
        arbitrarily.set((short) itrarily);
        gallery.set((short) 2);//通道
        pId.set((short) pid);//命令字
    }
}





}
