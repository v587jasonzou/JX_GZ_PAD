package com.yunda.gzjx.module.jcyj.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.jcyj.entity.PerformanceTestEntity;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>"机车加装改造" | "机车性能试验"
 * ================================================
 */
public interface TrainPerformanceTestContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void searchSuccess(List<PerformanceTestEntity> results);

        void getListSuccess(List<PerformanceTestEntity> list,String msg);

        void getListFail(String errMsg);

        void updateListItemSuccess(String msg);
        void updateListItemFail(String errMsg);

        boolean getIsJZGZ();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<PerformanceTestEntity>>> getList( String workPlanIdx,  String decisionType);

        Observable<BaseResponse> updateListItem(PerformanceTestEntity entity, boolean isJZGZ);
    }
}
