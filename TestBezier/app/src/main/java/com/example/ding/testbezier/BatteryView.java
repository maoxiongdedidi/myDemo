package com.example.ding.testbezier;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * 表示电池的view
 * Created by 丁赵来 on 2017/8/29.
 */
public class BatteryView extends View { 

    private int mPower = 100;
   private int battery_width = 15;//图片的宽度
    private int battery_height = 25;//图片的高度
    private int battery_left = 10;//距离左边的位置
    private int battery_top = 10;//距离上边的位置
    private int battery_inside_margin = 3;//电量与方框的距离
    private int battery_head_width = 3;//电池头的宽
    private int battery_head_height = 3;//电池头的高
    public BatteryView(Context context) {
        super(context);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BatteryView);
        battery_width=typedArray.getInteger(R.styleable.BatteryView_battery_width,battery_width);
        battery_height=typedArray.getInteger(R.styleable.BatteryView_battery_height,battery_height);
        battery_left=typedArray.getInteger(R.styleable.BatteryView_battery_left,battery_left);
        battery_top=typedArray.getInteger(R.styleable.BatteryView_battery_top,battery_top);
        battery_inside_margin=typedArray.getInteger(R.styleable.BatteryView_battery_inside_margin,battery_inside_margin);
        battery_head_width=typedArray.getInteger(R.styleable.BatteryView_battery_head_width,battery_head_width);
        battery_head_height=typedArray.getInteger(R.styleable.BatteryView_battery_head_height,battery_head_height);
        typedArray.recycle();

    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);//Paint.Style.STROKE 只绘制图形轮廓（描边） Paint.Style.FILL 只绘制图形内容   Paint.Style.FILL_AND_STROKE 既绘制轮廓也绘制内容
        //先画外框
        Rect rect = new Rect(battery_left, battery_top,battery_left + battery_width, battery_top + battery_height);
        canvas.drawRect(rect, paint);
        float power_percent = mPower / 100.0f;


        Paint paint2 = new Paint(paint);
        paint2.setStyle(Paint.Style.FILL);
        //先画电池头
        int h_left = battery_left + battery_width/2-battery_head_width/2;
        int h_top = battery_top-battery_head_height ;
        int h_right = h_left + battery_head_width;
        int h_bottom = h_top + battery_head_height;
        Rect rect3 = new Rect(h_left, h_top, h_right, h_bottom);
        canvas.drawRect(rect3, paint2);




        //画电量
        if(power_percent != 0) {
            int p_left = battery_left + battery_inside_margin;
            int p_top = battery_top + battery_inside_margin;
            int p_right = p_left - battery_inside_margin + (int)((battery_width - battery_inside_margin) * power_percent);
            int p_bottom = p_top + battery_height - battery_inside_margin * 2;
            Rect rect2 = new Rect(p_left, p_top, p_right , p_bottom);
            canvas.drawRect(rect2, paint2);
        }


    }

    public void setPower(int power) {
        mPower = power;
        if(mPower < 0) {
            mPower = 0;
        }
        invalidate();
    }
    public int getBattery_width() {
        return battery_width;
    }

    public void setBattery_width(int battery_width) {
        this.battery_width = battery_width;
    }

    public int getBattery_height() {
        return battery_height;
    }

    public void setBattery_height(int battery_height) {
        this.battery_height = battery_height;
    }

    public int getBattery_left() {
        return battery_left;
    }

    public void setBattery_left(int battery_left) {
        this.battery_left = battery_left;
    }

    public int getBattery_top() {
        return battery_top;
    }

    public void setBattery_top(int battery_top) {
        this.battery_top = battery_top;
    }

    public int getBattery_inside_margin() {
        return battery_inside_margin;
    }

    public void setBattery_inside_margin(int battery_inside_margin) {
        this.battery_inside_margin = battery_inside_margin;
    }

    public int getBattery_head_width() {
        return battery_head_width;
    }

    public void setBattery_head_width(int battery_head_width) {
        this.battery_head_width = battery_head_width;
    }

    public int getBattery_head_height() {
        return battery_head_height;
    }

    public void setBattery_head_height(int battery_head_height) {
        this.battery_head_height = battery_head_height;
    }
}