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

import com.blankj.utilcode.util.ToastUtils;
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
 * Description: 高压试验 - 选择车型 - 机车基本信息
 * ================================================
 */
public class HVTestBaseInfoActivity extends BaseActivity<HVTestBaseInfoPresenter> implements HVTestBaseInfoContract.View {

    private static String activityIdx;//
    private static String idx;//机车idx
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
    private String curWorkStationName = "";

    public static String getIdx() {
        return idx;
    }

    public static String getActivityIdx() {
        return activityIdx;
    }

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
        idx = getIntent().getStringExtra("idx");
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        curWorkStationName = HomeActivity.getCurWorkStationName();
        menuTp.setTitle(curWorkStationName);
        workSeq.setText(curWorkStationName);

        showLoading();
        mPresenter.getBaseInfo(idx, HomeActivity.getCurRelationIdx());
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
        getMenuInflater().inflate(R.menu.menu_hv_test_baseinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        Intent intent = new Intent(this, JXRecordProjectsActivity.class);
        intent.putExtra("trainIDX", idx);//机车IDX
        intent.putExtra("relationIdx", HomeActivity.getCurRelationIdx());//机车IDX
        intent.putExtra("trainTypeNo", trainTypeNo.getText().toString());
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void toMaterialListAct() {
        Intent intent = new Intent(this, MaterialListActivity.class);
        intent.putExtra("trainIDX", idx);//机车IDX
        intent.putExtra("relationIdx", HomeActivity.getCurRelationIdx());//菜单项relationIdx
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void toFaultUploadAct() {
        Intent intent = new Intent(this, FaultTaskListActivity.class);
        intent.putExtra("trainIDX", idx);//机车IDX
        intent.putExtra("relationIdx", HomeActivity.getCurRelationIdx());//菜单项relationIdx
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void getTrainBaseInfoSuccess(TrainType data) {
        activityIdx = "";
        hideLoading();
        if (data != null) {
            activityIdx = data.activityIdx;

            StringBuffer titleStr = new StringBuffer();
            if (TextUtils.isEmpty(data.trainTypeShortname)||TextUtils.isEmpty(data.repairClassName)) {
                titleStr.append("基本信息");
            }else {
                titleStr.append(TextUtils.isEmpty(data.trainTypeShortname) ? "" : data.trainTypeShortname).append("-").append(TextUtils.isEmpty(data.repairClassName) ? "" : data.repairClassName).append("基本信息");
            }
            menuTp.setTitle(titleStr);

            String typeNo = (TextUtils.isEmpty(data.trainTypeName) ? "" : data.trainTypeName) + "-" + (TextUtils.isEmpty(data.trainNo) ? "" : data.trainNo);
            trainTypeNo.setText(typeNo);
            xc.setText(TextUtils.isEmpty(data.repClassName) ? "" : data.repClassName);
            dispatchTime.setText(TextUtils.isEmpty(data.pStartTime) ? "" : data.pStartTime);
            startTime.setText(TextUtils.isEmpty(data.startTime) ? "" : data.startTime);
            endTime.setText(TextUtils.isEmpty(data.endTime) ? "" : data.endTime);
            conclusion.setText(TextUtils.isEmpty(data.result) ? "" : data.result);
        }
    }

    @Override
    public void getTrainBaseInfoFail(String msg) {
        ToastUtils.showShort(msg);
    }
}
