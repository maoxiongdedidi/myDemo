package com.example.ding.testble.black;

/**
 * Created by 丁赵来 on 2017/6/22.
 */

public class FileContent {

    public static void main(String[] args) {
        String hex="FFAA013EA1543142413030485941453032333356314241303048594145303034391E00003DB9F14200AFF1EC41050913383634353031303338323735363236012A1E11CB";

       /* byte[] bytes= DataTreatingUtils.getHexBytes(hex);
        for (byte byt:bytes){
            ReduceParse.reduce(byt, bytes.length, new ReduceParse.RedudeParseCallBack() {
                @Override
                public void ok() {
                    System.out.print("ok");
                }

                @Override
                public void failed() {
                    System.out.print("failed");
                }
            });
        }*/
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
    public static int byteint(byte[] res) {
        // res = InversionByte(res);
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00); // | 表示安位或
        return targets;
    }


}
