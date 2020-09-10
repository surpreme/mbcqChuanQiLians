package com.mbcq.commonlibrary;

public class ApiInterface {
    //    private static final String BASE_URI="http://47.96.133.133:7091/";
    private static final String BASE_URI = "http://192.168.2.43:8080/";
    /**
     * 登录
     */
    public static final String LOG_IN_POST = BASE_URI + "Common/Login";
    /**
     * 受理开单 获取运单号
     */
    public static final String ACCEPT_BILLING_WAYBILL_NUMBER_GET = BASE_URI + "RuleBillno/SelRule2Billno";
    /**
     * 受理开单 到达网点 Arrive at the outlet
     */
    public static final String ACCEPT_OUTLET_GET = BASE_URI + "WebMain/SelWebMainByCondition";
    /**
     * 受理开单 获取目的地
     */
    public static final String ACCEPT_DESTINATION_GET = BASE_URI + "Destination/SelDestinationByCondition";
    /**
     * 受理开单 获取回单要求 @14 receipt requirements
     * 运输方式  @9 transport mode
     * 付款方式  @13 payment mode
     */
    public static final String ACCEPT_RECEIPT_REQUIREMENTS_GET = BASE_URI + "Alltype/SelAlltypeByCondition";
    /**
     * 受理开单 查询货物名称
     */
    public static final String ACCEPT_SELECT_CARGO_APPELLATION_GET = BASE_URI + "ProductName/SelProductNameByCondition";
    /**
     * 受理开单 保存
     */
    public static final String ACCEPT_SAVE_INFO_POST = BASE_URI + "WaybillOpe/AddWaybill";
    /**
     * 受理开单 查询费用信息 种类集合的显示
     */
    public static final String ACCEPT_SELECT_COST_INFORMATION_GET = BASE_URI + "WebConfig/SelWebConfigByCondition";
    /**
     * 受理开单 查询包装
     */
    public static final String ACCEPT_SELECT_PACKAGE_GET = BASE_URI + "PackagesType/SelPackagesTypeByCondition";
    /**
     * 受理开单 通过卡号获取用户支付信息@1
     */
    public static final String ACCEPT_SELECT_BANK_INFO_GET = BASE_URI + "MemBanOpe/SelMemBanMaiByCondition";
    /**
     * 受理开单 通过卡号获取用户支付信息@2 -获取开户行信息
     */
    public static final String ACCEPT_SELECT_BANK_COMPANY_INFO_GET = BASE_URI + "MemBanOpe/SelMemBanByCondition";
    /**
     * 受理开单 查询发货人信息
     */
    public static final String ACCEPT_SELECT_SHIPPER_GET = BASE_URI + "Shipper/SelShipperByCondition";
    /**
     * 受理开单  添加发货人信息
     */
//    public static final String ACCEPT_ADD_SHIPPER_GET=BASE_URI+"Shipper/AddShipper";
    /**
     * 受理开单 查询收货人信息
     */
    public static final String ACCEPT_SELECT_RECEIVER_GET = BASE_URI + "Consignee/SelConsigneeByCondition";
    /**
     * 受理开单 添加收货人信息
     */
//    public static final String ACCEPT_ADD_RECEIVER_GET=BASE_URI+"Consignee/AddConsignee";

}
