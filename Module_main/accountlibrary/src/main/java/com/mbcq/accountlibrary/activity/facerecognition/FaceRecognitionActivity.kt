package com.mbcq.accountlibrary.activity.facerecognition


import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Point
import android.hardware.Camera
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.widget.CompoundButton
import android.widget.Switch
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.arcsoft.face.ErrorInfo
import com.arcsoft.face.FaceEngine
import com.arcsoft.face.FaceFeature
import com.arcsoft.face.LivenessInfo
import com.arcsoft.face.enums.DetectFaceOrientPriority
import com.arcsoft.face.enums.DetectMode
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.facerecognition.*
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/**
 * @author: lzy
 * @time: 2018.08.25
 */

@Route(path = ARouterConstants.FaceRecognitionActivity)
class FaceRecognitionActivity : BaseMVPActivity<FaceRecognitionContract.View, FaceRecognitionPresenter>(), FaceRecognitionContract.View, OnGlobalLayoutListener {
    private val TAG = "RegisterAndRecognize"
    private val MAX_DETECT_NUM = 10

    /**
     * 当FR成功，活体未成功时，FR等待活体的时间
     */
    private val WAIT_LIVENESS_INTERVAL = 100

    /**
     * 失败重试间隔时间（ms）
     */
    private val FAIL_RETRY_INTERVAL: Long = 1000

    /**
     * 出错重试最大次数
     */
    private val MAX_RETRY_TIME = 3

    private var cameraHelper: CameraHelper? = null
    private var drawHelper: DrawHelper? = null
    private lateinit var previewSize: Camera.Size

    /**
     * 优先打开的摄像头，本界面主要用于单目RGB摄像头设备，因此默认打开前置
     */
    private val rgbCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT

    /**
     * VIDEO模式人脸检测引擎，用于预览帧人脸追踪
     */
    private var ftEngine: FaceEngine? = null

    /**
     * 用于特征提取的引擎
     */
    private var frEngine: FaceEngine? = null

    /**
     * IMAGE模式活体检测引擎，用于预览帧人脸活体检测
     */
    private var flEngine: FaceEngine? = null

    private var ftInitCode = -1
    private var frInitCode = -1
    private var flInitCode = -1
    private var faceHelper: FaceHelper? = null
    private lateinit var compareResultList: ArrayList<CompareResult>
    private var adapter: FaceSearchResultAdapter? = null

    /**
     * 活体检测的开关
     */
    private var livenessDetect = true

    /**
     * 注册人脸状态码，准备注册
     */
    private val REGISTER_STATUS_READY = 0

    /**
     * 注册人脸状态码，注册中
     */
    private val REGISTER_STATUS_PROCESSING = 1

    /**
     * 注册人脸状态码，注册结束（无论成功失败）
     */
    private val REGISTER_STATUS_DONE = 2

    private val registerStatus = REGISTER_STATUS_DONE

    /**
     * 用于记录人脸识别相关状态
     */
    private val requestFeatureStatusMap = ConcurrentHashMap<Int, Int>()

    /**
     * 用于记录人脸特征提取出错重试次数
     */
    private val extractErrorRetryMap = ConcurrentHashMap<Int, Int>()

    /**
     * 用于存储活体值
     */
    private val livenessMap = ConcurrentHashMap<Int, Int>()

    /**
     * 用于存储活体检测出错重试次数
     */
    private val livenessErrorRetryMap = ConcurrentHashMap<Int, Int>()

    private val getFeatureDelayedDisposables = CompositeDisposable()
    private val delayFaceTaskCompositeDisposable = CompositeDisposable()

    /**
     * 相机预览显示的控件，可为SurfaceView或TextureView
     */
    private lateinit var previewView: View

    /**
     * 绘制人脸框的控件
     */
    private var faceRectView: FaceRectView? = null

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchLivenessDetect: Switch

    private val ACTION_REQUEST_PERMISSIONS = 0x001

    /**
     * 识别阈值
     */
    private val SIMILAR_THRESHOLD = 0.8f

