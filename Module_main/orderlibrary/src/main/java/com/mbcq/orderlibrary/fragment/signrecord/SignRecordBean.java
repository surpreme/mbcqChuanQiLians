package com.mbcq.orderlibrary.fragment.signrecord;

public class SignRecordBean {


    /**
     * billno : 10030002698
     * fetchType : 1
     * fetchTypeStr : 本地签收
     * billDate : 2020-11-07T16:52:01
     * product : 玻璃
     * webidCode : 1003
     * webidCodeStr : 汕头
     * ewebidCode : 1003
     * ewebidCodeStr : 汕头
     * destination : 彩塘
     * qty : 2
     * shipper : 王哓我
     * accNow : 0.0
     * consignee : t111111111
     * accBack : 0.0
     * accMonth : 0.0
     * accHuoKuanKou : 0.0
     * accSend : 0.0
     * okProcess : 1
     * okProcessStr : 客户自提
     * sendVehicleNo : null
     * accSum : 118.0
     * sendDate : null
     * accHuiKou : 0.0
     * opeMan : null
     * sendMan : null
     * fetchMan : 汕头
     * fetchDate : 2020-12-26T16:04:44
     * fetchidcard :
     * fetchagent :
     * fetageidcard :
     * opeMan1 : 汕头
     * recordDate : 2020-12-26T16:04:44
     * outCygs :
     * outAcc : 0.0
     * outBillno :
     * shipperCompany : null
     * consigneeId : null
     * consigneeCompany : null
     * weight : 5.0
     * volumn : 66.0
     * weightJs : 0.0
     * accSafe : 0.0
     * accGb : 0.0
     * accBackService : null
     * accSms : 0.0
     * accWz : 0.0
     * accType : 2
     * accTypeStr : 提付
     * fetchRemark :
     */

