package com.mbcq.commonlibrary.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 网点
 */
@Entity
public class WebAreaDbInfo {


    /**
     * id : 1
     * companyId : 2001
     * webid : 义乌后湖
     * webidCode : 1001
     * webSimplicity : ywhh
     * webidType : 1
     * webidTypeStr : 子公司
     * parWebCod : 9
     * parWebCodStr : null
     * webMb : 4
     * webTel : 6
     * province : 8
     * city : 3
     * county : 5
     * street : 8
     * address : 23
     * longitude :  31.194109
     * latitude : 121.269549
     * dbWebCode : 23
     * dbWebCodeStr : null
     * gxWebCode : 33434
     * gxWebCodeStr : null
     * xzqhbm : 23
     * isValid : 1
     * isValidStr : null
     * opeMan : 汕头
     * recordDate : 2018-12-03T00:00:00
     */
    /**
     * autoincrement设置自动递减功能
     * Unique唯一
     */
    @Id(autoincrement = true)
    private long _id;

    private String companyId;
    @NotNull
    private String webid;
    @Unique
    private String webidCode;

    private String webSimplicity;
    private String webidType;
    private String webidTypeStr;
    private String parWebCod;
    private String parWebCodStr;
    private String webMb;
    private String webTel;
    private String province;
    private String city;
    private String county;
    private String street;
    private String address;
    private String longitude;
    private String latitude;
    private String dbWebCode;
    private String dbWebCodeStr;
    private String gxWebCode;
    private String gxWebCodeStr;
    private String xzqhbm;
    private String isValid;
    private String isValidStr;
    private String opeMan;
    private String recordDate;
    @Generated(hash = 1763659412)
    public WebAreaDbInfo(long _id, String companyId, @NotNull String webid,
            String webidCode, String webSimplicity, String webidType,
            String webidTypeStr, String parWebCod, String parWebCodStr,
            String webMb, String webTel, String province, String city,
            String county, String street, String address, String longitude,
            String latitude, String dbWebCode, String dbWebCodeStr,
            String gxWebCode, String gxWebCodeStr, String xzqhbm, String isValid,
            String isValidStr, String opeMan, String recordDate) {
        this._id = _id;
        this.companyId = companyId;
        this.webid = webid;
        this.webidCode = webidCode;
        this.webSimplicity = webSimplicity;
        this.webidType = webidType;
        this.webidTypeStr = webidTypeStr;
        this.parWebCod = parWebCod;
        this.parWebCodStr = parWebCodStr;
        this.webMb = webMb;
        this.webTel = webTel;
        this.province = province;
        this.city = city;
        this.county = county;
        this.street = street;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.dbWebCode = dbWebCode;
        this.dbWebCodeStr = dbWebCodeStr;
        this.gxWebCode = gxWebCode;
        this.gxWebCodeStr = gxWebCodeStr;
        this.xzqhbm = xzqhbm;
        this.isValid = isValid;
        this.isValidStr = isValidStr;
        this.opeMan = opeMan;
        this.recordDate = recordDate;
    }
    @Generated(hash = 1131067282)
    public WebAreaDbInfo() {
    }
    public long get_id() {
        return this._id;
    }
    public void set_id(long _id) {
        this._id = _id;
    }
    public String getCompanyId() {
        return this.companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getWebid() {
        return this.webid;
    }
    public void setWebid(String webid) {
        this.webid = webid;
    }
    public String getWebidCode() {
        return this.webidCode;
    }
    public void setWebidCode(String webidCode) {
        this.webidCode = webidCode;
    }
    public String getWebSimplicity() {
        return this.webSimplicity;
    }
    public void setWebSimplicity(String webSimplicity) {
        this.webSimplicity = webSimplicity;
    }
    public String getWebidType() {
        return this.webidType;
    }
    public void setWebidType(String webidType) {
        this.webidType = webidType;
    }
    public String getWebidTypeStr() {
        return this.webidTypeStr;
    }
    public void setWebidTypeStr(String webidTypeStr) {
        this.webidTypeStr = webidTypeStr;
    }
    public String getParWebCod() {
        return this.parWebCod;
    }
    public void setParWebCod(String parWebCod) {
        this.parWebCod = parWebCod;
    }
    public String getParWebCodStr() {
        return this.parWebCodStr;
    }
    public void setParWebCodStr(String parWebCodStr) {
        this.parWebCodStr = parWebCodStr;
    }
    public String getWebMb() {
        return this.webMb;
    }
    public void setWebMb(String webMb) {
        this.webMb = webMb;
    }
    public String getWebTel() {
        return this.webTel;
    }
    public void setWebTel(String webTel) {
        this.webTel = webTel;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCounty() {
        return this.county;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public String getStreet() {
        return this.street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getLongitude() {
        return this.longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLatitude() {
        return this.latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getDbWebCode() {
        return this.dbWebCode;
    }
    public void setDbWebCode(String dbWebCode) {
        this.dbWebCode = dbWebCode;
    }
    public String getDbWebCodeStr() {
        return this.dbWebCodeStr;
    }
    public void setDbWebCodeStr(String dbWebCodeStr) {
        this.dbWebCodeStr = dbWebCodeStr;
    }
    public String getGxWebCode() {
        return this.gxWebCode;
    }
    public void setGxWebCode(String gxWebCode) {
        this.gxWebCode = gxWebCode;
    }
    public String getGxWebCodeStr() {
        return this.gxWebCodeStr;
    }
    public void setGxWebCodeStr(String gxWebCodeStr) {
        this.gxWebCodeStr = gxWebCodeStr;
    }
    public String getXzqhbm() {
        return this.xzqhbm;
    }
    public void setXzqhbm(String xzqhbm) {
        this.xzqhbm = xzqhbm;
    }
    public String getIsValid() {
        return this.isValid;
    }
    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
    public String getIsValidStr() {
        return this.isValidStr;
    }
    public void setIsValidStr(String isValidStr) {
        this.isValidStr = isValidStr;
    }
    public String getOpeMan() {
        return this.opeMan;
    }
    public void setOpeMan(String opeMan) {
        this.opeMan = opeMan;
    }
    public String getRecordDate() {
        return this.recordDate;
    }
    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }


}
