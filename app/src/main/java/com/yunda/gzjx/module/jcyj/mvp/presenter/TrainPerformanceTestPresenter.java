package com.yunda.gzjx.module.jcyj.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.TimeUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.yunda.gzjx.app.SysInfo;
import com.yunda.gzjx.module.jcyj.entity.PerformanceTestEntity;
import com.yunda.gzjx.module.jcyj.mvp.contract.TrainPerformanceTestContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class TrainPerformanceTestPresenter extends BasePresenter<TrainPerformanceTestContract.Model, TrainPerformanceTestContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TrainPerformanceTestPresenter(TrainPerformanceTestContract.Model model, TrainPerformanceTestContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 本地检索
     *
     * @param source
     * @param keyWord
     * @param isChecked
     */
    public void searchJXTasksOfProjectOnLocal(List<PerformanceTestEntity> source, String keyWord, boolean isChecked) {
        List<PerformanceTestEntity> resSearched = null;
        for (PerformanceTestEntity data : source) {
            if (data.item.contains(keyWord)) {
                if (resSearched == null) {
                    resSearched = new ArrayList<>();
                }
                if ("0".equals(data.status) && !isChecked) {//未检
                    resSearched.add(data);
                } else if (!"0".equals(data.status) && isChecked) {//已检
                    resSearched.add(data);
                }
            }
        }
        List<PerformanceTestEntity> finalResSearched = resSearched;
        Observable.just(1).delay(300, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
            mRootView.searchSuccess(finalResSearched);
        });
    }


    public void getList(String workPlanIdx, int decisionType) {
        mModel.getList(workPlanIdx, decisionType + "").observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(resp -> {
            if (resp.getSuccess()) {
                mRootView.getListSuccess(resp.getData(), resp.getMessage());
            } else {
                mRootView.getListFail(resp.getMessage());
            }
        }, throwable -> {
            throwable.printStackTrace();
            mRootView.getListFail(throwable.getMessage());
        });
    }

    public void updateListItem(PerformanceTestEntity entity, boolean isJZGZ) {
        entity.updatorName = entity.workEmpName = SysInfo.emp.getEmpname();
        entity.updator = entity.workEmpId = SysInfo.emp.getEmpid().toString();
        entity.updateTime = entity.workEmpTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));

        mModel.updateListItem(entity, isJZGZ).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(resp -> {
            if (resp.getSuccess()) {
                mRootView.updateListItemSuccess(resp.getMessage());
            } else {
                mRootView.updateListItemFail(resp.getMessage());
            }
        }, throwable -> {
            throwable.printStackTrace();
            mRootView.updateListItemFail(throwable.getMessage());
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

    public List<PerformanceTestEntity> filterList(List<PerformanceTestEntity> datas, boolean isChecked) {
        List<PerformanceTestEntity> list = new ArrayList<>();
        for (PerformanceTestEntity data : datas) {
            if ("0".equals(data.status) && !isChecked) {//未检
                list.add(data);
            } else if (!"0".equals(data.status) && isChecked) {//已检
                list.add(data);
            }
        }
        return list;
    }
}
