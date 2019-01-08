package com.yunda.gzjx.module.message.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yunda.gzjx.module.me.di.module.MeModule;
import com.yunda.gzjx.module.message.di.module.ConversationsModule;
import com.yunda.gzjx.module.message.mvp.contract.ConversationsContract;
import com.yunda.gzjx.module.message.mvp.ui.activity.ConversationsActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/06/2018 23:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = {ConversationsModule.class,MeModule.class}, dependencies = AppComponent.class)
public interface ConversationsComponent {
    void inject(ConversationsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ConversationsComponent.Builder view(ConversationsContract.View view);

        ConversationsComponent.Builder appComponent(AppComponent appComponent);

//        MeModule不能被Dagger2自动实例化，需要提供这个抽象方法（含参数构造方法）
        ConversationsComponent.Builder meModule(MeModule meModule);

        ConversationsComponent build();
    }
}