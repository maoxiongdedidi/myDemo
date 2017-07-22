package com.example.ding.testtvp;

/**
 * Created by 丁赵来 on 2017/5/17.
 */

public class MyJson {

    /**
     * success : true
     * message : OK
     * data : {"vcuNo":"V1TH0000AC0014","mode":3,"longitudeType":0,"latitudeType":0,"longitude":"120.11533","latitude":"30.338003","satellitesNumber":7,"gprsSignalStrength":-1,"externalBatteryElectricity":0,"tboxBatteryElectricity":30,"updateTime":"2017-05-11 16:02:24","temperature":0,"totalMileage":196928,"instrumentState":0,"unlockWay":0,"lighStatet":0,"bluetoothState":0,"audioBluetoothState":0,"voice":0,"createTime":"2017-05-11 15:35:26","bluetoothAddress":"8030DCD64F52","motorlock":"0","motorvoltage":"0","motorcurrent":"0","mcuverhw":"0","motorstate":"0","gear":"0","mcustate":"0","bmstotalcurrent":"0","bmstotalvoltage":"0","bmsbuffpower":"0","bmscycletimes":"0","bmspowerpercent":"0","bmsverhw":"0","bmsstate":"0","bmsstatetemputernbr":"0","bmsbatteryidnbr":"1","imsi":"864501032219331","tboxAppVerHw":"0","tboxBootVerSw":"1002","vcuAppVerSw":"0","vcuAppVerHw":"0"}
     */

    private boolean success;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * vcuNo : V1TH0000AC0014
         * mode : 3
         * longitudeType : 0
         * latitudeType : 0
         * longitude : 120.11533
         * latitude : 30.338003
         * satellitesNumber : 7
         * gprsSignalStrength : -1
         * externalBatteryElectricity : 0
         * tboxBatteryElectricity : 30
         * updateTime : 2017-05-11 16:02:24
         * temperature : 0
         * totalMileage : 196928
         * instrumentState : 0
         * unlockWay : 0
         * lighStatet : 0
         * bluetoothState : 0
         * audioBluetoothState : 0
         * voice : 0
         * createTime : 2017-05-11 15:35:26
         * bluetoothAddress : 8030DCD64F52
         * motorlock : 0
         * motorvoltage : 0
         * motorcurrent : 0
         * mcuverhw : 0
         * motorstate : 0
         * gear : 0
         * mcustate : 0
         * bmstotalcurrent : 0
         * bmstotalvoltage : 0
         * bmsbuffpower : 0
         * bmscycletimes : 0
         * bmspowerpercent : 0
         * bmsverhw : 0
         * bmsstate : 0
         * bmsstatetemputernbr : 0
         * bmsbatteryidnbr : 1
         * imsi : 864501032219331
         * tboxAppVerHw : 0
         * tboxBootVerSw : 1002
         * vcuAppVerSw : 0
         * vcuAppVerHw : 0
         */

        private String vcuNo;
        private int mode;
        private int longitudeType;
        private int latitudeType;
        private String longitude;
        private String latitude;
        private int satellitesNumber;
        private int gprsSignalStrength;
        private int externalBatteryElectricity;
        private int tboxBatteryElectricity;
        private String updateTime;
        private int temperature;
        private int totalMileage;
        private int instrumentState;
        private int unlockWay;
        private int lighStatet;
        private int bluetoothState;
        private int audioBluetoothState;
        private int voice;
        private String createTime;
        private String bluetoothAddress;
        private String motorlock;
        private String motorvoltage;
        private String motorcurrent;
        private String mcuverhw;
        private String motorstate;
        private String gear;
        private String mcustate;
        private String bmstotalcurrent;
        private String bmstotalvoltage;
        private String bmsbuffpower;
        private String bmscycletimes;
        private String bmspowerpercent;
        private String bmsverhw;
        private String bmsstate;
        private String bmsstatetemputernbr;
        private String bmsbatteryidnbr;
        private String imsi;
        private String tboxAppVerHw;
        private String tboxBootVerSw;
        private String vcuAppVerSw;
        private String vcuAppVerHw;

        public String getVcuNo() {
            return vcuNo;
        }

        public void setVcuNo(String vcuNo) {
            this.vcuNo = vcuNo;
        }

        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        public int getLongitudeType() {
            return longitudeType;
        }

        public void setLongitudeType(int longitudeType) {
            this.longitudeType = longitudeType;
        }

        public int getLatitudeType() {
            return latitudeType;
        }

