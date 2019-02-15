package com.yunda.gzjx.module.jcyj.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yunda.gzjx.module.jcyj.di.module.PrecheckRecordsModule;
import com.yunda.gzjx.module.jcyj.mvp.contract.PrecheckRecordsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yunda.gzjx.module.jcyj.mvp.ui.activity.PrecheckRecordsActivity;


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
@ActivityScope
@Component(modules = PrecheckRecordsModule.class, dependencies = AppComponent.class)
public interface PrecheckRecordsComponent {
    void inject(PrecheckRecordsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PrecheckRecordsComponent.Builder view(PrecheckRecordsContract.View view);

        PrecheckRecordsComponent.Builder appComponent(AppComponent appComponent);

        PrecheckRecordsComponent build();
    }
}