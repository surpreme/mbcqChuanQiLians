package com.mbcq.commonlibrary;

public class ApiInterface {
//    private static final String BASE_URI="http://47.96.133.133:7091/";
    private static final String BASE_URI="http://192.168.2.43:8080/";
    /**
     * 登录
     */
    public static final String LOG_IN_POST=BASE_URI+"Common/Login";
    /**
     * 受理开单 获取运单号
     */
    public static final String ACCEPT_BILLING_WAYBILL_NUMBER_GET=BASE_URI+"RuleBillno/SelRule2Billno";
    /**
     * 受理开单 到达网点 Arrive at the outlet
     */
    public static final String ACCEPT_OUTLET_GET=BASE_URI+"WebMain/SelWebMainByCondition";

}
