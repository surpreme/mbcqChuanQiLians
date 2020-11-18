package com.mbcq.baselibrary.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.mbcq.baselibrary.R;
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义软键盘
 * https://blog.csdn.net/qq_37980878/article/details/79023794 code 代码
 * TODO 返回键隐藏键盘失败 判断键盘视图是否显示失败
 * 已处理 不确认会不会出问题
 */

public class KeyBoardUtil {
    /**
     * 显示键盘的视图
     */
    private Activity mActivity;
    /**
     * 键盘视图
     */
    private KeyboardView mKeyboardView;
    /**
     * 键盘
     */
    private Keyboard mKeyboard;
    /**
     * 输入框
     */
    private EditText mEditText;
    /**
     * 键盘布局
     */
    private View mViewContainer;

    /**
     * 焦点改变监听
     */
    View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) showSoftKeyboard();
            else hideSoftKeyboard();
        }
    };

    /**
     * 构造方法
     *
     * @param activity 根视图
     */
    public KeyBoardUtil(Activity activity) {
        this.mActivity = activity;
        this.mKeyboard = new Keyboard(mActivity, R.xml.base_keyboard_number_white_style);
    }

    /**
     * 绑定输入框
     *
     * @param editText 输入框
     * @param isAuto   是否自动显示
     */
    public void attachTo(EditText editText, boolean isAuto) {
        this.mEditText = editText;
        hideSystemSoftKeyboard(this.mEditText);
        setAutoShowOnFocus(isAuto);
    }

    /**
     * 隐藏系统软件盘
     *
     * @param editText 输入框
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void hideSystemSoftKeyboard(EditText editText) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt < 11) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 键盘是否显示
     */
    private boolean isShow = false;

    /**
     * 焦点时自动显示
     *
     * @param enabled 是否显示
     */
    private void setAutoShowOnFocus(boolean enabled) {
        if (null == mEditText) return;
        if (enabled) mEditText.setOnFocusChangeListener(mOnFocusChangeListener);
        else mEditText.setOnFocusChangeListener(null);
    }

    /**
     * 显示软键盘
     */
    public void showSoftKeyboard() {
        if (null == mViewContainer) {
            mViewContainer = mActivity.getLayoutInflater().inflate(R.layout.dialog_number_keyboard, null);
        } else {
            if (null != mViewContainer.getParent()) return;
        }
        hideSystemSoftKeyboard(this.mEditText);
        FrameLayout frameLayout = (FrameLayout) mActivity.getWindow().getDecorView();
        KeyboardView keyboardView = mViewContainer.findViewById(R.id.activity_main_keyboard);
        this.mKeyboardView = keyboardView;
        this.mKeyboardView.setKeyboard(mKeyboard);
        this.mKeyboardView.setEnabled(true);
        this.mKeyboardView.setPreviewEnabled(false);
        this.mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        isShow = true;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        if (ScreenSizeUtils.INSTANCE.checkDeviceHasNavigationBar(mActivity))
            frameLayout.setPadding(0, 0, 0, ScreenSizeUtils.INSTANCE.getNavigationBarHeight(mActivity));
        layoutParams.gravity = Gravity.BOTTOM;
        frameLayout.addView(mViewContainer, layoutParams);
//        mViewContainer.setAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.anim_down_to_up));
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyboard() {
        if (null != mViewContainer && null != mViewContainer.getParent()) {
//            mViewContainer.setAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.anim_up_to_down));
            ((ViewGroup) mViewContainer.getParent()).removeView(mViewContainer);
        }
        isShow = false;
    }

    /**
     * 判断是否显示
     *
     * @return true, 显示; false, 不显示
     */
    public boolean isShowing() {
        if (null == mViewContainer) return false;
        return mViewContainer.getVisibility() == View.VISIBLE;
    }

    /**
     * @return
     */
    public boolean isShowings() {
        return isShow;
    }

    KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int i) {
        }

        @Override
        public void onRelease(int i) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (null != mEditText) keyCode(primaryCode, mEditText);
            mKeyboardView.postInvalidate();
        }

        @Override
        public void onText(CharSequence charSequence) {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeUp() {
        }
    };

    /**
     * 字符
     *
     * @param primaryCode 主要字符
     * @param editText    编辑框
     */
    private void keyCode(int primaryCode, EditText editText) {
        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        if (primaryCode == Keyboard.KEYCODE_DELETE) { // 回退
            if (editText.hasFocus()) {
                if (!TextUtils.isEmpty(editable)) {
                    if (start > 0) editable.delete(start - 1, start);
                }
            }
        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) { // 大小写切换
            mKeyboardView.setKeyboard(mKeyboard);
        } else if (primaryCode == -4) {
            hideSoftKeyboard();
        } else {
            if (editText.hasFocus()) editable.insert(start, Character.toString((char) primaryCode));
        }
    }
}

