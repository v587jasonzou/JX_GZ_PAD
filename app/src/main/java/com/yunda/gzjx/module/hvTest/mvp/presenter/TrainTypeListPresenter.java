package com.yunda.gzjx.module.hvTest.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.entry.TrainType;
import com.yunda.gzjx.module.hvTest.mvp.contract.TrainTypeListContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class TrainTypeListPresenter extends BasePresenter<TrainTypeListContract.Model, TrainTypeListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TrainTypeListPresenter(TrainTypeListContract.Model model, TrainTypeListContract.View rootView) {
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
    public void getTrainList(){
        mModel.getTrainList().compose(RxLifecycleUtils.bindUntilEvent(mRootView,ActivityEvent.DESTROY)).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<TrainType>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<TrainType>> baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null&&baseResponsBean.getSuccess()){
                            if(baseResponsBean.getData()!=null&&baseResponsBean.getData().size()>0){
                                mRootView.OnLoadTrainSuccess(baseResponsBean.getData());
                            }else {
                                mRootView.OnLoadFaild("无相关在修机车信息，请重试");
                            }
                        }else {
                            if(baseResponsBean!=null&&baseResponsBean.getMessage()!=null){
                                mRootView.OnLoadFaild("获取在修机车列表失败，请重试"+baseResponsBean.getMessage());
                            }else {
                                mRootView.OnLoadFaild("获取在修机车列表失败，请重试");
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        mRootView.OnLoadFaild("获取在修机车列表失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
