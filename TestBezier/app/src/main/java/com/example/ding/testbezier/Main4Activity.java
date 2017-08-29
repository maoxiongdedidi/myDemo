package com.example.ding.testbezier;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {

    private GridView gv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        gv = (GridView)findViewById(R.id.gridview);
        //为GridView设置适配器
        gv.setAdapter(new MyAdapter(this));

      //  gv.setColumnWidth(90);//每一列的宽度


       /* android:numColumns="auto_fit"

        android:verticalSpacing="10dp"
        android:horizontalSpacing="10dp"

        //图片的模式
        android:stretchMode="columnWidth"
        //对齐方式
        android:gravity="center"*/

        gv.setNumColumns(3);//每一行能显示出几列，auto_fit自动设置,也可以指定列数
        gv.setVerticalSpacing(30);//水平和竖直方向上的间距
       // gv.setHorizontalSpacing(10);

    }


    //自定义适配器
    class MyAdapter extends BaseAdapter {
        //上下文对象
        private Context context;
        //图片数组
        private Integer[] imgs = {
                R.mipmap.ic_launcher,  R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,  R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,  R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,  R.mipmap.ic_launcher, R.mipmap.ic_launcher,

        };
        MyAdapter(Context context){
            this.context = context;
        }
        public int getCount() {
            return imgs.length;
        }

        public Object getItem(int item) {
            return item;
        }

        public long getItemId(int id) {
            return id;
        }

        //创建View方法
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder vh=null;
            if(convertView==null){
                vh=new ViewHolder();
                convertView=View.inflate(context,R.layout.layout,null);
                convertView.setTag(vh);
            }else {
                vh= (ViewHolder) convertView.getTag();
            }
            vh.mImageView= (ImageView) convertView.findViewById(R.id.image);
            vh.mImageView.setImageResource(R.mipmap.ic_launcher);
            vh.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"点击的是"+position,Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    }
    public class ViewHolder{
        ImageView mImageView;
    }

}
