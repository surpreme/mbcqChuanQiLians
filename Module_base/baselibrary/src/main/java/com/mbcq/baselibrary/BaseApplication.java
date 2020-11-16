package com.mbcq.baselibrary;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.provider.SyncStateContract;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.mbcq.baselibrary.db.SPUtil;
import com.mbcq.baselibrary.finger.FingerConstant;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;
import okhttp3.OkHttpClient;

public class BaseApplication extends Application {
    private static Application context;
    //    private DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        init();

    }

    private void init() {
        initARouter();
        initCrasher();
        initFingerShare();
        initStetho();
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }


    /**
     * 指纹登录初始化
     */
    private void initFingerShare() {
        SPUtil.init(context, FingerConstant.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);

    }

    /**
     * https://blog.csdn.net/huangxiaoguo1/article/details/79053197
     * 配置参考
     */
    @SuppressLint("RestrictedApi")
    private void initCrasher() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .enabled(true) //default: true
                .showErrorDetails(true) //default: true
                .showRestartButton(true) //default: true
                .logErrorOnRestart(true) //default: true
                .trackActivities(true) //default: false
                .minTimeBetweenCrashesMs(2000) //default: 3000
                .errorDrawable(R.drawable.customactivityoncrash_error_image) //default: bug image
//                .restartActivity(YourCustomActivity.class) //default: null (your app's launch activity)
//                .errorActivity(YourCustomErrorActivity.class) //default: null (default error activity)
//                .eventListener(new YourCustomEventListener()) //default: null
                .apply();
        CustomActivityOnCrash.install(this);

    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);

    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.purples, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static Context getContext() {
        return context;
    }
}
