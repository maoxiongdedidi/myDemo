package com.example.ding.testtvp;



import com.example.ding.testtvp.ble.SampleGattAttributes;

import java.util.UUID;

/**
 * Created by ding on 2016/11/1.
 */
public class Constant {

  //   public static String DOOHANURL = "http://192.168.1.45:8080/";//飞虎端
    //public static final String DOOHANURL = "http://192.168.1.149:8081/";//王慧龙端
     public static String DOOHANURL="http://121.41.47.20/doohan-api/";
    //public static final String DOOHANURL = "http://120.27.144.45/doohan-api/";
    // public static final String DOOHANURL="http://api.doohan.cn/doohan-api/";
    public static String SMSTEST = DOOHANURL + "api/sms/sendCaptcha.json";
    public static String REGISTER = DOOHANURL + "api/user/register.json";
    public static String LOGIN = DOOHANURL + "api/user/login.json";
    public static String CHANGEPASSWOED = DOOHANURL + "api/user/resetPassword.json";
    public static String GETUSER = DOOHANURL + "api/user/userInfo.json";
    public static String LOGOUT = DOOHANURL + "api/user/logout.json";
    public static String CHANGEPHONE = DOOHANURL + "api/user/modifyMobile.json";
    public static String MYVEHICLE = DOOHANURL + "api/vehicle/myVehicle.json";
    public static String CHANGEUSER = DOOHANURL + "api/user/modifyInfo.json";
    public static String RETRIACTION = DOOHANURL + "api/user/feedback.json";
    public static String CHANGEHEAD = DOOHANURL + "api/user/modifyHeadImg.json";
    public static String CARCONDITION = DOOHANURL + "api/indexPage.json";
    public static String USERCONFIGINFO = DOOHANURL + "api/user/userConfigInfo.json";
    public static String UPDATEUSERCONFIG = DOOHANURL + "api/user/updateUserConfig.json";
    public static String VEHICLEBINDING = DOOHANURL + "api/vehicle/binding.json";
    public static String VEHICLEDETAILS = DOOHANURL + "api/vehicle/vehicleDetails.json";
    public static String SWITCHINGVEHICLES = DOOHANURL + "api/vehicle/switchingVehicle.json";
    public static String MODIFYVEHICLEINFO = DOOHANURL + "api/vehicle/modifyVehicleInfo.json";
    public static String REMOVEBINDING = DOOHANURL + "api/vehicle/removeBinding.json";
    public static String VVEHICLEBINDINGUSER = DOOHANURL + "api/vehicle/vehicleBindingUser.json";
    public static String GRANTRESULTS = DOOHANURL + "api/vehicle/grantResults.json";
    public static String MESSAGELIST = DOOHANURL + "api/message/messageList.json";
    public static String UPDATEMESSAGE = DOOHANURL + "api/message/updateMessage.json";
    public static String MARKALLREAD = DOOHANURL + "api/message/markAllRead.json";
    public static String EXAMINATION = DOOHANURL + "api/carState/examination.json";
    public static String VEHICLEPOSITION = DOOHANURL + "api/vehicle/vehiclePosition.json";
    public static String GRANTMESSAGE = DOOHANURL + "api/message/grantMessage.json";
    public static String STARTPAGE = DOOHANURL + "api/config/startPage.json";
    public static String GETSATELLITESNUMBER = DOOHANURL + "api/carState/getSatellitesNumber.json";
    public static String USECARREPORTDATE = DOOHANURL + "api/carState/useCarReport_date.json";
    public static String USECARREPORTDETILS = DOOHANURL + "api/carState/useCarReport_details.json";
    public static String GETTOALMIEAGE = DOOHANURL + "api/carState/getMileage.json";
    public static String MYCARTRACK = DOOHANURL + "api/carState/track.json";
    public static String ISFIRSTOPENWECHAT = DOOHANURL + "api/user/isFirstOpenWeChat.json";
    public static String PUSHWECHATTEST = DOOHANURL + "api/message/pushWeChatTest.json";
    public static String OUTLETSLIST = DOOHANURL + "api/outlets/outletsList.json";
    public static String ISUPGRADE = DOOHANURL + "api/upgrade/isUpgrade.json";
    public static String LIGHTCONTROL = DOOHANURL + "api/vehicle/lightControl.json";
    public static String VOLUME = DOOHANURL + "api/vehicle/volume.json";
    public static String MODESWITCH = DOOHANURL + "api/vehicle/modeSwitch.json";
    public static String FINDCAR = DOOHANURL + "api/vehicle/findCar.json";
    public static String CARINFO = DOOHANURL + "api/carState/carInfo.json";
    public static String ISAVTIVATE = DOOHANURL + "api/carState/isActivate.json";
    public static String GETBLUETOOTHADDRESS = DOOHANURL + "api/carState/getBluetoothAddress.json";
    public static String CANCLEAPPLY = DOOHANURL + "api/vehicle/cancelApply.json";
    public static String USERBOOKING = DOOHANURL + "api/booking/userBooking.json";
    public static String USEREVALUATION = DOOHANURL + "api/booking/userEvaluation.json";
    public static String BOOKINGRECORD = DOOHANURL + "api/booking/bookingRecord.json";
    public static String BOOKINGDETAILS = DOOHANURL + "api/booking/bookingDetails.json";
    public static String IMG = DOOHANURL + "api/upload/img.json";
    public static String CANCLEBOOKING = DOOHANURL + "api/booking/cancelBooking.json";
    public static boolean isOldCar = true;

