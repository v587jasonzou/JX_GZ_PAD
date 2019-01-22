package com.yunda.gzjx.module.hvTest.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.widget.RxRadioGroup;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yunda.gzjx.R;
import com.yunda.gzjx.app.SysInfo;
import com.yunda.gzjx.app.utils.ProgressDialogUtils;
import com.yunda.gzjx.module.hvTest.di.component.DaggerSaveOrUpdateMaterialComponent;
import com.yunda.gzjx.module.hvTest.entry.Material;
import com.yunda.gzjx.module.hvTest.entry.MaterialSpecInfo;
import com.yunda.gzjx.module.hvTest.mvp.contract.SaveOrUpdateMaterialContract;
import com.yunda.gzjx.module.hvTest.mvp.presenter.SaveOrUpdateMaterialPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p> 新增物料/修改物料页面
 * ================================================
 */
public class SaveOrUpdateMaterialActivity extends BaseActivity<SaveOrUpdateMaterialPresenter> implements SaveOrUpdateMaterialContract.View {

    @BindView(R.id.menu)
    Toolbar menu;
    @BindView(R.id.tv_name_spec)
    TextView tvNameSpec;
    @BindView(R.id.ll_chooese_name_spec)
    LinearLayout llChooeseNameSpec;
    @BindView(R.id.tv_manufacturer)
    TextView tvManufacturer;
    @BindView(R.id.et_manufacturer)
    EditText etManufacturer;
    @BindView(R.id.tv_part_no)
    TextView tvPartNo;
    @BindView(R.id.et_part_no)
    EditText etPartNo;
    @BindView(R.id.tv_goods_no)
    TextView tvGoodsNo;
    @BindView(R.id.et_goods_no)
    EditText etGoodsNo;
    @BindView(R.id.iv_scan_qrcode)
    ImageView ivScanQrcode;
    @BindView(R.id.tv_qrcode)
    TextView tvQrcode;
    @BindView(R.id.et_qrcode)
    EditText etQrcode;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.et_count)
    EditText etCount;
    @BindView(R.id.tv_cost)
    TextView tvCost;
    @BindView(R.id.rb_option_must)
    RadioButton rbOptionMust;
    @BindView(R.id.rb_option_optional)
    RadioButton rbOptionOptional;
    @BindView(R.id.rg_is_option)
    RadioGroup rgIsOption;
    @BindView(R.id.rb_source_attr_test)
    RadioButton rbSourceAttrTest;
    @BindView(R.id.rb_source_attr_out)
    RadioButton rbSourceAttrOut;
    @BindView(R.id.rb_source_new)
    RadioButton rbSourceNew;
    @BindView(R.id.rg_source_attr)
    RadioGroup rgSourceAttr;
    @BindView(R.id.rb_part_attr_high_price)
    RadioButton rbPartAttrHighPrice;
    @BindView(R.id.rb_part_attr_exchange)
    RadioButton rbPartAttrExchange;
    @BindView(R.id.rb_part_normal)
    RadioButton rbPartNormal;
    @BindView(R.id.rg_part_attr)
    RadioGroup rgPartAttr;
    @BindView(R.id.rb_is_delivery_yes)
    RadioButton rbIsDeliveryYes;
    @BindView(R.id.rb_is_delivery_no)
    RadioButton rbIsDeliveryNo;
    @BindView(R.id.rg_is_delivery)
    RadioGroup rgIsDelivery;
    @BindView(R.id.rb_check_ok)
    RadioButton rbCheckOk;
    @BindView(R.id.rb_check_no)
    RadioButton rbCheckNo;
    @BindView(R.id.rg_check)
    RadioGroup rgCheck;
    @BindView(R.id.et_setup_post)
    EditText etSetupPost;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.tv_title_work_leader)
    TextView tvTitleWorkLeader;
    @BindView(R.id.tv_work_man)
    TextView tvWorkMan;
    @BindView(R.id.tv_cell_work_leader)
    TextView tvCellWorkLeader;
    @BindView(R.id.rb_work_leader_check_ok)
    RadioButton rbWorkLeaderCheckOk;
    @BindView(R.id.rb_work_leader_check_no)
    RadioButton rbWorkLeaderCheckNo;
    @BindView(R.id.rg_work_leader_check)
    RadioGroup rgWorkLeaderCheck;
    @BindView(R.id.tv_cell_quality_check)
    TextView tvCellQualityCheck;
    @BindView(R.id.rb_quality_check_ok)
    RadioButton rbQualityCheckOk;
    @BindView(R.id.rb_quality_check_no)
    RadioButton rbQualityCheckNo;
    @BindView(R.id.rg_quality_check)
    RadioGroup rgQualityCheck;
    @BindView(R.id.btn_pre_material)
    Button btnPreMaterial;
    @BindView(R.id.btn_save_or_update)
    Button btnSaveOrUpdate;
    @BindView(R.id.btn_next_material)
    Button btnNextMaterial;
    @BindView(R.id.ll_opt)
    LinearLayout llOpt;
    @BindView(R.id.tv_title_quality_check)
    TextView tvTitleQualityCheck;
    @BindView(R.id.iv_expand)
    ImageView ivExpand;
    private Material curToUpdateMaterial;//更新物料时：非NULL，上级界面传值
    private List<Material> materials;//更新物料时：非NULL，上级界面传值

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSaveOrUpdateMaterialComponent //如找不到该类,请编译一下项目
                .builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_save_or_update_material; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(menu);
        menu.setNavigationOnClickListener(v -> {
            finish();
        });
        bindingObserver();

        getParmOfPrePage();
        boolean isOuhuan;
        if (curToUpdateMaterial != null) {//修改物料
            menu.setTitle("修改物料");
            isOuhuan = curToUpdateMaterial.isNeedChange == null || curToUpdateMaterial.isNeedChange.equals("2");
            doOnStateIsOuhuanOrBihuan(isOuhuan);//是否是偶换
            updateData(isOuhuan);
        } else {//新增物料
            btnPreMaterial.setVisibility(View.GONE);
            btnNextMaterial.setVisibility(View.GONE);
            menu.setTitle("添加物料");
            doOnStateIsOuhuanOrBihuan(true);
        }
    }

    private void updateData(boolean isOuhuan) {
        /**-------
         * 数据回显
         * -------
         */
        if (isOuhuan) {//偶换 ，数据回显到EditText
            etManufacturer.setText(TextUtils.isEmpty(curToUpdateMaterial.supplier) ? "" : curToUpdateMaterial.supplier);
            etPartNo.setText(TextUtils.isEmpty(curToUpdateMaterial.partsNo) ? "" : curToUpdateMaterial.partsNo);
            etGoodsNo.setText(TextUtils.isEmpty(curToUpdateMaterial.matCode) ? "" : curToUpdateMaterial.matCode);
            etQrcode.setText(TextUtils.isEmpty(curToUpdateMaterial.identificationCode) ? "" : curToUpdateMaterial.identificationCode);
            etPrice.setText(TextUtils.isEmpty(curToUpdateMaterial.price) ? "" : curToUpdateMaterial.price);
            etCount.setText(TextUtils.isEmpty(curToUpdateMaterial.qty) ? "" : curToUpdateMaterial.qty);
        } else {//必换，数据回显到TextView
            tvManufacturer.setText(TextUtils.isEmpty(curToUpdateMaterial.supplier) ? "" : curToUpdateMaterial.supplier);
            tvPartNo.setText(TextUtils.isEmpty(curToUpdateMaterial.partsNo) ? "" : curToUpdateMaterial.partsNo);
            tvGoodsNo.setText(TextUtils.isEmpty(curToUpdateMaterial.matCode) ? "" : curToUpdateMaterial.matCode);
            tvQrcode.setText(TextUtils.isEmpty(curToUpdateMaterial.identificationCode) ? "" : curToUpdateMaterial.identificationCode);
            tvPrice.setText(TextUtils.isEmpty(curToUpdateMaterial.price) ? "" : curToUpdateMaterial.price);
            tvCount.setText(TextUtils.isEmpty(curToUpdateMaterial.qty) ? "" : curToUpdateMaterial.qty);
        }

        tvNameSpec.setText(TextUtils.isEmpty(curToUpdateMaterial.matName) ? "" : curToUpdateMaterial.matName);
        try {
            tvCost.setText(String.valueOf(Integer.parseInt(curToUpdateMaterial.price) * Integer.parseInt(curToUpdateMaterial.qty)));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            tvCost.setText("");
        }

        /*必换、偶换*/
        rbOptionOptional.setChecked(isOuhuan);

        /*来源属性*/
        int sourceAttr = Integer.parseInt(curToUpdateMaterial.source);
        switch (sourceAttr) {
            case 1:
            case 2:
            case 3:
                ((RadioButton) rgSourceAttr.getChildAt(sourceAttr - 1)).setChecked(true);
                break;
            default:
                break;
        }

        /*部件属性*/
        int partAttr = Integer.parseInt(curToUpdateMaterial.partsSource);
        switch (partAttr) {
            case 1:
            case 2:
            case 3:
                ((RadioButton) rgPartAttr.getChildAt(partAttr - 1)).setChecked(true);
                break;
            default:
                break;
        }
        /*是否配送*/
        boolean isDelivery = curToUpdateMaterial.isSend != null && curToUpdateMaterial.isSend.equals("1");
        rbIsDeliveryYes.setChecked(isDelivery);
        /*检修情况*/
        boolean isCheckOk = curToUpdateMaterial.repairResult != null && curToUpdateMaterial.repairResult.equals("合格");
        rbCheckOk.setChecked(isCheckOk);
        /*安装位置*/
        etSetupPost.setText(TextUtils.isEmpty(curToUpdateMaterial.aboardPlace) ? "" : curToUpdateMaterial.aboardPlace);
        /*备注*/
        etComment.setText(TextUtils.isEmpty(curToUpdateMaterial.remarks) ? "" : curToUpdateMaterial.remarks);

        tvWorkMan.setText(curToUpdateMaterial.hanleUserName + " " + curToUpdateMaterial.matUpdateTime + " " + curToUpdateMaterial.repairResult);

        /*使 工长、质检检查可以修改 (根据tag判断，添加物料时点击无效)*/
        tvTitleWorkLeader.setTag(true);
        tvTitleWorkLeader.setText("工长(点击修改)");
        tvCellWorkLeader.setVisibility(View.VISIBLE);
        rgWorkLeaderCheck.setVisibility(View.GONE);
        Material.Quality workLeaderCheck = getQualityWithType(curToUpdateMaterial, "工长", false);
        tvCellWorkLeader.setText(workLeaderCheck != null ? workLeaderCheck.qualityEmpName + " " + workLeaderCheck.qualityUpdateTime + " " + workLeaderCheck.qualityResult : "");

        tvTitleQualityCheck.setTag(true);
        tvTitleQualityCheck.setText("质检(点击修改)");
        tvCellQualityCheck.setVisibility(View.VISIBLE);
        rgQualityCheck.setVisibility(View.GONE);
        Material.Quality qualityCheck = getQualityWithType(curToUpdateMaterial, "质检", false);
        tvCellQualityCheck.setText(qualityCheck != null ? qualityCheck.qualityEmpName + " " + qualityCheck.qualityUpdateTime + " " + qualityCheck.qualityResult : "");
    }

    private void bindingObserver() {
        /**----------
         * 绑定事件处理（点击事件:略）
         * ---------
         */
        RxTextView.textChanges(etManufacturer).subscribe(charSequence -> {
            curToUpdateMaterial.supplier = charSequence.toString();
        });
        RxTextView.textChanges(etPartNo).subscribe(charSequence -> {
            curToUpdateMaterial.partsNo = charSequence.toString();
        });
        RxTextView.textChanges(etGoodsNo).subscribe(charSequence -> {
            curToUpdateMaterial.matCode = charSequence.toString();
        });
        RxTextView.textChanges(etQrcode).subscribe(charSequence -> {
            curToUpdateMaterial.identificationCode = charSequence.toString();
        });
        RxTextView.textChanges(etPrice).subscribe(charSequence -> {
            curToUpdateMaterial.price = charSequence.toString();
            try {
                tvCost.setText(String.valueOf(Integer.parseInt(curToUpdateMaterial.price) * Integer.parseInt(curToUpdateMaterial.qty)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
        RxTextView.textChanges(etCount).subscribe(charSequence -> {
            curToUpdateMaterial.qty = charSequence.toString();
            try {
                tvCost.setText(String.valueOf(Integer.parseInt(curToUpdateMaterial.price) * Integer.parseInt(curToUpdateMaterial.qty)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
        RxTextView.textChanges(etSetupPost).subscribe(charSequence -> {
            curToUpdateMaterial.aboardPlace = charSequence.toString();
        });
        RxTextView.textChanges(etComment).subscribe(charSequence -> {
            curToUpdateMaterial.remarks = charSequence.toString();
        });

        RxRadioGroup.checkedChanges(rgIsOption).subscribe(integer -> {
            switch (integer) {
                case R.id.rb_option_must:
                    curToUpdateMaterial.isNeedChange = "1";
                    break;
                case R.id.rb_option_optional:
                    curToUpdateMaterial.isNeedChange = "2";
                    break;
            }
        });

        RxRadioGroup.checkedChanges(rgSourceAttr).subscribe(integer -> {
            switch (integer) {
                case R.id.rb_source_attr_test:
                    curToUpdateMaterial.source = "1";
                    break;
                case R.id.rb_source_attr_out:
                    curToUpdateMaterial.source = "2";
                    break;
                case R.id.rb_source_new:
                    curToUpdateMaterial.source = "3";
                    break;
            }
        });

        RxRadioGroup.checkedChanges(rgPartAttr).subscribe(integer -> {
            switch (integer) {
                case R.id.rb_part_attr_high_price:
                    curToUpdateMaterial.partsSource = "1";
                    break;
                case R.id.rb_part_attr_exchange:
                    curToUpdateMaterial.partsSource = "2";
                    break;
                case R.id.rb_part_normal:
                    curToUpdateMaterial.partsSource = "3";
                    break;
            }
        });

        RxRadioGroup.checkedChanges(rgIsDelivery).subscribe(integer -> {
            switch (integer) {
                case R.id.rb_is_delivery_yes:
                    curToUpdateMaterial.partsSource = "1";
                    break;
                case R.id.rb_is_delivery_no:
                    curToUpdateMaterial.partsSource = "0";
                    break;
            }
        });

        RxRadioGroup.checkedChanges(rgCheck).subscribe(integer -> {
            switch (integer) {
                case R.id.rb_check_ok:
                    curToUpdateMaterial.partsSource = "合格";
                    break;
                case R.id.rb_check_no:
                    curToUpdateMaterial.partsSource = "不合格";
                    break;
            }
        });

        //工长
        RxRadioGroup.checkedChanges(rgWorkLeaderCheck).subscribe(integer -> {
            Material.Quality quality = getQualityWithType(curToUpdateMaterial, "工长", true);
            switch (integer) {
                case R.id.rb_work_leader_check_ok:
                    quality.qualityResult = "合格";
                    break;
                case R.id.rb_work_leader_check_no:
                    quality.qualityResult = "不合格";
                    break;
            }
            quality.updator = SysInfo.emp.getEmpcode();
            quality.updatorName = SysInfo.emp.getEmpname();
            quality.qualityUpdateTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
        });

        //质检
        RxRadioGroup.checkedChanges(rgQualityCheck).subscribe(integer -> {
            Material.Quality quality = getQualityWithType(curToUpdateMaterial, "质检", true);
            switch (integer) {
                case R.id.rb_quality_check_ok:
                    quality.qualityResult = "合格";
                    break;
                case R.id.rb_quality_check_no:
                    quality.qualityResult = "不合格";
                    break;
            }
            quality.updator = SysInfo.emp.getEmpcode();
            quality.updatorName = SysInfo.emp.getEmpname();
            quality.qualityUpdateTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
        });

    }

    private Material.Quality getQualityWithType(Material curToUpdateMaterial, String type, boolean isNeedCreateWhenNull) {
        for (Material.Quality quality : curToUpdateMaterial.qualityList) {
            if (quality.qualityType.equals(type)) {
                return quality;
            }
        }

        if (isNeedCreateWhenNull) {
            Material.Quality cre = new Material.Quality();
            cre.createTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
            cre.creater = cre.qualityEmpId = cre.updator = SysInfo.emp.getEmpcode();
            cre.qualityEmpName = cre.createrName = cre.updatorName = SysInfo.emp.getEmpname();
            cre.qualityIdx = "";
            cre.qualityResult = "合格";
            cre.qualityType = type;
            cre.qualityUpdateTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
            return cre;
        }
        return null;
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "数据加载中，请稍后...");
    }

    @Override
    public void hideLoading() {
        ProgressDialogUtils.dismissProgressDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void saveOrUpdateSuccess(String msg) {

    }

    @Override
    public void saveOrUpdateFail(String msg) {

    }

    @Override
    public void getMaterialSpecInfoSuccess(List<MaterialSpecInfo> specInfo) {

    }

    @Override
    public void getMaterialSpecInfoFail(String msg) {

    }

    @Override
    public void nextMaterial() {

    }

    @Override
    public void getParmOfPrePage() {
        curToUpdateMaterial = MaterialListActivity.curToUpdateMaterial;
        materials = MaterialListActivity.getSourceData();
    }

    @Override
    public void doOnStateIsOuhuanOrBihuan(boolean isOuhuan) {
        if (isOuhuan) {
            llChooeseNameSpec.setBackground(getResources().getDrawable(R.drawable.bg_table_grid));
            ivExpand.setVisibility(View.VISIBLE);
        } else {//必换 - 更新界面
            /*UI不可修改部分*/
            llChooeseNameSpec.setBackgroundColor(Color.TRANSPARENT);
            ivExpand.setVisibility(View.GONE);
        }

        etManufacturer.setVisibility(!isOuhuan ? View.GONE : View.VISIBLE);
        tvManufacturer.setVisibility(!isOuhuan ? View.VISIBLE : View.GONE);

        etPartNo.setVisibility(!isOuhuan ? View.GONE : View.VISIBLE);
        tvPartNo.setVisibility(!isOuhuan ? View.VISIBLE : View.GONE);

        etGoodsNo.setVisibility(!isOuhuan ? View.GONE : View.VISIBLE);
        tvGoodsNo.setVisibility(!isOuhuan ? View.VISIBLE : View.GONE);

        etQrcode.setVisibility(!isOuhuan ? View.GONE : View.VISIBLE);
        tvQrcode.setVisibility(!isOuhuan ? View.VISIBLE : View.GONE);

        etPrice.setVisibility(!isOuhuan ? View.GONE : View.VISIBLE);
        tvPrice.setVisibility(!isOuhuan ? View.VISIBLE : View.GONE);

        etCount.setVisibility(!isOuhuan ? View.GONE : View.VISIBLE);
        tvCount.setVisibility(!isOuhuan ? View.VISIBLE : View.GONE);

        changeTheRadioGroupClickable(rgIsOption, isOuhuan);
        changeTheRadioGroupClickable(rgSourceAttr, isOuhuan);
        changeTheRadioGroupClickable(rgPartAttr, isOuhuan);
    }

    private void changeTheRadioGroupClickable(RadioGroup radioGroup, boolean clickAble) {
        int cnt = radioGroup.getChildCount();
        for (int i = 0; i < cnt; i++) {
            RadioButton btn = (RadioButton) radioGroup.getChildAt(i);
            btn.setClickable(clickAble);
        }

    }

    ;

    @Override
    public void toScanQRCode() {

    }

    @Override
    public void scanQRCodeSuccess(String qrcode) {

    }

    @Override
    public void scanQRCodeFail(String msg) {

    }

    @OnClick({R.id.iv_scan_qrcode, R.id.tv_title_work_leader, R.id.btn_pre_material, R.id.btn_save_or_update, R.id.btn_next_material, R.id.tv_title_quality_check, R.id.ll_chooese_name_spec})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scan_qrcode:
                ToastUtils.showShort("开发中...");
                break;
            case R.id.tv_title_work_leader://工长
                if (view.getTag() != null) {
                    /*使 工长、质检检查可以修改 (根据tag判断，添加物料时点击无效)*/
                    if ((boolean) view.getTag()) {//to修改
                        view.setTag(false);
                        tvCellWorkLeader.setVisibility(View.GONE);
                        rgWorkLeaderCheck.setVisibility(View.VISIBLE);

                        tvCellWorkLeader.setText("");
                    } else {//to显示
                        view.setTag(true);
                        tvCellWorkLeader.setVisibility(View.VISIBLE);
                        rgWorkLeaderCheck.setVisibility(View.GONE);

                        Material.Quality workLeaderCheck = getQualityWithType(curToUpdateMaterial, "工长", false);
                        tvCellWorkLeader.setText(workLeaderCheck != null ? workLeaderCheck.qualityEmpName + " " + workLeaderCheck.qualityUpdateTime + " " + workLeaderCheck.qualityResult : "");
                    }
                }
                break;
            case R.id.tv_title_quality_check://质检
                if (view.getTag() != null) {
                    /*使 工长、质检检查可以修改 (根据tag判断，添加物料时点击无效)*/
                    if ((boolean) view.getTag()) {//to修改
                        view.setTag(false);
                        tvCellQualityCheck.setVisibility(View.GONE);
                        rgQualityCheck.setVisibility(View.VISIBLE);

                        tvCellQualityCheck.setText("");
                    } else {//to显示
                        view.setTag(true);
                        tvCellQualityCheck.setVisibility(View.VISIBLE);
                        rgQualityCheck.setVisibility(View.GONE);

                        Material.Quality qualityCheck = getQualityWithType(curToUpdateMaterial, "质检", false);
                        tvCellQualityCheck.setText(qualityCheck != null ? qualityCheck.qualityEmpName + " " + qualityCheck.qualityUpdateTime + " " + qualityCheck.qualityResult : "");
                    }
                }
                break;
            case R.id.btn_pre_material:
                int p1 = materials.indexOf(curToUpdateMaterial);
                if (p1 - 1 < materials.size() && p1 - 1 >= 0) {
                    curToUpdateMaterial = materials.get(p1 - 1);
                }
                break;
            case R.id.btn_save_or_update:
                break;
            case R.id.btn_next_material:
                int p2 = materials.indexOf(curToUpdateMaterial);
                if (p2 + 1 < materials.size()) {
                    curToUpdateMaterial = materials.get(p2 + 1);
                }
                break;
            case R.id.ll_chooese_name_spec:
                mPresenter.getMaterialSpecInfo();
                break;
        }
    }
}
