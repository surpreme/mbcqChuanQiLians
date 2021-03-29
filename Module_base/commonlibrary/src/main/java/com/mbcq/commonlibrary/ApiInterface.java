package com.mbcq.commonlibrary;

public class ApiInterface {
    //    private static final String BASE_URI = "http://192.168.2.43:8080/"; 本地
    private static final String BASE_URI = "http://47.99.130.236:8081/";//服务器
    public static final String BASE_URIS = "http://47.99.130.236:8081/";
    /**
     * 登录
     */
    public static final String LOG_IN_POST = BASE_URI + "Common/Login";
    /**
     * 获取导航菜单显示item
     */
    public static final String NAVIGATION_ITEM_MENU_AUTHORITY_GET = BASE_URI + "Menuinfo/SelMenuinfoV1";
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
    /*  */
    /**
     * 查询网点配置详细参数
     */
    /*
    public static final String ACCEPT_OUTLET_MORE_INFORMATION_GET = BASE_URI + "WebMain/SelWebMainByCondition";
 */
    /**
     * 受理开单 获取目的地
     */
    public static final String ACCEPT_DESTINATION_GET = BASE_URI + "Destination/SelDestinationByCondition";
    /**
     * 获取种类表
     * 受理开单 获取回单要求 @14 receipt requirements
     * 运输方式  @9 transport mode
     * 付款方式  @13 payment mode
     * ***************************
     * 查询收款方式  @26
     * **************************
     * 车辆档案  选择车的来源 @5
     */
    public static final String ALLTYPE_SELECT_GET = BASE_URI + "Alltype/SelAlltypeByCondition";
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
     * 受理开单 查询业务员
     */
    public static final String ACCEPT_SELECT_GETSALESMAN_GET = BASE_URI + "Salesman/SelSalesmanByCondition";


    /**
     * --------------------------------------------------------------------------------------------------------------
     */


    /**
     * 运单记录  获取所有信息 Waybill record
     */
    public static final String WAYBILL_RECORD_SELECT_ALLINFO_GET = BASE_URI + "WaybillOpe/SelWaybillByCondition";
    /**
     * 短驳通过运单号获取发车批次
     */
    public static final String WAYBILL_RECORD_SELECT_SHORT_VEHICLES_GET = BASE_URI + "DbVehicleOpe/SelSmDbVehicleByCondition";
    /**
     * 干线通过运单号获取发车批次
     */
    public static final String WAYBILL_RECORD_SELECT_DEPARTURE_VEHICLES_GET = BASE_URI + "GxVehicleOpe/SelSmGxVehicleByCondition";
    /**
     * 改单记录
     */
    public static final String FIXED_WAYBILL_RECORD_GET = BASE_URI + "WaybillUpdateApplyOpe/SelwaybillUpdateApplyByCondition";
    /**
     * 沿途配载 查询
     */
    public static final String STOWAGE_ALONG_WAY_RECORD_GET = BASE_URI + "GxVehicleOpe/SelSmGxVehicleByCondition";
    /**
     * 运单记录  删除运单
     */
    public static final String WAYBILL_RECORD_DELETE_INFO_POST = BASE_URI + "WaybillOpe/DelWaybill";

    /**
     * 上门提货  按运单获取库存
     */
    public static final String HOME_DELIVERY_INVENTORY_GET = BASE_URI + "PickUpOpe/SelWaybillByCondition";
    /**
     * 上门提货  获取车辆内的运单
     */
    public static final String HOME_DELIVERY_LOADING_GET = BASE_URI + "PickUpOpe/SelPickUpDetByCondition";
  /**
     * 上门提货  剔除
     */
    public static final String HOME_DELIVERY_REMOVE_ITEM_GET = BASE_URI + "PickUpOpe/DelPickUpOpeByCondition";
  /**
     * 上门提货  添加
     */
    public static final String HOME_DELIVERY_ADD_ITEM_GET = BASE_URI + "PickUpOpe/AddPickUpJoinOpeByCondition";


    /**
     * --------------------------------------------------------------------------------------------------------------
     */


