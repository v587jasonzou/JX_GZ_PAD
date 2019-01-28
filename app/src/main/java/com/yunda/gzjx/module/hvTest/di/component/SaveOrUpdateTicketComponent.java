package com.yunda.gzjx.module.hvTest.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yunda.gzjx.module.hvTest.di.module.SaveOrUpdateTicketModule;
import com.yunda.gzjx.module.hvTest.mvp.contract.SaveOrUpdateTicketContract;
import com.yunda.gzjx.module.hvTest.mvp.ui.activity.SaveOrUpdateTicketActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/24/2019 11:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SaveOrUpdateTicketModule.class, dependencies = AppComponent.class)
public interface SaveOrUpdateTicketComponent {
    void inject(SaveOrUpdateTicketActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SaveOrUpdateTicketComponent.Builder view(SaveOrUpdateTicketContract.View view);

        SaveOrUpdateTicketComponent.Builder appComponent(AppComponent appComponent);

        SaveOrUpdateTicketComponent build();
    }
}