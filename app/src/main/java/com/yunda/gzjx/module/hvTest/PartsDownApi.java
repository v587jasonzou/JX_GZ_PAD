package com.yunda.gzjx.module.hvTest;

import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.entry.DownPartsBean;
import com.yunda.gzjx.module.hvTest.entry.PartsFactoryBean;
import com.yunda.gzjx.module.hvTest.entry.PartsModelBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PartsDownApi {
//    //在修机车列表
//    @GET("GZJX/trainaccess/trainWorkPlan/findAll2.action")
//    Observable<BaseResponse<List<PartsTrainBean>>> getTrainList();

    //下配件列表
    @FormUrlEncoded
    @POST("GZJX/wellparts/partsUnloadRegister/findPartsUnloadRegisterByIdx.action")
    Observable<BaseResponse<List<DownPartsBean>>> getPartsList(@Field("idx") String idx);
    //规格型号列表
    @GET("GZJX/partstype/partsType/findAll2.action")
    Observable<BaseResponse> getModelList();
//    //规格型号列表
//    @FormUrlEncoded
//    @POST("GZJX/partstype/partsType/findAll2.action")
//    Observable<BaseResponsBean>getMakers(@Field("idx") String idx);
    //规格型号列表
    @FormUrlEncoded
    @POST("GZJX/partstype/partsType/findPartsTypeByPartsTypeList.action")
    Observable<BaseResponse<List<PartsModelBean>>> getMakers(@Field("idx") String idx);

    //查询所有生产厂家
    @GET("GZJX/madefactory/partsMadeFactory/findAll2.action")
    Observable<BaseResponse<List<PartsFactoryBean>>> getAllMakers();
    //生产厂家列表
    @FormUrlEncoded
    @POST("GZJX/madefactory/partsMadeFactory/findMadeFactoryByid.action")
    Observable<BaseResponse<PartsFactoryBean>> getFactory(@Field("idx") String idx);
    //下配件登记
    @FormUrlEncoded
    @POST("GZJX/wellparts/partsUnloadRegister/updatePartsUnloadRegister.action")
    Observable<BaseResponse> Confirm(@Field("updateData") String updateData, @Field("flage") String flage);
}
