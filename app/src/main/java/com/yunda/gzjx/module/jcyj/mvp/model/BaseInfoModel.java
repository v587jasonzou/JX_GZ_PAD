package com.yunda.gzjx.module.jcyj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.Api;
import com.yunda.gzjx.module.hvTest.entry.TrainType;
import com.yunda.gzjx.module.jcyj.mvp.contract.BaseInfoContract;

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
public class BaseInfoModel extends BaseModel implements BaseInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public BaseInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<TrainType>>> getTrainBaseInfo(String trainIdx, String workStationIdx) {
        return mRepositoryManager.obtainRetrofitService(Api.class).getTrainBaseInfo(trainIdx, workStationIdx);
    }
}