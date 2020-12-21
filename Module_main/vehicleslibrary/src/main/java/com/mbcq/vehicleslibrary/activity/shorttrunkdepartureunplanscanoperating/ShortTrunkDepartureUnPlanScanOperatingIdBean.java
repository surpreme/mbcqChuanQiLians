package com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating;

import java.util.List;

public class ShortTrunkDepartureUnPlanScanOperatingIdBean {

    /**
     * code : 0
     * msg :
     * data : [{"data":[{"id":173,"vehicleState":0,"vehicleStateStr":"发车计划中","companyId":2001,"ecompanyId":2001,"inoneVehicleFlag":"DB1008-20201127-022","contractNo":"DB1008-20201127-022","sendOpeMan":"义乌青口","arriOpeMan":"","webidCode":1008,"webidCodeStr":"义乌青口","ewebidCode":1007,"ewebidCodeStr":"杭州萧山","vehicleNo":"浙G12374","chauffer":"张凯","chaufferMb":"16276665366","transneed":1,"transneedStr":"普运","sendDate":"2020-11-27T14:54:44","arrivedDate":"1900-01-01T00:00:00","accNow":0,"accBack":0,"accYk":0,"ykCard":"","ewebidCode1":1010,"ewebidCodeStr1":"永康中田","accArrived1":6,"ewebidCode2":1004,"ewebidCodeStr2":"潮州彩塘","accArrived2":9,"ewebidCode3":1002,"ewebidCodeStr3":"义乌篁园","accArrived3":6,"accZx":0,"accJh":0,"accArrSum":21,"accTansSum":21,"accOther":0,"vehicleInterval":"义乌青口-杭州萧山","remark":"","fromType":3,"fromTypeStr":"安卓APP","isScan":2,"scanPercentage":0,"ScanWebidType":1}]},{"data":[]}]
     */

    private int code;
    private String msg;
    private List<DataBeanX> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 173
             * vehicleState : 0
             * vehicleStateStr : 发车计划中
             * companyId : 2001
             * ecompanyId : 2001
             * inoneVehicleFlag : DB1008-20201127-022
             * contractNo : DB1008-20201127-022
             * sendOpeMan : 义乌青口
             * arriOpeMan :
             * webidCode : 1008
             * webidCodeStr : 义乌青口
             * ewebidCode : 1007
             * ewebidCodeStr : 杭州萧山
             * vehicleNo : 浙G12374
             * chauffer : 张凯
             * chaufferMb : 16276665366
             * transneed : 1
             * transneedStr : 普运
             * sendDate : 2020-11-27T14:54:44
             * arrivedDate : 1900-01-01T00:00:00
             * accNow : 0.0
             * accBack : 0.0
             * accYk : 0.0
             * ykCard :
             * ewebidCode1 : 1010
             * ewebidCodeStr1 : 永康中田
             * accArrived1 : 6.0
             * ewebidCode2 : 1004
             * ewebidCodeStr2 : 潮州彩塘
             * accArrived2 : 9.0
             * ewebidCode3 : 1002
             * ewebidCodeStr3 : 义乌篁园
             * accArrived3 : 6.0
             * accZx : 0.0
             * accJh : 0.0
             * accArrSum : 21.0
             * accTansSum : 21.0
             * accOther : 0.0
             * vehicleInterval : 义乌青口-杭州萧山
             * remark :
             * fromType : 3
             * fromTypeStr : 安卓APP
             * isScan : 2
             * scanPercentage : 0.0
             * ScanWebidType : 1
             */

