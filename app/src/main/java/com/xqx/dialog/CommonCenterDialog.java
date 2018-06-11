package com.xqx.dialog;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;


/**
 * Created by zan on 2017/8/1.
 * dialog
 * 可设置显示位置和样式
 */

public class CommonCenterDialog extends CommonDialog {


    public static CommonCenterDialog create(FragmentManager mFragmentManager) {
        CommonCenterDialog commentDialog = new CommonCenterDialog();
        commentDialog.setFragmentManager(mFragmentManager);
        return commentDialog;
    }

    @Override
    public int getGravity() {
        Log.d("888888", "CommonCenterDialog---getGravity");
        return Gravity.CENTER;
    }

}
