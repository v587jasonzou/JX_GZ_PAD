package com.yunda.gzjx.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jess.arms.utils.DeviceUtils;
import com.yunda.gzjx.R;


/**
 * 底部弹出窗口工具类
 * Created by hed on 2017/11/14.
 */

public class DialogBuilder {

    public static Dialog build(Context context, View root) {
        Dialog mCameraDialog = new Dialog(context, R.style.customDialog);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        // dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) DeviceUtils.getScreenWidth(root.getContext()); // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        //lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);

        return mCameraDialog;
    }

    public static Dialog buildBottom(Context context, View root) {
        Dialog mCameraDialog = new Dialog(context, R.style.bottomDialog);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) DeviceUtils.getScreenWidth(root.getContext()); // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);

        return mCameraDialog;
    }
}
