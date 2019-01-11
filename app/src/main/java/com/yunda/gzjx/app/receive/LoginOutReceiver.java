package com.yunda.gzjx.app.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jess.arms.utils.ArmsUtils;
import com.yunda.gzjx.module.login.mvp.ui.activity.LoginActivity;

/**退出登录广播（常驻广播）
 * @auther 周雪巍
 * @Data 2018/11/28
 */
public class LoginOutReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LoginActivity.isNeedReLogin = true;
        Intent intent1 = new Intent(context, LoginActivity.class);
        ArmsUtils.startActivity(intent1);
    }
}
