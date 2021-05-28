package com.mbcq.commonlibrary

object ARouterConstants {
    /**
     * 账号模块
     */
    const val LogInActivity = "/account/LogInActivity"
    const val HouseActivity = "/account/HouseActivity"
    const val SettingActivity = "/account/SettingActivity"
    const val BlueActivity = "/account/BlueActivity"
    const val SignatureActivity = "/account/SignatureActivity"
    const val WebPageLogInActivity = "/account/WebPageLogInActivity"
    const val CommonlyInformationActivity = "/account/CommonlyInformationActivity"
    const val HouseSearchActivity = "/account/HouseSearchActivity"
    const val CommonlyInformationConfigurationActivity = "/account/CommonlyInformationConfigurationActivity"
    const val CommonlyInformationConfigurationRemarkActivity = "/account/CommonlyInformationConfigurationRemarkActivity"
    const val AboutUsActivity = "/account/AboutUsActivity"
    const val FaceRecognitionActivity = "/account/FaceRecognitionActivity"

    /**
     * 订单模块
     */
    //受理开单
    const val AcceptBillingActivity = "/order/AcceptBillingActivity"

    //运单记录
    const val WaybillRecordActivity = "/order/WaybillRecordActivity"

    //添加发货人
    const val AddShipperActivity = "/order/AddShipperActivity"

    //修改发货人
    const val FixShipperActivity = "/order/FixShipperActivity"

    //修改收货客户
    const val FixReceiverActivity = "/order/FixReceiverActivity"

    //添加收货人
    const val AddReceiverActivity = "/order/AddReceiverActivity"
    const val ControlManagementActivity = "/order/ControlManagementActivity"
    const val DeliveryAdjustmentActivity = "/order/DeliveryAdjustmentActivity"
    const val AcceptBillingRecordingActivity = "/order/AcceptBillingRecordingActivity"
    const val FixedAcceptBillingActivity = "/order/FixedAcceptBillingActivity"
    const val WaybillDetailsActivity = "/order/WaybillDetailsActivity"
    const val PrintAcceptBillingActivity = "/order/PrintAcceptBillingActivity"
    const val ClaimSettlementActivity = "/order/ClaimSettlementActivity"

    //选择发货人
    const val ChoiceShipperActivity = "/order/ChoiceShipperActivity"

    //选择收货人
    const val ChoiceReceiverActivity = "/order/ChoiceReceiverActivity"

    //货物签收详情 以及 签收
    const val GoodsReceiptInfoActivity = "/order/GoodsReceiptInfoActivity"
    const val PayBarActivity = "/order/PayBarActivity"
    const val PaymentSuccessActivity = "/order/PaymentSuccessActivity"
    const val ExceptionRegistrationActivity = "/order/ExceptionRegistrationActivity"

    //送货
    const val DeliverySomeThingActivity = "/order/DeliverySomeThingActivity"

    //送货新增配置页面 暂时弃用
    const val AddDeliverySomeThingActivity = "/order/AddDeliverySomeThingActivity"

    //送货新增操作页面
    const val DeliverySomethingHouseActivity = "/order/DeliverySomethingHouseActivity"
    const val FixedDeliverySomethingHouseActivity = "/order/FixedDeliverySomethingHouseActivity"
    const val AddClaimSettlementActivity = "/order/AddClaimSettlementActivity"
    const val ReceiptInformationActivity = "/order/ReceiptInformationActivity"
    const val ReceiptSignActivity = "/order/ReceiptSignActivity"
    const val ReceiptConsignmentActivity = "/order/ReceiptConsignmentActivity"
    const val ReceiptReceiveActivity = "/order/ReceiptReceiveActivity"
    const val ReceiptReturnFactoryActivity = "/order/ReceiptReturnFactoryActivity"
    const val ReceiptGeneralLedgerActivity = "/order/ReceiptGeneralLedgerActivity"
    const val ReceiptCancelActivity = "/order/ReceiptCancelActivity"
    const val StowageAlongWayActivity = "/order/StowageAlongWayActivity"
    const val AcceptBillingFixedReviewActivity = "/order/AcceptBillingFixedReviewActivity"
    const val GoodsReceiptHouseActivity = "/order/GoodsReceiptHouseActivity"

    //货物签收详情
    const val GoodsReceiptHouseInfoActivity = "/order/GoodsReceiptHouseInfoActivity"

    //送货地图选择
    const val DeliverySomethingMapHouseActivity = "/order/DeliverySomethingMapHouseActivity"


    /**
     * 地图模块
     */
    const val LocationActivity = "/map/LocationActivity"
    const val LocationTestActivity = "/map/LocationTestActivity"

    /**
     * 车辆模块
     */
    const val DepartureRecordActivity = "/vehicles/DepartureRecordActivity"

    //添加上门提货配置
    const val AddHomeDeliveryActivity = "/vehicles/AddHomeDeliveryActivity"

    //上门提货记录
    const val HomeDeliveryActivity = "/vehicles/HomeDeliveryActivity"

