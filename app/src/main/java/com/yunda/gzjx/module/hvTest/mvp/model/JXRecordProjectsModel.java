package com.yunda.gzjx.module.hvTest.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.Api;
import com.yunda.gzjx.module.hvTest.entry.JXProject;
import com.yunda.gzjx.module.hvTest.entry.ReqJXProjcetsParm;
import com.yunda.gzjx.module.hvTest.mvp.contract.JXRecordProjectsContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/11/2019 11:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class JXRecordProjectsModel extends BaseModel implements JXRecordProjectsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public JXRecordProjectsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<JXProject>>> queryJXProjects(ReqJXProjcetsParm parm) {
           return mRepositoryManager.obtainRetrofitService(Api.class).queryJXProjects(parm.getTrainId(),parm.getRelationIdx(),parm.getPageNumber(),parm.getPageSize(),parm.getKeywords()).subscribeOn(Schedulers.io());
    }
}