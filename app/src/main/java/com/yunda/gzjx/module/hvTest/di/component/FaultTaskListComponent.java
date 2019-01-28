package com.yunda.gzjx.module.hvTest.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yunda.gzjx.module.hvTest.di.module.FaultTaskListModule;
import com.yunda.gzjx.module.hvTest.mvp.contract.FaultTaskListContract;
import com.yunda.gzjx.module.hvTest.mvp.ui.activity.FaultTaskListActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/24/2019 09:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FaultTaskListModule.class, dependencies = AppComponent.class)
public interface FaultTaskListComponent {
    void inject(FaultTaskListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FaultTaskListComponent.Builder view(FaultTaskListContract.View view);

        FaultTaskListComponent.Builder appComponent(AppComponent appComponent);

        FaultTaskListComponent build();
    }
}