    //修改上门提货配置
    const val FixHomeDeliveryConfigurationActivity = "/vehicles/FixHomeDeliveryConfigurationActivity"

    //上门提货 操作页
    const val HomeDeliveryHouseActivity = "/vehicles/HomeDeliveryHouseActivity"

    //上门提货修改
    const val FixedHomeDeliveryHouseActivity = "/vehicles/FixedHomeDeliveryHouseActivity"
    const val TrunkDepartureRecordActivity = "/vehicles/TrunkDepartureRecordActivity"
    const val ShortBargeDepartureRecordActivity = "/vehicles/ShortBargeDepartureRecordActivity"
    const val AddTrunkDepartureActivity = "/vehicles/AddTrunkDepartureActivity"
    const val AddShortFeederActivity = "/vehicles/AddShortFeederActivity"
    const val ShortFeederHouseActivity = "/vehicles/ShortFeederHouseActivity"
    const val TrunkDepartureHouseActivity = "/vehicles/TrunkDepartureHouseActivity"
    const val FixedTrunkDepartureHouseActivity = "/vehicles/FixedTrunkDepartureHouseActivity"
    const val FixShortFeederHouseActivity = "/vehicles/FixShortFeederHouseActivity"
    const val ArrivalRecordActivity = "/vehicles/ArrivalRecordActivity"
    const val ShortBargeArrivalRecordActivity = "/vehicles/ShortBargeArrivalRecordActivity"
    const val TrunkArrivalRecordActivity = "/vehicles/TrunkArrivalRecordActivity"
    const val UnloadingWarehousingActivity = "/vehicles/UnloadingWarehousingActivity"
    const val LoadingVehiclesActivity = "/vehicles/LoadingVehiclesActivity"
    const val ShortBargeUnLoadingVehiclesActivity = "/vehicles/ShortBargeUnLoadingVehiclesActivity"
    const val TrunkLoadingVehiclesActivity = "/vehicles/TrunkLoadingVehiclesActivity"
    const val ShortBargeLoadingVehiclesActivity = "/vehicles/ShortBargeLoadingVehiclesActivity"
    const val TrunkUnLoadingVehiclesActivity = "/vehicles/TrunkUnLoadingVehiclesActivity"
    const val LocalAgentActivity = "/vehicles/LocalAgentActivity"
    const val VehicleArchivesActivity = "/vehicles/VehicleArchivesActivity"

    //新增外转出库配置
    const val AddLocalGentShortFeederActivity = "/vehicles/AddLocalGentShortFeederActivity"

    //外转操作页
    const val LocalGentShortFeederHouseActivity = "/vehicles/LocalGentShortFeederHouseActivity"
    const val FixedLocalGentShortFeederHouseActivity = "/vehicles/FixedLocalGentShortFeederHouseActivity"
    const val TerminalAgentActivity = "/vehicles/TerminalAgentActivity"
    const val AddTerminalAgentByCarActivity = "/vehicles/AddTerminalAgentByCarActivity"
    const val FixedTerminalAgentByCarActivity = "/vehicles/FixedTerminalAgentByCarActivity"
    const val TerminalAgentByCarHouseActivity = "/vehicles/TerminalAgentByCarHouseActivity"
    const val ArrivalInventoryActivity = "/vehicles/ArrivalInventoryActivity"
    const val ShipmentInventoryActivity = "/vehicles/ShipmentInventoryActivity"
    const val AddVehicleArchivesActivity = "/vehicles/AddVehicleArchivesActivity"
    const val FixedVehicleArchivesActivity = "/vehicles/FixedVehicleArchivesActivity"
    const val ArrivalVehiclesScanActivity = "/vehicles/ArrivalVehiclesScanActivity"
    const val AddScanShortFeederActivity = "/vehicles/AddScanShortFeederActivity"
    const val AddDepartureTrunkActivity = "/vehicles/AddDepartureTrunkActivity"
    const val ArrivalTrunkDepartureScanOperatingActivity = "/vehicles/ArrivalTrunkDepartureScanOperatingActivity"
    const val DepartureTrunkDepartureScanOperatingActivity = "/vehicles/DepartureTrunkDepartureScanOperatingActivity"
    const val ShortTrunkDepartureScanOperatingActivity = "/vehicles/ShortTrunkDepartureScanOperatingActivity"
    const val RevokeShortTrunkDepartureScanOperatingActivity = "/vehicles/RevokeShortTrunkDepartureScanOperatingActivity"
    const val ShortTrunkDepartureUnPlanScanOperatingActivity = "/vehicles/ShortTrunkDepartureUnPlanScanOperatingActivity"
    const val RevokeDepartureTrunkDepartureScanOperatingActivity = "/vehicles/RevokeDepartureTrunkDepartureScanOperatingActivity"
    const val DepartureTrunkDepartureUnPlanScanOperatingActivity = "/vehicles/DepartureTrunkDepartureUnPlanScanOperatingActivity"
    const val StowageAlongWayHouseActivity = "/vehicles/StowageAlongWayHouseActivity"
    const val FixedScanShortFeederConfigurationActivity = "/vehicles/FixedScanShortFeederConfigurationActivity"
    const val FixedScanDepartureTrunkConfigurationActivity = "/vehicles/FixedScanDepartureTrunkConfigurationActivity"
    const val ShortHouseChecklistActivity = "/vehicles/ShortHouseChecklistActivity"
    const val DepartureHouseChecklistActivity = "/vehicles/DepartureHouseChecklistActivity"
    const val ShortTrunkDepartureScanOperatingMoreInfoActivity = "/vehicles/ShortTrunkDepartureScanOperatingMoreInfoActivity"
    const val DepartureTrunkDepartureScanOperatingScanInfoActivity = "/vehicles/DepartureTrunkDepartureScanOperatingScanInfoActivity"
    const val FixedShortFeederConfigurationActivity = "/vehicles/FixedShortFeederConfigurationActivity"
    const val FixedDepartureTrunkConfigurationActivity = "/vehicles/FixedDepartureTrunkConfigurationActivity"

