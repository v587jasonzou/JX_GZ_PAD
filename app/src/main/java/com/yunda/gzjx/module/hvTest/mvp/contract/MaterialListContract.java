package com.yunda.gzjx.module.hvTest.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.entry.Material;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public interface MaterialListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getMaterialListSuccess(List<Material> material);
        void getMaterialListFail(String msg);

        void delMaterialSuccess(int posDeleted);
        void delMaterialFail(String msg);

        void searchMaterialsSuccess(List<Material> list);

        void toAddMaterialPage();

        void toUpdateMaterialPage(Material material);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<Material>>> getMaterialList(String trainIdx,String workStationIdx);

        Observable<BaseResponse> delMaterial(String materialIDX);
    }
}
