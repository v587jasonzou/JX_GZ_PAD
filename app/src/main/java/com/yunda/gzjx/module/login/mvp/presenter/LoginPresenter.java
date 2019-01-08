package com.yunda.gzjx.module.login.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.utilcode.util.CacheDiskUtils;
import com.jess.arms.utils.utilcode.util.ObjectUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
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
        mModel.Login(username,password).observeOn(AndroidSchedulers.mainThread())
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
                                if(!StringUtils.isTrimEmpty(responseBody.getMessage())){
                                    ToastUtils.showShort(responseBody.getMessage());
                                }else {
                                    ToastUtils.showShort("登录失败请重试！");
                                }
                            }
                        }else {
                            ToastUtils.showShort("连接服务器失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("登录失败请重试！"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
