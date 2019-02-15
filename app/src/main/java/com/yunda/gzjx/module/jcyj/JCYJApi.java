package com.yunda.gzjx.module.jcyj;

import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.jcyj.entity.PerformanceTestEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  略<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/2/15 10:03<br>
 */
public interface JCYJApi {
    /**
     * 机车加改项和机车性能试验查询接口
     *
     * @param workPlanIdx
     * @param decisionType
     * @return
     */
    @POST("GZJX/recordDetails/getTechnicalStatusBook.action")
    @FormUrlEncoded
    Observable<BaseResponse<List<PerformanceTestEntity>>> getTechnicalStatusBook(@Field("workPlanIdx") String workPlanIdx, @Field("decisionType") String decisionType);

    /**
     * 机车加改项保存接口
     *
     * @param addAlterItemJSONStr
     * @return
     */
    @POST("GZJX/traindecision/technicalStatusBook/saveAddAlterItem.action")
    @FormUrlEncoded
    Observable<BaseResponse> saveAddAlterItem(@Field("addAlterItemJSONStr") String addAlterItemJSONStr);

    /**
     * 机车性能试验保存接口
     *
     * @param propertyTestJSONStr
     * @return
     */
    @POST("GZJX/traindecision/technicalStatusBook/savePropertyTest.action")
    @FormUrlEncoded
    Observable<BaseResponse> savePropertyTest(@Field("propertyTestJSONStr") String propertyTestJSONStr);
}