    /**
     * 发车记录  干线发车 获取所有信息
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_INFO_GET = BASE_URI + "GxVehicleOpe/SelGxVehicleByCondition";
    /**
     * 干线 通过运单号查询车次
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_BILLNO_GET = BASE_URI + "GxVehicleOpe/SelGxInOneVehicleFlagCondition";

    /**
     * 发车记录  短驳发车 获取所有信息
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_SELECT_INFO_GET = BASE_URI + "DbVehicleOpe/SelDbVehicleByCondition";
    /**
     * 短驳 通过运单号查询车次
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_SELECT_BILLNO_GET = BASE_URI + "DbVehicleOpe/SelDbInOneVehicleFlagCondition";
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
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_ARRIVAL_CANCEL_LOCAL_INFO_POST = BASE_URI + "GxVehicleOpe/UpdCancelConfirmGxVehicle";
    /**
     * 短驳发车 查询在途车辆记录
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_LOADING_LOCAL_INFO_POST = BASE_URI + "DbVehicleOpe/SelNeeDbVehicleArrivalByCon";
    /**
     * 短驳到车 查询到达车辆
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_OVERRING_LOCAL_INFO_GET = BASE_URI + "DbVehicleOpe/SelDbVehicleByCondition";
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
     * 短驳发车 有计划扫描
     */
    public static final String DEPARTURE_SHORT_FEEDER_DEPARTURE_SCAN_INFO_POST = BASE_URI + "Scan/AddDbFcPlanWaybillScan";
    /**
     * 干线发车 有计划扫描
     */
    public static final String DEPARTURE_TRUNK_DEPARTURE_SCAN_INFO_POST = BASE_URI + "Scan/AddGxFcPlanWaybillScan";
    /**
     * 短驳发车 有计划扫描撤销
     */
    public static final String DEPARTURE_SHORT_FEEDER_DEPARTURE_SCAN_INFO_REVOKE_POST = BASE_URI + "Scan/DelDbFcPlanWaybillScan";
    /**
     * 短驳和干线发车 有计划扫描删除
     */
    public static final String DEPARTURE_SCAN_INFO_DELETE_POST = BASE_URI + "Scan/DelFcPlanWaybill";
    /**
     * 干线发车 有计划扫描撤销
     */
    public static final String DEPARTURE_DEPARTURE_FEEDER_DEPARTURE_SCAN_INFO_REVOKE_POST = BASE_URI + "Scan/DelGxFcPlanWaybillScan";
    /**
     * 短驳发车 确认到车
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_ARRIVAL_CONFIRM_LOCAL_INFO_POST = BASE_URI + "DbVehicleOpe/UpdConfirmDbVehicle";
    /**
     * 短驳发车 取消到车
     */
    public static final String DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_ARRIVAL_CANCEL_LOCAL_INFO_GET = BASE_URI + "DbVehicleOpe/UpdCancelConfirmDbVehicle";
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
     * 沿途配载 添加沿途网点
     */
    public static final String STOWAGE_ALONG_WAY_RECORD_MAIN_LINE_ADD_LOCAL_INFO_POST = BASE_URI + "GxVehicleYtOpe/AddGxVehicleYt";
    /**
     * 沿途配载 查询沿途网点
     */
    public static final String STOWAGE_ALONG_WAY_RECORD_MAIN_LINE_SELECT_LOCAL_INFO_POST = BASE_URI + "GxVehicleYtOpe/SelGxVehicleYtByCondition";
    /**
     * 沿途配载 删除全部沿途网点
     */
    public static final String STOWAGE_ALONG_WAY_RECORD_MAIN_LINE_DELETE_LOCAL_INFO_POST = BASE_URI + "GxVehicleYtOpe/DelGxVehicleYtByCondition";
    /**
     * 干线发车 确认到车
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_ARRIVAL_CONFIRM_LOCAL_INFO_POST = BASE_URI + "GxVehicleOpe/UpdConfirmGxVehicle";
    /**
     * 干线发车 查询在途车辆记录
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_LOADING_LOCAL_INFO_GET = BASE_URI + "GxVehicleOpe/SelNeeGxVehicleArrivalByCon";

    /**
     * 干线到车 查询到达车辆
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_OVERRING_LOCAL_INFO_GET = BASE_URI + "GxVehicleOpe/SelGxVehicleByCondition";
    /**
     * 干线到车 确认到车 后台新增 （special）
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SURE_ARRIVAL_POST = BASE_URI + "GxXcSm/GxVehicleSmOk";
    /**
     * 短驳到车 确认到车 后台新增 （special）
     */
    public static final String SHORT_MAIN_LINE_DEPARTURE_SURE_ARRIVAL_POST = BASE_URI + "DbXcSm/DbVehicleSmOk";

