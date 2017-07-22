package com.doohan.testbluetooth.BluetoothConnect;



import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import android.util.Log;

/**
 * AES加密解密算法 for PHP 后台
 * 
 * @author Reek_Lee
 * @time 2014-4-18 下午4:28:50
 * @email：1522651962@qq.com
 */

 
public class AES
{
	
	private  static String AES_KEY="EJKD93LJK8923ACD";
    private  static String AES_IV="23KCSLED43DFJKDE";
    /**加密
     * @time 2014-4-22 下午5:11:38
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) {
        try {
            String key = AES_KEY;
            String iv = AES_IV;
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return new String(Base64.encodeBase64(encrypted));
 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 
    /**解密
     * @time 2014-4-22 下午5:11:45
     * @param data
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data) {
        try
        {
            String key = AES_KEY;
            String iv = AES_IV;
            byte[] encrypted1 = Base64.decodeBase64(data.replaceAll(" ", "+").getBytes("utf-8"));
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
    }
}
    

//
//    /**加密
//     * @time 2014-4-28 下午5:13:55
//     * @param input
//     * @return
//     */
//    public static String encrypt(String input){
//    	
//    	byte[] crypted = null;
//    	try{
//    	SecretKeySpec skey = new SecretKeySpec(AES_KEY.getBytes(), "AES");
//    	Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//    	cipher.init(Cipher.ENCRYPT_MODE, skey);
//    	crypted = cipher.doFinal(input.getBytes("utf-8"));
//    	}catch(Exception e){
//    	System.out.println(e.toString());
//    	}
//    	return new String(Base64.encodeBase64(crypted));
//    }
//     
//    /**解密
//     * @time 2014-4-28 下午5:13:39
//     * @param input
//     * @return
//     */
//    public static String decrypt(String input){
//    	
//    	byte[] output = null;
//    	try{
//    	SecretKeySpec skey = new SecretKeySpec(AES_KEY.getBytes(), "AES");
//    	Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//    	cipher.init(Cipher.DECRYPT_MODE, skey);
//    	Log.v("input=", input.getBytes().length+"");
//    	Log.v("base 64", new String(Base64.decodeBase64(input.replaceAll(" ", "+").getBytes())));
//    	output = cipher.doFinal(Base64.decodeBase64(input.replaceAll(" ", "+").getBytes("utf-8")));
//    	if (output==null) {
//			Log.v("output","is null" );
//		}
//    	}catch(Exception e){
//    	System.out.println(e.toString());
//    	}
//    	
//    	return new String(output);
//    }
}