    override fun getLayoutId(): Int = R.layout.activity_face_recognition

    override fun onBeforeCreate() {
        super.onBeforeCreate()
        //保持亮屏
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    override fun initExtra() {
        super.initExtra()
        // Activity启动后就锁定为启动时的方向
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        //本地人脸库初始化
        FaceServer.getInstance().init(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        previewView = findViewById(R.id.single_camera_texture_preview)
        //在布局结束后才做初始化操作
        previewView.viewTreeObserver.addOnGlobalLayoutListener(this)

        faceRectView = findViewById(R.id.single_camera_face_rect_view)
        switchLivenessDetect = findViewById(R.id.single_camera_switch_liveness_detect)
        switchLivenessDetect.isChecked = livenessDetect
        switchLivenessDetect.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked -> livenessDetect = isChecked })
        val recyclerShowFaceInfo: RecyclerView = findViewById(R.id.single_camera_recycler_view_person)
        compareResultList = ArrayList()
        adapter = FaceSearchResultAdapter(compareResultList, this)
        recyclerShowFaceInfo.adapter = adapter
        val dm = resources.displayMetrics
        val spanCount = (dm.widthPixels / (resources.displayMetrics.density * 100 + 0.5f)).toInt()
        recyclerShowFaceInfo.layoutManager = GridLayoutManager(this, spanCount)
        recyclerShowFaceInfo.itemAnimator = DefaultItemAnimator()

    }

    override fun onGlobalLayout() {
        previewView.viewTreeObserver.removeOnGlobalLayoutListener(this)
        initEngine()
        initCamera()

    }

    /**
     * 初始化引擎
     */
    private fun initEngine() {
        ftEngine = FaceEngine().also {
            ftInitCode = it.init(this, DetectMode.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(this),
                    16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_DETECT)
            frEngine = FaceEngine()
            frInitCode = it.init(this, DetectMode.ASF_DETECT_MODE_IMAGE, DetectFaceOrientPriority.ASF_OP_0_ONLY,
                    16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_RECOGNITION)
            flEngine = FaceEngine()
            flInitCode = it.init(this, DetectMode.ASF_DETECT_MODE_IMAGE, DetectFaceOrientPriority.ASF_OP_0_ONLY,
                    16, MAX_DETECT_NUM, FaceEngine.ASF_LIVENESS)
        }

        if (ftInitCode != ErrorInfo.MOK) {
//            val error = getString("%s 初始化失败，错误码为:%d", "ftEngine", ftInitCode)
            showToast("错误码为:${ftInitCode}")
        }

    }

