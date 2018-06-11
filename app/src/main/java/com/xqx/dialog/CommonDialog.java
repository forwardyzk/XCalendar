package com.xqx.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;

import com.xqx.xcalendar.R;


/**
 * 公共的Dialog,默认的是从底部弹出,底部消失,如果想修改布局或者样式,请使用方法修改
 */

public class CommonDialog extends BaseDialog {
    //tag
    private String TAG = "comment_dialog";

    private static final int DISSMISS_MSG_CODE = 100;
    //layout imageKey
    private static final String KEY_LAYOUT_RES = "comment_layout_res";
    //遮罩透明度 imageKey
    private static final String KEY_DIM = "comment_dim";
    //是否触摸关闭key
    private static final String KEY_CANCEL_OUTSIDE = "comment_cancel_outside";
    //关闭时间
    private static final String KEY_CANCEL_CUTDOWN = "comment_cancel_cutdown";
    //FragmentManager
    private FragmentManager mFragmentManager;
    //是否点击其他区域关闭 默认关闭
    private boolean mIsCancelOutside = true;
    //遮罩透明度 默认是0.2f
    private float mDimAmount = 0.2f;
    // layout布局
    @LayoutRes
    private int mLayoutRes;
    //自定义布局
    private View mCustomView;
    //显示时间  默认是秒值
    private long mCutDown = -1;

    private CutDownTime cutDownTime;


    public static CommonDialog create(FragmentManager mFragmentManager) {
        CommonDialog commentDialog = new CommonDialog();
        commentDialog.setFragmentManager(mFragmentManager);
        return commentDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLayoutRes = savedInstanceState.getInt(KEY_LAYOUT_RES);
            mDimAmount = savedInstanceState.getFloat(KEY_DIM);
            mIsCancelOutside = savedInstanceState.getBoolean(KEY_CANCEL_OUTSIDE);
            mCutDown = savedInstanceState.getLong(KEY_CANCEL_CUTDOWN, -1);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_LAYOUT_RES, mLayoutRes);
        outState.putFloat(KEY_DIM, mDimAmount);
        outState.putBoolean(KEY_CANCEL_OUTSIDE, mIsCancelOutside);
        outState.putLong(KEY_CANCEL_CUTDOWN, mCutDown);
        super.onSaveInstanceState(outState);
    }

    @Override
    public int getLayoutRes() {
        return mLayoutRes;
    }

    @Override
    public View getCustomView() {
        return mCustomView;
    }


    @Override
    public void bindView(View v) {
        if (mViewListener != null) {
            mViewListener.bindView(v, this);
        }
    }

    @Override
    public int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public int getStyleRes() {

        if(mStyle==-1){
            return R.style.CommonDialog;
        }else {
            return  mStyle;
        }
    }

    int  mStyle=-1;

    @Override
    public boolean getCancelOutside() {
        return mIsCancelOutside;
    }

    public CommonDialog setMyCustomStyle(int  style){
        mStyle=style;
       return   this;
    }
    protected CommonDialog setFragmentManager(FragmentManager manager) {
        mFragmentManager = manager;
        return this;
    }

    public CommonDialog setViewListener(ViewListener listener) {
        mViewListener = listener;
        return this;
    }

    public CommonDialog setLayoutRes(@LayoutRes int layoutRes) {
        mLayoutRes = layoutRes;
        return this;
    }


    public CommonDialog setCustomView(View customView){
        mCustomView=customView;
        return this;
    }

    public CommonDialog setCancelOutside(boolean cancel) {
        mIsCancelOutside = cancel;
        return this;
    }

    public CommonDialog setTag(String tag) {
        TAG = tag;
        return this;
    }

    public CommonDialog setDimAmount(float dim) {
        mDimAmount = dim;
        return this;
    }

    public CommonDialog setCutDownTime(long time) {
        mCutDown = time / 1000;
        return this;
    }

    public long getCutDownTime() {
        return mCutDown;
    }

    public CommonDialog show() {
        setTag(TAG);
        show(mFragmentManager);
        if (mCutDown != -1) {
            if (mHandle != null && mHandle.hasMessages(DISSMISS_MSG_CODE))
                mHandle.removeMessages(DISSMISS_MSG_CODE);
            mCutDown = getCutDownTime();
            cutDownTime = new CutDownTime();
            cutDownTime.start();
        }
        return this;
    }


    private ViewListener mViewListener;

    public interface ViewListener {
        void bindView(View v, CommonDialog dialog);
    }

    private class CutDownTime extends Thread {

        @Override
        public void run() {
            super.run();
            while (mCutDown > 0) {
                try {
                    Thread.sleep(1000);
                    mCutDown--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (mCutDown == 0) {
                mHandle.sendEmptyMessage(DISSMISS_MSG_CODE);
            }

        }
    }

    public Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DISSMISS_MSG_CODE:
                    dismiss();
                    break;
            }
        }
    };

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mCutDown = -1;
        if (cutDownTime != null)
            cutDownTime.interrupt();
    }

}
