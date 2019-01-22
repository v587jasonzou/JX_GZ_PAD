package com.yunda.gzjx.module.hvTest.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yunda.gzjx.module.hvTest.mvp.contract.SaveOrUpdateMaterialContract;
import com.yunda.gzjx.module.hvTest.mvp.model.SaveOrUpdateMaterialModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/21/2019 16:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SaveOrUpdateMaterialModule {

    @Binds
    abstract SaveOrUpdateMaterialContract.Model bindSaveOrUpdateMaterialModel(SaveOrUpdateMaterialModel model);
}