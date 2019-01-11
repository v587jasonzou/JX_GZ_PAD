package com.yunda.gzjx.module.login.repository;


import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.login.repository.entity.LoginRes;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {
    //登录
    @FormUrlEncoded
    @POST("CoreFrameSSOAuthServer/user/login")
    Observable<BaseResponse> LoginFirst(@Field("userId") String userid, @Field("passWord") String userpwd, @Field("clientType") String clientType);

    //登录
//    @FormUrlEncoded
//    @POST("CoreFrame/purview!purviewValidate.action")
//    Observable<LoginReponsBody> Login(@Field("userId") String userid,
//                                      @Field("passWord") String userpwd);
    @FormUrlEncoded
    @POST("GZJX/component/login/terminalLogin.action")
    Observable<BaseResponse<LoginRes>> Login(@Field("userId") String userid, @Field("passWord") String userpwd);

}
