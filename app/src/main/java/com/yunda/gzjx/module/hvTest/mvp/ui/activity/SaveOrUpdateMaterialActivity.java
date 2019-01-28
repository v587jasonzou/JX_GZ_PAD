package com.yunda.gzjx.module.hvTest.mvp.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yunda.gzjx.R;
import com.yunda.gzjx.app.EventBusTags;
import com.yunda.gzjx.app.SysInfo;
import com.yunda.gzjx.app.utils.ProgressDialogUtils;
import com.yunda.gzjx.constant.BusinessConstant;
import com.yunda.gzjx.module.hvTest.di.component.DaggerSaveOrUpdateMaterialComponent;
import com.yunda.gzjx.module.hvTest.entry.Material;
import com.yunda.gzjx.module.hvTest.entry.MaterialSpecInfo;
import com.yunda.gzjx.module.hvTest.mvp.contract.SaveOrUpdateMaterialContract;
import com.yunda.gzjx.module.hvTest.mvp.presenter.SaveOrUpdateMaterialPresenter;

import org.simple.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    //    @BindView(R.id.iv_expand)
    //    ImageView ivExpand;
    @BindView(R.id.sp_chooese_name_spec)
    AppCompatSpinner spChooeseNameSpec;
    private Material curToUpdateMaterial;//更新物料时：非NULL，上级界面传值
    private List<Material> materials;//更新物料时：非NULL，上级界面传值
    private List<MaterialSpecInfo> specInfoList = new ArrayList<>();//下拉列表框数据
    private ArrayList<String> titles = new ArrayList<>();
    private Context mContext;
    ;//下拉列表框对应的title

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
        mContext = this;
        setSupportActionBar(menu);
        menu.setNavigationOnClickListener(v -> {
            finish();
        });

        boolean isOuhuan = true;
        if (MaterialListActivity.getCurToUpdateMaterial() != null) {//修改物料
            menu.setTitle("修改物料");
            getParmOfPrePage(true);
            isOuhuan = curToUpdateMaterial.isNeedChange == null || curToUpdateMaterial.isNeedChange.equals("2");
            doOnStateIsOuhuanOrBihuan(isOuhuan);//是否是偶换
            updateData(isOuhuan);
        } else {//新增物料
            menu.setTitle("添加物料");
            getParmOfPrePage(false);
            btnPreMaterial.setVisibility(View.GONE);
            btnNextMaterial.setVisibility(View.GONE);
            rbOptionOptional.setChecked(true);//偶换
            doOnStateIsOuhuanOrBihuan(true);
        }
        bindingObserver();


        if (isOuhuan) {
            showLoading();
            mPresenter.getMaterialSpecInfo();
        }
    }

    private void getTitleList(List<MaterialSpecInfo> specInfoList) {
        titles.clear();
        for (MaterialSpecInfo materialSpecInfo : specInfoList) {
            titles.add(materialSpecInfo.matName + " " + materialSpecInfo.modelsSpecifications);
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
            etCount.setText(String.valueOf(curToUpdateMaterial.qty));
        } else {//必换，数据回显到TextView
            tvManufacturer.setText(TextUtils.isEmpty(curToUpdateMaterial.supplier) ? "" : curToUpdateMaterial.supplier);
            tvPartNo.setText(TextUtils.isEmpty(curToUpdateMaterial.partsNo) ? "" : curToUpdateMaterial.partsNo);
            tvGoodsNo.setText(TextUtils.isEmpty(curToUpdateMaterial.matCode) ? "" : curToUpdateMaterial.matCode);
            tvQrcode.setText(TextUtils.isEmpty(curToUpdateMaterial.identificationCode) ? "" : curToUpdateMaterial.identificationCode);
            tvPrice.setText(TextUtils.isEmpty(curToUpdateMaterial.price) ? "" : curToUpdateMaterial.price);
            tvCount.setText(String.valueOf(curToUpdateMaterial.qty));
        }

        tvNameSpec.setText(TextUtils.isEmpty(curToUpdateMaterial.matName) ? "" : curToUpdateMaterial.matName);
        try {
            tvCost.setText(String.valueOf(Double.parseDouble(curToUpdateMaterial.price) * curToUpdateMaterial.qty));
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
        rbCheckNo.setChecked(!isCheckOk);
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
                tvCost.setText(String.valueOf(Double.parseDouble(curToUpdateMaterial.price) * curToUpdateMaterial.qty));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                tvCost.setText("");
            }
        });
        RxTextView.textChanges(etCount).subscribe(charSequence -> {
            try {
                curToUpdateMaterial.qty = Integer.parseInt(charSequence.toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                curToUpdateMaterial.qty = 0;
            }
            try {
                tvCost.setText(String.valueOf(Double.parseDouble(curToUpdateMaterial.price) * curToUpdateMaterial.qty));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                tvCost.setText("");
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
                    curToUpdateMaterial.isSend = "1";
                    break;
                case R.id.rb_is_delivery_no:
                    curToUpdateMaterial.isSend = "0";
                    break;
            }
        });

        RxRadioGroup.checkedChanges(rgCheck).subscribe(integer -> {
            switch (integer) {
                case R.id.rb_check_ok:
                    curToUpdateMaterial.repairResult = "合格";
                    break;
                case R.id.rb_check_no:
                    curToUpdateMaterial.repairResult = "不合格";
                    break;
            }
        });

        //工长
        RxRadioGroup.checkedChanges(rgWorkLeaderCheck).skip(1).subscribe(integer -> {
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
        RxRadioGroup.checkedChanges(rgQualityCheck).skip(1).subscribe(integer -> {
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
        if (curToUpdateMaterial.qualityList == null) {
            curToUpdateMaterial.qualityList = new ArrayList<>();
        }
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
            cre.qualityResult = "";
            cre.qualityType = type;
            cre.createTime = cre.qualityUpdateTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
            curToUpdateMaterial.qualityList.add(cre);
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
    public void saveOrUpdateSuccess(List<Material> data, String msg) {
        hideLoading();
        ToastUtils.showShort(msg);
        EventBus.getDefault().post(curToUpdateMaterial, EventBusTags.NEED_TO_REFRESH_MATERIAL_LIST);//通知更新
        finish();

        /*if (data != null && data.size() != 0) {
            curToUpdateMaterial = data.get(0);
            EventBus.getDefault().post(null, EventBusTags.NEED_TO_REFRESH_MATERIAL_LIST);//通知更新
            if (menu.getTitle().toString().contains("添加")) {
                updateData(true);//添加物料只能是偶换
            } else {
                boolean isOuhuan = curToUpdateMaterial.isNeedChange == null || curToUpdateMaterial.isNeedChange.equals("2");
                doOnStateIsOuhuanOrBihuan(isOuhuan);//是否是偶换
                updateData(isOuhuan);
            }
        }*/
    }

    @Override
    public void saveOrUpdateFail(String msg) {
        ToastUtils.showShort(msg);
        hideLoading();
    }

    @Override
    public void getMaterialSpecInfoSuccess(List<MaterialSpecInfo> specInfo) {
        hideLoading();

        specInfoList.clear();
        for (MaterialSpecInfo materialSpecInfo : specInfo) {
            specInfoList.add(materialSpecInfo);
        }

        getTitleList(specInfoList);
        //适配器
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, titles);
        //设置样式
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spChooeseNameSpec.setAdapter(arrAdapter);
        spChooeseNameSpec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean isInit = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isInit && MaterialListActivity.getCurToUpdateMaterial() != null) {
                    isInit = false;
                } else {
                    MaterialSpecInfo spec = specInfoList.get(position);
                    curToUpdateMaterial.matName = spec.matName;
                    curToUpdateMaterial.modelsSpecifications = spec.modelsSpecifications;
                    etManufacturer.setText(spec.supplier);
                    etGoodsNo.setText(spec.matCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void getMaterialSpecInfoFail(String msg) {
        hideLoading();
        ToastUtils.showShort(msg);
    }

    @Override
    public void nextMaterial() {

    }

    @Override
    public void getParmOfPrePage(boolean isUpdateMaterial) {
        if (isUpdateMaterial) {
            curToUpdateMaterial = MaterialListActivity.getCurToUpdateMaterial();
        } else {
            curToUpdateMaterial = new Material(HVTestBaseInfoActivity.getActivityIdx(), HVTestBaseInfoActivity.getIdx(), SysInfo.emp.getUserid(), SysInfo.emp.getEmpname());
        }
        materials = MaterialListActivity.getSourceData();
    }

    @Override
    public void doOnStateIsOuhuanOrBihuan(boolean isOuhuan) {
        if (isOuhuan) {
            llChooeseNameSpec.setBackground(getResources().getDrawable(R.drawable.bg_table_grid));
            //            ivExpand.setVisibility(View.VISIBLE);
            spChooeseNameSpec.setVisibility(View.VISIBLE);
            tvNameSpec.setVisibility(View.GONE);
        } else {//必换 - 更新界面
            /*UI不可修改部分*/
            llChooeseNameSpec.setBackgroundColor(Color.TRANSPARENT);
            spChooeseNameSpec.setVisibility(View.GONE);
            tvNameSpec.setVisibility(View.VISIBLE);
            //            ivExpand.setVisibility(View.GONE);
        }
        /*  !isOuhuan  修改-必换：隐藏输入框，显示文本框*/
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

        changeTheRadioGroupClickable(rgIsOption, false);//偶换，必换，永远不可以修改
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

    @Override
    public void toScanQRCode() {
        RxPermissions rxPermissions = new RxPermissions(this);
        // Must be done during an initialization phase like onCreate
        rxPermissions.requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.VIBRATE).subscribe(permission -> { // will emit 1 Permission object
            if (permission.granted) {
                // All permissions are granted !
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent, BusinessConstant.REQ_SCAN_QRCODE);
            } else if (permission.shouldShowRequestPermissionRationale) {
                // At least one denied permission without ask never again
            } else {
                // At least one denied permission with ask never again
                // Need to go to the settings
            }
        });
    }

    @Override
    public void scanQRCodeSuccess(String qrcode) {
        etQrcode.setText(qrcode);
    }

    @Override
    public void scanQRCodeFail(String msg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == BusinessConstant.REQ_SCAN_QRCODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    scanQRCodeSuccess(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    scanQRCodeFail("解析二维码失败");
                }
            }
        }
    }

    @OnClick({R.id.iv_scan_qrcode, R.id.tv_title_work_leader, R.id.btn_pre_material, R.id.btn_save_or_update, R.id.btn_next_material, R.id.tv_title_quality_check, R.id.ll_chooese_name_spec})
    public void onViewClicked(View view) {
        boolean isOuhuan = true;
        switch (view.getId()) {
            case R.id.iv_scan_qrcode:
                toScanQRCode();
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
                } else {
                    ToastUtils.showShort("没有了");
                }
                showLoading();
                isOuhuan = curToUpdateMaterial.isNeedChange == null || curToUpdateMaterial.isNeedChange.equals("2");
                doOnStateIsOuhuanOrBihuan(isOuhuan);//是否是偶换
                updateData(isOuhuan);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                }, 300);
                break;
            case R.id.btn_save_or_update:
                showLoading();
                mPresenter.saveOrUpdateMaterial(curToUpdateMaterial);
                break;
            case R.id.btn_next_material:
                int p2 = materials.indexOf(curToUpdateMaterial);
                if (p2 + 1 < materials.size()) {
                    curToUpdateMaterial = materials.get(p2 + 1);
                } else {
                    ToastUtils.showShort("没有了");
                }
                showLoading();
                isOuhuan = curToUpdateMaterial.isNeedChange == null || curToUpdateMaterial.isNeedChange.equals("2");
                doOnStateIsOuhuanOrBihuan(isOuhuan);//是否是偶换
                updateData(isOuhuan);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                }, 300);
                break;
            case R.id.ll_chooese_name_spec:
                mPresenter.getMaterialSpecInfo();
                break;
        }
    }
}
