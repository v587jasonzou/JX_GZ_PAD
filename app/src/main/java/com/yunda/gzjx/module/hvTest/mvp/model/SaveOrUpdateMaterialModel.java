package com.yunda.gzjx.module.hvTest.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.Api;
import com.yunda.gzjx.module.hvTest.entry.Material;
import com.yunda.gzjx.module.hvTest.entry.MaterialSpecInfo;
import com.yunda.gzjx.module.hvTest.mvp.contract.SaveOrUpdateMaterialContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class SaveOrUpdateMaterialModel extends BaseModel implements SaveOrUpdateMaterialContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SaveOrUpdateMaterialModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> saveOrUpdateSuccess(Material materialNew) {
        return mRepositoryManager.obtainRetrofitService(Api.class).saveOrUpdateMaterial("");
    }

    @Override
    public Observable<BaseResponse<List<MaterialSpecInfo>>> getMaterialSpecInfo() {
        return mRepositoryManager.obtainRetrofitService(Api.class).getMaterialSpecInfo("");
    }
}