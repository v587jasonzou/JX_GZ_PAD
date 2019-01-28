package com.yunda.gzjx.module.hvTest.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yunda.gzjx.module.hvTest.entry.FaultTask;
import com.yunda.gzjx.module.hvTest.mvp.contract.FaultTaskListContract;

import java.util.ArrayList;
import java.util.List;

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
public class FaultTaskListPresenter extends BasePresenter<FaultTaskListContract.Model, FaultTaskListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FaultTaskListPresenter(FaultTaskListContract.Model model, FaultTaskListContract.View rootView) {
        super(model, rootView);
    }

    public void getTicketList(String workPlanIdx, String workStationIdx) {
        mModel.getFaultTaskList(workPlanIdx, workStationIdx).compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY)).observeOn(AndroidSchedulers.mainThread()).subscribe(res -> {
            if (res.getSuccess()) {
                mRootView.getTicketListSuccess(res.getData());
            } else {
                mRootView.getTicketListFail(res.getMessage());
            }
        }, throwable -> {
            throwable.printStackTrace();
            mRootView.getTicketListFail(throwable.getMessage());
        });
    }

    public void delTicket(String ticketIdx, int posDel) {
        mModel.delTicket(ticketIdx).compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY)).observeOn(AndroidSchedulers.mainThread()).subscribe(res -> {
            if (res.getSuccess()) {
                mRootView.delTicketSuccess(posDel);
            } else {
                mRootView.delTicketFail(res.getMessage());
            }
        }, throwable -> {
            throwable.printStackTrace();
            mRootView.delTicketFail(throwable.getMessage());
        });
    }

    /**
     * 报活检索 - 本地检索
     *
     * @param keyWords
     * @param faultTasks
     */
    public void searchMaterialInLocal(String keyWords, List<FaultTask> faultTasks) {
        List<FaultTask> list = null;
        for (FaultTask match : faultTasks) {
            if (match.ticketContent != null && match.ticketContent.contains(keyWords)) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(match);
            }
        }
        mRootView.searchTicketSuccess(list);
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
