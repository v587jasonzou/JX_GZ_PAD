package com.yunda.gzjx.module.hvTest.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yunda.gzjx.module.hvTest.entry.ReqJXProjcetsParm;
import com.yunda.gzjx.module.hvTest.mvp.contract.JXRecordProjectsContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * ================================================
 */
@ActivityScope
public class JXRecordProjectsPresenter extends BasePresenter<JXRecordProjectsContract.Model, JXRecordProjectsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public JXRecordProjectsPresenter(JXRecordProjectsContract.Model model, JXRecordProjectsContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 检修项目列表(检索)
     */
    public void queryJXProjects(ReqJXProjcetsParm parm, boolean isRefresh) {
        mModel.queryJXProjects(parm).compose(RxLifecycleUtils.bindUntilEvent(mRootView,ActivityEvent.DESTROY)).observeOn(AndroidSchedulers.mainThread()).subscribe(jxProjects -> {
            if (jxProjects != null) {
                mRootView.getProjectsSuccess(jxProjects.getData(), isRefresh);
            } else {
                mRootView.getProjectsFail("");
            }
        }, throwable -> {
            throwable.printStackTrace();
            mRootView.getProjectsFail(throwable.getMessage());
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
