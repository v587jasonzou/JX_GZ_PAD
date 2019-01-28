package com.yunda.gzjx.module.hvTest.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yunda.gzjx.R;
import com.yunda.gzjx.app.EventBusTags;
import com.yunda.gzjx.app.SysInfo;
import com.yunda.gzjx.app.utils.ProgressDialogUtils;
import com.yunda.gzjx.module.home.mvp.ui.activity.HomeActivity;
import com.yunda.gzjx.module.hvTest.di.component.DaggerSaveOrUpdateTicketComponent;
import com.yunda.gzjx.module.hvTest.entry.FaultTask;
import com.yunda.gzjx.module.hvTest.mvp.contract.SaveOrUpdateTicketContract;
import com.yunda.gzjx.module.hvTest.mvp.presenter.SaveOrUpdateTicketPresenter;

import org.simple.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public class SaveOrUpdateTicketActivity extends BaseActivity<SaveOrUpdateTicketPresenter> implements SaveOrUpdateTicketContract.View {

    @BindView(R.id.menu)
    Toolbar menu;
    @BindView(R.id.tv_manufacturer)
    TextView tvManufacturer;
    @BindView(R.id.et_ticket_location)
    EditText etTicketLocation;
    @BindView(R.id.et_work_station_name)
    EditText etWorkStationName;
    @BindView(R.id.et_create_ticket_man)
    TextView tvCreateTicketMan;
    @BindView(R.id.et_ticket_content)
    EditText etTicketContent;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.tv_title_work_leader)
    TextView tvTitleWorkLeader;
    @BindView(R.id.tv_title_quality_check)
    TextView tvTitleQualityCheck;
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
    @BindView(R.id.btn_pre_ticket)
    Button btnPreTicket;
    @BindView(R.id.btn_save_or_update)
    Button btnSaveOrUpdate;
    @BindView(R.id.btn_next_ticket)
    Button btnNextTicket;
    @BindView(R.id.ll_opt)
    LinearLayout llOpt;
    @BindView(R.id.tv_work_man_status)
    TextView tvWorkManStatus;
    @BindView(R.id.tv_cell_check_and_accept)
    TextView tvCellCheckAndAccept;
    @BindView(R.id.rb_check_and_accept_ok)
    RadioButton rbCheckAndAcceptOk;
    @BindView(R.id.rb_check_and_accept_no)
    RadioButton rbCheckAndAcceptNo;
    @BindView(R.id.rg_check_and_accept)
    RadioGroup rgCheckAndAccept;
    @BindView(R.id.tv_title_check_and_accept)
    TextView tvTitleCheckAndAccept;
    @BindView(R.id.et_work_type)
    EditText etWorkType;
    @BindView(R.id.ll_work_type)
    LinearLayout llWorkType;
    @BindView(R.id.ll_work_man_status)
    LinearLayout llWorkManStatus;
    private FaultTask curTicket;//更新时，与列表中的对象是同一个
    private String trainIdx;//机车idx
    private String relationIdx;//工位idx
    private Subscription subcription;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSaveOrUpdateTicketComponent //如找不到该类,请编译一下项目
                .builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_save_or_update_ticket; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(menu);
        menu.setNavigationOnClickListener(v -> {
            finish();
        });
        trainIdx = getIntent().getStringExtra("trainIdx");
        relationIdx = getIntent().getStringExtra("relationIdx");

        refTicket(FaultTaskListActivity.getCurToUpdateTicket());
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "数据加载中，请稍后");
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
    public void saveOrUpdateSuccess(List<FaultTask> data, String msg) {
        hideLoading();
        ToastUtils.showShort(msg);
        EventBus.getDefault().post(curTicket, EventBusTags.NEED_TO_REFRESH_TICKET_LIST);//通知更新
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
        hideLoading();
        ToastUtils.showShort(msg);
    }

    @Override
    public void nextTicket() {
        List<FaultTask> ticketList = FaultTaskListActivity.getSourceData();
        int pos = FaultTaskListActivity.getSourceData().indexOf(curTicket);
        if (pos + 1 < ticketList.size() && pos + 1 >= 0) {
            curTicket = ticketList.get(pos + 1);
        } else {
            ToastUtils.showShort("没有了");
        }
        showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading();
                refTicket(curTicket);
            }
        }, 300);
    }

    @Override
    public void preTicket() {
        List<FaultTask> ticketList = FaultTaskListActivity.getSourceData();
        int pos = FaultTaskListActivity.getSourceData().indexOf(curTicket);
        if (pos - 1 < ticketList.size() && pos - 1 >= 0) {
            curTicket = ticketList.get(pos - 1);
        } else {
            ToastUtils.showShort("没有了");
        }
        showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading();
                refTicket(curTicket);
            }
        }, 300);
    }

    @Override
    public void refTicket(FaultTask ticket) {
        if (subcription != null && subcription.isUnsubscribed()) {
            subcription.unsubscribe();
            subcription = null;
        }

        if (ticket != null) {//修改
            curTicket = ticket;
            menu.setTitle("报活处理");
            btnPreTicket.setVisibility(View.VISIBLE);
            btnNextTicket.setVisibility(View.VISIBLE);

            /*工长*/
            FaultTask.Quality quality = getQualityWithType(curTicket, "工长", false);
            if (quality != null && !quality.qualityResult.isEmpty()) {//未设置
                tvTitleWorkLeader.setText("工长(点击修改)");
                tvTitleWorkLeader.setTag(true);
                rgWorkLeaderCheck.setVisibility(View.GONE);
                tvCellWorkLeader.setVisibility(View.VISIBLE);
                FaultTask.Quality workLeaderCheck = getQualityWithType(curTicket, "工长", false);
                tvCellWorkLeader.setText(workLeaderCheck != null ? workLeaderCheck.qualityEmpName + " " + workLeaderCheck.qualityUpdateTime + " " + workLeaderCheck.qualityResult : "");
            } else {
                tvTitleWorkLeader.setText("工长");
                tvTitleWorkLeader.setTag(null);//禁用点击
                rgWorkLeaderCheck.setVisibility(View.VISIBLE);
                tvCellWorkLeader.setVisibility(View.GONE);
                rgWorkLeaderCheck.clearCheck();
            }

            /*质检*/
            quality = getQualityWithType(curTicket, "质检", false);
            if (quality != null && !quality.qualityResult.isEmpty()) {//未设置
                tvTitleQualityCheck.setText("质检(点击修改)");
                tvTitleQualityCheck.setTag(true);
                rgQualityCheck.setVisibility(View.GONE);
                tvCellQualityCheck.setVisibility(View.VISIBLE);
                FaultTask.Quality qualityCheck = getQualityWithType(curTicket, "质检", false);
                tvCellQualityCheck.setText(qualityCheck != null ? qualityCheck.qualityEmpName + " " + qualityCheck.qualityUpdateTime + " " + qualityCheck.qualityResult : "");
            } else {
                tvTitleQualityCheck.setText("质检");
                tvTitleQualityCheck.setTag(null);//禁用点击
                rgQualityCheck.setVisibility(View.VISIBLE);
                tvCellQualityCheck.setVisibility(View.GONE);
                rgQualityCheck.clearCheck();
            }

            /*验收*/
            quality = getQualityWithType(curTicket, "验收", false);
            if (quality != null && !quality.qualityResult.isEmpty()) {//未设置
                tvTitleCheckAndAccept.setText("验收(点击修改)");
                tvTitleCheckAndAccept.setTag(true);
                rgCheckAndAccept.setVisibility(View.GONE);
                tvCellCheckAndAccept.setVisibility(View.VISIBLE);
                FaultTask.Quality checkAndAcceptCheck = getQualityWithType(curTicket, "验收", false);
                tvCellCheckAndAccept.setText(checkAndAcceptCheck != null ? checkAndAcceptCheck.qualityEmpName + " " + checkAndAcceptCheck.qualityUpdateTime + " " + checkAndAcceptCheck.qualityResult : "");
            } else {
                tvTitleCheckAndAccept.setText("验收");
                tvTitleCheckAndAccept.setTag(null);//禁用点击
                rgCheckAndAccept.setVisibility(View.VISIBLE);
                tvCellCheckAndAccept.setVisibility(View.GONE);
                rgCheckAndAccept.clearCheck();
            }

            //   作业方式，作业者
            if (!TextUtils.isEmpty(curTicket.handleWorkStationName) && curTicket.handleWorkStationName.equals(HomeActivity.getCurWorkStationName())) {//只有对应的工位才能显示这个操作项
                llWorkType.setVisibility(View.VISIBLE);
                llWorkManStatus.setVisibility(View.VISIBLE);

                etWorkType.setText(TextUtils.isEmpty(curTicket.repairMode) ? "" : curTicket.repairMode);
                if (!TextUtils.isEmpty(curTicket.handleTime) && !TextUtils.isEmpty(curTicket.handleEmpName)) {
                    tvWorkManStatus.setText(curTicket.handleEmpName + " " + curTicket.handleTime);//XX 201X-XX-XX
                } else {
                    tvWorkManStatus.setText("");
                }

                subcription = RxTextView.textChanges(etWorkType).skip(1).subscribe(str -> {//忽略回显
                    if (str.length() == 0) {//回显
                        if (curTicket.handleTime != null && curTicket.handleEmpName != null) {
                            tvWorkManStatus.setText(curTicket.handleEmpName + " " + curTicket.handleTime);//XX 201X-XX-XX
                        }
                    } else {
                        tvWorkManStatus.setText(SysInfo.emp.getEmpname() + " " + TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd")));
                    }
                });
            } else {
                llWorkType.setVisibility(View.GONE);
                llWorkManStatus.setVisibility(View.GONE);
            }
        } else {//新增
            curTicket = new FaultTask(SysInfo.emp.getUserid(), SysInfo.emp.getEmpname());
            curTicket.workPlanIdx = trainIdx;
            curTicket.ticketWorkStationIdx = relationIdx;
            curTicket.ticketWorkStationName = HomeActivity.getCurWorkStationName();
            curTicket.ticketLocation = curTicket.ticketWorkStationName;

            menu.setTitle("新增报活");
            btnPreTicket.setVisibility(View.GONE);
            btnNextTicket.setVisibility(View.GONE);

            tvTitleWorkLeader.setText("工长");
            tvTitleWorkLeader.setTag(null);//禁用点击
            rgWorkLeaderCheck.setVisibility(View.VISIBLE);
            tvCellWorkLeader.setVisibility(View.GONE);
            rgWorkLeaderCheck.clearCheck();

            tvTitleQualityCheck.setText("质检");
            tvTitleQualityCheck.setTag(null);//禁用点击
            rgQualityCheck.setVisibility(View.VISIBLE);
            tvCellQualityCheck.setVisibility(View.GONE);
            rgQualityCheck.clearCheck();

            tvTitleCheckAndAccept.setText("验收");
            tvTitleCheckAndAccept.setTag(null);//禁用点击
            rgCheckAndAccept.setVisibility(View.VISIBLE);
            tvCellCheckAndAccept.setVisibility(View.GONE);
            rgCheckAndAccept.clearCheck();

            //  作业方式，作业者
            llWorkType.setVisibility(View.GONE);
            llWorkManStatus.setVisibility(View.GONE);
        }
        tvCreateTicketMan.setText(curTicket.createrName);//提报人
        etTicketLocation.setText(curTicket.ticketLocation);
        etWorkStationName.setText(curTicket.workStationName);
        etTicketContent.setText(curTicket.ticketContent);
        tvCreateTicketMan.setText(curTicket.createrName);
        etComment.setText(curTicket.remarks);

    }

    @OnClick({R.id.btn_pre_ticket, R.id.btn_save_or_update, R.id.btn_next_ticket, R.id.tv_title_work_leader, R.id.tv_title_quality_check, R.id.tv_title_check_and_accept})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pre_ticket:
                preTicket();
                break;
            case R.id.btn_save_or_update:
                showLoading();
                mPresenter.saveOrUpdateTicket(getUpdatedTicketInfo());
                break;
            case R.id.btn_next_ticket:
                nextTicket();
                break;
            case R.id.tv_title_work_leader://工长
                if (view.getTag() != null) {
                    FaultTask.Quality workLeaderCheck = getQualityWithType(curTicket, "工长", true);
                    /*使 工长、质检检查可以修改 (根据tag判断，添加物料时点击无效)*/
                    if ((boolean) view.getTag()) {/*true:当前为显示，false:当前为修改*/
                        view.setTag(false);
                        tvCellWorkLeader.setVisibility(View.GONE);
                        rgWorkLeaderCheck.setVisibility(View.VISIBLE);

                        rgWorkLeaderCheck.check(workLeaderCheck.qualityResult == null && workLeaderCheck.qualityResult.equals("合格") ? R.id.rb_work_leader_check_ok : R.id.rb_work_leader_check_no);
                    } else {//to显示
                        view.setTag(true);//false：当前为显示状态
                        tvCellWorkLeader.setVisibility(View.VISIBLE);
                        rgWorkLeaderCheck.setVisibility(View.GONE);

                        tvCellWorkLeader.setText(workLeaderCheck != null ? workLeaderCheck.qualityEmpName + " " + workLeaderCheck.qualityUpdateTime + " " + workLeaderCheck.qualityResult : "");
                    }
                }
                break;
            case R.id.tv_title_quality_check://质检
                if (view.getTag() != null) {
                    FaultTask.Quality qualityCheck = getQualityWithType(curTicket, "质检", false);
                    /*使 工长、质检检查可以修改 (根据tag判断，添加物料时点击无效)*/
                    if ((boolean) view.getTag()) {/*true:当前为显示，false:当前为修改*/
                        view.setTag(false);
                        tvCellQualityCheck.setVisibility(View.GONE);
                        rgQualityCheck.setVisibility(View.VISIBLE);

                        rgQualityCheck.check(qualityCheck.qualityResult == null || qualityCheck.qualityResult.equals("合格") ? R.id.rb_quality_check_ok : R.id.rb_quality_check_no);
                    } else {//to显示
                        view.setTag(true);
                        tvCellQualityCheck.setVisibility(View.VISIBLE);
                        rgQualityCheck.setVisibility(View.GONE);

                        tvCellQualityCheck.setText(qualityCheck != null ? qualityCheck.qualityEmpName + " " + qualityCheck.qualityUpdateTime + " " + qualityCheck.qualityResult : "");
                    }
                }
                break;
            case R.id.tv_title_check_and_accept://验收
                if (view.getTag() != null) {
                    FaultTask.Quality checkAndAcceptQuality = getQualityWithType(curTicket, "验收", false);
                    if ((boolean) view.getTag()) {/*true:当前为显示，false:当前为修改*/
                        view.setTag(false);
                        tvCellCheckAndAccept.setVisibility(View.GONE);
                        rgCheckAndAccept.setVisibility(View.VISIBLE);

                        rgCheckAndAccept.check(checkAndAcceptQuality.qualityResult == null || checkAndAcceptQuality.qualityResult.equals("合格") ? R.id.rb_check_and_accept_ok : R.id.rb_check_and_accept_no);
                    } else {//to显示
                        view.setTag(true);
                        tvCellCheckAndAccept.setVisibility(View.VISIBLE);
                        rgCheckAndAccept.setVisibility(View.GONE);

                        tvCellCheckAndAccept.setText(checkAndAcceptQuality != null ? checkAndAcceptQuality.qualityEmpName + " " + checkAndAcceptQuality.qualityUpdateTime + " " + checkAndAcceptQuality.qualityResult : "");
                    }
                }
                break;
        }
    }

    /**
     * 获取上传参数
     *
     * @return
     */
    private FaultTask getUpdatedTicketInfo() {
        if (!TextUtils.isEmpty(curTicket.ticketIdx)) {//修改状态信息
            if (etWorkType.getText().toString().length() != 0 && !etWorkType.getText().toString().equals(curTicket.repairMode)) {
                curTicket.repairMode = etWorkType.getText().toString();
                curTicket.handleEmpId = SysInfo.emp.getUserid();
                curTicket.handleEmpName = SysInfo.emp.getEmpname();
                curTicket.handleTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
                curTicket.status = "3";//标记修改完成
            } else if (etWorkType.getText().toString().length() == 0) {
                curTicket.handleEmpId = "";
                curTicket.handleEmpName = "";
                curTicket.repairMode = "";
                curTicket.handleTime = "";
                curTicket.status = "";//标记修改完成
            }
        }

        String local;
        //报活地点
        local = etTicketLocation.getText().toString();
        curTicket.ticketLocation = StringUtils.isSpace(local) ? "" : local;
        //责任单位
        local = etWorkStationName.getText().toString();
        curTicket.workStationName = StringUtils.isSpace(local) ? "" : local;
        //报活内容
        local = etTicketContent.getText().toString();
        curTicket.ticketContent = StringUtils.isSpace(local) ? "" : local;
        //提报人
        local = tvCreateTicketMan.getText().toString();
        curTicket.createrName = StringUtils.isSpace(local) ? "" : local;
        //        备注
        local = etComment.getText().toString();
        curTicket.remarks = StringUtils.isSpace(local) ? "" : local;


        //工长
        if (rgWorkLeaderCheck.getCheckedRadioButtonId() != -1) {
            FaultTask.Quality workLeaderQuality = getQualityWithType(curTicket, "工长", true);
            switch (rgWorkLeaderCheck.getCheckedRadioButtonId()) {
                case R.id.rb_work_leader_check_ok:
                    workLeaderQuality.qualityResult = "合格";
                    break;
                case R.id.rb_work_leader_check_no:
                    workLeaderQuality.qualityResult = "不合格";
                    break;
            }
            workLeaderQuality.updator = SysInfo.emp.getEmpcode();
            workLeaderQuality.updatorName = SysInfo.emp.getEmpname();
            workLeaderQuality.qualityUpdateTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
        }

        //质检
        if (rgQualityCheck.getCheckedRadioButtonId() != -1) {
            FaultTask.Quality qualityCheckQuality = getQualityWithType(curTicket, "质检", true);
            switch (rgQualityCheck.getCheckedRadioButtonId()) {
                case R.id.rb_quality_check_ok:
                    qualityCheckQuality.qualityResult = "合格";
                    break;
                case R.id.rb_quality_check_no:
                    qualityCheckQuality.qualityResult = "不合格";
                    break;
            }
            qualityCheckQuality.updator = SysInfo.emp.getEmpcode();
            qualityCheckQuality.updatorName = SysInfo.emp.getEmpname();
            qualityCheckQuality.qualityUpdateTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
        }


        //验收
        if (rgCheckAndAccept.getCheckedRadioButtonId() != -1) {
            FaultTask.Quality checkAndAcceptQuality = getQualityWithType(curTicket, "验收", true);
            switch (rgCheckAndAccept.getCheckedRadioButtonId()) {
                case R.id.rb_check_and_accept_ok:
                    checkAndAcceptQuality.qualityResult = "合格";
                    break;
                case R.id.rb_check_and_accept_no:
                    checkAndAcceptQuality.qualityResult = "不合格";
                    break;
            }
            checkAndAcceptQuality.updator = SysInfo.emp.getEmpcode();
            checkAndAcceptQuality.updatorName = SysInfo.emp.getEmpname();
            checkAndAcceptQuality.qualityUpdateTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
        }

        /*提报人、更信人...*/
        curTicket.updator = SysInfo.emp.getUserid();
        curTicket.updatorName = SysInfo.emp.getEmpname();
        curTicket.updateTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
        return curTicket;
    }

    private FaultTask.Quality getQualityWithType(FaultTask curTicket, String type, boolean isNeedCreateWhenNull) {
        if (curTicket.qualityList == null) {
            curTicket.qualityList = new ArrayList<>();
        }
        for (FaultTask.Quality quality : curTicket.qualityList) {
            if (quality.qualityType.equals(type)) {
                return quality;
            }
        }
        if (isNeedCreateWhenNull) {
            FaultTask.Quality cre = new FaultTask.Quality();
            cre.createTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
            cre.creater = cre.qualityEmpId = cre.updator = SysInfo.emp.getEmpcode();
            cre.qualityEmpName = cre.createrName = cre.updatorName = SysInfo.emp.getEmpname();
            cre.qualityIdx = "";
            cre.qualityResult = "";
            cre.qualityType = type;
            cre.createTime = cre.qualityUpdateTime = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
            curTicket.qualityList.add(cre);
            return cre;
        }
        return null;
    }
}
