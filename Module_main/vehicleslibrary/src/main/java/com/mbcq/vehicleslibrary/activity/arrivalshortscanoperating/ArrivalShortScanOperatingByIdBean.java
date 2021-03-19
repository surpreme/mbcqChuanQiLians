package com.mbcq.vehicleslibrary.activity.arrivalshortscanoperating;

import java.util.List;

public class ArrivalShortScanOperatingByIdBean {


    /**
     * code : 0
     * msg :
     * data : [{"data":[{"id":553,"sendDate":"2021-03-19 16:00:36","vehicleState":1,"vehicleStateStr":"发货","contractNo":"DB1001-20210319-002","inoneVehicleFlag":"DB1001-20210319-002","vehicleInterval":"义乌后湖-汕头","vehicleNo":"浙G12370","Chauffer":"张凯","volumn":3,"weight":5,"yf":668,"accWz":0,"chaufferMb":"16276665366","webidCode":1001,"webidCodeStr":"义乌后湖","ewebidCode":1003,"ewebidCodeStr":"汕头","accNow":0,"accArrSum":0,"accBack":0,"accTansSum":0,"accDaiShou":0,"accYk":0,"ykRemake":"","sendOpeMan":"义乌后湖","createMan":"义乌后湖","arrivedDate":"1900-01-01 00:00:00","hous":0,"accdfYk":0,"vusetypeStr":null,"peizai":"已完毕","isScan":0,"unloadingDate":"1900-01-01 00:00:00","qty":5,"weightJs":780}]},{"data":[{"id":970,"billno":"10010000175","shipper":"宋学宝","consignee":"非常慢","webidCode":1001,"webidCodeStr":"义乌后湖","ewebidCode":1003,"ewebidCodeStr":"汕头","Product":"家电,,","unLoadQty":0,"loadQty":0,"qty":5,"totalQty":5,"weight":5,"volumn":3,"inoneVehicleFlag":"DB1001-20210319-002"}]}]
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
             * id : 553
             * sendDate : 2021-03-19 16:00:36
             * vehicleState : 1
             * vehicleStateStr : 发货
             * contractNo : DB1001-20210319-002
             * inoneVehicleFlag : DB1001-20210319-002
             * vehicleInterval : 义乌后湖-汕头
             * vehicleNo : 浙G12370
             * Chauffer : 张凯
             * volumn : 3.0
             * weight : 5.0
             * yf : 668.0
             * accWz : 0.0
             * chaufferMb : 16276665366
             * webidCode : 1001
             * webidCodeStr : 义乌后湖
             * ewebidCode : 1003
             * ewebidCodeStr : 汕头
             * accNow : 0.0
             * accArrSum : 0.0
             * accBack : 0.0
             * accTansSum : 0.0
             * accDaiShou : 0.0
             * accYk : 0.0
             * ykRemake :
             * sendOpeMan : 义乌后湖
             * createMan : 义乌后湖
             * arrivedDate : 1900-01-01 00:00:00
             * hous : 0
             * accdfYk : 0.0
             * vusetypeStr : null
             * peizai : 已完毕
             * isScan : 0
             * unloadingDate : 1900-01-01 00:00:00
             * qty : 5
             * weightJs : 780.0
             */

            private int id;
            private String sendDate;
            private int vehicleState;
            private String vehicleStateStr;
            private String contractNo;
            private String inoneVehicleFlag;
            private String vehicleInterval;
            private String vehicleNo;
            private String Chauffer;
            private double volumn;
            private double weight;
            private double yf;
            private double accWz;
            private String chaufferMb;
            private int webidCode;
            private String webidCodeStr;
            private int ewebidCode;
            private String ewebidCodeStr;
            private double accNow;
            private double accArrSum;
            private double accBack;
            private double accTansSum;
            private double accDaiShou;
            private double accYk;
            private String ykRemake;
            private String sendOpeMan;
            private String createMan;
            private String arrivedDate;
            private int hous;
            private double accdfYk;
            private Object vusetypeStr;
            private String peizai;
            private int isScan;
            private String unloadingDate;
            private int qty;
            private double weightJs;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSendDate() {
                return sendDate;
            }

