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
     * *1登录
     * *2受理开单
     * *3发车
     * 到达网点 Arrive at the outlet
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
     * 受理开单 查询收货人信息
     */
    public static final String ACCEPT_SELECT_RECEIVER_GET = BASE_URI + "Consignee/SelConsigneeByCondition";



    /**
     * --------------------------------------------------------------------------------------------------------------
     */



    /**
     * 运单记录  获取所有信息 Waybill record
     */
    public static final String WAYBILL_RECORD_SELECT_ALLINFO_GET = BASE_URI + "WaybillOpe/SelWaybillByCondition";
    /**
     * 运单记录  删除运单
     */
    public static final String WAYBILL_RECORD_DELETE_INFO_POST = BASE_URI + "WaybillOpe/DelWaybill";



    /**
     * --------------------------------------------------------------------------------------------------------------
     */


    /**
     * 发车记录  干线发车 获取所有信息
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_INFO_GET = BASE_URI + "GxVehicleOpe/SelGxVehicleByCondition";

  /**
     * 发车记录  短驳发车 获取所有信息
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_SELECT_INFO_GET = BASE_URI + "DbVehicleOpe/SelDbVehicleByCondition";
    /**
     * 添加短驳计划装车
     * 获取短驳 发车批次号 get
     */
    public static final String ADD_SHORT_TRANSFER_DEPARTURE_BATCH_NUMBER_GET = BASE_URI + "DbVehicleOpe/SelDbInOneVehicleFlag";
    /**
     * 完成短驳发车
     */
    public static final String COMPELETE_SHORT_TRANSFER_DEPARTURE_BATCH_NUMBER_POST = BASE_URI + "DbVehicleOpe/AddDbVehicle";
    /**
     * 车辆档案 查询
     *GET Vehicles/SelVehiclesByCondition
     */
    public static final String VEHICLE_SELECT_INFO_GET = BASE_URI + "Vehicles/SelVehiclesByCondition";
    /**
     * 目的地 查询
     * Destination/SelDestinationByCondition
     */
    public static final String DESTINATION_SELECT_INFO_GET = BASE_URI + "Destination/SelDestinationByCondition";
    /**
     * 短驳和干线 运单库存 Waybill inventory
     */
    public static final String WAYBILL_INVENTORY_SELECT_INFO_GET = BASE_URI + "WaybillFcdOpe/SelDbGxWaybillFcdByCon";


}
