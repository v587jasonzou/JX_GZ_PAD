package com.yunda.gzjx.module.jcyj.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.entry.TrainType;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public interface BaseInfoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void toJXProjectsListAct();

        void toJZGZAct();

        void toPerformanceTestAct();

        void getTrainBaseInfoSuccess(TrainType data);

        void getTrainBaseInfoFail(String msg);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<TrainType>>> getTrainBaseInfo(String trainIdx, String workStationIdx);
    }
}