    public static void setDOOHANURL(String DOOHANURL) {
        Constant.DOOHANURL = DOOHANURL;
        SMSTEST = DOOHANURL + "api/sms/sendCaptcha.json";
        REGISTER = DOOHANURL + "api/user/register.json";
        LOGIN = DOOHANURL + "api/user/login.json";
        CHANGEPASSWOED = DOOHANURL + "api/user/resetPassword.json";
        GETUSER = DOOHANURL + "api/user/userInfo.json";
        LOGOUT = DOOHANURL + "api/user/logout.json";
        CHANGEPHONE = DOOHANURL + "api/user/modifyMobile.json";
        MYVEHICLE = DOOHANURL + "api/vehicle/myVehicle.json";
        CHANGEUSER = DOOHANURL + "api/user/modifyInfo.json";
        RETRIACTION = DOOHANURL + "api/user/feedback.json";
        CHANGEHEAD = DOOHANURL + "api/user/modifyHeadImg.json";
        CARCONDITION = DOOHANURL + "api/indexPage.json";
        USERCONFIGINFO = DOOHANURL + "api/user/userConfigInfo.json";
        UPDATEUSERCONFIG = DOOHANURL + "api/user/updateUserConfig.json";
        VEHICLEBINDING = DOOHANURL + "api/vehicle/binding.json";
        VEHICLEDETAILS = DOOHANURL + "api/vehicle/vehicleDetails.json";
        SWITCHINGVEHICLES = DOOHANURL + "api/vehicle/switchingVehicle.json";
        MODIFYVEHICLEINFO = DOOHANURL + "api/vehicle/modifyVehicleInfo.json";
        REMOVEBINDING = DOOHANURL + "api/vehicle/removeBinding.json";
        VVEHICLEBINDINGUSER = DOOHANURL + "api/vehicle/vehicleBindingUser.json";
        GRANTRESULTS = DOOHANURL + "api/vehicle/grantResults.json";
        MESSAGELIST = DOOHANURL + "api/message/messageList.json";
        UPDATEMESSAGE = DOOHANURL + "api/message/updateMessage.json";
        MARKALLREAD = DOOHANURL + "api/message/markAllRead.json";
        EXAMINATION = DOOHANURL + "api/carState/examination.json";
        VEHICLEPOSITION = DOOHANURL + "api/vehicle/vehiclePosition.json";
        GRANTMESSAGE = DOOHANURL + "api/message/grantMessage.json";
        STARTPAGE = DOOHANURL + "api/config/startPage.json";
        GETSATELLITESNUMBER = DOOHANURL + "api/carState/getSatellitesNumber.json";
        USECARREPORTDATE = DOOHANURL + "api/carState/useCarReport_date.json";
        USECARREPORTDETILS = DOOHANURL + "api/carState/useCarReport_details.json";
        GETTOALMIEAGE = DOOHANURL + "api/carState/getMileage.json";
        MYCARTRACK = DOOHANURL + "api/carState/track.json";
        ISFIRSTOPENWECHAT = DOOHANURL + "api/user/isFirstOpenWeChat.json";
        PUSHWECHATTEST = DOOHANURL + "api/message/pushWeChatTest.json";
        OUTLETSLIST = DOOHANURL + "api/outlets/outletsList.json";
        ISUPGRADE = DOOHANURL + "api/upgrade/isUpgrade.json";
        LIGHTCONTROL = DOOHANURL + "api/vehicle/lightControl.json";
        VOLUME = DOOHANURL + "api/vehicle/volume.json";
        MODESWITCH = DOOHANURL + "api/vehicle/modeSwitch.json";
        FINDCAR = DOOHANURL + "api/vehicle/findCar.json";
        CARINFO = DOOHANURL + "api/carState/carInfo.json";
        ISAVTIVATE = DOOHANURL + "api/carState/isActivate.json";
        GETBLUETOOTHADDRESS = DOOHANURL + "api/carState/getBluetoothAddress.json";
        CANCLEAPPLY = DOOHANURL + "api/vehicle/cancelApply.json";
        USERBOOKING = DOOHANURL + "api/booking/userBooking.json";
        USEREVALUATION = DOOHANURL + "api/booking/userEvaluation.json";
        BOOKINGRECORD = DOOHANURL + "api/booking/bookingRecord.json";
        BOOKINGDETAILS = DOOHANURL + "api/booking/bookingDetails.json";
        IMG = DOOHANURL + "api/upload/img.json";
        CANCLEBOOKING = DOOHANURL + "api/booking/cancelBooking.json";
    }

    /**
     * 所有固定值
     */
    public static final Integer OPENREQUESTCODE = 1000;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;
    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";
    public static final UUID SERVIE_UUID = UUID
            .fromString("0000FFe5-0000-1000-8000-00805f9b34fb");//设置发送数据的uuid
    public static final UUID RED_LIGHT_CONTROL_UUID = UUID
            .fromString("0000FFF4-0000-1000-8000-00805f9b34fb");//设置发送数据服务特征值的uuid
    public static final UUID
            RED_LIGHT_CONTROL_UUID_TWO = UUID
            .fromString("0000FFe9-0000-1000-8000-00805f9b34fb");//设置发送数据服务特征值的uuid
    public final static UUID UUID_HEART_RATE_MEASUREMENT =
            UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);
}
