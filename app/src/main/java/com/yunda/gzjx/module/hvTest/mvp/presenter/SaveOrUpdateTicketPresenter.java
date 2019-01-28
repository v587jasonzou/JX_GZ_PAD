package com.yunda.gzjx.module.hvTest.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yunda.gzjx.module.hvTest.entry.FaultTask;
import com.yunda.gzjx.module.hvTest.mvp.contract.SaveOrUpdateTicketContract;

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
public class SaveOrUpdateTicketPresenter extends BasePresenter<SaveOrUpdateTicketContract.Model, SaveOrUpdateTicketContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SaveOrUpdateTicketPresenter(SaveOrUpdateTicketContract.Model model, SaveOrUpdateTicketContract.View rootView) {
        super(model, rootView);
    }

    public void saveOrUpdateTicket(FaultTask ticketNew){
        mModel.saveOrUpdateTicket(ticketNew).compose(RxLifecycleUtils.bindUntilEvent(mRootView,ActivityEvent.DESTROY)).observeOn(AndroidSchedulers.mainThread()).subscribe(res->{
            if (res.getSuccess()) {
                mRootView.saveOrUpdateSuccess(res.getData(),res.getMessage());
            }else {
                mRootView.saveOrUpdateFail(res.getMessage());
            }
        },throwable -> {
            throwable.printStackTrace();
            mRootView.saveOrUpdateFail(throwable.getMessage());
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
