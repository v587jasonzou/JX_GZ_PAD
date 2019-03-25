package com.yunda.gzjx.module.hvTest.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.Api;
import com.yunda.gzjx.module.hvTest.entry.FaultTask;
import com.yunda.gzjx.module.hvTest.entry.ZRGWEntity;
import com.yunda.gzjx.module.hvTest.mvp.contract.SaveOrUpdateTicketContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/24/2019 11:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SaveOrUpdateTicketModel extends BaseModel implements SaveOrUpdateTicketContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SaveOrUpdateTicketModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<FaultTask>>> saveOrUpdateTicket(FaultTask ticketNew) {
        List<FaultTask> list = new ArrayList<>();
        list.add(ticketNew);
        return mRepositoryManager.obtainRetrofitService(Api.class).saveOrUpdateTicket(new Gson().toJson(list));
    }

    @Override
    public Observable<BaseResponse<List<ZRGWEntity>>> getZRGW() {
        return mRepositoryManager.obtainRetrofitService(Api.class).getZRGW().subscribeOn(Schedulers.io());
    }
}