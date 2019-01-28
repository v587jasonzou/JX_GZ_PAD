package com.yunda.gzjx.module.hvTest.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yunda.gzjx.module.hvTest.mvp.contract.FaultTaskListContract;
import com.yunda.gzjx.module.hvTest.mvp.model.FaultTaskListModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/24/2019 09:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class FaultTaskListModule {

    @Binds
    abstract FaultTaskListContract.Model bindFaultTaskListModel(FaultTaskListModel model);
}