package com.yunda.gzjx.module.hvTest.mvp.presenter;

import android.app.Application;

import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yunda.gzjx.app.SysInfo;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.entry.JXTask;
import com.yunda.gzjx.module.hvTest.mvp.contract.JXTasksOfProjectContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 *
 * @author 邹旭
 * ================================================
 */
public class JXTasksOfProjectPresenter extends BasePresenter<JXTasksOfProjectContract.Model, JXTasksOfProjectContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public JXTasksOfProjectPresenter(JXTasksOfProjectContract.Model model, JXTasksOfProjectContract.View rootView) {
        super(model, rootView);
    }

    public void queryJXTasksOfProject(String workCardIdx) {
        mModel.queryJXTasksOfProject(workCardIdx).compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY)).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<List<JXTask>>>() {
            @Override
            public void accept(BaseResponse<List<JXTask>> data) throws Exception {
                if (data != null) {
                    mRootView.getTasksSuccess(data.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mRootView.getTasksFail(throwable.getMessage());
            }
        });
    }

    /**
     * 本地检索
     *
     * @param source
     * @param keyWord
     */
    public void searchJXTasksOfProjectOnLocal(List<JXTask> source, String keyWord) {
        List<JXTask> resSearched = null;
        for (JXTask jxTask : source) {
            if (jxTask.repairContent.contains(keyWord)) {
                if (resSearched == null) {
                    resSearched = new ArrayList<>();
                }
                resSearched.add(jxTask);
            }
        }
        mRootView.searchSuccess(resSearched);
    }

    public void updateTaskInfo(List<JXTask> jxTaskNews) {
        for (JXTask jxTaskNew : jxTaskNews) {
            jxTaskNew.workEmpName = SysInfo.emp.getEmpname();
            jxTaskNew.workEmpId = SysInfo.emp.getEmpid().toString();
        }
        mModel.updateTaskInfo(jxTaskNews).compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY)).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<String>>() {
            @Override
            public void accept(BaseResponse<String> response) throws Exception {
                mRootView.updateTasksSuccess(response.getMessage());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
                mRootView.updateTaskFail(throwable.getMessage());
            }
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
