package com.example.ding.testble;

import android.widget.Toast;



/**
 * Created by ding on 2017/3/17.
 */
public class ToastUtil {
    private static Toast toast;
    /**
     * @param stringId
     */
    public static void showShortToast(int stringId) {
        if (toast != null) {
            toast.cancel();
        }
        Toast.makeText(MyApplication.getContext(), stringId, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param stringId
     */


    public static void showShortToast(String stringId) {
        if (toast != null) {
            toast.cancel();
        }
        Toast.makeText(MyApplication.getContext(), stringId, Toast.LENGTH_SHORT).show();
    }
    /**
     * @param stringId
     */
    public static void showLongToast( int stringId) {
        if (toast != null) {
            toast.cancel();
        }
        Toast.makeText(MyApplication.getContext(), stringId, Toast.LENGTH_LONG).show();
    }

    /**
     * @param stringId
     */
    public static void showToast( int stringId) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), stringId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(stringId);
        }
        toast.show();
    }
}
