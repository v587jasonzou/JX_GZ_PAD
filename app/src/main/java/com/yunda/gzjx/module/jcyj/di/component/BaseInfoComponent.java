package com.yunda.gzjx.module.jcyj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yunda.gzjx.module.jcyj.di.module.BaseInfoModule;
import com.yunda.gzjx.module.jcyj.mvp.contract.BaseInfoContract;
import com.yunda.gzjx.module.jcyj.mvp.ui.activity.BaseInfoActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
@Component(modules = BaseInfoModule.class, dependencies = AppComponent.class)
public interface BaseInfoComponent {
    void inject(BaseInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BaseInfoComponent.Builder view(BaseInfoContract.View view);

        BaseInfoComponent.Builder appComponent(AppComponent appComponent);

        BaseInfoComponent build();
    }
}