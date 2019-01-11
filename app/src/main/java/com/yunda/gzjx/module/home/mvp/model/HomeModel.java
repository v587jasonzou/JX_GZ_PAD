package com.yunda.gzjx.module.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.home.HomeApi;
import com.yunda.gzjx.module.home.mvp.contract.HomeContract;
import com.yunda.gzjx.module.login.repository.entity.MenuSimpleBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


@ActivityScope
public class HomeModel extends BaseModel implements HomeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<MenuSimpleBean>>> getMenus(String loginClient) {
        return mRepositoryManager.obtainRetrofitService(HomeApi.class).getMenus(loginClient)
                .subscribeOn(Schedulers.io());
    }
}