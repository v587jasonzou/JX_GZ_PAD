package com.yunda.gzjx.module.jcyj.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yunda.gzjx.module.jcyj.di.module.TrainPerformanceTestModule;
import com.yunda.gzjx.module.jcyj.mvp.contract.TrainPerformanceTestContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yunda.gzjx.module.jcyj.mvp.ui.activity.TrainPerformanceTestActivity;


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
@ActivityScope
@Component(modules = TrainPerformanceTestModule.class, dependencies = AppComponent.class)
public interface TrainPerformanceTestComponent {
    void inject(TrainPerformanceTestActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TrainPerformanceTestComponent.Builder view(TrainPerformanceTestContract.View view);

        TrainPerformanceTestComponent.Builder appComponent(AppComponent appComponent);

        TrainPerformanceTestComponent build();
    }
}