package com.yunda.gzjx.module.login.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.login.repository.entity.LoginRes;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * ================================================
 */
public interface LoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void toMainActivity();
        void requestNeededPermission();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<LoginRes>> Login(String username, String password);

        Observable<BaseResponse> LoginFirst(String username, String password);
    }
}
