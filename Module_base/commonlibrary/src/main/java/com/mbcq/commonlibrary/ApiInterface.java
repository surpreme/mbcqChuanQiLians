package com.mbcq.commonlibrary;

public class ApiInterface {
    //    private static final String BASE_URI="http://47.96.133.133:7091/";
    private static final String BASE_URI = "http://192.168.2.43:8080/";
    public static final String BASE_URIS = "http://192.168.2.43:8080/";
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
     * 干线发车 取消完成本车
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_MODIFY_LOCAL_INFO_POST = BASE_URI + "GxVehicleOpe/UpdCancelGxVehicle";
    /**
     * 干线发车 查询汇总
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_LOCAL_INFO_POST = BASE_URI + "GxVehicleOpe/SelSmGxVehicleByCondition";
    /**
     * 干线发车 完成本车
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_COMPLETE_LOCAL_INFO_POST = BASE_URI + "GxVehicleOpe/UpdOverGxVehicle";
    /**
     * 干线发车 取消到车
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_ARRIVAL_CANCEL_LOCAL_INFO_POST = BASE_URI + "GxVehicleOpe/UpdDbVehicleByCondition";
    /**
     * 短驳发车 查询在途车辆记录
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_LOADING_LOCAL_INFO_POST = BASE_URI + "DbVehicleOpe/SelNeeDbVehicleArrivalByCon";
    /**
     * 短驳发车 查询到达车辆
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_OVERRING_LOCAL_INFO_GET = BASE_URI + "DbVehicleOpe/SelDbVehicleArrivalByCon";
    /**
     * 短驳发车 取消完成本车
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_MODIFY_LOCAL_INFO_POST = BASE_URI + "DbVehicleOpe/UpdCancelDbVehicle";
    /**
     * 短驳发车 完成本车 completeCar
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_COMPLETE_LOCAL_INFO_POST = BASE_URI + "DbVehicleOpe/UpdOverDbVehicle";
    /**
     * 短驳发车 查询汇总
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_LOCAL_INFO_GET = BASE_URI + "DbVehicleOpe/SelSmDbVehicleByCondition";
    /**
     * 短驳发车 确认到车
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_ARRIVAL_CONFIRM_LOCAL_INFO_POST = BASE_URI + "DbVehicleOpe/UpdCofArrDbVehicle";
    /**
     * 短驳发车 取消到车
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_ARRIVAL_CANCEL_LOCAL_INFO_GET = BASE_URI + "DbVehicleOpe/UpdDbVehicleByCondition";
    /**
     * 短驳发车 剔除运单
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_REMOVE_LOCAL_INFO_POST = BASE_URI + "DbVehicleOpe/CurDbVehDelWay";
    /**
     * 短驳发车 本车加货
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_ADD_LOCAL_INFO_POST = BASE_URI + "DbVehicleOpe/CurDbVehAddWay";
    /**
     * 干线发车 本车加货
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_ADD_LOCAL_INFO_POST = BASE_URI + "GxVehicleOpe/CurGxVehAddWay";
    /**
     * 干线发车 确认到车
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_ARRIVAL_CONFIRM_LOCAL_INFO_POST = BASE_URI + "GxVehicleOpe/UpdCofArrGxVehicle";
    /**
     * 干线发车 查询在途车辆记录
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_LOADING_LOCAL_INFO_GET = BASE_URI + "GxVehicleOpe/SelNeeGxVehicleArrivalByCon";

    /**
     * 干线发车 查询到达车辆
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_OVERRING_LOCAL_INFO_GET = BASE_URI + "GxVehicleOpe/SelGxVehicleArrivalByCon";

    /**
     * 干线发车 剔除运单
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_REMOVE_LOCAL_INFO_POST = BASE_URI + "GxVehicleOpe/CurGxVehDelWay";

    /**
     * 发车记录  干线发车 作废
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_INVALID_INFO_POST = BASE_URI + "GxVehicleOpe/DelGxVehicle";
    /**
     * 发车记录  短驳发车 作废
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_INVALID_INFO_POST = BASE_URI + "DbVehicleOpe/DelDbVehicle";
    /**
     * 添加短驳计划装车
     * 获取短驳 发车批次号 get
     */
    public static final String ADD_SHORT_TRANSFER_DEPARTURE_BATCH_NUMBER_GET = BASE_URI + "DbVehicleOpe/SelDbInOneVehicleFlag";
    /**
     * 添加干线计划装车
     * 获取干线 发车批次号 get
     */
    public static final String ADD_TRUNK_TRANSFER_DEPARTURE_BATCH_NUMBER_GET = BASE_URI + "GxVehicleOpe/SelGxInOneVehicleFlag";
    /**
     * 完成短驳发车
     */
    public static final String COMPELETE_SHORT_TRANSFER_DEPARTURE_BATCH_NUMBER_POST = BASE_URI + "DbVehicleOpe/AddDbVehicle";
    /**
     * 完成干线发车
     */
    public static final String COMPELETE_TRUNK_TRANSFER_DEPARTURE_BATCH_NUMBER_POST = BASE_URI + "GxVehicleOpe/AddGxVehicle";
    /**
     * 车辆档案 查询
     * GET Vehicles/SelVehiclesByCondition
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
    /**
     * --------------------------------------------------------------------------------------------------------------
     */


