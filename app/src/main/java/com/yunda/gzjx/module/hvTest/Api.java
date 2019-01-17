package com.yunda.gzjx.module.hvTest;

import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.entry.JXProject;
import com.yunda.gzjx.module.hvTest.entry.JXTask;
import com.yunda.gzjx.module.hvTest.entry.TrainType;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  略<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/10 10:57<br>
 */
public interface Api {
    /**
     * 选择车型
     *
     * @return
     */
    @GET("GZJX/trainWorkList/queryList.action")
    Observable<BaseResponse<List<TrainType>>> getTrainList();

    /**
     * 查询检修记录 - 作业项目列表
     *
     * @param trainId 机车ID
     * @param relationIdx 工位idx
     * @param pageNumber
     * @param pageSize
     * @param keywords 查询关键字
     * @return
     */
    @FormUrlEncoded
    @POST("GZJX/trainmainte/workCard/queryBaseDetails.action")
    Observable<BaseResponse<List<JXProject>>> queryJXProjects(@Field("workPlanIdx") String trainId, @Field("workStationIdx") String relationIdx, @Field("pageNumber") int pageNumber, @Field("pageSize") int pageSize, @Field("findKey") String keywords);


    /**
     * 获取巡检任务 - （菜单 -> 机车 -> 项目 -> 任务）
     * 别名：检修记录列表
     *
     * @param workCardIdx
     * @return
     */
    @FormUrlEncoded
    @POST("GZJX/trainmainte/workTask/queryTaskDetails.action")
    Observable<BaseResponse<List<JXTask>>> queryJXTasksOfProject(@Field("workCardIdx") String workCardIdx);

    /**
     * 更新巡检任务项
     *
     * @param jxTasksNew
     * @return
     */
    @POST("GZJX/")
    Observable<BaseResponse<String>> updateTaskInfo(@Body List<JXTask> jxTasksNew);
}
