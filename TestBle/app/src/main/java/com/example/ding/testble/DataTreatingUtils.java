package com.example.ding.testble;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javolution.io.Struct;


/**
 * Created by ding on 2017/2/28.
 */
public class DataTreatingUtils {

    public String devicesName;
    public String bleAdress;


    public DataTreatingUtils() {
        //初始化结构体中的内容，加快速度
        TestHax testHax = new TestHax();
        testHax.name.set(1);
    }



    /**
     *字符串转化成16进制数组
     */

    public static   byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }
    public static int byte2int(byte[] b) {
        int res = 0;
        int bLen = b.length;

        if (bLen < 5) {// int 最大到4个字节
            for (int i = 0; i < bLen; i++) {
                res += (b[i] & 0xFF) << (8 * i);
            }
        }

        return res;
    }
    /**
     * 添加校验值
     */
    public static byte[] jiaoyan(byte[] bytes1){
        String s1 = bytes2hex02(bytes1);
        String[] hexBytes = getTenBytes(s1);
        int numbersum =0;
        for (int z = 0; z <hexBytes.length ; z++) {
            numbersum=numbersum+ Integer.parseInt(hexBytes[z],16);
        }
        TestHax t16two= new TestHax();
        byte[] Checksum= new byte[2];
        t16two.name.set(numbersum);
        t16two.getByteBuffer().get(Checksum);
        byte[] allbyte = byteMerger(bytes1, Checksum);
        return Checksum;
    }

    /**
     * 转换成16进制两字节
     */
    public static  class TestHax extends Struct {

        public boolean isPacked() {
            System.out.println("");
            return true; // MyStruct is packed.
        }
        public final Unsigned16  name= new Unsigned16();
    }
    /**
     *数组合并
     */
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){
        byte[] byte_3 = new byte[byte_1.length+byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }
    /**
     *获取16进制字符串数组
     */
    public static String[] getTenBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return hexStr;
    }
    /**
     * 转换成16进制
     */
    public static String bytes2hex02(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : bytes)
        {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1)// 每个字节8为，转为16进制标志，2个16进制位
            {
                tmp = "0" + tmp;
            }
            sb.append(tmp);
        }
        return sb.toString();

    }
    /**
     *字符串去除空格
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    /**
     * Object 转化成百分比 传如的参数必须是数字类型， 如"0.02" return 2.00%
     * <p/>
     * 百分比位数 参数可自行调整
     *
     * @param obj
     * @return 返回百分比
     */
    public static String parsePercent(Object obj) {
        Double d = Double.parseDouble(obj.toString());
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(2); //最大小数位数
        percentFormat.setMaximumIntegerDigits(3);//最大整数位数
        percentFormat.setMinimumFractionDigits(2); //最小小数位数
        percentFormat.setMinimumIntegerDigits(1);//最小整数位数
        return percentFormat.format(d);//自动转换成百分比显示
    }
    /**
     * 将蓝牙地址转化成标准
     */
    public static String bleTreating(String s ){
        String newstr = "";
        int size = ((s.length())%2 == 0) ? ((s.length())/2):((s.length())/2+ 1);
        for(int i=0;i<size ;i++){
            int endIndex = (i+1)*2;
            if((i+1)==size){
                endIndex = s.length();
            }
            if(i==0){
                newstr += s.substring(i,endIndex);
            }else{
                newstr += ":"+s.substring(i*2, endIndex);
            }
        }
        return newstr;
    }



}
