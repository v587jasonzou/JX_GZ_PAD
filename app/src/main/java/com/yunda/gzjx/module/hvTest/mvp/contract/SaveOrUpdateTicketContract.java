package com.yunda.gzjx.module.hvTest.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.entry.FaultTask;
import com.yunda.gzjx.module.hvTest.entry.ZRGWEntity;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/24/2019 11:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface SaveOrUpdateTicketContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getZRGWSuccess(List<ZRGWEntity> list, String message);

        void getZRGWFail(String message);

        void saveOrUpdateSuccess(List<FaultTask> data, String msg);
        void saveOrUpdateFail(String msg);

        void nextTicket();
        void preTicket();
        void refTicket(FaultTask ticket);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 保存/更新
         *
         * @param ticketNew
         * @return
         */
        Observable<BaseResponse<List<FaultTask>>> saveOrUpdateTicket(FaultTask ticketNew);

        Observable<BaseResponse<List<ZRGWEntity>>> getZRGW();
    }
}
