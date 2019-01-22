package com.yunda.gzjx.module.hvTest.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yunda.gzjx.module.hvTest.di.module.SaveOrUpdateMaterialModule;
import com.yunda.gzjx.module.hvTest.mvp.contract.SaveOrUpdateMaterialContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yunda.gzjx.module.hvTest.mvp.ui.activity.SaveOrUpdateMaterialActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/21/2019 16:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SaveOrUpdateMaterialModule.class, dependencies = AppComponent.class)
public interface SaveOrUpdateMaterialComponent {
    void inject(SaveOrUpdateMaterialActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SaveOrUpdateMaterialComponent.Builder view(SaveOrUpdateMaterialContract.View view);

        SaveOrUpdateMaterialComponent.Builder appComponent(AppComponent appComponent);

        SaveOrUpdateMaterialComponent build();
    }
}