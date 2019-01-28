package com.yunda.gzjx.module.hvTest.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.yunda.gzjx.entity.BaseResponse;
import com.yunda.gzjx.module.hvTest.entry.Material;
import com.yunda.gzjx.module.hvTest.entry.MaterialSpecInfo;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public interface SaveOrUpdateMaterialContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void saveOrUpdateSuccess(List<Material> data, String msg);
        void saveOrUpdateFail(String msg);

        void getMaterialSpecInfoSuccess(List<MaterialSpecInfo> specInfo);
        void getMaterialSpecInfoFail(String msg);

        void nextMaterial();

        void getParmOfPrePage(boolean isUpdateMaterial);

        /**
         * "必换/偶换"，执行不同的操作限制，偶换(其中新增物料只能是偶换)大部分可编辑，必换小部分可编辑
         *
         * @param isOuhuan 是否是偶换
         */
        void doOnStateIsOuhuanOrBihuan(boolean isOuhuan);

        /**
         * 扫描二维码
         */
        void toScanQRCode();

        void scanQRCodeSuccess(String qrcode);

        void scanQRCodeFail(String msg);


    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 保存/更新
         *
         * @param materialNew
         * @return
         */
        Observable<BaseResponse<List<Material>>> saveOrUpdateSuccess(Material materialNew);

        /**
         * 物料名称/规格型号/生产厂家...
         *
         * @return
         */
        Observable<BaseResponse<List<MaterialSpecInfo>>> getMaterialSpecInfo();
    }
}
