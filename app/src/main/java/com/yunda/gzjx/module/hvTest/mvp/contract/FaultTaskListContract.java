package com.yunda.gzjx.module.hvTest.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.entry.FaultTask;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public interface FaultTaskListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getTicketListSuccess(List<FaultTask> tickets);
        void getTicketListFail(String msg);

        void delTicketSuccess(int posDeleted);
        void delTicketFail(String msg);

        void searchTicketSuccess(List<FaultTask> list);

        void toAddFaultTaskPage();

        void toUpdateFaultTaskPage(FaultTask faultTask);
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<FaultTask>>> getFaultTaskList(String workPlanIdx, String workStationIdx);

        Observable<BaseResponse> delTicket(String ticketIdx);
    }
}
