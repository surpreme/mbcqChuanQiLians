package com.mbcq.orderlibrary.fragment.waybillinformation

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2020-10-20 13:13:03 运单信息
 */

class WaybillInformationPresenter : BasePresenterImpl<WaybillInformationContract.View>(), WaybillInformationContract.Presenter {
    /**
     *{"code":0,"msg":"","count":1,"data":[
    {
    "id": 21,
    "webidCodeStr": "汕头",
    "webidCode": 1003,
    "isCanRecGoo": 1,
    "isCanRecGooStr": "是",
    "isUseMonth": 0,
    "isUseMonthStr": "是",
    "showCost": "16,5,1,2,3,4,6,7,8,18,17,15,14,13,12,11,10,9",
    "showCostStr": "外转费,工本费,基本运费,提货费,打包费,送货费,保价费,燃油费,返款,回单服务费,进仓费,分拣费,安装费,上楼费,拆包费,装卸费,垫付费,短信费",
    "showCostFilNam": "accWz,accGb,accTrans,accFetch,accPackage,accSend,accSafe,accRyf,accHuiKou,accBackService,accJc,accFj,accAz,accSl,accCb,accZx,accZz,accSms",
    "canAccType": "1,2,3,4,6,5,7",
    "canAccTypeStr": "现付,提付,回单付,货款扣,免费,月结,两笔付",
    "accGb": 5.00,
    "limDowAccTrans": 3.00,
    "limDowAccDaiShou": 5.00,
    "canHandBill": 1,
    "canHandBillStr": "是",
    "priBillCount": 1,
    "priFetCount": 1,
    "isCanSenDs": 0,
    "isCanSenDsStr": "",
    "isCanRecDs": 0,
    "isCanRecDsStr": "",
    "isMustIdCard": 0,
    "isMustIdCardStr": "",
    "isUseWaiNot": 1,
    "isUseWaiNotStr": "",
    "accNowIsCanHk": 0,
    "accNowIsCanHkStr": "现付,提付,回单付,货款扣",
    "skipCursor": "",
    "banInfIsWri": 0,
    "banInfIsWriStr": "",
    "arrHowHouCae": 0,
    "accSafe": 0.00,
    "bqWebidCode": 1002,
    "bqWebidCodeStr": "义乌后湖",
    "dsOverDay": 13,
    "pickUpOverFcdDay": 23,
    "sendOverFcdDay": 232,
    "cashFcdOverMoney": 3.00,
    "sendBackOverDay": 121,
    "pickUpBackOverDay": 112,
    "bdAgentBackOverDay": 1,
    "zdAgentBackOverDay": 22,
    "cusComNoDealwithDay": 11,
    "moneyExceptionOut": 1,
    "sendNoFetchOverDay": 1,
    "outNoFetchOverDay": 1,
    "canEwebidCode": "",
    "canEwebidCodeStr": "",
    "joinBelongWebidCode": 1003,
    "joinBelongWebidCodeStr": "汕头",
    "balanceType": 1,
    "balanceTypeStr": "比例",
    "belongArea": 1,
    "belongAreaStr": "",
    "isUseTbAutoCount": 1,
    "isUseOnlinePay": 1,
    "isBasAccTraNoCanLessMinPri": 1,
    "isUseOneCityDelivery": 1,
    "isUseWms": 1,
    "unionPayBusinessNo": "1123",
    "unionPaySecret": "45664565",
    "unionPayCashier": "4564564654654",
    "isTransferCount": 1,
    "isTransferCountStr": "1",
    "isAutoChargeOff": 1,
    "isAutoChargeOffStr": "是",
    "isNoBalance": 1,
    "isNoBalanceStr": "1",
    "isStop": 1,
    "isStopStr": "是",
    "opeMan": "测试",
    "recordDate": "2019-03-08T15:32:38",
    "mustWrite": "orderId,billno,,shipperMb,shpperAddr,shipperCid,consignee,consigneeTel,consigneeMb,consigneeAddr,product,qty,packages,weight,volumn,
    weightJs,safeMoney,qtyPrice,wPrice,vPrice,shipperCompany,shipperAddr,consigneeCompany,Lightandheavy,ewebidCode,destination,valueAddedService",
    "vehicleinfo": 1,
    "vehicleinfoStr": null,
    "element": 0.00
    }
    ]}
     */
    override fun getCostInformation(webidCode: String) {
        val params = HttpParams()
        params.put("webidCode", webidCode)
        get<String>(ApiInterface.ACCEPT_SELECT_COST_INFORMATION_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data")?.let {
                        if (!it.isNull(0)) {
                            mView?.getCostInformationS(it.get(0).toString())
                        }


                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }

            }

        })
    }

    override fun getOrderBigInfo(billno: String) {
        val params = HttpParams()
        params.put("billno", billno)
        get<String>(ApiInterface.RECORD_SELECT_ORDER_MORES_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data")?.let {
                        if (!it.isNull(0)) {
                            mView?.getOrderBigInfoS(it.get(0).toString())
                        }


                    }
                } else {
                    mView?.showError("服务器返回异常信息:找不到此运单！")
                }

            }

        })
    }

}