    /**
     * 本地代理记录  按车
     */
    public static final String LOCAL_AGENT_RECORD_SELECT_INFO_GET = BASE_URI + "WaybillAgentOpe/SelWaybillAgentByCondition";
    /**
     * 本地代理 获取本地代理发车 批次号
     */
    public static final String LOCAL_AGENT_BATCH_NUMBER_GET = BASE_URI + "WaybillAgentOpe/SelBddlInOneVehicleFlag";
    /**
     * 本地代理 获取本地代理库存
     */
    public static final String LOCAL_AGENT_INVENTORY_GET = BASE_URI + "WaybillFcdOpe/SelBddlWaybillFcdByCon";
    /**
     * 本地代理 修改 剔除运单
     */
    public static final String LOCAL_AGENT_FIXED_REMOVE_LOADING_POST = BASE_URI + "WaybillAgentOpe/CurWayAgeDelWay";
    /**
     * 本地代理 修改 本车加货
     */
    public static final String LOCAL_AGENT_FIXED_ADD_LOADING_POST = BASE_URI + "WaybillAgentOpe/CurWayAgeAddWay";
    /**
     * 本地代理  终端代理 修改 获取车辆的详细信息
     *
     * @1 本地代理 @2 终端代理
     */
    public static final String LOCAL_AGENT_AND_TERMINAL_AGENT_FIXED_SELECT_LOADING_GET = BASE_URI + "WaybillAgentOpe/SelWaybillAgentDetByCondition";

    /**
     * 本地代理 -添加 完成本车
     */
    public static final String LOCAL_AGENT_COMPLETE_VEHICLE_POST = BASE_URI + "WaybillAgentOpe/AddWaybillAgent";

    /**
     * 本地代理-取消本车
     */
    public static final String LOCAL_AGENT_CANCEL_VEHICLE_POST = BASE_URI + "WaybillAgentOpe/DelWaybillAgentByCon";
    /**
     * ---------------------------------------------------------------------------------------------***********************************************
     */
    /**
     * 终端代理 获取终端代理发车批次号
     */

    public static final String TERMINAL_AGENT_BATCH_NUMBER_GET = BASE_URI + "WaybillAgentOpe/SelZddlInOneVehicleFlag";
    /**
     * 终端代理 获取终端代理发车批次号
     */

    public static final String TERMINAL_AGENT_INVENTORY_GET = BASE_URI + "WaybillFcdOpe/SelZddlWaybillFcdByCon";
    /**
     * 终端代理 获取终端代理发车批次号
     */

    public static final String TERMINAL_AGENT_COMPLETE_VEHICLE_GET = BASE_URI + "WaybillAgentOpe/AddWaybillAgentZd";
    /**
     * 终端代理 修改 剔除运单
     */
    public static final String TERMINAL_AGENT_FIXED_REMOVE_LOADING_POST = BASE_URI + "WaybillAgentOpe/CurWayAgeDelWay";
    /**
     * 终端代理 修改 本车加货
     */
    public static final String TERMINAL_AGENT_FIXED_ADD_LOADING_POST = BASE_URI + "WaybillAgentOpe/CurWayAgeAddWay";
    /**
     * ------------------------------------------******************************************************----------------------------------------------
     */