            public void setSendDate(String sendDate) {
                this.sendDate = sendDate;
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

            public String getContractNo() {
                return contractNo;
            }

            public void setContractNo(String contractNo) {
                this.contractNo = contractNo;
            }

            public String getInoneVehicleFlag() {
                return inoneVehicleFlag;
            }

            public void setInoneVehicleFlag(String inoneVehicleFlag) {
                this.inoneVehicleFlag = inoneVehicleFlag;
            }

            public String getVehicleInterval() {
                return vehicleInterval;
            }

            public void setVehicleInterval(String vehicleInterval) {
                this.vehicleInterval = vehicleInterval;
            }

            public String getVehicleNo() {
                return vehicleNo;
            }

            public void setVehicleNo(String vehicleNo) {
                this.vehicleNo = vehicleNo;
            }

            public String getChauffer() {
                return Chauffer;
            }

            public void setChauffer(String Chauffer) {
                this.Chauffer = Chauffer;
            }

            public double getVolumn() {
                return volumn;
            }

            public void setVolumn(double volumn) {
                this.volumn = volumn;
            }

            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
            }

            public double getYf() {
                return yf;
            }

            public void setYf(double yf) {
                this.yf = yf;
            }

            public double getAccWz() {
                return accWz;
            }

            public void setAccWz(double accWz) {
                this.accWz = accWz;
            }

            public String getChaufferMb() {
                return chaufferMb;
            }

            public void setChaufferMb(String chaufferMb) {
                this.chaufferMb = chaufferMb;
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

            public double getAccNow() {
                return accNow;
            }

            public void setAccNow(double accNow) {
                this.accNow = accNow;
            }

            public double getAccArrSum() {
                return accArrSum;
            }

            public void setAccArrSum(double accArrSum) {
                this.accArrSum = accArrSum;
            }

            public double getAccBack() {
                return accBack;
            }

            public void setAccBack(double accBack) {
                this.accBack = accBack;
            }

            public double getAccTansSum() {
                return accTansSum;
            }

            public void setAccTansSum(double accTansSum) {
                this.accTansSum = accTansSum;
            }

            public double getAccDaiShou() {
                return accDaiShou;
            }

            public void setAccDaiShou(double accDaiShou) {
                this.accDaiShou = accDaiShou;
            }

            public double getAccYk() {
                return accYk;
            }

            public void setAccYk(double accYk) {
                this.accYk = accYk;
            }

            public String getYkRemake() {
                return ykRemake;
            }

            public void setYkRemake(String ykRemake) {
                this.ykRemake = ykRemake;
            }

            public String getSendOpeMan() {
                return sendOpeMan;
            }

            public void setSendOpeMan(String sendOpeMan) {
                this.sendOpeMan = sendOpeMan;
            }

            public String getCreateMan() {
                return createMan;
            }

            public void setCreateMan(String createMan) {
                this.createMan = createMan;
            }

            public String getArrivedDate() {
                return arrivedDate;
            }

            public void setArrivedDate(String arrivedDate) {
                this.arrivedDate = arrivedDate;
            }

            public int getHous() {
                return hous;
            }

            public void setHous(int hous) {
                this.hous = hous;
            }

            public double getAccdfYk() {
                return accdfYk;
            }

            public void setAccdfYk(double accdfYk) {
                this.accdfYk = accdfYk;
            }

            public Object getVusetypeStr() {
                return vusetypeStr;
            }

            public void setVusetypeStr(Object vusetypeStr) {
                this.vusetypeStr = vusetypeStr;
            }

            public String getPeizai() {
                return peizai;
            }

            public void setPeizai(String peizai) {
                this.peizai = peizai;
            }

            public int getIsScan() {
                return isScan;
            }

            public void setIsScan(int isScan) {
                this.isScan = isScan;
            }

            public String getUnloadingDate() {
                return unloadingDate;
            }

            public void setUnloadingDate(String unloadingDate) {
                this.unloadingDate = unloadingDate;
            }

            public int getQty() {
                return qty;
            }

            public void setQty(int qty) {
                this.qty = qty;
            }

            public double getWeightJs() {
                return weightJs;
            }

            public void setWeightJs(double weightJs) {
                this.weightJs = weightJs;
            }
        }
    }
}
