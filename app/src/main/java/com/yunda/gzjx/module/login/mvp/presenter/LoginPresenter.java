package com.yunda.gzjx.module.login.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.CacheDiskUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yunda.gzjx.app.SysInfo;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.login.mvp.contract.LoginContract;
import com.yunda.gzjx.module.login.repository.entity.LoginRes;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * ================================================
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
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

    public void login(String username,String password,boolean isSave){
        mModel.Login(username,password).compose(RxLifecycleUtils.bindUntilEvent(mRootView,ActivityEvent.DESTROY)).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LoginRes>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<LoginRes> responseBody) {
                        mRootView.hideLoading();
                        if(!ObjectUtils.isEmpty(responseBody)){
                            if(responseBody.getSuccess()){
                                if(responseBody.getData()!=null){
                                    SysInfo.emp = responseBody.getData().getEmp();
                                    SysInfo.acOperator = responseBody.getData().getAcOperator();
                                    SysInfo.org = responseBody.getData().getOrg();
                                }

                                ToastUtils.showShort("登录成功！");

                                if(isSave){//磁盘缓存
                                    ArrayList<String> list = (ArrayList<String>)CacheDiskUtils.getInstance().getSerializable("users");
                                    if(list==null){
                                        list = new ArrayList<>();
                                    }
                                    if(!list.contains(username)){
                                        list.add(username);
                                        CacheDiskUtils.getInstance().put("users",list);
                                    }
                                }
                                mRootView.toMainActivity();
                            }else {
                                SysInfo.cookieStore.clear();
                                if(!StringUtils.isTrimEmpty(responseBody.getMessage())){
                                    ToastUtils.showShort(responseBody.getMessage());
                                }else {
                                    ToastUtils.showShort("登录失败请重试！");
                                }
                            }
                        }else {
                            SysInfo.cookieStore.clear();
                            ToastUtils.showShort("连接服务器失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        SysInfo.cookieStore.clear();
                        mRootView.hideLoading();

                        // TODO: 2019/1/16 debug
                        if (true) {
                            mRootView.toMainActivity();
                        }

                        ToastUtils.showShort("登录失败请重试！"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
