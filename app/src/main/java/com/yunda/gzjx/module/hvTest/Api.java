package com.yunda.gzjx.module.hvTest;

import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.entry.TrainType;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

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
}
