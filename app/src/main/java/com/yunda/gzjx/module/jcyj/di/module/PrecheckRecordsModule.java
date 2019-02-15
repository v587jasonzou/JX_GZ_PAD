package com.yunda.gzjx.module.jcyj.di.module;

import com.yunda.gzjx.module.jcyj.mvp.contract.PrecheckRecordsContract;
import com.yunda.gzjx.module.jcyj.mvp.model.PrecheckRecordsModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/14/2019 15:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PrecheckRecordsModule {

    @Binds
    abstract PrecheckRecordsContract.Model bindPrecheckRecordsModel(PrecheckRecordsModel model);
}