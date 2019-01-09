package com.yunda.gzjx.module.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.home.mvp.contract.HomeContract;
import com.yunda.gzjx.module.login.repository.entity.MenuSimpleBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getMenus(String loginClient) {
        mModel.getMenus(loginClient).observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Observer<BaseResponse<List<MenuSimpleBean>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<List<MenuSimpleBean>> s) {
                if (s != null) {
                    if (s.getSuccess()) {
                        if (s.getData() != null && s.getData().size() > 0) {
                            mRootView.getMenuSuccess(s.getData());
                        } else {
                            mRootView.OnLoadFaild("当前用户无相关权限，请联系管理员分配权限！");
                        }
                    } else {
                        if (s.getMessage() != null) {
                            mRootView.OnLoadFaild(s.getMessage());
                        } else {
                            mRootView.OnLoadFaild("当前用户无相关权限，请联系管理员分配权限！");
                        }
                    }
                } else {
                    mRootView.OnLoadFaild("获取当前用户信息失败，请检查网络重试");
                }
            }

            @Override
            public void onError(Throwable e) {
                mRootView.OnLoadFaild("获取当前用户信息失败，请检查网络重试" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
