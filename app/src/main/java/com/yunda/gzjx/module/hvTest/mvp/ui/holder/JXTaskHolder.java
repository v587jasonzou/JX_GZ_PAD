package com.yunda.gzjx.module.hvTest.mvp.ui.holder;

import android.graphics.Color;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.ArmsUtils;
import com.orhanobut.logger.Logger;
import com.yunda.gzjx.R;
import com.yunda.gzjx.app.SysInfo;
import com.yunda.gzjx.constant.BusinessConstant;
import com.yunda.gzjx.module.hvTest.entry.JXTask;
import com.yunda.gzjx.module.hvTest.mvp.contract.JXTasksOfProjectContract;
import com.yunda.gzjx.module.hvTest.mvp.presenter.JXTasksOfProjectPresenter;
import com.yunda.gzjx.module.hvTest.mvp.ui.adapter.TaskConditionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  略<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/20 14:25<br>
 */
public class JXTaskHolder extends BaseHolder<JXTask> implements TextWatcher {
    private final JXTasksOfProjectContract.View view;
    private final JXTasksOfProjectPresenter presenter;
    @BindView(R.id.tv_task_name)
    TextView tvTaskName;
    @BindView(R.id.rv_task_condition)
    RecyclerView rvTaskCondition;
    @BindView(R.id.tv_handle_man)
    TextView tvHandleMan;
    @BindView(R.id.checkEachotherOk)
    AppCompatRadioButton checkEachotherOk;
    @BindView(R.id.checkEachotherNo)
    AppCompatRadioButton checkEachotherNo;
    @BindView(R.id.checkEachother)
    RadioGroup checkEachother;
    @BindView(R.id.tv_check_eachother)
    TextView tvCheckEachother;
    @BindView(R.id.workerLeaderOk)
    AppCompatRadioButton workerLeaderOk;
    @BindView(R.id.workerLeaderNo)
    AppCompatRadioButton workerLeaderNo;
    @BindView(R.id.workerLeader)
    RadioGroup workerLeader;
    @BindView(R.id.tv_work_leader_check_res)
    TextView tvWorkLeaderCheckRes;
    @BindView(R.id.acceptanceCheckOk)
    AppCompatRadioButton acceptanceCheckOk;
    @BindView(R.id.acceptanceCheckNo)
    AppCompatRadioButton acceptanceCheckNo;
    @BindView(R.id.acceptanceCheck)
    RadioGroup acceptanceCheck;
    @BindView(R.id.tv_acceptance_check_res)
    TextView tvAcceptanceCheckRes;
    @BindView(R.id.qualityCheckOk)
    AppCompatRadioButton qualityCheckOk;
    @BindView(R.id.qualityCheckNo)
    AppCompatRadioButton qualityCheckNo;
    @BindView(R.id.qualityCheck)
    RadioGroup qualityCheck;
    @BindView(R.id.tv_quality_check_res)
    TextView tvQualityCheckRes;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.rb_saveSingle)
    Button rbSaveSingle;
    @BindView(R.id.rb_saveLater)
    CheckBox rbSaveLater;
    @BindView(R.id.rg_save)
    LinearLayout rgSave;
    private JXTask data;
    private int position;

    public JXTaskHolder(JXTasksOfProjectContract.View view, JXTasksOfProjectPresenter presenter, View itemView) {
        super(itemView);
        initListeners();
        this.view = view;
        this.presenter = presenter;
        /*保存*/
        rbSaveSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<JXTask> list = new ArrayList<>();
                list.add(data);

                view.showLoading();
                presenter.updateTaskInfo(list);
            }
        });
        rbSaveLater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data.isSaveLaterChecked = isChecked;
            }
        });
    }

    private void initListeners() {
        RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (data == null) {
                    return;
                }
                if (data.qualityList == null) {
                    data.qualityList = new ArrayList<>();
                }
                String qualityStr = "";
                switch (group.getId()) {
                    case R.id.workerLeader:
                        qualityStr = "工长";
                        break;
                    case R.id.checkEachother:
                        qualityStr = "互检";
                        break;
                    case R.id.acceptanceCheck:
                        qualityStr = "验收";
                        break;
                    case R.id.qualityCheck:
                        qualityStr = "质检";
                        break;
                    default:
                        Logger.d("未知的单选按钮组 RadioGroup");
                }
                JXTask.Quality quality = getQualityTest(qualityStr);
                String res = "";
                switch (checkedId) {
                    case R.id.workerLeaderOk:
                    case R.id.acceptanceCheckOk:
                    case R.id.qualityCheckOk:
                    case R.id.checkEachotherOk:
                        res = "合格";
                        break;

                    case R.id.workerLeaderNo:
                    case R.id.acceptanceCheckNo:
                    case R.id.qualityCheckNo:
                    case R.id.checkEachotherNo:
                        res = "不合格";
                        break;
                }
                quality.repairResult = res;
            }
        };
        workerLeader.setOnCheckedChangeListener(listener);
        checkEachother.setOnCheckedChangeListener(listener);
        acceptanceCheck.setOnCheckedChangeListener(listener);
        qualityCheck.setOnCheckedChangeListener(listener);

    }

    /**
     * 本地修改的值，"qualityIDX"为""
     *
     * @param qualityStr
     * @return
     */
    private JXTask.Quality getQualityTest(String qualityStr) {
        for (JXTask.Quality quality : data.qualityList) {
            if (quality.qualityItem.equals(qualityStr)) {
                return quality;
            }
        }

        /*不存在*/
        JXTask.Quality quality = new JXTask.Quality(SysInfo.emp.getEmpid().toString(), SysInfo.emp.getEmpname(), "", "", qualityStr, "", data.idx);
        data.qualityList.add(quality);
        return quality;
    }

    @Override
    public void setData(JXTask data, int position) {
        this.data = data;
        this.position = position;
        tvTaskName.setText(data.repairContent);
        etComment.setText(data.remarks);
        etComment.addTextChangedListener(this);
        if (data.status == BusinessConstant.JX_RECORD_UN_QUALIFIED) {//检修记录不合格
            tvTaskName.setBackgroundColor(itemView.getResources().getColor(R.color.colorAccent));
        } else {
            tvTaskName.setBackgroundColor(Color.TRANSPARENT);
        }

        rbSaveLater.setChecked(data.isSaveLaterChecked);

        /*作业者...*/
        tvHandleMan.setText(data.workEmpName != null ? data.workEmpName : "");
        updateQualityState(data);
        updateTaskCondition(data);//作业情况
    }

    /**
     * 作业情况：数据适配
     *
     * @param data
     */
    private void updateTaskCondition(JXTask data) {
        ArmsUtils.configRecyclerView(rvTaskCondition, new LinearLayoutManager(itemView.getContext()));
        if (rvTaskCondition.getAdapter() == null) {
            rvTaskCondition.setAdapter(new TaskConditionAdapter(data,data.detectResultList));
        }
        ((TaskConditionAdapter) rvTaskCondition.getAdapter()).updateData(data.detectResultList);
        rvTaskCondition.getAdapter().notifyDataSetChanged();
    }

    /**
     * 设置XX检查的状态
     *
     * @param data
     */
    private void updateQualityState(JXTask data) {
        /*重置状态*/
        tvWorkLeaderCheckRes.setText("");
        workerLeaderOk.setChecked(false);
        workerLeaderNo.setChecked(false);

        tvCheckEachother.setText("");
        checkEachotherOk.setChecked(false);
        checkEachotherNo.setChecked(false);

        tvQualityCheckRes.setText("");
        qualityCheckOk.setChecked(false);
        qualityCheckNo.setChecked(false);

        tvAcceptanceCheckRes.setText("");
        acceptanceCheckOk.setChecked(false);
        acceptanceCheckNo.setChecked(false);

        /*设置*/
        for (int i = 0; data.qualityList != null && i < data.qualityList.size(); i++) {//质量检查
            JXTask.Quality check = data.qualityList.get(i);
            String sb = check.checkEmpName+" " + check.updateTime +" "+ check.repairResult;
            switch (check.qualityItem) {
                case "工长":
                    if (TextUtils.isEmpty(check.idx)) {//本地编辑产生的数据没有qualityIdx
                        tvWorkLeaderCheckRes.setVisibility(View.GONE);
                        workerLeader.setVisibility(View.VISIBLE);

                        if (check.repairResult.equals("合格")) {
                            workerLeaderOk.setChecked(true);
                        } else {
                            workerLeaderNo.setChecked(true);
                        }
                    } else {//服务端返回的他人编辑的数据
                        tvWorkLeaderCheckRes.setVisibility(View.VISIBLE);
                        workerLeader.setVisibility(View.GONE);

                        tvWorkLeaderCheckRes.setText(sb);
                    }
                    break;
                case "互检":
                    if (TextUtils.isEmpty(check.idx)) {//本地编辑产生的数据没有qualityIdx
                        checkEachother.setVisibility(View.VISIBLE);
                        tvCheckEachother.setVisibility(View.GONE);

                        if (check.repairResult.equals("合格")) {
                            checkEachotherOk.setChecked(true);
                        } else {
                            checkEachotherNo.setChecked(true);
                        }
                    } else {
                        checkEachother.setVisibility(View.GONE);
                        tvCheckEachother.setVisibility(View.VISIBLE);

                        tvCheckEachother.setText(sb);
                    }
                    break;
                case "质检":
                    if (TextUtils.isEmpty(check.idx)) {//本地编辑产生的数据没有qualityIdx
                        qualityCheck.setVisibility(View.VISIBLE);
                        tvQualityCheckRes.setVisibility(View.GONE);

                        if (check.repairResult.equals("合格")) {
                            qualityCheckOk.setChecked(true);
                        } else {
                            qualityCheckNo.setChecked(true);
                        }
                    } else {
                        qualityCheck.setVisibility(View.GONE);
                        tvQualityCheckRes.setVisibility(View.VISIBLE);

                        tvQualityCheckRes.setText(sb);
                    }

                    break;
                case "验收":
                    if (TextUtils.isEmpty(check.idx)) {//本地编辑产生的数据没有qualityIdx
                        acceptanceCheck.setVisibility(View.VISIBLE);
                        tvAcceptanceCheckRes.setVisibility(View.GONE);

                        if (check.repairResult.equals("合格")) {
                            acceptanceCheckOk.setChecked(true);
                        } else {
                            acceptanceCheckNo.setChecked(true);
                        }
                    } else {
                        acceptanceCheck.setVisibility(View.GONE);
                        tvAcceptanceCheckRes.setVisibility(View.VISIBLE);

                        tvAcceptanceCheckRes.setText(sb);
                    }
                    break;
            }
        }
    }

    @OnClick({R.id.tv_check_eachother, R.id.tv_work_leader_check_res, R.id.tv_acceptance_check_res, R.id.tv_quality_check_res})
    public void onCheckResClicked(View view) {
        /*允许修改质检状态*/
        /*switch (view.getId()) {
            case R.id.tv_check_eachother:
                tvCheckEachother.setVisibility(View.GONE);
                checkEachother.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_work_leader_check_res:
                tvWorkLeaderCheckRes.setVisibility(View.GONE);
                workerLeader.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_acceptance_check_res:
                tvAcceptanceCheckRes.setVisibility(View.GONE);
                acceptanceCheck.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_quality_check_res:
                tvQualityCheckRes.setVisibility(View.GONE);
                qualityCheck.setVisibility(View.VISIBLE);
                break;
        }*/
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {//备注编辑监听
        data.remarks = s.toString();
    }
}

