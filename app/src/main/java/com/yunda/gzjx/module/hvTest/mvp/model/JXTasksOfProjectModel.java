package com.yunda.gzjx.module.hvTest.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.Api;
import com.yunda.gzjx.module.hvTest.entry.JXTask;
import com.yunda.gzjx.module.hvTest.mvp.contract.JXTasksOfProjectContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/14/2019 11:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class JXTasksOfProjectModel extends BaseModel implements JXTasksOfProjectContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public JXTasksOfProjectModel(IRepositoryManager repositoryManager) {
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
        return  mRepositoryManager.obtainRetrofitService(Api.class).updateTaskInfo(jxTaskNews);
    }
}