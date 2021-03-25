package com.mbcq.vehicleslibrary.bean;

import java.io.Serializable;

public class StockWaybillListBean implements Serializable {

    /**
     * id : 2
     * companyId : 2001
     * eCompanyId : 2
     * orderId :
     * billno : 10030000111
     * oBillno :
     * billDate : 2020-06-19T13:04:54
     * billState : 2
     * billStateStr : 在库
     * billType : 9
     * billTypeStr : string 10
     * goodsNum : string 13
     * okProcess : 14
     * okProcessStr : string 15
     * isUrgent : 16
     * isUrgentStr : string 17
     * isTalkGoods : 18
     * isTalkGoodsStr : string 19
     * webidCode : 20
     * webidCodeStr : string 21
     * ewebidCode : 22
     * ewebidCodeStr : string 23
     * destination : string 24
     * transneed : 25
     * transneedStr :  string 26
     * vipId : string 27
     * shipperId : string 28
     * shipperMb : string 29
     * shipperTel : sample string 30
     * shipper : string 31
     * shipperCid : sample string 32
     * shipperAddr : sample string 33
     * consigneeMb : sample string 34
     * consigneeTel : sample string 35
     * consignee : string 36
     * consigneeAddr : sample string 37
     * product : string 38
     * totalQty : 39
     * qty : 40
     * packages : sample string 41
     * weight : 42.0
     * volumn : 43.0
     * weightJs : 44.0
     * safeMoney : 45.0
     * accDaiShou : 46.0
     * accHKChange : 47.0
     * hkChangeReason : sample string 48
     * sxf : 49.0
     * wPrice : 50.0
     * vPrice : 51.0
     * qtyPrice : 52.0
     * accNow : 53.0
     * accArrived : 54.0
     * accBack : 55.0
     * accMonth : 56.0
     * accHuoKuanKou : 57.0
     * accTrans : 58.0
     * accFetch : 59.0
     * accPackage : 60.0
     * accSend : 61.0
     * accGb : 62.0
     * accSafe : 63.0
     * accRyf : 64.0
     * accHuiKou : 65.0
     * accSms : 66.0
     * accZz : 67.0
     * accZx : 68.0
     * accCb : 69.0
     * accSl : 70.0
     * accAz : 71.0
     * accFj : 72.0
     * accWz : 73.0
     * accJc : 74.0
     * accSum : 75.0
     * accType : 76
     * accTypeStr : string 77
     * backQty : string 78
     * backState : 79
     * isWaitNotice : 80
     * isWaitNoticeStr :  string 81
     * bankCode :  string 82
     * bankName :  string 83
     * bankMan :  string 84
     * bankNumber : string 85
     * createMan : string 86
     * salesMan : string 87
     * opeMan : 汕头
     * remark : string 88
     * fromType : 89
     */

    private String id;
    private String companyId;
    private String eCompanyId;
    private String orderId;
    private String billno;
    private String oBillno;
    private String billDate;
    private String billState;
    private String billStateStr;
    private String billType;
    private String billTypeStr;
    private String goodsNum;
    private String okProcess;
    private String okProcessStr;
    private String isUrgent;
    private String isUrgentStr;
    private String isTalkGoods;
    private String isTalkGoodsStr;
    private String webidCode;
    private String webidCodeStr;
    private String ewebidCode;
    private String ewebidCodeStr;
    private String destination;
    private String transneed;
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
    private String totalQty;
    private String qty;
    private String packages;
    private String weight;
    private String volumn;
    private String weightJs;
    private String safeMoney;
    private String accDaiShou;
    private String accHKChange;
    private String hkChangeReason;
    private String sxf;
    private String wPrice;
    private String vPrice;
    private String qtyPrice;
    private String accNow;
    private String accArrived;
    private String accBack;
    private String accMonth;
    private String accHuoKuanKou;
    private String accTrans;
    private String accFetch;
    private String accPackage;
    private String accSend;
    private String accGb;
    private String accSafe;
    private String accRyf;
    private String accHuiKou;
    private String accSms;
    private String accZz;
    private String accZx;
    private String accCb;
    private String accSl;
    private String accAz;
    private String accFj;
    private String accWz;
    private String accJc;
    private String accSum;
    private String accType;
    private String accTypeStr;
    private String backQty;
    private String backState;
    private String isWaitNotice;
    private String isWaitNoticeStr;
    private String bankCode;
    private String bankName;
    private String bankMan;
    private String bankNumber;
    private String createMan;
    private String salesMan;
    private String opeMan;
    private String remark;
    private String fromType;
    private String qtyDb = "";
    private String qtyGx = "";
    private boolean isDevelopments = false;//是否可以拆票
    private int developmentsQty = 0;//拆票缓存数量
    private boolean isChecked = false;


