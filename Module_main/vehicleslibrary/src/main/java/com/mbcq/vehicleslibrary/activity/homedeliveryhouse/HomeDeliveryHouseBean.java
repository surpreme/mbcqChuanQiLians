package com.mbcq.vehicleslibrary.activity.homedeliveryhouse;

public class HomeDeliveryHouseBean {

    /**
     * id : 39
     * companyId : 2001
     * eCompanyId : 0
     * orderId :
     * billno : 10030000217
     * oBillno :
     * billDate : 2020-06-23T17:01:59
     * billState : 1
     * billStateStr : 已入库
     * billType : 0
     * billTypeStr : 机打
     * goodsNum : 00217-50
     * okProcess : 1
     * okProcessStr : 自提
     * isUrgent : 0
     * isUrgentStr : 否
     * isTalkGoods : 0
     * isTalkGoodsStr : 否
     * webidCode : 1003
     * webidCodeStr : 汕头
     * ewebidCode : 1003
     * ewebidCodeStr : 汕头
     * destination : 彩塘
     * transneed : 1
     * transneedStr : 零担
     * vipId :
     * shipperId :
     * shipperMb : 17530957256
     * shipperTel : 0123-1234567
     * shipper : 王哓我
     * shipperCid : 410482199002265912
     * shipperAddr : 发货人地址
     * consigneeMb : 17530957256
     * consigneeTel : 0248-5235544
     * consignee : 1禾
     * consigneeAddr : 蜚厘士别三日奔奔夺
     * product : 玻璃1
     * totalQty : 50
     * qty : 50
     * packages : aaas
     * weight : 10.0
     * volumn : 0.6
     * weightJs : 156.0
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
     * accTrans : 30.0
     * accFetch : 0.0
     * accPackage : 0.0
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
     * accSum : 30.0
     * accType : 1
     * accTypeStr : 现付
     * backQty : 签回单
     * backState : 0
     * isWaitNotice : 0
     * isWaitNoticeStr : 否
     * bankCode :
     * bankName :
     * bankMan :
     * bankNumber :
     * createMan :
     * salesMan :
     * opeMan : 汕头
     * remark :
     * fromType : 0
     */

    private String id;
    private String companyId;
    private String pickUpDate = "";//提货时间
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
    private String totalQty="";
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
    private String accZx="0.00";//装卸费
    private String accZxf="0.00";//装卸费
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
    private String outCygs = "";
    private String outacc = "0.00";
    private String accCc = "0.00";//叉车费

    private String outbillno = "";
    private String contactmb = "";
    private boolean isChecked = false;

    public String getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }
    public String getAccCc() {
        return accCc;
    }

    public void setAccCc(String accCc) {
        this.accCc = accCc;
    }

    public String getAccZxf() {
        return accZxf;
    }

    public void setAccZxf(String accZxf) {
        this.accZxf = accZxf;
    }
    public String geteCompanyId() {
        return eCompanyId;
    }

    public void seteCompanyId(String eCompanyId) {
        this.eCompanyId = eCompanyId;
    }

    public String getoBillno() {
        return oBillno;
    }

    public void setoBillno(String oBillno) {
        this.oBillno = oBillno;
    }

    public String getwPrice() {
        return wPrice;
    }

    public void setwPrice(String wPrice) {
        this.wPrice = wPrice;
    }

    public String getvPrice() {
        return vPrice;
    }

    public void setvPrice(String vPrice) {
        this.vPrice = vPrice;
    }

    public String getOutCygs() {
        return outCygs;
    }

    public void setOutCygs(String outCygs) {
        this.outCygs = outCygs;
    }

    public String getOutacc() {
        return outacc;
    }

    public void setOutacc(String outacc) {
        this.outacc = outacc;
    }

    public String getOutbillno() {
        return outbillno;
    }

    public void setOutbillno(String outbillno) {
        this.outbillno = outbillno;
    }

    public String getContactmb() {
        return contactmb;
    }

    public void setContactmb(String contactmb) {
        this.contactmb = contactmb;
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
}
