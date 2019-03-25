package com.yunda.gzjx.module.hvTest;

import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.entry.FaultTask;
import com.yunda.gzjx.module.hvTest.entry.JXProject;
import com.yunda.gzjx.module.hvTest.entry.JXTask;
import com.yunda.gzjx.module.hvTest.entry.Material;
import com.yunda.gzjx.module.hvTest.entry.MaterialSpecInfo;
import com.yunda.gzjx.module.hvTest.entry.TrainType;
import com.yunda.gzjx.module.hvTest.entry.ZRGWEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
     * 机车基本信息
     *
     * @return
     */
    @POST("GZJX/recordDetails/queryTitleAndBaseData.action")
    @FormUrlEncoded
    Observable<BaseResponse<List<TrainType>>> getTrainBaseInfo(@Field("workPlanIdx") String trainIdx,@Field("workStationIdx") String workStationIdx);

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
    Observable<BaseResponse<List<JXProject>>> queryJXProjects(@Field(value = "workPlanIdx") String trainId, @Field(value = "workStationIdx") String relationIdx, @Field(value = "pageNumber") int pageNumber, @Field(value = "pageSize") int pageSize, @Field(value = "findKey") String keywords);


    /**
     * 获取巡检任务 - （菜单 -> 机车 -> 项目 -> 任务）
     * 别名：检修记录列表
     *
     * @param workCardIdx
     * @return
     */
    @FormUrlEncoded
    @POST("GZJX/trainmainte/workTask/queryTaskDetails.action")
    Observable<BaseResponse<List<JXTask>>> queryJXTasksOfProject(@Field(value = "workCardIdx") String workCardIdx);

    /**
     * 更新巡检任务项
     *
     * @param taskJsonArray
     * @return
     */
    @POST("GZJX/trainmainte/workTask/saveTasks.action")
    @FormUrlEncoded
    Observable<BaseResponse<String>> updateTaskInfo(@Field(value = "taskJsonArray") String taskJsonArray);

    /**
     * 物料清单
     *
     * @param workPlanIdx
     * @return
     */
//    @POST("GZJX/partsRepairRecord/getPartsMatList.action")
    @POST("GZJX/matList/getMatList.action")
    @FormUrlEncoded
    Observable<BaseResponse<List<Material>>>  getMaterialList(@Field("workPlanIdx") String workPlanIdx,@Field("workStationIdx") String workStationIdx);

    /**
     * 删除物料
     *
     * @param materialIDX
     * @return
     */
    @POST("GZJX/matrdplist/matRdpList/deleteMatInfo.action")
    @FormUrlEncoded
    Observable<BaseResponse>  delMaterialWithIDX(@Field("matIdx") String materialIDX);


    /**
     * 添加/更新物料
     *
     * @param materialJSON
     * @return
     */
    @POST("GZJX/matrdplist/matRdpList/saveMatInfo.action")
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加
    Observable<BaseResponse<List<Material>>> saveOrUpdateMaterial(@Field("matJSONStr") String materialJSON);

    /**
     * 获取物料规格信息(供选择)
     *
     * @return
     */
    @GET("GZJX/matsinfo/matTypeList/getMatTypeList.action")
    Observable<BaseResponse<List<MaterialSpecInfo>>> getMaterialSpecInfo();

    /**
     * 过程报活列表
     *
     * @param workPlanIdx  机车idx
     * @param workStationIdx 工位IDX
     * @return
     */
    @POST("GZJX/jxgcTicket/getTicketList.action")
    @FormUrlEncoded
    Observable<BaseResponse<List<FaultTask>>> getTicketList(@Field("workPlanIdx") String workPlanIdx, @Field("workStationIdx") String workStationIdx);

    /**
     * 删除过程报活 - 提票
     *
     * @param ticketIdx
     * @return
     */
    @POST("GZJX/ticket/faultTicket/deleteTicketInfo.action")
    @FormUrlEncoded
    Observable<BaseResponse> delTicket(@Field("ticketIdx") String ticketIdx);

    /**
     * 添加、更新，过程报活
     *
     * @param ticketIdx
     * @return
     */
    @POST("GZJX/ticket/faultTicket/saveTicketInfo.action")
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加
    Observable<BaseResponse<List<FaultTask>>> saveOrUpdateTicket(@Field("ticketJSONStr") String ticketIdx);

    @GET("GZJX/getWorkStationList/queryList.action")
    Observable<BaseResponse<List<ZRGWEntity>>> getZRGW();
}
