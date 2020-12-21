package com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating;

import java.io.Serializable;

public class ShortTrunkDepartureUnPlanScanOperatingBean implements Serializable {


    /**
     * sendOpeMan : lzy
     * arriOpeMan :
     * sendDate : 2020-11-16T13:17:57
     * arrivedDate : 1900-01-01T00:00:00
     * inoneVehicleFlag : DB1003-20201116-006
     * qtyDb : 1
     * unLoadQty : 1
     * loadQty : 0
     * webidCodeDb : 1003
     * webidCodeStrDb : 汕头
     * ewebidCodeDb : 1002
     * ewebidCodeStrDb : 义乌篁园
     * state : 1
     * stateStr : 发货
     * sendTimes : 0
     * sfWeight : 2.5
     * sfVolumn : 2.5
     * inPdaState : 0
     * outPdaState : 0
     * finishState : 0
     * accForktruck : 0.0
     * orderId :
     * billno : 10030002793
     * oBillno :
     * billDate : 2020-11-16T11:32:45
     * billState : 3
     * billStateStr : 短驳在途
     * billType : 0
     * billTypeStr : 机打
     * goodsNum : 02793-2
     * okProcess : 1
     * okProcessStr : 客户自提
     * isUrgent : 0
     * isUrgentStr : 否
     * isTalkGoods : 0
     * isTalkGoodsStr : 否
     * webidCode : 1003
     * webidCodeStr : 汕头
     * ewebidCode : 1002
     * ewebidCodeStr : 义乌篁园
     * destination : 篁园a
     * transneed : 2
     * transneedStr : 马帮快线
     * vipId :
     * shipperId :
     * shipperMb : 17530957256
     * shipperTel : 0123-1234567
     * shipper : 王哓我
     * shipperCid :
     * shipperAddr : 发货人地址
     * consigneeMb : 17530957256
     * consigneeTel :
     * consignee : t111111111
     * consigneeAddr : t222222222
     * product : 玻璃
     * totalQty : 2
     * qty : 2
     * packages : 工工式
     * weight : 5.0
     * volumn : 5.0
     * weightJs : 0.0
     * safeMoney : 0.0
     * accDaiShou : 0.0
     * accHKChange : 0.0
     * hkChangeReason :
     * sxf : 0.0
     * wPrice : 0.0
     * vPrice : 0.0
     * qtyPrice : 0.0
     * accNow : 0.0
     * accArrived : 0.0
     * accBack : 0.0
     * accMonth : 0.0
     * accHuoKuanKou : 0.0
     * accTrans : 36.0
     * accFetch : 0.0
     * accPackage : 2.0
     * accSend : 0.0
     * accGb : 0.0
     * accSafe : 0.0
     * accRyf : 0.0
     * accHuiKou : 0.0
     * accSms : 0.0
     * accZz : 0.0
     * accZx : 0.0
     * accCb : 0.0
     * accSl : 0.0
     * accAz : 0.0
     * accFj : 0.0
     * accWz : 0.0
     * accJc : 0.0
     * accSum : 38.0
     * accType : 2
     * accTypeStr : 提付
     * backQty : 签回单
     * backState : 0
     * isWaitNotice : 0
     * isWaitNoticeStr : 否
     * bankCode :
     * bankName :
     * bankMan :
     * bankNumber :
     * createMan : lzy
     * salesMan :
     * opeMan : lzy
     * remark :
     */

