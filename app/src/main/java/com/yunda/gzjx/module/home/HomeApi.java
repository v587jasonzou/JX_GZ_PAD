package com.yunda.gzjx.module.home;


import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.login.repository.entity.MenuSimpleBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HomeApi {
    //登录
    @FormUrlEncoded
    @POST("GZJX/terminalMenu/queryMenu.action")
    Observable<BaseResponse<List<MenuSimpleBean>>> getMenus(@Field("loginClient") String loginClient);
}
