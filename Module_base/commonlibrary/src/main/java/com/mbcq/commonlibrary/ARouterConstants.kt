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
    const val AboutUsActivity = "/account/AboutUsActivity"

    /**
     * 订单模块
     */
    const val AcceptBillingActivity = "/order/AcceptBillingActivity"
    const val WaybillRecordActivity = "/order/WaybillRecordActivity"
    const val AddShipperActivity = "/order/AddShipperActivity"
    const val AddReceiverActivity = "/order/AddReceiverActivity"
    const val ControlManagementActivity = "/order/ControlManagementActivity"
    const val DeliveryAdjustmentActivity = "/order/DeliveryAdjustmentActivity"
    const val AcceptBillingRecordingActivity = "/order/AcceptBillingRecordingActivity"
    const val FixedAcceptBillingActivity = "/order/FixedAcceptBillingActivity"
    const val WaybillDetailsActivity = "/order/WaybillDetailsActivity"
    const val PrintAcceptBillingActivity = "/order/PrintAcceptBillingActivity"
    const val ClaimSettlementActivity = "/order/ClaimSettlementActivity"
    const val ChoiceShipperActivity = "/order/ChoiceShipperActivity"
    const val ChoiceReceiverActivity = "/order/ChoiceReceiverActivity"
    const val GoodsReceiptActivity = "/order/GoodsReceiptActivity"
    const val GoodsReceiptInfoActivity = "/order/GoodsReceiptInfoActivity"
    const val PayBarActivity = "/order/PayBarActivity"
    const val PaymentSuccessActivity = "/order/PaymentSuccessActivity"
    const val ExceptionRegistrationActivity = "/order/ExceptionRegistrationActivity"
    const val DeliverySomeThingActivity = "/order/DeliverySomeThingActivity"
    const val AddDeliverySomeThingActivity = "/order/AddDeliverySomeThingActivity"
    const val DeliverySomethingHouseActivity = "/order/DeliverySomethingHouseActivity"
    const val FixedDeliverySomethingHouseActivity = "/order/FixedDeliverySomethingHouseActivity"
    const val SignRecordActivity = "/order/SignRecordActivity"
    const val AddClaimSettlementActivity = "/order/AddClaimSettlementActivity"
    const val ReceiptInformationActivity = "/order/ReceiptInformationActivity"
    const val ReceiptSignActivity = "/order/ReceiptSignActivity"
    const val ReceiptConsignmentActivity = "/order/ReceiptConsignmentActivity"
    const val ReceiptReceiveActivity = "/order/ReceiptReceiveActivity"
    const val ReceiptReturnFactoryActivity = "/order/ReceiptReturnFactoryActivity"
    const val ReceiptGeneralLedgerActivity = "/order/ReceiptGeneralLedgerActivity"
    const val ReceiptCancelActivity = "/order/ReceiptCancelActivity"
    const val StowageAlongWayActivity = "/order/StowageAlongWayActivity"

    /**
     * 地图模块
     */
    const val LocationActivity = "/map/LocationActivity"

    /**
     * 车辆模块
     */
    const val DepartureRecordActivity = "/vehicles/DepartureRecordActivity"
    const val AddTrunkDepartureActivity = "/vehicles/AddTrunkDepartureActivity"
    const val AddShortFeederActivity = "/vehicles/AddShortFeederActivity"
    const val ShortFeederHouseActivity = "/vehicles/ShortFeederHouseActivity"
    const val TrunkDepartureHouseActivity = "/vehicles/TrunkDepartureHouseActivity"
    const val FixedTrunkDepartureHouseActivity = "/vehicles/FixedTrunkDepartureHouseActivity"
    const val FixShortFeederHouseActivity = "/vehicles/FixShortFeederHouseActivity"
    const val ArrivalRecordActivity = "/vehicles/ArrivalRecordActivity"
    const val UnloadingWarehousingActivity = "/vehicles/UnloadingWarehousingActivity"
    const val LoadingVehiclesActivity = "/vehicles/LoadingVehiclesActivity"
    const val LocalAgentActivity = "/vehicles/LocalAgentActivity"
    const val VehicleArchivesActivity = "/vehicles/VehicleArchivesActivity"
    const val AddLocalGentShortFeederActivity = "/vehicles/AddLocalGentShortFeederActivity"
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

    /**
     * 财务模块
     */
    const val LoanChangeActivity = "/amount/LoanChangeActivity"
    const val LoanRecycleActivity = "/amount/LoanRecycleActivity"
    const val GeneratePaymentVoucherActivity = "/amount/GeneratePaymentVoucherActivity"
    const val GeneralLedgerActivity = "/amount/GeneralLedgerActivity"
    const val LoanIssuanceActivity = "/amount/LoanIssuanceActivity"
    const val GenerateReceiptVoucherActivity = "/amount/GenerateReceiptVoucherActivity"

    /**
     * 公用模块
     */
    const val TakePhotosActivity = "/common/TakePhotosActivity"
    const val ScanActivity = "/common/ScanActivity"
}