    //短驳到车清单
    const val ShortFeederUnloadingWarehousingActivity = "/vehicles/ShortFeederUnloadingWarehousingActivity"

    //干线到车清单
    const val TrunkDepartureUnloadingWarehousingActivity = "/vehicles/TrunkDepartureUnloadingWarehousingActivity"
    const val LocalGentShortFeederHouseInfoActivity = "/vehicles/LocalGentShortFeederHouseInfoActivity"

    //到车干线扫描详情
    const val ArrivalScanOperatingMoreInfoActivity = "/vehicles/ArrivalScanOperatingMoreInfoActivity"

    //到车短驳扫描页面
    const val ArrivalShortScanOperatingActivity = "/vehicles/ArrivalShortScanOperatingActivity"

    //到车短驳扫描详情
    const val ArrivalShortScanOperatingMoreInfoActivityActivity = "/vehicles/ArrivalShortScanOperatingMoreInfoActivityActivity"

    //到车短驳记录
    const val ArrivalVehiclesScanShortActivity = "/vehicles/ArrivalVehiclesScanShortActivity"

    //到车干线记录
    const val ArrivalVehiclesScanShortTrunkActivity = "/vehicles/ArrivalVehiclesScanShortTrunkActivity"

    //修改上门提货
    const val FixHomeDeliveryHouseActivity = "/vehicles/FixHomeDeliveryHouseActivity"

    /**
     * 财务模块
     */
    const val LoanChangeActivity = "/amount/LoanChangeActivity"
    const val LoanRecycleActivity = "/amount/LoanRecycleActivity"
    const val GeneratePaymentVoucherActivity = "/amount/GeneratePaymentVoucherActivity"
    const val GeneralLedgerActivity = "/amount/GeneralLedgerActivity"
    const val LoanIssuanceActivity = "/amount/LoanIssuanceActivity"
    const val GenerateReceiptVoucherActivity = "/amount/GenerateReceiptVoucherActivity"
    const val PaymentingWriteoffActivity = "/amount/PaymentingWriteoffActivity"
    const val PaymentedWriteoffActivity = "/amount/PaymentedWriteoffActivity"
    const val PaymentingWriteOffPayCardActivity = "/amount/PaymentingWriteOffPayCardActivity"
    const val PaymentedWriteOffPayCardActivity = "/amount/PaymentedWriteOffPayCardActivity"
    const val PaymentedWriteoffInfoActivity = "/amount/PaymentedWriteoffInfoActivity"
    const val ReceiptMonthlyTuberculosisSalesActivity = "/amount/ReceiptMonthlyTuberculosisSalesActivity"
    const val ReceiptTuberculosisSalesPayCardActivity = "/amount/ReceiptTuberculosisSalesPayCardActivity"
    const val MonthlyTuberculosisSalesActivity = "/amount/MonthlyTuberculosisSalesActivity"
    const val MonthlyTuberculosisSalesPayCardActivity = "/amount/MonthlyTuberculosisSalesPayCardActivity"
    const val MonthlyTuberculosisSalesInfoActivity = "/amount/MonthlyTuberculosisSalesInfoActivity"
    const val ReceiptTuberculosisSalesInfoActivity = "/amount/ReceiptTuberculosisSalesInfoActivity"
    const val CommonWriteOffActivity = "/amount/CommonWriteOffActivity"
    const val CommonWriteOffPayCardActivity = "/amount/CommonWriteOffPayCardActivity"
    const val CommonWriteOffInfoActivity = "/amount/CommonWriteOffInfoActivity"

    //应收核销凭证
    const val CommonWriteOffReceivePayCardActivity = "/amount/CommonWriteOffReceivePayCardActivity"
    const val CommonReceiveWriteOffActivity = "/amount/CommonReceiveWriteOffActivity"
    const val CommonReceiveWriteOffInfoActivity = "/amount/CommonReceiveWriteOffInfoActivity"

    /**
     * 公用模块
     */
    const val TakePhotosActivity = "/common/TakePhotosActivity"
    const val ScanActivity = "/common/ScanActivity"
}