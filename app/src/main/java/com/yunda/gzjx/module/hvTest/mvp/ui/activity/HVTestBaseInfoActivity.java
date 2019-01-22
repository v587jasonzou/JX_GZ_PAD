package com.yunda.gzjx.module.hvTest.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yunda.gzjx.R;
import com.yunda.gzjx.module.home.mvp.ui.activity.HomeActivity;
import com.yunda.gzjx.module.hvTest.di.component.DaggerHVTestBaseInfoComponent;
import com.yunda.gzjx.module.hvTest.entry.TrainType;
import com.yunda.gzjx.module.hvTest.mvp.contract.HVTestBaseInfoContract;
import com.yunda.gzjx.module.hvTest.mvp.presenter.HVTestBaseInfoPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 高压试验 - 选择车型 - 基本信息
 * ================================================
 */
public class HVTestBaseInfoActivity extends BaseActivity<HVTestBaseInfoPresenter> implements HVTestBaseInfoContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.workSeq)
    TextView workSeq;
    @BindView(R.id.trainTypeNo)
    TextView trainTypeNo;
    @BindView(R.id.xc)
    TextView xc;
    @BindView(R.id.dispatchTime)
    TextView dispatchTime;
    @BindView(R.id.startTime)
    TextView startTime;
    @BindView(R.id.endTime)
    TextView endTime;
    @BindView(R.id.conclusion)
    TextView conclusion;
    @BindView(R.id.jxRecords)
    TextView jxRecords;//巡检记录
    @BindView(R.id.materialList)
    TextView materialList;//物料清单
    @BindView(R.id.progressUpload)
    TextView progressUpload;//过程报活

    private TrainType itemData;//详情页基础数据，由上级列表页传入

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHVTestBaseInfoComponent //如找不到该类,请编译一下项目
                .builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_hvtest_base_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        itemData = (TrainType) getIntent().getSerializableExtra("itemData");
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        workSeq.setText("高压试验");
        if (itemData != null) {
            StringBuffer titleStr = new StringBuffer();
            titleStr.append(TextUtils.isEmpty(itemData.getTrainTypeShortname()) ? "" : itemData.getTrainTypeShortname())
                    .append("-")
                    .append(TextUtils.isEmpty(itemData.getRepairClassName()) ? "" : itemData.getRepairClassName())
                    .append("高压试验");
            menuTp.setTitle(titleStr);

            String typeNo = (TextUtils.isEmpty(itemData.getTrainTypeShortname()) ? "" : itemData.getTrainTypeShortname()) + "-" + (TextUtils.isEmpty(itemData.getTrainNo()) ? "" : itemData.getTrainNo());
            trainTypeNo.setText(typeNo);
            xc.setText(TextUtils.isEmpty(itemData.getRepairClassName()) ? "" : itemData.getRepairClassName());
            dispatchTime.setText(TextUtils.isEmpty(itemData.getPlanBeginTime()) ? "" : itemData.getPlanBeginTime());
            startTime.setText(TextUtils.isEmpty(itemData.getBeginTime()) ? "" : itemData.getBeginTime());
            endTime.setText(TextUtils.isEmpty(itemData.getEndTime()) ? "" : itemData.getEndTime());
            conclusion.setText(TextUtils.isEmpty(itemData.getRemarks()) ? "" : itemData.getRemarks());
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    @OnClick(R.id.jxRecords)
    public void onJxRecordsClicked() {
        toJXProjectsListAct();
    }

    @OnClick(R.id.materialList)
    public void onMaterialListClicked() {
        toMaterialListAct();
    }

    @OnClick(R.id.progressUpload)
    public void onProgressUploadClicked() {
        toFaultUploadAct();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hv_test_baseinfo,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.jxRecords:
                toJXProjectsListAct();
                Toast.makeText(this, "检修记录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.materialList:
                toMaterialListAct();
                Toast.makeText(this, "物料清单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.progressUpload:
                toFaultUploadAct();
                Toast.makeText(this, "过程报活", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void toJXProjectsListAct() {
        Intent intent = new Intent(this,JXRecordProjectsActivity.class);
        intent.putExtra("trainIDX", itemData.getIdx());//机车IDX
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void toMaterialListAct() {
        Intent intent = new Intent(this,MaterialListActivity.class);
        intent.putExtra("trainIDX", itemData.getIdx());//机车IDX
        intent.putExtra("relationIdx", HomeActivity.getCurRelationIdx());//菜单项relationIdx
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void toFaultUploadAct() {
        Intent intent = new Intent(this,JXRecordProjectsActivity.class);
        intent.putExtra("trainIDX", itemData.getIdx());//机车IDX
        intent.putExtra("relationIdx", HomeActivity.getCurRelationIdx());//菜单项relationIdx
        ArmsUtils.startActivity(intent);
    }
}
