package com.yunda.gzjx.module.hvTest.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yunda.gzjx.module.hvTest.mvp.contract.JXTasksOfProjectContract;
import com.yunda.gzjx.module.hvTest.mvp.model.JXTasksOfProjectModel;


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
@Module
public abstract class JXTasksOfProjectModule {

    @Binds
    abstract JXTasksOfProjectContract.Model bindJXTasksOfProjectModel(JXTasksOfProjectModel model);
}