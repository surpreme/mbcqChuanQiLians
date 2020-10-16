package com.mbcq.vehicleslibrary.activity.fixedvehiclesarchives


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.commonlibrary.dialog.FilterMoreWebDialog
import com.mbcq.commonlibrary.dialog.TimeDialogUtil
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.addvehiclesarchives.AddVehicleArchivesTypeBean
import com.mbcq.vehicleslibrary.activity.vehiclesarchives.VehicleArchivesBean
import kotlinx.android.synthetic.main.activity_fixed_vehicle_archives.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author: lzy
 * @time: 2020-10-14 17:32:12 修改车辆档案
 */

@Route(path = ARouterConstants.FixedVehicleArchivesActivity)
class FixedVehicleArchivesActivity : BaseMVPActivity<FixedVehicleArchivesContract.View, FixedVehicleArchivesPresenter>(), FixedVehicleArchivesContract.View {
    @Autowired(name = "FixedVehicleArchives")
    @JvmField
    var mLastDataJson: String = ""

    var mDataId =0
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun getLayoutId(): Int = R.layout.activity_fixed_vehicle_archives
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        showFixedInfo()

    }

    fun showFixedInfo() {
        val mVehicleArchivesBean = Gson().fromJson<VehicleArchivesBean>(mLastDataJson, VehicleArchivesBean::class.java)
        mIntentSelectRaduioId = if (mVehicleArchivesBean.vusetype != 0) mVehicleArchivesBean.vusetype - 1 else 0
        plate_number_ed.setText(mVehicleArchivesBean.vehicleno)
        driver_ed.setText(mVehicleArchivesBean.chauffer)
        vehicle_shape_ed.setText(mVehicleArchivesBean.vehicleshape)
        sup_weight_ed.setText(mVehicleArchivesBean.supweight.toString())
        volumn_ed.setText(mVehicleArchivesBean.volumn.toString())
        driver_tel_number_ed.setText(mVehicleArchivesBean.chauffertel.toString())
        driver_phone_number_ed.setText(mVehicleArchivesBean.chauffermb)
        driver_card_number_ed.setText(mVehicleArchivesBean.driverlicense)
        mVehicleType = mVehicleArchivesBean.vehicletype
        vehicle_type_tv.text = if (mVehicleArchivesBean.vehicletype == 1) "大车" else "小车"
        annual_review_date_tv.text = mVehicleArchivesBean.yeachedate
        current_webid_tv.text = mVehicleArchivesBean.belweb
        mCurrentWebIdId = mVehicleArchivesBean.belweb
        mCurrentWebIdTag = mVehicleArchivesBean.belwebcode.toString()
        mSharedWebIdId = mVehicleArchivesBean.sharewebcode.toString()
        mSelectSharedWebIdList = mVehicleArchivesBean.shareweb.split(",")
        mSharedWebIdTag = mVehicleArchivesBean.shareweb
        shared_webid_tv.text = mVehicleArchivesBean.shareweb
        mDataId = mVehicleArchivesBean.id
        unreliable_cb.isChecked = mVehicleArchivesBean.israliable == 1
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getTransportMode()
    }

    fun isCanSave(): Boolean {
        if (plate_number_ed.text.toString().isEmpty()) {
            showToast("请输入车牌号")
            return false
        }
        if (driver_ed.text.toString().isEmpty()) {
            showToast("请输入驾驶员")
            return false
        }
        if (annual_review_date_tv.text.toString().isEmpty()) {
            showToast("请选择年审日期")
            return false
        }
        if (driver_phone_number_ed.text.toString().isEmpty()) {
            showToast("请输入手机号码")
            return false
        }
        return true

    }

    fun saveVehicleInfo() {
        val obj = JSONObject()
        val Id = mDataId
        obj.put("Id", Id)

        val VehicleNo = plate_number_ed.text//车牌号
        obj.put("VehicleNo", VehicleNo)
        val VehicleType = mVehicleType//车类别
        obj.put("VehicleType", VehicleType)
        val VehicleShape = vehicle_shape_ed.text//车型
        obj.put("VehicleShape", VehicleShape)
        val VLength = ""//车长
        obj.put("VLength", VLength)
        val VWidth = ""//车宽
        obj.put("VWidth", VWidth)
        val VHeight = ""//高度
        obj.put("VHeight", VHeight)
        val EngineNum = ""//发动机编号
        obj.put("EngineNum", EngineNum)
        val YlpNum = ""//养路票号
        obj.put("YlpNum", YlpNum)
        val VjNum = ""//车架号
        obj.put("VjNum", VjNum)
        val AddNum = ""//付加费号
        obj.put("AddNum", AddNum)
        val OpeNum = ""//营运号
        obj.put("OpeNum", OpeNum)
        val VColor = ""//车辆颜色
        obj.put("VColor", VColor)
        val BuyDate = ""//购买日期
        obj.put("BuyDate", BuyDate)
        val SupWeight = sup_weight_ed.text.toString()//承重
        obj.put("SupWeight", SupWeight)
        val VOlumn = volumn_ed.text.toString()//体积
        obj.put("VOlumn", VOlumn)
        val YeacheDate = annual_review_date_tv.text.toString()//年审日期
        obj.put("YeacheDate", YeacheDate)
        val SafeNum = ""//保险号
        obj.put("SafeNum", SafeNum)
        val SafeUnit = ""//承保单位
        obj.put("SafeUnit", SafeUnit)
        val BoxMoney = ""//铝车厢费
        obj.put("BoxMoney", BoxMoney)
        val VOwner = ""//车主
        obj.put("VOwner", VOwner)
        val IdCard = ""//身份证号
        obj.put("IdCard", IdCard)
        val OwnerTel = ""//车主座机号
        obj.put("OwnerTel", OwnerTel)
        val OwnerMb = ""//车主手机号
        obj.put("OwnerMb", OwnerMb)
        val OwnerAdd = ""//车主地址
        obj.put("OwnerAdd", OwnerAdd)
        val VbelongUnit = ""//车属单位
        obj.put("VbelongUnit", VbelongUnit)
        val UnitAdd = ""//v单位地址
        obj.put("UnitAdd", UnitAdd)
        val Chauffer = driver_ed.text.toString()//驾驶员
        obj.put("Chauffer", Chauffer)
        val ChaidCard = ""//驾驶员身份证号
        obj.put("ChaidCard", ChaidCard)
        val ChaufferTel = driver_tel_number_ed.text.toString()//驾驶员座机号
        obj.put("ChaufferTel", ChaufferTel)
        val ChaufferMb = driver_phone_number_ed.text.toString()//驾驶员手机号
        obj.put("ChaufferMb", ChaufferMb)
        val DriverLicense = driver_card_number_ed.text.toString()//驾驶证
        obj.put("DriverLicense", DriverLicense)
        val ChaAdd = ""//驾驶员家庭住址
        obj.put("ChaAdd", ChaAdd)
        val BelWebCode = mCurrentWebIdTag//所属网点编码
        obj.put("BelWebCode", BelWebCode)
        val BelWeb = mCurrentWebIdId//所属网点编码对应的文字
        obj.put("BelWeb", BelWeb)
        val ShareWebCode = mSharedWebIdId//共享网点编码
        obj.put("ShareWebCode", ShareWebCode)
        val ShareWeb = mSharedWebIdTag//共享网点编码对应的文字
        obj.put("ShareWeb", ShareWeb)
        val VuseType = mTransneed//使用类型
        obj.put("VuseType", VuseType)
        val OilWear = ""//油耗
        obj.put("OilWear", OilWear)
        val IsraLiable = if (unreliable_cb.isChecked) 1 else 0//是否可靠0代表可靠1代表不可靠
        obj.put("IsraLiable", IsraLiable)
        mPresenter?.saveInfo(obj)
    }

    override fun onClick() {
        super.onClick()
        annual_review_date_ll.setOnClickListener(object : SingleClick() {
            @SuppressLint("SimpleDateFormat")
            override fun onSingleClick(v: View) {
                hideKeyboard(v)
                TimeDialogUtil.getChoiceTimer(mContext, OnTimeSelectListener { date, _ ->
                    val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val format: String = mDateFormat.format(date)
                    annual_review_date_tv.text = format


                }, "选择开始时间", isStartCurrentTime = false, isEndCurrentTime = false, isYear = true, isHM = false, isDialog = false).show(annual_review_date_ll)
            }

        })
        commit_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (isCanSave())
                    saveVehicleInfo()
            }

        })
        current_webid_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {
                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        showCurrentWebIdDialog(list)
                    }

                })
            }

        })

        shared_webid_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {
                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        showSharedWebIdDialog(list)
                    }

                })
            }

        })

        fixed_vehicle_archives_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })

        vehicle_type_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                showCarType()
            }

        })
    }

    var mCurrentWebIdTag = ""
    var mCurrentWebIdId = ""
    private fun showCurrentWebIdDialog(list: MutableList<WebAreaDbInfo>) {
        FilterDialog(getScreenWidth(), Gson().toJson(list), "webid", "选择所属网点", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                current_webid_tv.text = list[position].webid
                mCurrentWebIdTag = list[position].webid
                mCurrentWebIdId = list[position].webidCode
            }

        }).show(supportFragmentManager, "showCurrentWebIdDialog")

    }

    var mSharedWebIdTag = ""
    var mSharedWebIdId = ""
    var mSelectSharedWebIdList: List<String>? = null
    private fun showSharedWebIdDialog(list: MutableList<WebAreaDbInfo>) {
        if (!mSelectSharedWebIdList.isNullOrEmpty()) {
            FilterMoreWebDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", mSelectSharedWebIdList!!, isShowTime = false, isOnlyLast = false, tips = "选择共享网点", isGridLayoutManager = true, mClickInterface = object : FilterMoreWebDialog.OnClickInterface {
                /**
                 * s1 网点id
                 * s2 网点 名称
                 * s3  start@end
                 */


                /**
                 * s1 网点id
                 * s2 网点 名称
                 * s3  start@end
                 */


                override fun onResult(s1: String, s2: String, s3: String) {
                    mSelectSharedWebIdList = s2.split(",")
                    shared_webid_tv.text = s2
                    mSharedWebIdTag = s2
                    mSharedWebIdId = s1

                }

            }).show(supportFragmentManager, "showSharedWebIdDialog")
        } else {
            FilterMoreWebDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, isShowTime = false, isOnlyLast = false, tips = "选择共享网点", isGridLayoutManager = true, mClickInterface = object : FilterMoreWebDialog.OnClickInterface {
                /**
                 * s1 网点id
                 * s2 网点 名称
                 * s3  start@end
                 */


                /**
                 * s1 网点id
                 * s2 网点 名称
                 * s3  start@end
                 */


                override fun onResult(s1: String, s2: String, s3: String) {
                    mSelectSharedWebIdList = s2.split(",")
                    shared_webid_tv.text = s2
                    mSharedWebIdTag = s2
                    mSharedWebIdId = s1

                }

            }).show(supportFragmentManager, "showFirstSharedWebIdDialog")
        }


    }

    var mVehicleType = 1
    fun showCarType() {
        val list = mutableListOf<AddVehicleArchivesTypeBean>()
        for (index in 0..1) {
            val mAddVehicleArchivesTypeBean = AddVehicleArchivesTypeBean()
            when (index) {
                0 -> {
                    mAddVehicleArchivesTypeBean.tag = "1"
                    mAddVehicleArchivesTypeBean.title = "大车"

                }
                1 -> {
                    mAddVehicleArchivesTypeBean.tag = "2"
                    mAddVehicleArchivesTypeBean.title = "小车"

                }
            }
            list.add(mAddVehicleArchivesTypeBean)
        }
        FilterDialog(getScreenWidth(), Gson().toJson(list), "title", "选择车类型", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            @SuppressLint("SetTextI18n")
            override fun onItemClick(v: View, position: Int, mResult: String) {
                vehicle_type_tv.text = list[position].title
                mVehicleType = if (list[position].tag == "1") 1 else 2
            }

        }).show(supportFragmentManager, "showCarTypeDialog")

    }

    override fun saveInfoS(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), "车辆档案${plate_number_ed.text}  修改完成，点击返回查看详情！") {
            onBackPressed()
        }.show()

    }

    var mIntentSelectRaduioId = 0
    var mTransneed = ""
    var mTransneedStr = ""
    override fun getTransportModeS(result: String) {
        val mTransportModeArray = JSONArray(result)
        /**
         * 默认数据
         */
        if (!mTransportModeArray.isNull(mIntentSelectRaduioId)) {
            mTransportModeArray.optJSONObject(mIntentSelectRaduioId)?.let {
                mTransneed = it.optString("typecode")
                mTransneedStr = it.optString("tdescribe")
            }
        }
        /**
         * 添加数据到view
         */
        for (mIndex in 0 until mTransportModeArray.length()) {
            val obj = mTransportModeArray.optJSONObject(mIndex)
            obj?.let {
                val mRadioButton = RadioButton(mContext)
                mRadioButton.id = mIndex
                mRadioButton.text = it.optString("tdescribe")
                owner_type_rg.addView(mRadioButton)
            }
        }
        owner_type_rg.check(mIntentSelectRaduioId)
        /**
         * 选中后的操作
         */
        owner_type_rg.setOnCheckedChangeListener { _, checkedId ->
            mTransneed = mTransportModeArray.getJSONObject(checkedId).optString("typecode")
            mTransneedStr = mTransportModeArray.getJSONObject(checkedId).optString("tdescribe")
        }
    }

}