        public void setLatitudeType(int latitudeType) {
            this.latitudeType = latitudeType;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public int getSatellitesNumber() {
            return satellitesNumber;
        }

        public void setSatellitesNumber(int satellitesNumber) {
            this.satellitesNumber = satellitesNumber;
        }

        public int getGprsSignalStrength() {
            return gprsSignalStrength;
        }

        public void setGprsSignalStrength(int gprsSignalStrength) {
            this.gprsSignalStrength = gprsSignalStrength;
        }

        public int getExternalBatteryElectricity() {
            return externalBatteryElectricity;
        }

        public void setExternalBatteryElectricity(int externalBatteryElectricity) {
            this.externalBatteryElectricity = externalBatteryElectricity;
        }

        public int getTboxBatteryElectricity() {
            return tboxBatteryElectricity;
        }

        public void setTboxBatteryElectricity(int tboxBatteryElectricity) {
            this.tboxBatteryElectricity = tboxBatteryElectricity;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getTemperature() {
            return temperature;
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
        }

        public int getTotalMileage() {
            return totalMileage;
        }

        public void setTotalMileage(int totalMileage) {
            this.totalMileage = totalMileage;
        }

        public int getInstrumentState() {
            return instrumentState;
        }

        public void setInstrumentState(int instrumentState) {
            this.instrumentState = instrumentState;
        }

        public int getUnlockWay() {
            return unlockWay;
        }

        public void setUnlockWay(int unlockWay) {
            this.unlockWay = unlockWay;
        }

        public int getLighStatet() {
            return lighStatet;
        }

        public void setLighStatet(int lighStatet) {
            this.lighStatet = lighStatet;
        }

        public int getBluetoothState() {
            return bluetoothState;
        }

        public void setBluetoothState(int bluetoothState) {
            this.bluetoothState = bluetoothState;
        }

        public int getAudioBluetoothState() {
            return audioBluetoothState;
        }

        public void setAudioBluetoothState(int audioBluetoothState) {
            this.audioBluetoothState = audioBluetoothState;
        }

        public int getVoice() {
            return voice;
        }

        public void setVoice(int voice) {
            this.voice = voice;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getBluetoothAddress() {
            return bluetoothAddress;
        }

        public void setBluetoothAddress(String bluetoothAddress) {
            this.bluetoothAddress = bluetoothAddress;
        }

        public String getMotorlock() {
            return motorlock;
        }

        public void setMotorlock(String motorlock) {
            this.motorlock = motorlock;
        }

        public String getMotorvoltage() {
            return motorvoltage;
        }

        public void setMotorvoltage(String motorvoltage) {
            this.motorvoltage = motorvoltage;
        }

        public String getMotorcurrent() {
            return motorcurrent;
        }

        public void setMotorcurrent(String motorcurrent) {
            this.motorcurrent = motorcurrent;
        }

        public String getMcuverhw() {
            return mcuverhw;
        }

        public void setMcuverhw(String mcuverhw) {
            this.mcuverhw = mcuverhw;
        }

        public String getMotorstate() {
            return motorstate;
        }

        public void setMotorstate(String motorstate) {
            this.motorstate = motorstate;
        }

        public String getGear() {
            return gear;
        }

        public void setGear(String gear) {
            this.gear = gear;
        }

        public String getMcustate() {
            return mcustate;
        }

        public void setMcustate(String mcustate) {
            this.mcustate = mcustate;
        }

        public String getBmstotalcurrent() {
            return bmstotalcurrent;
        }

        public void setBmstotalcurrent(String bmstotalcurrent) {
            this.bmstotalcurrent = bmstotalcurrent;
        }

        public String getBmstotalvoltage() {
            return bmstotalvoltage;
        }

        public void setBmstotalvoltage(String bmstotalvoltage) {
            this.bmstotalvoltage = bmstotalvoltage;
        }

        public String getBmsbuffpower() {
            return bmsbuffpower;
        }

        public void setBmsbuffpower(String bmsbuffpower) {
            this.bmsbuffpower = bmsbuffpower;
        }

        public String getBmscycletimes() {
            return bmscycletimes;
        }

        public void setBmscycletimes(String bmscycletimes) {
            this.bmscycletimes = bmscycletimes;
        }

        public String getBmspowerpercent() {
            return bmspowerpercent;
        }

        public void setBmspowerpercent(String bmspowerpercent) {
            this.bmspowerpercent = bmspowerpercent;
        }

        public String getBmsverhw() {
            return bmsverhw;
        }

        public void setBmsverhw(String bmsverhw) {
            this.bmsverhw = bmsverhw;
        }

        public String getBmsstate() {
            return bmsstate;
        }

        public void setBmsstate(String bmsstate) {
            this.bmsstate = bmsstate;
        }

        public String getBmsstatetemputernbr() {
            return bmsstatetemputernbr;
        }

        public void setBmsstatetemputernbr(String bmsstatetemputernbr) {
            this.bmsstatetemputernbr = bmsstatetemputernbr;
        }

        public String getBmsbatteryidnbr() {
            return bmsbatteryidnbr;
        }

        public void setBmsbatteryidnbr(String bmsbatteryidnbr) {
            this.bmsbatteryidnbr = bmsbatteryidnbr;
        }

        public String getImsi() {
            return imsi;
        }

        public void setImsi(String imsi) {
            this.imsi = imsi;
        }

        public String getTboxAppVerHw() {
            return tboxAppVerHw;
        }

        public void setTboxAppVerHw(String tboxAppVerHw) {
            this.tboxAppVerHw = tboxAppVerHw;
        }

        public String getTboxBootVerSw() {
            return tboxBootVerSw;
        }

        public void setTboxBootVerSw(String tboxBootVerSw) {
            this.tboxBootVerSw = tboxBootVerSw;
        }

        public String getVcuAppVerSw() {
            return vcuAppVerSw;
        }

        public void setVcuAppVerSw(String vcuAppVerSw) {
            this.vcuAppVerSw = vcuAppVerSw;
        }

        public String getVcuAppVerHw() {
            return vcuAppVerHw;
        }

        public void setVcuAppVerHw(String vcuAppVerHw) {
            this.vcuAppVerHw = vcuAppVerHw;
        }
    }
}
