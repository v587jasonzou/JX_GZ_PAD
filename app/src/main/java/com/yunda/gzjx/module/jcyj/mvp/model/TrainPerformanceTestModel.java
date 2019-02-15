package com.yunda.gzjx.module.jcyj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.jcyj.JCYJApi;
import com.yunda.gzjx.module.jcyj.entity.PerformanceTestEntity;
import com.yunda.gzjx.module.jcyj.mvp.contract.TrainPerformanceTestContract;

import java.util.ArrayList;
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
public class TrainPerformanceTestModel extends BaseModel implements TrainPerformanceTestContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TrainPerformanceTestModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<PerformanceTestEntity>>> getList(String workPlanIdx, String decisionType) {
        return mRepositoryManager.obtainRetrofitService(JCYJApi.class).getTechnicalStatusBook(workPlanIdx, decisionType);
    }

    @Override
    public Observable<BaseResponse> updateListItem(PerformanceTestEntity entity, boolean isJZGZ) {
        List list = new ArrayList();
        list.add(entity);
        if (isJZGZ) {
            return mRepositoryManager.obtainRetrofitService(JCYJApi.class).saveAddAlterItem(new Gson().toJson(list));
        } else {
            return mRepositoryManager.obtainRetrofitService(JCYJApi.class).savePropertyTest(new Gson().toJson(list));
        }
    }
}