    private String sendOpeMan;
    private String arriOpeMan;
    private String sendDate;
    private String arrivedDate;
    private String inoneVehicleFlag;
    private int qtyDb;
    private int unLoadQty;
    private int waybillFcdQty = 0;
    private int loadQty;
    private int webidCodeDb;
    private String webidCodeStrDb;
    private int ewebidCodeDb;
    private String ewebidCodeStrDb;
    private int state;
    private String stateStr;
    private int sendTimes;
    private double sfWeight;
    private double sfVolumn;
    private int inPdaState;
    private int outPdaState;
    private int finishState;
    private double accForktruck;
    private String orderId;
    private String billno;
    private String oBillno;
    private String billDate;
    private int billState;
    private String billStateStr;
    private int billType;
    private String billTypeStr;
    private String goodsNum;
    private int okProcess;
    private String okProcessStr;
    private int isUrgent;
    private String isUrgentStr;
    private int isTalkGoods;
    private String isTalkGoodsStr;
    private int webidCode;
    private String webidCodeStr;
    private int ewebidCode;
    private String ewebidCodeStr;
    private String destination;
    private int transneed;
    private String transneedStr;
    private String vipId;
    private String shipperId;
    private String shipperMb;
    private String shipperTel;
    private String shipper;
    private String shipperCid;
    private String shipperAddr;
    private String consigneeMb;
    private String consigneeTel;
    private String consignee;
    private String consigneeAddr;
    private String product;
    private int totalQty;
    private int qty;
    private String packages;
    private double weight;
    private double volumn;
    private double weightJs;
    private double safeMoney;
    private double accDaiShou;
    private double accHKChange;
    private String hkChangeReason;
    private double sxf;
    private double wPrice;
    private double vPrice;
    private double qtyPrice;
    private double accNow;
    private double accArrived;
    private double accBack;
    private double accMonth;
    private double accHuoKuanKou;
    private double accTrans;
    private double accFetch;
    private double accPackage;
    private double accSend;
    private double accGb;
    private double accSafe;
    private double accRyf;
    private double accHuiKou;
    private double accSms;
    private double accZz;
    private double accZx;
    private double accCb;
    private double accSl;
    private double accAz;
    private double accFj;
    private double accWz;
    private double accJc;
    private double accSum;
    private int accType;
    private String accTypeStr;
    private String backQty;
    private int backState;
    private int isWaitNotice;
    private String isWaitNoticeStr;
    private String bankCode;
    private String bankName;
    private String bankMan;
    private String bankNumber;
    private String createMan;
    private String salesMan;
    private String opeMan;
    private String remark;
    public int getWaybillFcdQty() {
        return waybillFcdQty;
    }