    private fun initCamera() {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val faceListener: FaceListener = object : FaceListener {
            override fun onFail(e: Exception) {
                Log.e("", "onFail: " + e.message)
            }

            //请求FR的回调
            override fun onFaceFeatureInfoGet(@Nullable faceFeature: FaceFeature?, requestId: Int, errorCode: Int?) {
                //FR成功
                if (faceFeature != null) {
//                    Log.i(TAG, "onPreview: fr end = " + System.currentTimeMillis() + " trackId = " + requestId);
                    val liveness = livenessMap[requestId]
                    //不做活体检测的情况，直接搜索
                    if (!livenessDetect) {
                        searchFace(faceFeature, requestId)
                    } else if (liveness != null && liveness == LivenessInfo.ALIVE) {
                        searchFace(faceFeature, requestId)
                    } else {
                        if (requestFeatureStatusMap.containsKey(requestId)) {
                            Observable.timer(WAIT_LIVENESS_INTERVAL.toLong(), TimeUnit.MILLISECONDS)
                                    .subscribe(object : Observer<Long> {
                                        var disposable: Disposable? = null
                                        override fun onSubscribe(d: Disposable) {
                                            disposable = d
                                            getFeatureDelayedDisposables.add(disposable!!)
                                        }
                                        override fun onError(e: Throwable) {}
                                        override fun onComplete() {
                                            getFeatureDelayedDisposables.remove(disposable!!)
                                        }

                                        override fun onNext(t: Long) {
                                            onFaceFeatureInfoGet(faceFeature, requestId, errorCode)

                                        }
                                    })
                        }
                    }
                } else {
                    if (increaseAndGetValue(extractErrorRetryMap, requestId) > MAX_RETRY_TIME) {
                        extractErrorRetryMap[requestId!!] = 0
                        val msg: String
                        // 传入的FaceInfo在指定的图像上无法解析人脸，此处使用的是RGB人脸数据，一般是人脸模糊
                        msg = if (errorCode != null && errorCode == ErrorInfo.MERR_FSDK_FACEFEATURE_LOW_CONFIDENCE_LEVEL) {
                            getString(R.string.low_confidence_level)
                        } else {
                            "ExtractCode:$errorCode"
                        }
                        faceHelper!!.setName(requestId, getString(R.string.recognize_failed_notice, msg))
                        // 在尝试最大次数后，特征提取仍然失败，则认为识别未通过
                        requestFeatureStatusMap[requestId] = RequestFeatureStatus.FAILED
                        retryRecognizeDelayed(requestId)
                    } else {
                        requestFeatureStatusMap[requestId!!] = RequestFeatureStatus.TO_RETRY
                    }
                }
            }

            override fun onFaceLivenessInfoGet(@Nullable livenessInfo: LivenessInfo?, requestId: Int, errorCode: Int?) {
                if (livenessInfo != null) {
                    val liveness = livenessInfo.liveness
                    livenessMap[requestId!!] = liveness
                    // 非活体，重试
                    if (liveness == LivenessInfo.NOT_ALIVE) {
                        faceHelper!!.setName(requestId, getString(R.string.recognize_failed_notice, "NOT_ALIVE"))
                        // 延迟 FAIL_RETRY_INTERVAL 后，将该人脸状态置为UNKNOWN，帧回调处理时会重新进行活体检测
//                        retryLivenessDetectDelayed(requestId)TODO
                    }
                } else {
                    if (increaseAndGetValue(livenessErrorRetryMap, requestId) > MAX_RETRY_TIME) {
                        livenessErrorRetryMap[requestId!!] = 0
                        val msg: String
                        // 传入的FaceInfo在指定的图像上无法解析人脸，此处使用的是RGB人脸数据，一般是人脸模糊
                        msg = if (errorCode != null && errorCode == ErrorInfo.MERR_FSDK_FACEFEATURE_LOW_CONFIDENCE_LEVEL) {
                            getString(R.string.low_confidence_level)
                        } else {
                            "ProcessCode:$errorCode"
                        }
                        faceHelper!!.setName(requestId, getString(R.string.recognize_failed_notice, msg))
//                        retryLivenessDetectDelayed(requestId)  TODO
                    } else {
                        livenessMap[requestId!!] = LivenessInfo.UNKNOWN
                    }
                }
            }
        }
        val cameraListener: CameraListener = object : CameraListener {
            override fun onCameraOpened(camera: Camera, cameraId: Int, displayOrientation: Int, isMirror: Boolean) {
                val lastPreviewSize = previewSize
                previewSize = camera.parameters.previewSize
                drawHelper = DrawHelper(previewSize.width, previewSize.height, previewView.width, previewView.height, displayOrientation, cameraId, isMirror, false, false)
                Log.i(TAG, "onCameraOpened: " + drawHelper.toString())
                // 切换相机的时候可能会导致预览尺寸发生变化
                if (faceHelper == null || lastPreviewSize == null || lastPreviewSize.width != previewSize.width || lastPreviewSize.height != previewSize.height) {
                    var trackedFaceCount: Int? = null
                    // 记录切换时的人脸序号
                    if (faceHelper != null) {
                        TODO()
                      /*  trackedFaceCount = faceHelper.getTrackedFaceCount()
                        faceHelper.release()*/
                    }
                 TODO()/*   faceHelper = Builder()
                            .ftEngine(ftEngine)
                            .frEngine(frEngine)
                            .flEngine(flEngine)
                            .frQueueSize(MAX_DETECT_NUM)
                            .flQueueSize(MAX_DETECT_NUM)
                            .previewSize(previewSize)
                            .faceListener(faceListener)
                            .trackedFaceCount(trackedFaceCount
                                    ?: ConfigUtil.getTrackedFaceCount(applicationContext))
                            .build()*/
                }
            }

            override fun onPreview(nv21: ByteArray?, camera: Camera?) {
                if (faceRectView != null) {
                    faceRectView!!.clearFaceInfo()
                }
                val facePreviewInfoList = faceHelper!!.onPreviewFrame(nv21)
            /*    if (facePreviewInfoList != null && faceRectView != null && drawHelper != null) {
                    drawPreviewInfo(facePreviewInfoList)
                }
                registerFace(nv21, facePreviewInfoList)
                clearLeftFace(facePreviewInfoList)*/TODO()
                if (facePreviewInfoList != null && facePreviewInfoList.size > 0 && previewSize != null) {
                    for (i in facePreviewInfoList.indices) {
                        val status = requestFeatureStatusMap[facePreviewInfoList[i].trackId]
                        /**
                         * 在活体检测开启，在人脸识别状态不为成功或人脸活体状态不为处理中（ANALYZING）且不为处理完成（ALIVE、NOT_ALIVE）时重新进行活体检测
                         */
                        if (livenessDetect && (status == null || status !== RequestFeatureStatus.SUCCEED)) {
                            val liveness = livenessMap[facePreviewInfoList[i].trackId]
                            if (liveness == null
                                    || liveness != LivenessInfo.ALIVE && liveness != LivenessInfo.NOT_ALIVE && liveness !== RequestLivenessStatus.ANALYZING) {
                                livenessMap[facePreviewInfoList[i].trackId] = RequestLivenessStatus.ANALYZING
                                faceHelper!!.requestFaceLiveness(nv21, facePreviewInfoList[i].faceInfo, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, facePreviewInfoList[i].trackId, LivenessType.RGB)
                            }
                        }
                        /**
                         * 对于每个人脸，若状态为空或者为失败，则请求特征提取（可根据需要添加其他判断以限制特征提取次数），
                         * 特征提取回传的人脸特征结果在[FaceListener.onFaceFeatureInfoGet]中回传
                         */
                        if (status == null
                                || status === RequestFeatureStatus.TO_RETRY) {
                            requestFeatureStatusMap[facePreviewInfoList[i].trackId] = RequestFeatureStatus.SEARCHING
                            faceHelper!!.requestFaceFeature(nv21, facePreviewInfoList[i].faceInfo, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, facePreviewInfoList[i].trackId)
                            //                            Log.i(TAG, "onPreview: fr start = " + System.currentTimeMillis() + " trackId = " + facePreviewInfoList.get(i).getTrackedFaceCount());
                        }
                    }
                }
            }

            override fun onCameraClosed() {
                Log.i(TAG, "onCameraClosed: ")
            }

            override fun onCameraError(e: Exception) {
                Log.i(TAG, "onCameraError: " + e.message)
            }

            override fun onCameraConfigurationChanged(cameraID: Int, displayOrientation: Int) {

                drawHelper?.cameraDisplayOrientation = displayOrientation
                Log.i(TAG, "onCameraConfigurationChanged: $cameraID  $displayOrientation")
            }
        }
        cameraHelper = CameraHelper.Builder()
                .previewViewSize(Point(previewView.measuredWidth, previewView.measuredHeight))
                .rotation(windowManager.defaultDisplay.rotation)
                .specificCameraId(rgbCameraID ?: Camera.CameraInfo.CAMERA_FACING_FRONT)
                .isMirror(false)
                .previewOn(previewView)
                .cameraListener(cameraListener)
                .build()
        cameraHelper?.init()
        cameraHelper?.start()
    }

