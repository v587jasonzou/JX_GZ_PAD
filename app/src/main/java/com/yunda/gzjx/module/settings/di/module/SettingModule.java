package com.yunda.gzjx.module.settings.di.module;

import com.yunda.gzjx.module.settings.mvp.contract.SettingContract;
import com.yunda.gzjx.module.settings.mvp.model.SettingModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/09/2019 17:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SettingModule {

    @Binds
    abstract SettingContract.Model bindSettingModel(SettingModel model);
}