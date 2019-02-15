package com.yunda.gzjx.module.jcyj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.Api;
import com.yunda.gzjx.module.hvTest.entry.JXTask;
import com.yunda.gzjx.module.jcyj.mvp.contract.PrecheckRecordsContract;

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
public class PrecheckRecordsModel extends BaseModel implements PrecheckRecordsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PrecheckRecordsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<JXTask>>> queryJXTasksOfProject(String workCardIdx) {
        return mRepositoryManager.obtainRetrofitService(Api.class).queryJXTasksOfProject(workCardIdx);
    }

    @Override
    public Observable<BaseResponse<String>> updateTaskInfo(List<JXTask> jxTaskNews) {
        return  mRepositoryManager.obtainRetrofitService(Api.class).updateTaskInfo(new Gson().toJson(jxTaskNews));
    }
}