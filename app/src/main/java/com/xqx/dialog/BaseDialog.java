package com.xqx.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Dialog基类
 */

public abstract class BaseDialog extends DialogFragment {
    //tag
    private static final String TAG = BaseDialog.class.getSimpleName();

    //默认遮罩透明度
    private static final float DEFAULT_MASK_TRANS = 0.6f;

    private boolean mIsShow = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, getStyleRes());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(getCancelOutside());
        View v = null;
        if (getLayoutRes() != 0) {
            v = inflater.inflate(getLayoutRes(), container, false);
        } else {
            if (getCustomView() != null) {
                v = getCustomView();
                //反复添加
                ViewGroup vg = (ViewGroup) v.getParent();
                if (vg != null) {
                    vg.removeView(v);
                }
            } else {
                throw new RuntimeException("have no view -getLayoutRes=0&&getCustomView=null");
            }
        }
        bindView(v);
        return v;
    }

    /**
     * 获取布局文件
     *
     * @return
     */
    @LayoutRes
    public abstract int getLayoutRes();

    /**
     * 获取自定义布局
     *
     * @return
     */
    public abstract View getCustomView();

    /**
     * 绑定的view处理
     *
     * @param v
     */
    public abstract void bindView(View v);

    /**
     * 获取主题
     *
     * @return
     */
    public abstract int getStyleRes();

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = getDimAmount();
        params.width = getWidth();
        params.height = getHeight();
        params.gravity = getGravity();
        window.setAttributes(params);
    }

    public int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public int getWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    /**
     * 获取点击其他区域是否关闭
     *
     * @return
     */
    public boolean getCancelOutside() {
        return true;
    }

    /**
     * 获取遮罩透明度
     *
     * @return
     */
    public float getDimAmount() {
        return DEFAULT_MASK_TRANS;
    }

    /**
     * 获取对齐方式
     *
     * @return
     */
    public int getGravity() {
        return Gravity.BOTTOM;
    }

    /**
     * 获取tag
     *
     * @return
     */
    public String getFragmentTag() {
        return TAG;
    }

    /**
     * show显示dialog
     *
     * @param fragmentManager
     */
    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, TAG);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (mIsShow) {
            return;
        }
        mIsShow = true;
        super.show(manager, tag);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mIsShow = false;
    }
}
