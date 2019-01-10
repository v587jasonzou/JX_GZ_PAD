package com.yunda.gzjx.module.hvTest.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yunda.gzjx.module.hvTest.di.module.TrainTypeListModule;
import com.yunda.gzjx.module.hvTest.mvp.contract.TrainTypeListContract;
import com.yunda.gzjx.module.hvTest.mvp.ui.activity.TrainTypeListActivity;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = TrainTypeListModule.class, dependencies = AppComponent.class)
public interface TrainTypeListComponent {
    void inject(TrainTypeListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TrainTypeListComponent.Builder view(TrainTypeListContract.View view);

        TrainTypeListComponent.Builder appComponent(AppComponent appComponent);

        TrainTypeListComponent build();
    }
}