    /**
     * 干线到车 查询到达扫描车辆 后台新增 （special）
     */
    public static final String DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SCAN_OVER_LOCAL_INFO_GET = BASE_URI + "GxXcSm/GxVehicleSmList";
    /**
     * 短驳到车 查询到达扫描车辆 后台新增 （special）
     */
    public static final String SHORT_RECORD_MAIN_LINE_DEPARTURE_SCAN_OVER_LOCAL_INFO_GET = BASE_URI + "DbXcSm/DbVehicleSmList";
    /**
     * 干线扫描 扫描 后台新增 （special）
     */
    public static final String DEPARTURE_SCAN_ARRIVAL_DATA_POST = BASE_URI + "GxXcSm/AddGxXcWaybillScan";
    /**
     * 短驳扫描 扫描 后台新增 （special）
     */
    public static final String SHORT_SCAN_ARRIVAL_DATA_POST = BASE_URI + "DbXcSm/AddDbXcWaybillScan";

    /**
     * 干线扫描卸车获取车辆运单列表 后台新增（special）
     */
    public static final String DEPARTURE_VEHICLE_ORDER_INFO_GET = BASE_URI + "GxXcSm/GxVehicleSmDetailData";
    /**
     * 短驳扫描卸车获取车辆运单列表 后台新增（special）
     */
    public static final String SHORT_VEHICLE_ORDER_INFO_GET = BASE_URI + "DbXcSm/DbVehicleSmDetailData";

    /**
     * 干线扫描卸车 获取扫描详情 后台新增（special）
     */
    public static final String DEPARTURE_VEHICLE_SCANED_ORDER_INFO_GET = BASE_URI + "GxXcSm/SelGxXcWaybillScanDetail";
    /**
     * 短驳扫描卸车 获取扫描详情 后台新增（special）
     */
    public static final String SHORT_VEHICLE_SCANED_ORDER_INFO_GET = BASE_URI + "DbXcSm/SelDbXcWaybillScanDetail";

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
     * 完成上门提货
     */
    public static final String COMPELETE_HOME_DELIVERY_HOUSE_POST = BASE_URI + "PickUpOpe/AddPickUpOpeByCondition";
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
     * 本地代理-获取中转公司列表
     */
    public static final String LOCAL_AGENT_TRANSIT_COMPANY_GET = BASE_URI + "Carriage_Unit/SelCarriage_UnitByCondition";
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
     * //waybillFcd.SelType = 12; selloc=12 按开单时间查询
     * //waybillFcd.SelType=17; selloc=17 按到货时间查询
     */

    public static final String ARRIVAL_INVENTORY_SELECTED_INFO_GET = BASE_URI + "WaybillFcdOpe/SelDhWaybillFcdByCon";
    /**
     * 发货库存
     */
    public static final String SHIPMENT_INVENTORY_SELECTED_INFO_GET = BASE_URI + "WaybillFcdOpe/SelFhWaybillFcdByCon";
    /**
     * 签收查询库存
     */
    public static final String SIGN_INVENTORY_SELECTED_INFO_GET = BASE_URI + "WaybillFcdOpe/SelFetchWaybillFcdByCon";
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
     *
     */
    /**
     * 回单管理 回单签收记录——提取库存
     */
    public static final String RECEIPT_MANAGEMENT_SIGN_LOAD_GET = BASE_URI + "BackFetchOpe/SelKcBackFetchByCondition";
    /**
     * 回单管理 回单签收记录——完成
     */
    public static final String RECEIPT_MANAGEMENT_SIGN_OVER_POST = BASE_URI + "BackFetchOpe/AddBackFetch";
    /**
     * 回单管理 回单寄出记录——提取库存
     */
    public static final String RECEIPT_MANAGEMENT_CONSIGNMENT_LOAD_GET = BASE_URI + "BackFetchOpe/SelJcKcBackFetchByCondition";
    /**
     * 回单管理 回单寄出记录——完成
     */
    public static final String RECEIPT_MANAGEMENT_CONSIGNMENT_OVER_POST = BASE_URI + "BackFetchOpe/AddJcBackFetch";
    /**
     * 回单管理 回单接收记录——提取库存
     */

