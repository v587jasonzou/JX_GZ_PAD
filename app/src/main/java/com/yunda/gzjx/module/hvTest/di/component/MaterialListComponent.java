package com.yunda.gzjx.module.hvTest.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yunda.gzjx.module.hvTest.di.module.MaterialListModule;
import com.yunda.gzjx.module.hvTest.mvp.contract.MaterialListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yunda.gzjx.module.hvTest.mvp.ui.activity.MaterialListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/21/2019 10:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MaterialListModule.class, dependencies = AppComponent.class)
public interface MaterialListComponent {
    void inject(MaterialListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MaterialListComponent.Builder view(MaterialListContract.View view);

        MaterialListComponent.Builder appComponent(AppComponent appComponent);

        MaterialListComponent build();
    }
}