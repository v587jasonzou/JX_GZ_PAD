package com.yunda.gzjx.module.hvTest.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yunda.gzjx.module.hvTest.entry.Material;
import com.yunda.gzjx.module.hvTest.mvp.contract.SaveOrUpdateMaterialContract;

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
public class SaveOrUpdateMaterialPresenter extends BasePresenter<SaveOrUpdateMaterialContract.Model, SaveOrUpdateMaterialContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SaveOrUpdateMaterialPresenter(SaveOrUpdateMaterialContract.Model model, SaveOrUpdateMaterialContract.View rootView) {
        super(model, rootView);
    }

    public void saveOrUpdateMaterial(Material material) {
        mModel.saveOrUpdateSuccess(material).compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY)).observeOn(AndroidSchedulers.mainThread()).subscribe(baseResponse -> {
            if (baseResponse.getSuccess()) {
                mRootView.saveOrUpdateSuccess(baseResponse.getMessage());
            }
        }, throwable -> {
            mRootView.saveOrUpdateFail(throwable.getMessage());
        });
    }

    public void getMaterialSpecInfo() {
        mModel.getMaterialSpecInfo().compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY)).observeOn(AndroidSchedulers.mainThread()).subscribe(listBaseResponse -> {
            if (listBaseResponse.getSuccess()) {
                mRootView.getMaterialSpecInfoSuccess(listBaseResponse.getData());
            }
        }, throwable -> {
            mRootView.getMaterialSpecInfoFail(throwable.getMessage());
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
