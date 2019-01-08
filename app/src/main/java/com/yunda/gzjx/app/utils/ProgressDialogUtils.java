package com.yunda.gzjx.app.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  显示与关闭等待提示框<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/8 14:41<br>
 */
public class ProgressDialogUtils {
    private static ProgressDialog mProgressDialog;

    /**
     * 显示ProgressDialog
     * @param context
     * @param message
     */
    public static void showProgressDialog(Context context, CharSequence message) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(context, "", message);
        } else {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    /**
     * 关闭ProgressDialog
     */
    public static void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