            private int id=-1;
            private int vehicleState;
            private String vehicleStateStr;
            private int companyId;
            private int ecompanyId;
            private String inoneVehicleFlag;
            private String contractNo;
            private String sendOpeMan;
            private String arriOpeMan;
            private int webidCode;
            private String webidCodeStr;
            private int ewebidCode;
            private String ewebidCodeStr;
            private String vehicleNo;
            private String chauffer;
            private String chaufferMb;
            private int transneed;
            private String transneedStr;
            private String sendDate;
            private String arrivedDate;
            private double accNow;
            private double accBack;
            private double accYk;
            private String ykCard;
            private int ewebidCode1;
            private String ewebidCodeStr1;
            private double accArrived1;
            private int ewebidCode2;
            private String ewebidCodeStr2;
            private double accArrived2;
            private int ewebidCode3;
            private String ewebidCodeStr3;
            private double accArrived3;
            private double accZx;
            private double accJh;
            private double accArrSum;
            private double accTansSum;
            private double accOther;
            private String vehicleInterval;
            private String remark;
            private int fromType;
            private String fromTypeStr;
            private int isScan;
            private double scanPercentage;
            private int ScanWebidType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getVehicleState() {
                return vehicleState;
            }

            public void setVehicleState(int vehicleState) {
                this.vehicleState = vehicleState;
            }

            public String getVehicleStateStr() {
                return vehicleStateStr;
            }

            public void setVehicleStateStr(String vehicleStateStr) {
                this.vehicleStateStr = vehicleStateStr;
            }

            public int getCompanyId() {
                return companyId;
            }

            public void setCompanyId(int companyId) {
                this.companyId = companyId;
            }

            public int getEcompanyId() {
                return ecompanyId;
            }

            public void setEcompanyId(int ecompanyId) {
                this.ecompanyId = ecompanyId;
            }

            public String getInoneVehicleFlag() {
                return inoneVehicleFlag;
            }

            public void setInoneVehicleFlag(String inoneVehicleFlag) {
                this.inoneVehicleFlag = inoneVehicleFlag;
            }

            public String getContractNo() {
                return contractNo;
            }

            public void setContractNo(String contractNo) {
                this.contractNo = contractNo;
            }

            public String getSendOpeMan() {
                return sendOpeMan;
            }

            public void setSendOpeMan(String sendOpeMan) {
                this.sendOpeMan = sendOpeMan;
            }

            public String getArriOpeMan() {
                return arriOpeMan;
            }

            public void setArriOpeMan(String arriOpeMan) {
                this.arriOpeMan = arriOpeMan;
            }

            public int getWebidCode() {
                return webidCode;
            }

            public void setWebidCode(int webidCode) {
                this.webidCode = webidCode;
            }

            public String getWebidCodeStr() {
                return webidCodeStr;
            }

            public void setWebidCodeStr(String webidCodeStr) {
                this.webidCodeStr = webidCodeStr;
            }

            public int getEwebidCode() {
                return ewebidCode;
            }

            public void setEwebidCode(int ewebidCode) {
                this.ewebidCode = ewebidCode;
            }

            public String getEwebidCodeStr() {
                return ewebidCodeStr;
            }

            public void setEwebidCodeStr(String ewebidCodeStr) {
                this.ewebidCodeStr = ewebidCodeStr;
            }

            public String getVehicleNo() {
                return vehicleNo;
            }

            public void setVehicleNo(String vehicleNo) {
                this.vehicleNo = vehicleNo;
            }

            public String getChauffer() {
                return chauffer;
            }

            public void setChauffer(String chauffer) {
                this.chauffer = chauffer;
            }

            public String getChaufferMb() {
                return chaufferMb;
            }

            public void setChaufferMb(String chaufferMb) {
                this.chaufferMb = chaufferMb;
            }

            public int getTransneed() {
                return transneed;
            }

            public void setTransneed(int transneed) {
                this.transneed = transneed;
            }

            public String getTransneedStr() {
                return transneedStr;
            }

            public void setTransneedStr(String transneedStr) {
                this.transneedStr = transneedStr;
            }

            public String getSendDate() {
                return sendDate;
            }

            public void setSendDate(String sendDate) {
                this.sendDate = sendDate;
            }

            public String getArrivedDate() {
                return arrivedDate;
            }

            public void setArrivedDate(String arrivedDate) {
                this.arrivedDate = arrivedDate;
            }