    public void setWaybillFcdQty(int waybillFcdQty) {
        this.waybillFcdQty = waybillFcdQty;
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

    public String getInoneVehicleFlag() {
        return inoneVehicleFlag;
    }

    public void setInoneVehicleFlag(String inoneVehicleFlag) {
        this.inoneVehicleFlag = inoneVehicleFlag;
    }

    public int getQtyDb() {
        return qtyDb;
    }

    public void setQtyDb(int qtyDb) {
        this.qtyDb = qtyDb;
    }

    public int getUnLoadQty() {
        return unLoadQty;
    }

    public void setUnLoadQty(int unLoadQty) {
        this.unLoadQty = unLoadQty;
    }

    public int getLoadQty() {
        return loadQty;
    }

    public void setLoadQty(int loadQty) {
        this.loadQty = loadQty;
    }

    public int getWebidCodeDb() {
        return webidCodeDb;
    }

    public void setWebidCodeDb(int webidCodeDb) {
        this.webidCodeDb = webidCodeDb;
    }

    public String getWebidCodeStrDb() {
        return webidCodeStrDb;
    }

    public void setWebidCodeStrDb(String webidCodeStrDb) {
        this.webidCodeStrDb = webidCodeStrDb;
    }

    public int getEwebidCodeDb() {
        return ewebidCodeDb;
    }

    public void setEwebidCodeDb(int ewebidCodeDb) {
        this.ewebidCodeDb = ewebidCodeDb;
    }

    public String getEwebidCodeStrDb() {
        return ewebidCodeStrDb;
    }

    public void setEwebidCodeStrDb(String ewebidCodeStrDb) {
        this.ewebidCodeStrDb = ewebidCodeStrDb;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public int getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(int sendTimes) {
        this.sendTimes = sendTimes;
    }

    public double getSfWeight() {
        return sfWeight;
    }

    public void setSfWeight(double sfWeight) {
        this.sfWeight = sfWeight;
    }

    public double getSfVolumn() {
        return sfVolumn;
    }

    public void setSfVolumn(double sfVolumn) {
        this.sfVolumn = sfVolumn;
    }

    public int getInPdaState() {
        return inPdaState;
    }

    public void setInPdaState(int inPdaState) {
        this.inPdaState = inPdaState;
    }

    public int getOutPdaState() {
        return outPdaState;
    }

    public void setOutPdaState(int outPdaState) {
        this.outPdaState = outPdaState;
    }

    public int getFinishState() {
        return finishState;
    }

    public void setFinishState(int finishState) {
        this.finishState = finishState;
    }

    public double getAccForktruck() {
        return accForktruck;
    }

    public void setAccForktruck(double accForktruck) {
        this.accForktruck = accForktruck;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getOBillno() {
        return oBillno;
    }

    public void setOBillno(String oBillno) {
        this.oBillno = oBillno;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public int getBillState() {
        return billState;
    }

    public void setBillState(int billState) {
        this.billState = billState;
    }

    public String getBillStateStr() {
        return billStateStr;
    }

    public void setBillStateStr(String billStateStr) {
        this.billStateStr = billStateStr;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public String getBillTypeStr() {
        return billTypeStr;
    }

    public void setBillTypeStr(String billTypeStr) {
        this.billTypeStr = billTypeStr;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
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

    public int getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(int isUrgent) {
        this.isUrgent = isUrgent;
    }

    public String getIsUrgentStr() {
        return isUrgentStr;
    }

    public void setIsUrgentStr(String isUrgentStr) {
        this.isUrgentStr = isUrgentStr;
    }

    public int getIsTalkGoods() {
        return isTalkGoods;
    }

    public void setIsTalkGoods(int isTalkGoods) {
        this.isTalkGoods = isTalkGoods;
    }

    public String getIsTalkGoodsStr() {
        return isTalkGoodsStr;
    }

    public void setIsTalkGoodsStr(String isTalkGoodsStr) {
        this.isTalkGoodsStr = isTalkGoodsStr;
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

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public String getShipperId() {
        return shipperId;
    }

    public void setShipperId(String shipperId) {
        this.shipperId = shipperId;
    }

    public String getShipperMb() {
        return shipperMb;
    }

    public void setShipperMb(String shipperMb) {
        this.shipperMb = shipperMb;
    }

    public String getShipperTel() {
        return shipperTel;
    }

    public void setShipperTel(String shipperTel) {
        this.shipperTel = shipperTel;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getShipperCid() {
        return shipperCid;
    }

    public void setShipperCid(String shipperCid) {
        this.shipperCid = shipperCid;
    }

    public String getShipperAddr() {
        return shipperAddr;
    }

    public void setShipperAddr(String shipperAddr) {
        this.shipperAddr = shipperAddr;
    }

    public String getConsigneeMb() {
        return consigneeMb;
    }

    public void setConsigneeMb(String consigneeMb) {
        this.consigneeMb = consigneeMb;
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeAddr() {
        return consigneeAddr;
    }

    public void setConsigneeAddr(String consigneeAddr) {
        this.consigneeAddr = consigneeAddr;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
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

    public double getSafeMoney() {
        return safeMoney;
    }

    public void setSafeMoney(double safeMoney) {
        this.safeMoney = safeMoney;
    }

    public double getAccDaiShou() {
        return accDaiShou;
    }

    public void setAccDaiShou(double accDaiShou) {
        this.accDaiShou = accDaiShou;
    }

    public double getAccHKChange() {
        return accHKChange;
    }

    public void setAccHKChange(double accHKChange) {
        this.accHKChange = accHKChange;
    }

    public String getHkChangeReason() {
        return hkChangeReason;
    }

    public void setHkChangeReason(String hkChangeReason) {
        this.hkChangeReason = hkChangeReason;
    }

    public double getSxf() {
        return sxf;
    }

    public void setSxf(double sxf) {
        this.sxf = sxf;
    }

    public double getWPrice() {
        return wPrice;
    }

    public void setWPrice(double wPrice) {
        this.wPrice = wPrice;
    }

    public double getVPrice() {
        return vPrice;
    }

    public void setVPrice(double vPrice) {
        this.vPrice = vPrice;
    }

    public double getQtyPrice() {
        return qtyPrice;
    }

    public void setQtyPrice(double qtyPrice) {
        this.qtyPrice = qtyPrice;
    }

    public double getAccNow() {
        return accNow;
    }

    public void setAccNow(double accNow) {
        this.accNow = accNow;
    }

    public double getAccArrived() {
        return accArrived;
    }

    public void setAccArrived(double accArrived) {
        this.accArrived = accArrived;
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

    public double getAccTrans() {
        return accTrans;
    }

    public void setAccTrans(double accTrans) {
        this.accTrans = accTrans;
    }

    public double getAccFetch() {
        return accFetch;
    }

    public void setAccFetch(double accFetch) {
        this.accFetch = accFetch;
    }

    public double getAccPackage() {
        return accPackage;
    }

    public void setAccPackage(double accPackage) {
        this.accPackage = accPackage;
    }

    public double getAccSend() {
        return accSend;
    }

    public void setAccSend(double accSend) {
        this.accSend = accSend;
    }

    public double getAccGb() {
        return accGb;
    }

    public void setAccGb(double accGb) {
        this.accGb = accGb;
    }

    public double getAccSafe() {
        return accSafe;
    }

    public void setAccSafe(double accSafe) {
        this.accSafe = accSafe;
    }

    public double getAccRyf() {
        return accRyf;
    }

    public void setAccRyf(double accRyf) {
        this.accRyf = accRyf;
    }

    public double getAccHuiKou() {
        return accHuiKou;
    }

    public void setAccHuiKou(double accHuiKou) {
        this.accHuiKou = accHuiKou;
    }

    public double getAccSms() {
        return accSms;
    }

    public void setAccSms(double accSms) {
        this.accSms = accSms;
    }

    public double getAccZz() {
        return accZz;
    }

    public void setAccZz(double accZz) {
        this.accZz = accZz;
    }

    public double getAccZx() {
        return accZx;
    }

    public void setAccZx(double accZx) {
        this.accZx = accZx;
    }

    public double getAccCb() {
        return accCb;
    }

    public void setAccCb(double accCb) {
        this.accCb = accCb;
    }

    public double getAccSl() {
        return accSl;
    }

    public void setAccSl(double accSl) {
        this.accSl = accSl;
    }

    public double getAccAz() {
        return accAz;
    }

    public void setAccAz(double accAz) {
        this.accAz = accAz;
    }

    public double getAccFj() {
        return accFj;
    }

    public void setAccFj(double accFj) {
        this.accFj = accFj;
    }

    public double getAccWz() {
        return accWz;
    }

    public void setAccWz(double accWz) {
        this.accWz = accWz;
    }

    public double getAccJc() {
        return accJc;
    }

    public void setAccJc(double accJc) {
        this.accJc = accJc;
    }

    public double getAccSum() {
        return accSum;
    }

    public void setAccSum(double accSum) {
        this.accSum = accSum;
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

    public String getBackQty() {
        return backQty;
    }

    public void setBackQty(String backQty) {
        this.backQty = backQty;
    }

    public int getBackState() {
        return backState;
    }

    public void setBackState(int backState) {
        this.backState = backState;
    }

    public int getIsWaitNotice() {
        return isWaitNotice;
    }

    public void setIsWaitNotice(int isWaitNotice) {
        this.isWaitNotice = isWaitNotice;
    }

    public String getIsWaitNoticeStr() {
        return isWaitNoticeStr;
    }

    public void setIsWaitNoticeStr(String isWaitNoticeStr) {
        this.isWaitNoticeStr = isWaitNoticeStr;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankMan() {
        return bankMan;
    }

    public void setBankMan(String bankMan) {
        this.bankMan = bankMan;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getSalesMan() {
        return salesMan;
    }

    public void setSalesMan(String salesMan) {
        this.salesMan = salesMan;
    }

    public String getOpeMan() {
        return opeMan;
    }

    public void setOpeMan(String opeMan) {
        this.opeMan = opeMan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
