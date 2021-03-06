package com.yunda.gzjx.module.jcyj.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yunda.gzjx.module.jcyj.mvp.contract.BaseInfoContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class BaseInfoPresenter extends BasePresenter<BaseInfoContract.Model, BaseInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public BaseInfoPresenter(BaseInfoContract.Model model, BaseInfoContract.View rootView) {
        super(model, rootView);
    }

    /**
     * @param trainIdx       机车IDX
     * @param workStationIdx 工位idx
     */
    public void getBaseInfo(String trainIdx, String workStationIdx) {
        mModel.getTrainBaseInfo(trainIdx, workStationIdx).compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY)).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
            if (response.getSuccess()) {
                mRootView.getTrainBaseInfoSuccess(response.getData().get(0));
            }else {
                mRootView.getTrainBaseInfoFail("");
            }
        }, throwable -> {
            throwable.printStackTrace();
            mRootView.getTrainBaseInfoFail(throwable.getMessage());
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