    private String billno;
    private int fetchType;
    private String fetchTypeStr;
    private String billDate;
    private String product;
    private int webidCode;
    private String webidCodeStr;
    private int ewebidCode;
    private String ewebidCodeStr;
    private String destination;
    private int qty;
    private String shipper;
    private double accNow;
    private String consignee;
    private double accBack;
    private double accMonth;
    private double accHuoKuanKou;
    private double accSend;
    private int okProcess;
    private String okProcessStr;
    private Object sendVehicleNo;
    private double accSum;
    private Object sendDate;
    private double accHuiKou;
    private Object opeMan;
    private Object sendMan;
    private String fetchMan;
    private String fetchDate;
    private String fetchidcard;
    private String fetchagent;
    private String fetageidcard;
    private String opeMan1;
    private String recordDate;
    private String outCygs;
    private double outAcc;
    private String outBillno;
    private Object shipperCompany;
    private Object consigneeId;
    private Object consigneeCompany;
    private double weight;
    private double volumn;
    private double weightJs;
    private double accSafe;
    private double accGb;
    private Object accBackService;
    private double accSms;
    private double accWz;
    private int accType;
    private String accTypeStr;
    private String fetchRemark;
    private String commonStr="";
    private Boolean isSelected=false;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }



    public String getCommonStr() {
        return commonStr;
    }

    public void setCommonStr(String commonStr) {
        this.commonStr = commonStr;
    }





    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public int getFetchType() {
        return fetchType;
    }

    public void setFetchType(int fetchType) {
        this.fetchType = fetchType;
    }

    public String getFetchTypeStr() {
        return fetchTypeStr;
    }

    public void setFetchTypeStr(String fetchTypeStr) {
        this.fetchTypeStr = fetchTypeStr;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public double getAccNow() {
        return accNow;
    }

    public void setAccNow(double accNow) {
        this.accNow = accNow;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public double getAccBack() {
        return accBack;
    }

    public void setAccBack(double accBack) {
        this.accBack = accBack;
    }

    public double getAccMonth() {
        return accMonth;
    }

    public void setAccMonth(double accMonth) {
        this.accMonth = accMonth;
    }

    public double getAccHuoKuanKou() {
        return accHuoKuanKou;
    }

    public void setAccHuoKuanKou(double accHuoKuanKou) {
        this.accHuoKuanKou = accHuoKuanKou;
    }

    public double getAccSend() {
        return accSend;
    }

    public void setAccSend(double accSend) {
        this.accSend = accSend;
    }

    public int getOkProcess() {
        return okProcess;
    }

    public void setOkProcess(int okProcess) {
        this.okProcess = okProcess;
    }

    public String getOkProcessStr() {
        return okProcessStr;
    }

    public void setOkProcessStr(String okProcessStr) {
        this.okProcessStr = okProcessStr;
    }

    public Object getSendVehicleNo() {
        return sendVehicleNo;
    }

    public void setSendVehicleNo(Object sendVehicleNo) {
        this.sendVehicleNo = sendVehicleNo;
    }

    public double getAccSum() {
        return accSum;
    }

    public void setAccSum(double accSum) {
        this.accSum = accSum;
    }

    public Object getSendDate() {
        return sendDate;
    }

    public void setSendDate(Object sendDate) {
        this.sendDate = sendDate;
    }

    public double getAccHuiKou() {
        return accHuiKou;
    }

    public void setAccHuiKou(double accHuiKou) {
        this.accHuiKou = accHuiKou;
    }

    public Object getOpeMan() {
        return opeMan;
    }

    public void setOpeMan(Object opeMan) {
        this.opeMan = opeMan;
    }

    public Object getSendMan() {
        return sendMan;
    }

    public void setSendMan(Object sendMan) {
        this.sendMan = sendMan;
    }

    public String getFetchMan() {
        return fetchMan;
    }

    public void setFetchMan(String fetchMan) {
        this.fetchMan = fetchMan;
    }

    public String getFetchDate() {
        return fetchDate;
    }

    public void setFetchDate(String fetchDate) {
        this.fetchDate = fetchDate;
    }

    public String getFetchidcard() {
        return fetchidcard;
    }

    public void setFetchidcard(String fetchidcard) {
        this.fetchidcard = fetchidcard;
    }

    public String getFetchagent() {
        return fetchagent;
    }

    public void setFetchagent(String fetchagent) {
        this.fetchagent = fetchagent;
    }

    public String getFetageidcard() {
        return fetageidcard;
    }

    public void setFetageidcard(String fetageidcard) {
        this.fetageidcard = fetageidcard;
    }

    public String getOpeMan1() {
        return opeMan1;
    }

    public void setOpeMan1(String opeMan1) {
        this.opeMan1 = opeMan1;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getOutCygs() {
        return outCygs;
    }

    public void setOutCygs(String outCygs) {
        this.outCygs = outCygs;
    }

    public double getOutAcc() {
        return outAcc;
    }

    public void setOutAcc(double outAcc) {
        this.outAcc = outAcc;
    }

    public String getOutBillno() {
        return outBillno;
    }

    public void setOutBillno(String outBillno) {
        this.outBillno = outBillno;
    }

    public Object getShipperCompany() {
        return shipperCompany;
    }

    public void setShipperCompany(Object shipperCompany) {
        this.shipperCompany = shipperCompany;
    }

    public Object getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(Object consigneeId) {
        this.consigneeId = consigneeId;
    }

    public Object getConsigneeCompany() {
        return consigneeCompany;
    }

    public void setConsigneeCompany(Object consigneeCompany) {
        this.consigneeCompany = consigneeCompany;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolumn() {
        return volumn;
    }

    public void setVolumn(double volumn) {
        this.volumn = volumn;
    }

    public double getWeightJs() {
        return weightJs;
    }

    public void setWeightJs(double weightJs) {
        this.weightJs = weightJs;
    }

    public double getAccSafe() {
        return accSafe;
    }

    public void setAccSafe(double accSafe) {
        this.accSafe = accSafe;
    }

    public double getAccGb() {
        return accGb;
    }

    public void setAccGb(double accGb) {
        this.accGb = accGb;
    }

    public Object getAccBackService() {
        return accBackService;
    }

    public void setAccBackService(Object accBackService) {
        this.accBackService = accBackService;
    }

    public double getAccSms() {
        return accSms;
    }

    public void setAccSms(double accSms) {
        this.accSms = accSms;
    }

    public double getAccWz() {
        return accWz;
    }

    public void setAccWz(double accWz) {
        this.accWz = accWz;
    }

    public int getAccType() {
        return accType;
    }

    public void setAccType(int accType) {
        this.accType = accType;
    }

    public String getAccTypeStr() {
        return accTypeStr;
    }

    public void setAccTypeStr(String accTypeStr) {
        this.accTypeStr = accTypeStr;
    }

    public String getFetchRemark() {
        return fetchRemark;
    }

    public void setFetchRemark(String fetchRemark) {
        this.fetchRemark = fetchRemark;
    }
}