    public static final String RECEIPT_MANAGEMENT_RECEIVE_LOAD_GET = BASE_URI + "BackFetchOpe/SelJsKcBackFetchByCondition";
    /**
     * 回单管理 取消查询详情
     */

    public static final String RECEIPT_CANCEL_RECEIVE_INFO_GET = BASE_URI + "BackFetchOpe/SelQxBackFetchByCondition";
    /**
     * 回单管理 取消
     */

    public static final String RECEIPT_CANCEL_RECEIVE_REMOVE_STATE_GET = BASE_URI + "BackFetchOpe/CancelBackFetchByCondition";

    /**
     * 回单管理 回单接收记录——完成
     */

    public static final String RECEIPT_MANAGEMENT_RECEIVE_OVER_POST = BASE_URI + "BackFetchOpe/AddJsBackFetch";

    /**
     * 回单管理 回单返厂记录——提取库存
     */

    public static final String RECEIPT_MANAGEMENT_RETURN_FACTORY_LOAD_GET = BASE_URI + "BackFetchOpe/SelFcKcBackFetchByCondition";
    /**
     * 回单管理 回单返厂记录——完成
     */

    public static final String RECEIPT_MANAGEMENT_RETURN_FACTORY_OVER_POST = BASE_URI + "BackFetchOpe/UpdBackFetchByCondition";
    /**
     * * 回单管理 回单总账——全部
     */
    public static final String RECEIPT_GENERAL_LEDGER_RETURN_FACTORY_OVER_POST = BASE_URI + "BackFetchOpe/SelHdGeneralLedgerByCondition";

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
     * -查询运单的信息
     */
    public static final String RECORD_SELECT_ORDER_INFO_GET = BASE_URI + "WaybillOpe/SelWaybillByCondition";
    /**
     * 获取发货人列表
     */
    public static final String SHIPPER_SELECT_INFO_GET = BASE_URI + "Shipper/SelShipperByCondition";
    /**
     * 获取收货人列表
     */
    public static final String RECEIVER_SELECT_INFO_GET = BASE_URI + "Consignee/SelConsigneeByCondition";
    /**
     * 查询运单的信息 详细
     */
    public static final String RECORD_SELECT_ORDER_MORE_INFO_GET = BASE_URI + "WaybillOpe/SelOneWaybillById";

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
    /**
     * 下载
     */
    public static final String DOWNLOAD_PICTURE_GET = BASE_URI + "AllImages/SelAllImagesByCondition";
    /**
     * 获取运单轨迹
     */
    public static final String TRACK_ROAD_WAYBILL_GET = BASE_URI + "WaybillTrack/SelWaybillTrackByCondition";
    /**
     *
     */
    /**
     * 车辆档案 记录
     */
    public static final String VEHICLE_ARCHIVES_SELECT_GET = BASE_URI + "Vehicles/SelVehiclesByCondition";
    /**
     * 车辆档案 添加
     */
    public static final String VEHICLE_ARCHIVES_ADD_POST = BASE_URI + "Vehicles/AddVehicles";
    /**
     * 车辆档案 修改
     */
    public static final String VEHICLE_ARCHIVES_CHANGE_POST = BASE_URI + "Vehicles/UpdVehiclesByCondition";
    /**
     * 车辆档案 删除
     */
    public static final String VEHICLE_ARCHIVES_DELETE_POST = BASE_URI + "Vehicles/DelVehiclesByCondition";
    /**
     * 控货 - 修改信息
     */
    public static final String DELIVERY_ADJUSTMENT_UPDATE_POST = BASE_URI + "WaybillOpe/UpdWaybillByCondition";
    /**
     * 改单申请 添加
     */
    public static final String CHANGE_ORDER_APPLICATION_ADD_POST = BASE_URI + "WaybillUpdateApplyOpe/AddWaybillUpdateApplyOpeByCondition";

