package com.example.ding.testble;

/**
 * Created by ding on 2016/11/1.
 */
public class LoginJson {


    /**
     * success : true
     * message : OK
     * data :
     */

    private boolean success;
    private String message;
    private String data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
