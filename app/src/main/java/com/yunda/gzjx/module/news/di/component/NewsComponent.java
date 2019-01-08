package com.yunda.gzjx.module.news.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yunda.gzjx.module.message.di.module.ConversationsModule;
import com.yunda.gzjx.module.message.mvp.contract.ConversationsContract;
import com.yunda.gzjx.module.news.di.module.NewsModule;
import com.yunda.gzjx.module.news.mvp.contract.NewsContract;
import com.yunda.gzjx.module.news.mvp.ui.activity.NewsActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/07/2018 00:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = {NewsModule.class,ConversationsModule.class}, dependencies = AppComponent.class)
public interface NewsComponent {
    void inject(NewsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NewsComponent.Builder view(NewsContract.View view);

        @BindsInstance
        NewsComponent.Builder conversationView(ConversationsContract.View conversationView);

        NewsComponent.Builder appComponent(AppComponent appComponent);

        NewsComponent build();
    }
}