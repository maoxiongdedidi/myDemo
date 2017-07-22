package com.doohan.myapplication222;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyExplorerDemo";
    private static final int REQUEST_EX = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("explorer_title",
                        getString(R.string.dialog_read_from_dir));
                intent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");
                intent.setClass(MainActivity.this, ExDialog.class);
                startActivityForResult(intent, REQUEST_EX);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        String path;
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_EX) {
                Uri uri = intent.getData();
                TextView text = (TextView) findViewById(R.id.text);
                text.setText("select: " + uri);
            }
        }
    }
}