    private fun searchFace(frFace: FaceFeature, requestId: Int) {
        Observable
                .create(ObservableOnSubscribe<CompareResult?> { emitter -> //                        Log.i(TAG, "subscribe: fr search start = " + System.currentTimeMillis() + " trackId = " + requestId);
                    val compareResult = FaceServer.getInstance().getTopOfFaceLib(frFace)
                    //                        Log.i(TAG, "subscribe: fr search end = " + System.currentTimeMillis() + " trackId = " + requestId);
                    emitter.onNext(compareResult)
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<CompareResult?> {
                    override fun onSubscribe(d: Disposable) {}


                    override fun onError(e: Throwable) {
                        faceHelper!!.setName(requestId, getString(R.string.recognize_failed_notice, "NOT_REGISTERED"))
                        retryRecognizeDelayed(requestId)
                    }

                    override fun onComplete() {}
                    override fun onNext(compareResult: CompareResult) {
                        if (compareResult.userName == null) {
                            requestFeatureStatusMap[requestId] = RequestFeatureStatus.FAILED
                            faceHelper!!.setName(requestId, "VISITOR $requestId")
                            return
                        }

//                        Log.i(TAG, "onNext: fr search get result  = " + System.currentTimeMillis() + " trackId = " + requestId + "  similar = " + compareResult.getSimilar());
                        if (compareResult.similar > SIMILAR_THRESHOLD) {
                            var isAdded = false
                            for (compareResult1 in compareResultList) {
                                if (compareResult1.trackId === requestId) {
                                    isAdded = true
                                    break
                                }
                            }
                            if (!isAdded) {
                                //对于多人脸搜索，假如最大显示数量为 MAX_DETECT_NUM 且有新的人脸进入，则以队列的形式移除
                                if (compareResultList.size >= MAX_DETECT_NUM) {
                                    compareResultList.removeAt(0)
                                    adapter!!.notifyItemRemoved(0)
                                }
                                //添加显示人员时，保存其trackId
                                compareResult.trackId = requestId
                                compareResultList.add(compareResult)
                                adapter!!.notifyItemInserted(compareResultList!!.size - 1)
                            }
                            requestFeatureStatusMap[requestId] = RequestFeatureStatus.SUCCEED
                            faceHelper!!.setName(requestId, getString(R.string.recognize_success_notice, compareResult.userName))
                        } else {
                            faceHelper!!.setName(requestId, getString(R.string.recognize_failed_notice, "NOT_REGISTERED"))
                            retryRecognizeDelayed(requestId)
                        }
                    }
                })
    }

    /**
     * 延迟 FAIL_RETRY_INTERVAL 重新进行人脸识别
     *
     * @param requestId 人脸ID
     */
    private fun retryRecognizeDelayed(requestId: Int) {
        requestFeatureStatusMap[requestId] = RequestFeatureStatus.FAILED
        Observable.timer(FAIL_RETRY_INTERVAL, TimeUnit.MILLISECONDS)
                .subscribe(object : Observer<Long> {
                    var disposable: Disposable? = null
                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                        delayFaceTaskCompositeDisposable.add(disposable!!)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        // 将该人脸特征提取状态置为FAILED，帧回调处理时会重新进行活体检测
                        faceHelper?.setName(requestId, requestId.toString())
                        requestFeatureStatusMap[requestId] = RequestFeatureStatus.TO_RETRY
                        disposable?.let {
                            delayFaceTaskCompositeDisposable.remove(it)
                        }
                    }

                    override fun onNext(t: Long) {
                    }
                })
    }

    /**
     * 将map中key对应的value增1回传
     *
     * @param countMap map
     * @param key      key
     * @return 增1后的value
     */
    fun increaseAndGetValue(countMap: MutableMap<Int, Int>, key: Int): Int {
        if (countMap == null) {
            return 0
        }
        var value = countMap[key]
        if (value == null) {
            value = 0
        }
        countMap[key] = ++value
        return value
    }
}