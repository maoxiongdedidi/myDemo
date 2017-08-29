package com.example.ding.testble.ble;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by ding on 2017/1/11.
 */
public class FileUtils {
    private Context mContext;

    public FileUtils(Context context) {
        mContext = context;
    }

    //读数据
    public  byte[] readFile() throws IOException {
        

        try{
            InputStream is =mContext.getAssets().open("vcu_app_sleep.bin");
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }

           byte[] res=outSteam.toByteArray();

            outSteam.close();
            is.close();
            return res;
        }catch(Exception e){
            e.printStackTrace();
        }


        return null;

    }




    public HashMap<Integer,byte[]> initData(byte[] filecontent, int blockSize) {

        HashMap<Integer,byte[]> map = new HashMap<>();

        // 等待发送完毕

        byte[] bb=filecontent;


            int a, b;
            a = bb.length / blockSize;
            b = bb.length - a * blockSize;
            for (int i = 0; i < a; i++) {
                byte[] buf = new byte[blockSize];
                System.arraycopy(bb, i * blockSize, buf, 0, blockSize);

                map.put(i,buf);
            }

            if (b > 0) {
                byte[] b1= new byte[b];
                System.arraycopy(bb, a * blockSize, b1, 0, b);
                map.put(a,b1);

            }
/*

        for (Map.Entry<Integer, byte[]> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + Arrays.toString(entry.getValue()));
        }
*/

            return map;


    }




    /**
     *字符串转化成16进制数组
     */

    private  byte[] getHexBytes(String message) {
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

    private  String[] getTenBytes(String message) {
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
     *数组合并
     */
    public  byte[] byteMerger(byte[] byte_1, byte[] byte_2){
        byte[] byte_3 = new byte[byte_1.length+byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }
    /**
     * 去除空格
     */
    public String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    /**
     * 转换成16进制
     */
    public String bytes2hex02(byte[] bytes)
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

}
