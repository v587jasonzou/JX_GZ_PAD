package com.yunda.gzjx.module.me.di.module;

import com.jess.arms.integration.IRepositoryManager;
import com.yunda.gzjx.module.me.mvp.contract.MeContract;
import com.yunda.gzjx.module.me.mvp.model.MeModel;

import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/06/2018 15:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class MeModule {
    public MeModule(MeContract.View view) {
        this.view = view;
    }

    private MeContract.View view;

//    @ActivityScope
    @Provides
    MeContract.View provideView() {
        return this.view;
    }

//    @ActivityScope
    @Provides
    MeContract.Model provideModel(IRepositoryManager iRepositoryManager) {
        return new MeModel(iRepositoryManager);
    }


//    @Binds
//    abstract MeContract.Model bindMeModel(MeModel model);
}