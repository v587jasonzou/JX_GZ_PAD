package com.yunda.gzjx.module.hvTest.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yunda.gzjx.module.hvTest.di.module.JXTasksOfProjectModule;
import com.yunda.gzjx.module.hvTest.mvp.contract.JXTasksOfProjectContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yunda.gzjx.module.hvTest.mvp.ui.activity.JXTasksOfProjectActivity;


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
@ActivityScope
@Component(modules = JXTasksOfProjectModule.class, dependencies = AppComponent.class)
public interface JXTasksOfProjectComponent {
    void inject(JXTasksOfProjectActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        JXTasksOfProjectComponent.Builder view(JXTasksOfProjectContract.View view);

        JXTasksOfProjectComponent.Builder appComponent(AppComponent appComponent);

        JXTasksOfProjectComponent build();
    }
}