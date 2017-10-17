package com.example.ding.testble;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by 丁赵来 on 2017/9/25.
 */

public class ScreenManager {
    private static Stack<Activity> activityStack;

    private static ScreenManager instance;

    private ScreenManager(){

    }
    public static ScreenManager getScreenManager(){
        if(instance==null){
            instance=new ScreenManager();
        }
        return instance;

    }

//退出栈顶Activity

    public void popActivity(Activity activity){
        if(activity!=null){
            activity.finish();
            activityStack.remove(activity);
            activity=null;

        }

    }

//获得当前栈顶Activity

    public Activity currentActivity(){
        Activity activity=activityStack.lastElement();
        return activity;
    }

//将当前Activity推入栈中

    public void pushActivity(Activity activity){
        if(activityStack==null){
            activityStack=new Stack<Activity>();
        }
        activityStack.add(activity);

    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

//退出栈中所有Activity

    public void popAllActivityExceptOne(){
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context) {
        try {
            popAllActivityExceptOne();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
