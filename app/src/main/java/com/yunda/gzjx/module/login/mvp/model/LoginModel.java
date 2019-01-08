package com.yunda.gzjx.module.login.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.login.mvp.contract.LoginContract;
import com.yunda.gzjx.module.login.repository.LoginApi;
import com.yunda.gzjx.module.login.repository.entity.LoginRes;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


/**
 * ================================================
 * Description:
 * ================================================
 */
@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<LoginRes>> Login(String username, String password) {
        return mRepositoryManager.obtainRetrofitService(LoginApi.class).Login(username,password)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse> LoginFirst(String username, String password) {
        return mRepositoryManager.obtainRetrofitService(LoginApi.class).LoginFirst(username,password,"mobile")
                .subscribeOn(Schedulers.io());
    }
}