package com.yunda.gzjx.module.hvTest.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yunda.gzjx.module.hvTest.entry.Material;
import com.yunda.gzjx.module.hvTest.mvp.contract.MaterialListContract;

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
public class MaterialListPresenter extends BasePresenter<MaterialListContract.Model, MaterialListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MaterialListPresenter(MaterialListContract.Model model, MaterialListContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取物料清单
     *
     * @param trainIdx
     * @param workStationIdx
     */
    public void getMaterialList(String trainIdx,String workStationIdx) {
        mModel.getMaterialList(trainIdx,workStationIdx).compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY)).observeOn(AndroidSchedulers.mainThread()).subscribe(data -> {
            List<Material> list = data.getData();
            /*if (list == null || list.size() == 0) {
                mRootView.showNoDataPage();
            } else {
                mRootView.showDataPage();
            }*/
            // TODO: 2019/1/21 测试
            if (true){
                String res = "[{\"matName\":\"mock数据1:物料名称\",\"repairResult\":\"合格\",\"matUpdateTime\":\"2018-12-19\",\"remarks\":\"测试1\",\"qty\":\"1\",\"hanleUserId\":\"wangdajun\",\"partsNo\":\"111\",\"modelsSpecifications\":\"测试1\",\"identificationCode\":\"4232424\",\"matCode\":\"111\",\"price\":\"40\",\"source\":\"1\",\"matIdx\":\"de2d4a1bb7224dafb38ea4c7cfc466e9\",\"isSend\":\"1\",\"aboardPlace\":\"\",\"isNeedChange\":\"2\",\"supplier\":\"测试1\",\"partsSource\":\"2\",\"hanleUserName\":\"王大军\",\"qualityListData\":[{\"qualityEmpId\":\"wangdajun\",\"qualityUpdateTime\":\"2018-12-19\",\"qualityEmpName\":\"王大军\",\"qualityIdx\":\"b5a17fecea084515a11b24fb111bf3ef\",\"qualityType\":\"工长\",\"qualityResult\":\"合格\"},{\"qualityEmpId\":\"wangdajun\",\"qualityUpdateTime\":\"2018-12-19\",\"qualityEmpName\":\"王大军\",\"qualityIdx\":\"b5a17fecea084515a11b24fb111bf3ef\",\"qualityType\":\"质检\",\"qualityResult\":\"合格\"}]},{\"matName\":\"mock数据2:物料名称\",\"repairResult\":\"合格\",\"matUpdateTime\":\"2018-12-19\",\"remarks\":\"测试1\",\"qty\":\"1\",\"hanleUserId\":\"wangdajun\",\"partsNo\":\"111\",\"modelsSpecifications\":\"测试1\",\"identificationCode\":\"4232424\",\"matCode\":\"111\",\"price\":\"40\",\"source\":\"1\",\"matIdx\":\"de2d4a1bb7224dafb38ea4c7cfc466e9\",\"isSend\":\"1\",\"aboardPlace\":\"\",\"isNeedChange\":\"1\",\"supplier\":\"测试1\",\"partsSource\":\"2\",\"hanleUserName\":\"王大军\",\"qualityListData\":[{\"qualityEmpId\":\"wangdajun\",\"qualityUpdateTime\":\"2018-12-19\",\"qualityEmpName\":\"王大军\",\"qualityIdx\":\"b5a17fecea084515a11b24fb111bf3ef\",\"qualityType\":\"工长\",\"qualityResult\":\"合格\"},{\"qualityEmpId\":\"wangdajun\",\"qualityUpdateTime\":\"2018-12-19\",\"qualityEmpName\":\"王大军\",\"qualityIdx\":\"b5a17fecea084515a11b24fb111bf3ef\",\"qualityType\":\"质检\",\"qualityResult\":\"合格\"}]}]";
                list = new Gson().fromJson(res, new TypeToken<List<Material>>() {}.getType());
                mRootView.getMaterialListSuccess(list);
                return;
            }

            mRootView.getMaterialListSuccess(list);
        }, throwable -> {
            mRootView.getMaterialListFail(throwable.getMessage());
        });
    }

    /**
     * 物料检索 - 本地检索
     *
     * @param keyWords
     * @param materialsLocal
     */
    public void searchMaterialInLocal(String keyWords,List<Material> materialsLocal){
        List<Material> list = null;
        for (Material match : materialsLocal) {
            if (match.matName.contains(keyWords)) {
                if (list==null) {
                    list = new ArrayList<>();
                }
                list.add(match);
            }
        }
        mRootView.searchMaterialsSuccess(list);
    }

    /**
     * 删除物料
     *
     * @param materialIDX
     * @param posNeedToDel 删除的位置pos
     */
    public void delMaterial(String materialIDX,int posNeedToDel) {
        mModel.delMaterial(materialIDX).compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY)).observeOn(AndroidSchedulers.mainThread()).subscribe(baseResponse -> {
            if (baseResponse.getSuccess()) {
                mRootView.delMaterialSuccess(posNeedToDel);
            }else {
                mRootView.delMaterialFail(baseResponse.getMessage());
            }
        }, throwable -> {
            if (true) {
                mRootView.delMaterialSuccess(posNeedToDel);
                return;
            }
            mRootView.delMaterialFail(throwable.getMessage());
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