    /**
     * 贷款变更 添加
     */
    public static final String LOAN_CHANGE_ADD_POST = BASE_URI + "HkUpdate/UpdHkUpdateByCondition";
    /**
     * 货款回收
     */
    public static final String LOAN_RECYCLE_INFO_GET = BASE_URI + "HkRecoveryOpe/SelHkRecoveryByCondition";
    /**
     * 货款总账
     */
    public static final String GENERAL_LEDGER_INFO_GET = BASE_URI + "HkGeneralLedger/SelHkGeneralLedgerByCondition";

    /**
     * 货款回款确认
     */
    public static final String GENERAL_PAYMENT_CONFIRMATION_INFO_GET = BASE_URI + "HkReleasedOpe/SelHkReleasedByCondition";
    /**
     * 待发款明细表
     * 7
     */
    public static final String GENERAL_SCHEDULE_PAYMENTS_PENDING_INFO_GET = BASE_URI + "HkReleasedOpe/SelHkReleasedDetByCondition";
    /**
     * 短驳 发车更新数据操作
     */
    public static final String SCAN_SHORT_FEEDER_CONFIGURATION_FIXED_GET = BASE_URI + "DbVehicleOpe/UpdDbVehicleByCondition";
    /**
     * 干线 发车更新数据操作
     */

    public static final String SCAN_DEPARTURE_TRUNK_CONFIGURATION_FIXED_GET = BASE_URI + "GxVehicleOpe/UpdGxVehicleByCondition";
    /**
     * 查詢扫描数据
     */
    public static final String SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET = BASE_URI + "Scan/SelWaybillScanByCondition";
    /**
     * 改单记录 驳回申请
     */
    public static final String ACCEPT_BILLING_RECORDING_REJECT_ORDER_POST = BASE_URI + "WaybillUpdateApplyOpe/UpdWaybillCheckManByCondition";
    /**
     * 改单申请 获取详情
     */
    public static final String ACCEPT_BILLING_RECORDING_INFO_ORDER_GET = BASE_URI + "WaybillUpdateApplyOpe/SelwaybillUpdateApplyByCondition";
    /**
     * 改单申请 运营审核
     */
    public static final String ACCEPT_BILLING_RECORDING_INFO_OPERATION_REVIEW_POST = BASE_URI + "WaybillUpdateApplyOpe/UpdYywaybillUpdateApplyByCondition";
    /**
     * 改单申请 财务审核
     */
    public static final String ACCEPT_BILLING_RECORDING_INFO_FINANCE_REVIEW_POST = BASE_URI + "WaybillUpdateApplyOpe/UpdCwwaybillUpdateApplyByCondition";
    /**
     * 短驳到车 本车信封回单获取详情
     */
    public static final String SHORT_FEEDER_UNLOADING_WAREHOUSING_RECEIPT_GET = BASE_URI + "DbVehicleOpe/SelSmDbVehicleByConditionRts";
    /**
     * 短驳到车 卸车入库
     */
    public static final String SHORT_FEEDER_UNLOADING_WAREHOUSING_POST = BASE_URI + "DbVehicleOpe/UpdCofArrDbVehicle";
    /**
     * 干线到车 本车信封回单获取详情
     */
    public static final String TRUNK_DEPARTURE_UNLOADING_WAREHOUSING_RECEIPT_GET = BASE_URI + "GxVehicleOpe/SelSmGxVehicleByConditionRts";
    /**
     * 干线到车 卸车入库
     */
    public static final String TRUNK_DEPARTURE_UNLOADING_WAREHOUSING_POST = BASE_URI + "GxVehicleOpe/UpdCofArrGxVehicle";
    /**
     *
     */
    /**
     * 现付核销 获取记录
     */
    public static final String PAYMENTING_WRITE_OFF_RECORD_GET = BASE_URI + "CwhxOpe/SelAccNowHxByCondition";
    /**
     * 现付核销 核销
     */
    public static final String PAYMENTING_WRITE_OFF_SAVE_INFO_POST = BASE_URI + "CwhxOpe/AddAccNowHx";
    /**
     * 核销科目 查询
     */
    public static final String PAYMENTING_WRITE_OFF_TYPE_GET = BASE_URI + "ChargeOffItem/SelChargeOffItemByCondition";
    /**
     * 现付核销 获取 收款流水号
     */
    public static final String PAYMENTING_WRITE_OFF_SERIAL_NUMBER_GET = BASE_URI + "ChargeOffSSerialNo/SelChargeOffSSerialNo";
    /**
     * 现付核销 获取 凭证单号
     */
    public static final String PAYMENTING_WRITE_OFF_DOCUMENT_NO_GET = BASE_URI + "ChargeOffSReceiptNo/SelChargeOffSReceiptNo";
    /**
     * 提付核销 获取记录
     */
    public static final String PAYMENTED_WRITE_OFF_SERIAL_NUMBER_GET = BASE_URI + "CwhxOpe/SelAccArrivedHxByCondition";
    /**
     * 提付核销 核销
     */
    public static final String PAYMENTED_WRITE_OFF_SERIAL_SAVE_INFO_POST = BASE_URI + "CwhxOpe/AddAccArrivedHx";
    /**
     * 回单核销 获取记录
     */
    public static final String RECEIPT_MONTHLY_TUBERCULOSIS_SALES_RECORD_GET = BASE_URI + "CwhxOpe/SelAccBackHxByCondition";
    /**
     * 回单核销 核销
     */
    public static final String RECEIPT_MONTHLY_TUBERCULOSIS_SALES_SAVE_POST = BASE_URI + "CwhxOpe/AddAccBackHx";
    /**
     * 月付核销 获取记录
     */
    public static final String MONTHLY_TUBERCULOSIS_SALES_RECORD_GET = BASE_URI + "CwhxOpe/SelAccMonthHxByCondition";

