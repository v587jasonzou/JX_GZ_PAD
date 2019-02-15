package com.yunda.gzjx.module.jcyj.di.module;

import com.yunda.gzjx.module.jcyj.mvp.contract.BaseInfoContract;
import com.yunda.gzjx.module.jcyj.mvp.model.BaseInfoModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@Module
public abstract class BaseInfoModule {

    @Binds
    abstract BaseInfoContract.Model bindBaseInfoModel(BaseInfoModel model);
}