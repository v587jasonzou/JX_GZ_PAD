package com.yunda.gzjx.module.hvTest.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yunda.gzjx.module.hvTest.di.module.HVTestBaseInfoModule;
import com.yunda.gzjx.module.hvTest.mvp.contract.HVTestBaseInfoContract;
import com.yunda.gzjx.module.hvTest.mvp.ui.activity.HVTestBaseInfoActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/10/2019 11:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HVTestBaseInfoModule.class, dependencies = AppComponent.class)
public interface HVTestBaseInfoComponent {
    void inject(HVTestBaseInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HVTestBaseInfoComponent.Builder view(HVTestBaseInfoContract.View view);

        HVTestBaseInfoComponent.Builder appComponent(AppComponent appComponent);

        HVTestBaseInfoComponent build();
    }
}