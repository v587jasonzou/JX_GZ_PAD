package com.yunda.gzjx.module.hvTest.di.module;

import com.yunda.gzjx.module.hvTest.mvp.contract.JXRecordProjectsContract;
import com.yunda.gzjx.module.hvTest.mvp.model.JXRecordProjectsModel;

import dagger.Binds;
import dagger.Module;


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
@Module
public abstract class JXRecordProjectsModule {

    @Binds
    abstract JXRecordProjectsContract.Model bindJXRecordProjectsModel(JXRecordProjectsModel model);
}