    public String getQtyGx() {
        return qtyGx;
    }

    public void setQtyGx(String qtyGx) {
        this.qtyGx = qtyGx;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getECompanyId() {
        return eCompanyId;
    }

    public void setECompanyId(String eCompanyId) {
        this.eCompanyId = eCompanyId;
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

    public String getBillState() {
        return billState;
    }

    public void setBillState(String billState) {
        this.billState = billState;
    }

    public String getBillStateStr() {
        return billStateStr;
    }

    public void setBillStateStr(String billStateStr) {
        this.billStateStr = billStateStr;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
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

    public String getOkProcess() {
        return okProcess;
    }

    public void setOkProcess(String okProcess) {
        this.okProcess = okProcess;
    }

    public String getOkProcessStr() {
        return okProcessStr;
    }

    public void setOkProcessStr(String okProcessStr) {
        this.okProcessStr = okProcessStr;
    }

    public String getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(String isUrgent) {
        this.isUrgent = isUrgent;
    }

    public String getIsUrgentStr() {
        return isUrgentStr;
    }

    public void setIsUrgentStr(String isUrgentStr) {
        this.isUrgentStr = isUrgentStr;
    }

    public String getIsTalkGoods() {
        return isTalkGoods;
    }

    public void setIsTalkGoods(String isTalkGoods) {
        this.isTalkGoods = isTalkGoods;
    }

    public String getIsTalkGoodsStr() {
        return isTalkGoodsStr;
    }

    public void setIsTalkGoodsStr(String isTalkGoodsStr) {
        this.isTalkGoodsStr = isTalkGoodsStr;
    }

    public String getWebidCode() {
        return webidCode;
    }

    public void setWebidCode(String webidCode) {
        this.webidCode = webidCode;
    }

    public String getWebidCodeStr() {
        return webidCodeStr;
    }

    public void setWebidCodeStr(String webidCodeStr) {
        this.webidCodeStr = webidCodeStr;
    }

    public String getEwebidCode() {
        return ewebidCode;
    }

    public void setEwebidCode(String ewebidCode) {
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

    public String getTransneed() {
        return transneed;
    }

    public void setTransneed(String transneed) {
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

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolumn() {
        return volumn;
    }

    public void setVolumn(String volumn) {
        this.volumn = volumn;
    }

    public String getWeightJs() {
        return weightJs;
    }

    public void setWeightJs(String weightJs) {
        this.weightJs = weightJs;
    }

    public String getSafeMoney() {
        return safeMoney;
    }

    public void setSafeMoney(String safeMoney) {
        this.safeMoney = safeMoney;
    }

    public String getAccDaiShou() {
        return accDaiShou;
    }

    public void setAccDaiShou(String accDaiShou) {
        this.accDaiShou = accDaiShou;
    }

    public String getAccHKChange() {
        return accHKChange;
    }

    public void setAccHKChange(String accHKChange) {
        this.accHKChange = accHKChange;
    }

    public String getHkChangeReason() {
        return hkChangeReason;
    }

    public void setHkChangeReason(String hkChangeReason) {
        this.hkChangeReason = hkChangeReason;
    }

    public String getSxf() {
        return sxf;
    }

    public void setSxf(String sxf) {
        this.sxf = sxf;
    }

    public String getWPrice() {
        return wPrice;
    }

    public void setWPrice(String wPrice) {
        this.wPrice = wPrice;
    }

    public String getVPrice() {
        return vPrice;
    }

    public void setVPrice(String vPrice) {
        this.vPrice = vPrice;
    }

    public String getQtyPrice() {
        return qtyPrice;
    }

    public void setQtyPrice(String qtyPrice) {
        this.qtyPrice = qtyPrice;
    }

    public String getAccNow() {
        return accNow;
    }

    public void setAccNow(String accNow) {
        this.accNow = accNow;
    }

    public String getAccArrived() {
        return accArrived;
    }

    public void setAccArrived(String accArrived) {
        this.accArrived = accArrived;
    }

    public String getAccBack() {
        return accBack;
    }

    public void setAccBack(String accBack) {
        this.accBack = accBack;
    }

    public String getAccMonth() {
        return accMonth;
    }

    public void setAccMonth(String accMonth) {
        this.accMonth = accMonth;
    }

    public String getAccHuoKuanKou() {
        return accHuoKuanKou;
    }

    public void setAccHuoKuanKou(String accHuoKuanKou) {
        this.accHuoKuanKou = accHuoKuanKou;
    }

    public String getAccTrans() {
        return accTrans;
    }

    public void setAccTrans(String accTrans) {
        this.accTrans = accTrans;
    }

    public String getAccFetch() {
        return accFetch;
    }

    public void setAccFetch(String accFetch) {
        this.accFetch = accFetch;
    }

    public String getAccPackage() {
        return accPackage;
    }

    public void setAccPackage(String accPackage) {
        this.accPackage = accPackage;
    }

    public String getAccSend() {
        return accSend;
    }

    public void setAccSend(String accSend) {
        this.accSend = accSend;
    }

    public String getAccGb() {
        return accGb;
    }

    public void setAccGb(String accGb) {
        this.accGb = accGb;
    }

    public String getAccSafe() {
        return accSafe;
    }

    public void setAccSafe(String accSafe) {
        this.accSafe = accSafe;
    }

    public String getAccRyf() {
        return accRyf;
    }

    public void setAccRyf(String accRyf) {
        this.accRyf = accRyf;
    }

    public String getAccHuiKou() {
        return accHuiKou;
    }

    public void setAccHuiKou(String accHuiKou) {
        this.accHuiKou = accHuiKou;
    }

    public String getAccSms() {
        return accSms;
    }

    public void setAccSms(String accSms) {
        this.accSms = accSms;
    }

    public String getAccZz() {
        return accZz;
    }

    public void setAccZz(String accZz) {
        this.accZz = accZz;
    }

    public String getAccZx() {
        return accZx;
    }

    public void setAccZx(String accZx) {
        this.accZx = accZx;
    }

    public String getAccCb() {
        return accCb;
    }

    public void setAccCb(String accCb) {
        this.accCb = accCb;
    }

    public String getAccSl() {
        return accSl;
    }

    public void setAccSl(String accSl) {
        this.accSl = accSl;
    }

    public String getAccAz() {
        return accAz;
    }

    public void setAccAz(String accAz) {
        this.accAz = accAz;
    }

    public String getAccFj() {
        return accFj;
    }

    public void setAccFj(String accFj) {
        this.accFj = accFj;
    }

    public String getAccWz() {
        return accWz;
    }

    public void setAccWz(String accWz) {
        this.accWz = accWz;
    }

    public String getAccJc() {
        return accJc;
    }

    public void setAccJc(String accJc) {
        this.accJc = accJc;
    }

    public String getAccSum() {
        return accSum;
    }

    public void setAccSum(String accSum) {
        this.accSum = accSum;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
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

    public String getBackState() {
        return backState;
    }

    public void setBackState(String backState) {
        this.backState = backState;
    }

    public String getIsWaitNotice() {
        return isWaitNotice;
    }

    public void setIsWaitNotice(String isWaitNotice) {
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

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isDevelopments() {
        return isDevelopments;
    }

    public void setDevelopments(boolean developments) {
        isDevelopments = developments;
    }

    public String getQtyDb() {
        return qtyDb;
    }

    public void setQtyDb(String qtyDb) {
        this.qtyDb = qtyDb;
    }

    public int getDevelopmentsQty() {
        return developmentsQty;
    }

    public void setDevelopmentsQty(int developmentsQty) {
        this.developmentsQty = developmentsQty;
    }
}