    /**
     * 干线车费核销 获取记录
     */
    public static final String MAIN_LINE_FARE_RECORD_GET = BASE_URI + "CwhxOpe/SelGxAccHxByCondition";

    /**
     * 返款核销 获取记录
     */
    public static final String REBATE_VERIFICATION_RECORD_GET = BASE_URI + "CwhxOpe/SelAccHuiKouHxByCondition";
    /**
     * 中转费核销 获取记录
     */
    public static final String TRANSFER_FEE_VERIFICATION_RECORD_GET = BASE_URI + "CwhxOpe/SelOutaccHxByCondition";
    /**
     * 短驳车费 获取记录
     */
    public static final String SHUTTLE_FARE_VERIFICATION_RECORD_GET = BASE_URI + "CwhxOpe/SelDbAccHxByCondition";

    /**
     * 月付核销 核销
     */
    public static final String MONTHLY_TUBERCULOSIS_SALES_SAVE_POST = BASE_URI + "CwhxOpe/AddAccArrivedHx";
    /**
     * 短驳车费 核销
     */
    public static final String SHUTTLE_FARE_VERIFICATION_SAVE_POST = BASE_URI + "CwhxOpe/AddDbAccHx";

    /**
     * 返款核销 核销
     */
    public static final String REBATE_VERIFICATION_SAVE_POST = BASE_URI + "CwhxOpe/AddAccHuiKouHx";

    /**
     * 干线车费 核销
     */
    public static final String MAIN_LINE_FARE_SAVE_POST = BASE_URI + "CwhxOpe/AddGxAccHx";


    //*********


    /**
     * 上门提货 获取记录
     */
    public static final String HOME_DELIVERY_RECORD_GET = BASE_URI + "PickUpOpe/SelPickUpByCondition";
    /**
     * 上门提货 作废
     */
    public static final String HOME_DELIVERY_RECORD_REMOVE_POST = BASE_URI + "PickUpOpe/UpdPickUpOpeByCondition";

    /**
     * 新增 上门提货 获取发车批次
     */
    public static final String HOME_DELIVERY_BATCH_GET = BASE_URI + "PickUpOpe/SelSmthInOneVehicleFlag";


}
