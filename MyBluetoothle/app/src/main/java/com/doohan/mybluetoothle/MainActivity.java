package com.doohan.mybluetoothle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.util.Arrays;

import javolution.io.Struct;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button viewById = (Button) findViewById(R.id.button);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s="FFAA000601260103000101d7";
                String substring3 = s.substring(s.length() - 8, s.length() - 4);
                try {
                    int i= Integer.parseInt(substring3, 16);
                    Log.e("-----",substring3+"'");
                    Log.e("-----",i+"'");
                } catch (Exception e) {
                    e.printStackTrace();
                }




            }
        });


    }
    public static int ByteArrayToInt(byte b[]) throws Exception {
        int temp = 0, a=0;
        ByteArrayInputStream buf = new ByteArrayInputStream(b);

        DataInputStream dis= new DataInputStream (buf);
        return dis.readInt();

    }
    public static int bytesToInt2(byte[] src, int offset) {
        int value;
        value = (int) ( ((src[offset] & 0xFF)<<24)
                |((src[offset+1] & 0xFF)<<16)
                |((src[offset+2] & 0xFF)<<8)
                |(src[offset+3] & 0xFF));
        return value;
    }
    public static int byte2int(byte[] res) {
// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00); // | 表示安位或
               // | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }
    private byte[] getHexBytes(String message) {
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


    public  class TestHax extends Struct {

        public boolean isPacked() {
            System.out.println("");
            return true; // MyStruct is packed.
        }
        public final Unsigned16  name= new Unsigned16();
    }

}
