package com.yunda.gzjx.module.jcyj.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.entry.JXTask;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public interface PrecheckRecordsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getTasksSuccess(List<JXTask> jxTasks);

        void getTasksFail(String message);

        void updateTasksSuccess(String message);

        void updateTaskFail(String errMsg);

        void searchSuccess(List<JXTask> results);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<JXTask>>> queryJXTasksOfProject(String workCardIdx);

        Observable<BaseResponse<String>> updateTaskInfo(List<JXTask> jxTaskNews);
    }
}