            public double getAccNow() {
                return accNow;
            }

            public void setAccNow(double accNow) {
                this.accNow = accNow;
            }

            public double getAccBack() {
                return accBack;
            }

            public void setAccBack(double accBack) {
                this.accBack = accBack;
            }

            public double getAccYk() {
                return accYk;
            }

            public void setAccYk(double accYk) {
                this.accYk = accYk;
            }

            public String getYkCard() {
                return ykCard;
            }

            public void setYkCard(String ykCard) {
                this.ykCard = ykCard;
            }

            public int getEwebidCode1() {
                return ewebidCode1;
            }

            public void setEwebidCode1(int ewebidCode1) {
                this.ewebidCode1 = ewebidCode1;
            }

            public String getEwebidCodeStr1() {
                return ewebidCodeStr1;
            }

            public void setEwebidCodeStr1(String ewebidCodeStr1) {
                this.ewebidCodeStr1 = ewebidCodeStr1;
            }

            public double getAccArrived1() {
                return accArrived1;
            }

            public void setAccArrived1(double accArrived1) {
                this.accArrived1 = accArrived1;
            }

            public int getEwebidCode2() {
                return ewebidCode2;
            }

            public void setEwebidCode2(int ewebidCode2) {
                this.ewebidCode2 = ewebidCode2;
            }

            public String getEwebidCodeStr2() {
                return ewebidCodeStr2;
            }

            public void setEwebidCodeStr2(String ewebidCodeStr2) {
                this.ewebidCodeStr2 = ewebidCodeStr2;
            }

            public double getAccArrived2() {
                return accArrived2;
            }

            public void setAccArrived2(double accArrived2) {
                this.accArrived2 = accArrived2;
            }

            public int getEwebidCode3() {
                return ewebidCode3;
            }

            public void setEwebidCode3(int ewebidCode3) {
                this.ewebidCode3 = ewebidCode3;
            }

            public String getEwebidCodeStr3() {
                return ewebidCodeStr3;
            }

            public void setEwebidCodeStr3(String ewebidCodeStr3) {
                this.ewebidCodeStr3 = ewebidCodeStr3;
            }

            public double getAccArrived3() {
                return accArrived3;
            }

            public void setAccArrived3(double accArrived3) {
                this.accArrived3 = accArrived3;
            }

            public double getAccZx() {
                return accZx;
            }

            public void setAccZx(double accZx) {
                this.accZx = accZx;
            }

            public double getAccJh() {
                return accJh;
            }

            public void setAccJh(double accJh) {
                this.accJh = accJh;
            }

            public double getAccArrSum() {
                return accArrSum;
            }

            public void setAccArrSum(double accArrSum) {
                this.accArrSum = accArrSum;
            }

            public double getAccTansSum() {
                return accTansSum;
            }

            public void setAccTansSum(double accTansSum) {
                this.accTansSum = accTansSum;
            }

            public double getAccOther() {
                return accOther;
            }

            public void setAccOther(double accOther) {
                this.accOther = accOther;
            }

            public String getVehicleInterval() {
                return vehicleInterval;
            }

            public void setVehicleInterval(String vehicleInterval) {
                this.vehicleInterval = vehicleInterval;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getFromType() {
                return fromType;
            }

            public void setFromType(int fromType) {
                this.fromType = fromType;
            }

            public String getFromTypeStr() {
                return fromTypeStr;
            }

            public void setFromTypeStr(String fromTypeStr) {
                this.fromTypeStr = fromTypeStr;
            }

            public int getIsScan() {
                return isScan;
            }

            public void setIsScan(int isScan) {
                this.isScan = isScan;
            }

            public double getScanPercentage() {
                return scanPercentage;
            }

            public void setScanPercentage(double scanPercentage) {
                this.scanPercentage = scanPercentage;
            }

            public int getScanWebidType() {
                return ScanWebidType;
            }

            public void setScanWebidType(int ScanWebidType) {
                this.ScanWebidType = ScanWebidType;
            }
        }
    }
}
