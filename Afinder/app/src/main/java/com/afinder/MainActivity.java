package com.afinder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.afinder.read.InformationScanViewFile;
import com.afinder.util.Util;
import com.example.afinder.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

  
public class MainActivity extends Activity { 
  private  ArrayList<String> search_result = new ArrayList<String>();//
  private ArrayList<String> filterDateList = new ArrayList<String>();
  ArrayList<HashMap<String, Object>> itemdatafilter=null;
  private ListView file_list;
  private SimpleAdapter madapter;
  private ClearEditText inputsearch;//
  private String infopath;
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.search_list);  
        InitView();  
    }  
  private void InitView(){
	  file_list=(ListView)this.findViewById(R.id.file_list);	 
	  try {
			LoadData();
		} catch (Exception e) {
			Toast.makeText(this, "δ��ȡ����Ϣ", Toast.LENGTH_LONG).show();
		}
	  
	  file_list.setOnItemClickListener(itemListenerfile);	  
	  inputsearch=(ClearEditText)this.findViewById(R.id.inputsearch);
	  inputsearch.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//
				filterData(s.toString());
			}			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {				
			}			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
  }
  
  private void LoadData() throws Exception {  
		boolean hasSDCard = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (hasSDCard) {
			infopath = Environment.getExternalStorageDirectory().toString()
					+ File.separator + "xautcate/";
		} else {
			infopath = Environment.getDownloadCacheDirectory().toString()
					+ File.separator + "xautcate/";
		}
		File file = new File(infopath);
		if (!file.exists()) {

			file.mkdirs();
		}
		getFileList(file, search_result);
	file_list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,search_result)); ////�������� 	
	filterDateList=search_result;
  }	
	/**
	 *
	 * 
	 * **/
	OnItemClickListener itemListenerfile = new OnItemClickListener() {
		//��ȡ�ļ����µ�word�ļ�
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub						
				String filename=filterDateList.get(position).toString();
				String filePath=infopath+filename; 
				   /* ȡ����չ�� */  
			 String end=filename.substring(filename.lastIndexOf(".")+1,  
						   filename.length()).toLowerCase();   
				if(end.equals("doc")){
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, InformationScanViewFile.class);				
					startActivity(intent);
					Util.pathfile=filePath;
				}else if(end.equals("pdf")){					
					getPdfFileIntent(filePath);
				}else if(end.equals("mp4")){	
					File file = new File(filePath);
					Intent intent = getVideoFileIntent(file);
			    	startActivity(intent);
								
			}
	    }
	};

    public  Intent getPdfFileIntent( String filePath )
    {

      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.addCategory(Intent.CATEGORY_DEFAULT);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      
      Uri uri = Uri.fromFile(new File(filePath));
      intent.setDataAndType(uri, "application/pdf");
      startActivity(intent);
      return intent;
    }
    //android�в��ű�����Ƶ�ĵ���
    public Intent getVideoFileIntent(File videoFile)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(videoFile);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }
    private void getFileList(File path, ArrayList<String> search_result){  
              //������ļ��еĻ�   
              if(path.isDirectory()){     
                  //�����ļ������е�����      
                  File[] files = path.listFiles();      
                  //���ж�����û��Ȩ�ޣ����û��Ȩ�޵Ļ����Ͳ�ִ����     
                  if(null == files)    
                      return;
                  for(int i = 0; i < files.length; i++){     
                      getFileList(files[i], search_result);      
                  }                  
              }  
              //������ļ��Ļ�ֱ�Ӽ���     
              else{
                  //�����ļ��Ĵ���     
                  String filePath = path.getAbsolutePath();     
                  //�ļ���      
                  String fileName = filePath.substring(filePath.lastIndexOf("/")+1);      
                  //���    
                  search_result.add(fileName);               
              }  
          }
	private void filterData(String filterStr){		
		//break�ǽ�����ǰ��ѭ��������ѭ�������Ĳ��ֻ��ǻ�ִ��
		filterDateList=new ArrayList<String>();
		 if(filterDateList==null){
			  return; //return�Ƿ��ص���˼������������ǰִ�еķ���,����ִ�к������䣻
		  }	 	
		if(!TextUtils.isEmpty(filterStr)){	
			filterDateList.clear();		 
			for (int i = 0; i < search_result.size(); i++) {
				if(search_result.get(i).contains(filterStr)){
					filterDateList.add(search_result.get(i));
				}
		     }
		}else
		{
			filterDateList=search_result;
		}
		getDataFilter(filterDateList);		
		file_list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,filterDateList)); ////�������� 						
	}	
	private void getDataFilter(ArrayList<String> fieldlist) {
		itemdatafilter=new ArrayList<HashMap<String, Object>>();
		if(fieldlist.size()==0)
		{
			Toast.makeText(this, "û����Ҫ���ҵ���Ϣ", 3000).show();
		}
		for (int i = 0; i < fieldlist.size(); i++) {
			try {
				HashMap<String, Object> map = new HashMap<String, Object>();								
				map.put("list", fieldlist.get(i));
				itemdatafilter.add(map);				
			} catch (Exception e) {				
				e.printStackTrace();				
			}
		}
	}
}   
