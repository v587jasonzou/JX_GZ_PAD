package com.yunda.gzjx.module.hvTest.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yunda.gzjx.module.hvTest.di.module.JXRecordProjectsModule;
import com.yunda.gzjx.module.hvTest.mvp.contract.JXRecordProjectsContract;
import com.yunda.gzjx.module.hvTest.mvp.ui.activity.JXRecordProjectsActivity;

import dagger.BindsInstance;
import dagger.Component;


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
@ActivityScope
@Component(modules = JXRecordProjectsModule.class, dependencies = AppComponent.class)
public interface JXRecordProjectsComponent {
    void inject(JXRecordProjectsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        JXRecordProjectsComponent.Builder view(JXRecordProjectsContract.View view);

        JXRecordProjectsComponent.Builder appComponent(AppComponent appComponent);

        JXRecordProjectsComponent build();
    }
}