    /**
     * 到货库存
     */

    public static final String ARRIVAL_INVENTORY_SELECTED_INFO_GET = BASE_URI + "WaybillFcdOpe/SelDhWaybillFcdByCon";
    /**
     * 发货库存
     */
    public static final String SHIPMENT_INVENTORY_SELECTED_INFO_GET = BASE_URI + "WaybillFcdOpe/SelDhWaybillFcdByCon";
    /**
     * 签收查询库存
     */
    public static final String SIGN_INVENTORY_SELECTED_INFO_GET = BASE_URI + "WaybillFcdOpe/SelFetchWaybillFcdByCon";
    /**
     * 查询收款方式
     */
    public static final String PAYMENT_METHOD_SELECTED_INFO_GET = BASE_URI + "Alltype/SelAlltypeByCondition";
    /**
     * 签收货物
     */
    public static final String RECEIPT_GOODS_POST = BASE_URI + "WaybillFetchOpe/AddWaybillFetch";


    /**
     * --------------------------------------------------------------------------
     */


    /**
     * 送货上门 -查询主表
     */
    public static final String DELIVERY_SOMETHING_SELECT_INFO_GET = BASE_URI + "WaybillSendOpe/SelWaybillSendByCondition";
    /**
     * 送货上门 -发车批次
     */
    public static final String DELIVERY_SOMETHING_DEPARTURE_GET = BASE_URI + "WaybillSendOpe/SelWaySenInOneVehicleFlag";
    /**
     * 送货上门 -库存
     */
    public static final String DELIVERY_SOMETHING_STOCK_GET = BASE_URI + "WaybillFcdOpe/SelWaySenWaybillFcdByCon";
    /**
     * 送货上门 -修改 获取车辆的货物库存
     */
    public static final String DELIVERY_SOMETHING_LOADING_STOCK_GET = BASE_URI + "WaybillSendOpe/SelWaybillSendDetByCondition";
    /**
     * 送货上门 - 本车加货
     */
    public static final String DELIVERY_SOMETHING_ADD_POST = BASE_URI + "WaybillSendOpe/CurWaybillSendAddWay";

    /**
     * 送货上门 - 本车移出货物
     */
    public static final String DELIVERY_SOMETHING_REMOVE_POST = BASE_URI + "WaybillSendOpe/CurWaybillSendDelWay";

    /**
     * 送货上门 - 新增 完成本车
     */
    public static final String DELIVERY_SOMETHING_NEW_COMPELETE_POST = BASE_URI + "WaybillSendOpe/AddWaybillSend";
    /**
     *
     */

    /**
     * 签收记录
     */
    public static final String SIGN_FOR_RECORD_SELECT_GET = BASE_URI + "WaybillFetchOpe/SelWaybillFetchByCondition";
    /**
     * 签收记录 取消签收
     */
    public static final String SIGN_FOR_RECORD_CANCEL_POST = BASE_URI + "WaybillFetchOpe/DelWaybillFetch";
    /**
     *
     */
    /**
     * 异常记录 新增 -查询运单的信息
     */
    public static final String EXCEPTION_RECORD_SELECT_ORDER_GET = BASE_URI + "WaybillOpe/SelWaybillByCondition";

    /**
     * 异常记录 新增 提交
     */
    public static final String EXCEPTION_RECORD_ADD_WRONG_POST = BASE_URI + "BadWaybill/AddBadWaybillOpe";
    /**
     * 异常记录 新增 -差错类型
     */
    public static final String EXCEPTION_RECORD_SELECT_WRONG_TYPE_GET = BASE_URI + "BadType/SelAllTypeByType";
    /**
     * 异常记录 新增 -差错类型 子类
     */
    public static final String EXCEPTION_RECORD_SELECT_WRONG_CHILDREN_TYPE_GET = BASE_URI + "BadType/SelBadChildTypeByType";
    /**
     * 上传图片
     */
    public static final String POST_PICTURE_POST = BASE_URI + "AllImages/UploadAllImages";


}
