package com.yunda.gzjx.module.jcyj.di.module;

import com.yunda.gzjx.module.jcyj.mvp.contract.TrainPerformanceTestContract;
import com.yunda.gzjx.module.jcyj.mvp.model.TrainPerformanceTestModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/14/2019 18:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class TrainPerformanceTestModule {

    @Binds
    abstract TrainPerformanceTestContract.Model bindTrainPerformanceTestModel(TrainPerformanceTestModel model);
}