package com.yunda.gzjx.module.hvTest.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.Api;
import com.yunda.gzjx.module.hvTest.entry.FaultTask;
import com.yunda.gzjx.module.hvTest.mvp.contract.FaultTaskListContract;

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
public class FaultTaskListModel extends BaseModel implements FaultTaskListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FaultTaskListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<FaultTask>>> getFaultTaskList(String workPlanIdx, String workStationIdx) {
        return mRepositoryManager.obtainRetrofitService(Api.class).getTicketList(workPlanIdx,workStationIdx);
    }

    @Override
    public Observable<BaseResponse> delTicket(String ticketIdx) {
        return mRepositoryManager.obtainRetrofitService(Api.class).delTicket(ticketIdx);
    }
}