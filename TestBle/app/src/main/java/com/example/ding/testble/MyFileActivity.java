package com.example.ding.testble;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MyFileActivity extends ListActivity {
    private static final String ROOT_PATH = "sdcard/";
    //存储文件名称
    private ArrayList<String> mFileName = null;
    //存储文件路径
    private ArrayList<String> mFilePath = null;
    //重命名布局xml文件显示dialog
    private View view;

    private EditText editText;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_main_my);
        //显示文件列表
        showFileDir(ROOT_PATH);
    }

    /**
     * 扫描显示文件列表
     * @param path
     */
    private void showFileDir(String path) {
        mFileName = new ArrayList<String>();
        mFilePath = new ArrayList<String>();
        File file = new File(path);

         File[] files = file.listFiles();
        //如果当前目录不是根目录
        if (!ROOT_PATH.equals(path)) {
            mFileName.add("@1");
            mFilePath.add(ROOT_PATH);
            mFileName.add("@2");
            mFilePath.add(file.getParent());
        }
        //添加所有文件
        for (File f : files) {
            String name = f.getName();
            if(name.endsWith(".bin")||name.endsWith(".rmf")){
                mFileName.add(name);
                mFilePath.add(f.getPath());
            }
        }
        this.setListAdapter(new MyAdapter(this, mFileName, mFilePath));
    }

    /**
     * 点击事件
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String path = mFilePath.get(position);
        Log.e("=========", Environment.getExternalStorageDirectory()
                .getPath()+path);
        File file = new File(path);

        // 文件存在并可读
        if (file.exists() && file.canRead()) {
            if (file.isDirectory()) {
                //显示子目录及文件
                showFileDir(path);
            } else {
                Intent intent = new Intent();
                intent.putExtra("data",path);
                setResult(RESULT_OK,intent);
                finish();


               /* try{
                    FileInputStream fis = new FileInputStream(file);

                    ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = fis.read(buffer)) != -1) {
                        outSteam.write(buffer, 0, len);
                    }

                    byte[] res=outSteam.toByteArray();

                    outSteam.close();
                    fis.close();
                    Log.e("=========", Arrays.toString(res));
                    Intent intent = new Intent();
                    intent.putExtra("data",res);
                    setResult(RESULT_OK,intent);
                    finish();
                    //setResult();
                }catch(Exception e){
                    e.printStackTrace();
                }*/
            }
        }
        //没有权限
        else {
            Resources res = getResources();
            new AlertDialog.Builder(this).setTitle("Message")
                    .setMessage(res.getString(R.string.no_permission))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "没有权限删除不了..", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        }
        super.onListItemClick(l, v, position, id);
    }

}