package com.yunda.gzjx.module.hvTest.mvp.ui.holder;

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

import com.blankj.utilcode.util.ToastUtils;
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
    @BindView(R.id.checkEachother)
    RadioGroup checkEachother;
    @BindView(R.id.tv_check_eachother)
    TextView tvCheckEachother;
    @BindView(R.id.workerLeader)
    RadioGroup workerLeader;
    @BindView(R.id.tv_work_leader_check_res)
    TextView tvWorkLeaderCheckRes;
    @BindView(R.id.acceptanceCheck)
    RadioGroup acceptanceCheck;
    @BindView(R.id.tv_acceptance_check_res)
    TextView tvAcceptanceCheckRes;
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
    @BindView(R.id.undo)
    TextView undo;
    private JXTask data;
    private boolean isRefreshing;//正在加载数据,不响RadioGroup应事件监听

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
                if (isRefreshing) {
                    return;
                }
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

                /**
                 * --------------------
                 * 此处创建了一条不含idx的本地操作项，只应该在手动选择时执行创建，其他情况不应该执行
                 * --------------------
                 */
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

        /*不存在(没有idx的是临时项，可以从qualityList中删除)*/
        JXTask.Quality quality = new JXTask.Quality(SysInfo.emp.getEmpid().toString(), SysInfo.emp.getEmpname(), "", "", qualityStr, "", data.idx);
        data.qualityList.add(quality);
        return quality;
    }

    @Override
    public void setData(JXTask data, int position) {
        this.data = data;
        tvTaskName.setText(data.repairContent);
        etComment.setText(data.remarks);
        etComment.addTextChangedListener(this);
        if (data.status == BusinessConstant.JX_RECORD_UN_QUALIFIED || containNotOkQuality(data)) {//检修记录不合格
            tvTaskName.setTextColor(itemView.getResources().getColor(R.color.red));
        } else {
            tvTaskName.setTextColor(itemView.getResources().getColor(R.color.text_color));
        }

        rbSaveLater.setChecked(data.isSaveLaterChecked);

        /*作业者...*/
        tvHandleMan.setText(data.workEmpName != null ? data.workEmpName : "");
        updateQualityState(data, position);//检测情况
        updateTaskCondition(data);//作业情况
        isRefreshing = false;
    }

    private boolean containNotOkQuality(JXTask data) {
        if (data == null || data.qualityList == null || data.qualityList.size() == 0) {
            return false;
        } else {
            for (JXTask.Quality quality : data.qualityList) {
                if (!quality.idx.isEmpty() && "不合格".equals(quality.repairResult)) {//修改项
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 作业情况：数据适配
     *
     * @param data
     */
    private void updateTaskCondition(JXTask data) {
        ArmsUtils.configRecyclerView(rvTaskCondition, new LinearLayoutManager(itemView.getContext()));
        if (rvTaskCondition.getAdapter() == null) {
            rvTaskCondition.setAdapter(new TaskConditionAdapter(data, data.detectResultList));
        }
        ((TaskConditionAdapter) rvTaskCondition.getAdapter()).updateData(data.detectResultList);
        rvTaskCondition.getAdapter().notifyDataSetChanged();
    }

    /**
     * 设置XX检查的状态
     *
     * @param data
     */
    private void updateQualityState(JXTask data, int position) {
        isRefreshing = true;

        if (position == 0) {
            boolean b = true;
        }
        /*重置状态*/
        tvWorkLeaderCheckRes.setVisibility(View.GONE);
        workerLeader.setVisibility(View.VISIBLE);
        tvWorkLeaderCheckRes.setText("");
        workerLeader.clearCheck();

        tvCheckEachother.setVisibility(View.GONE);
        checkEachother.setVisibility(View.VISIBLE);
        tvCheckEachother.setText("");
        checkEachother.clearCheck();

        tvQualityCheckRes.setVisibility(View.GONE);
        qualityCheck.setVisibility(View.VISIBLE);
        tvQualityCheckRes.setText("");
        qualityCheck.clearCheck();

        tvAcceptanceCheckRes.setVisibility(View.GONE);
        acceptanceCheck.setVisibility(View.VISIBLE);
        tvAcceptanceCheckRes.setText("");
        acceptanceCheck.clearCheck();

        /*回显数据*/
        for (int i = 0; data.qualityList != null && i < data.qualityList.size(); i++) {//质量检查
            JXTask.Quality check = data.qualityList.get(i);
            String sb = check.checkEmpName + " " + check.updateTime + " " + check.repairResult;
            switch (check.qualityItem) {
                case "工长":
                    if (TextUtils.isEmpty(check.idx)) {//本地编辑产生的数据没有qualityIdx
                        tvWorkLeaderCheckRes.setVisibility(View.GONE);
                        workerLeader.setVisibility(View.VISIBLE);

                        if ("合格".equals(check.repairResult)) {
                            workerLeader.check(R.id.workerLeaderOk);
                            //                            workerLeaderOk.setChecked(true);
                        } else if ("不合格".equals(check.repairResult)) {
                            workerLeader.check(R.id.workerLeaderNo);
                            //workerLeaderNo.setChecked(true);
                        }
                    } else {//服务端返回的他人编辑的数据
                        tvWorkLeaderCheckRes.setVisibility(View.VISIBLE);
                        workerLeader.setVisibility(View.GONE);

                        tvWorkLeaderCheckRes.setText(sb);
                    }
                    break;
                case "互检":
                    if (TextUtils.isEmpty(check.idx)) {//本地编辑产生的数据没有qualityIdx
                        tvCheckEachother.setVisibility(View.GONE);
                        checkEachother.setVisibility(View.VISIBLE);

                        if ("合格".equals(check.repairResult)) {
                            checkEachother.check(R.id.checkEachotherOk);
                            //                            checkEachotherOk.setChecked(true);
                        } else if ("不合格".equals(check.repairResult)) {
                            checkEachother.check(R.id.checkEachotherNo);
                            //                            checkEachotherNo.setChecked(true);
                        }
                    } else {
                        tvCheckEachother.setVisibility(View.VISIBLE);
                        checkEachother.setVisibility(View.GONE);

                        tvCheckEachother.setText(sb);
                    }
                    break;
                case "质检":
                    if (TextUtils.isEmpty(check.idx)) {//本地编辑产生的数据没有qualityIdx
                        tvQualityCheckRes.setVisibility(View.GONE);
                        qualityCheck.setVisibility(View.VISIBLE);

                        if ("合格".equals(check.repairResult)) {
                            qualityCheck.check(R.id.qualityCheckOk);
                            //                            qualityCheckOk.setChecked(true);
                        } else if ("不合格".equals(check.repairResult)) {
                            qualityCheck.check(R.id.qualityCheckNo);
                            //                            qualityCheckNo.setChecked(true);
                        }
                    } else {
                        tvQualityCheckRes.setVisibility(View.VISIBLE);
                        qualityCheck.setVisibility(View.GONE);

                        tvQualityCheckRes.setText(sb);
                    }

                    break;
                case "验收":
                    if (TextUtils.isEmpty(check.idx)) {//本地编辑产生的数据没有qualityIdx
                        tvAcceptanceCheckRes.setVisibility(View.GONE);
                        acceptanceCheck.setVisibility(View.VISIBLE);

                        if ("合格".equals(check.repairResult)) {
                            acceptanceCheck.check(R.id.acceptanceCheckOk);
                            //                            acceptanceCheckOk.setChecked(true);
                        } else if ("不合格".equals(check.repairResult)) {
                            acceptanceCheck.check(R.id.acceptanceCheckNo);
                            //                            acceptanceCheckNo.setChecked(true);
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

    @OnClick({R.id.undo})
    public void undo(View view) {//撤销所有本地新增修改的检查项，默认不选择任何项
        if (data == null || data.qualityList == null) {
            return;
        }
        /*设置*/
        for (int i = data.qualityList.size() - 1; i >= 0; i--) {//质量检查
            JXTask.Quality check = data.qualityList.get(i);
            data.qualityList.remove(check);
            switch (check.qualityItem) {
                case "工长":
                    if (TextUtils.isEmpty(check.idx)) {//本地编辑产生的数据没有qualityIdx
                        tvWorkLeaderCheckRes.setVisibility(View.GONE);
                        workerLeader.setVisibility(View.VISIBLE);
                        workerLeader.clearCheck();
                    }
                    break;
                case "互检":
                    if (TextUtils.isEmpty(check.idx)) {//本地编辑产生的数据没有qualityIdx
                        tvCheckEachother.setVisibility(View.GONE);
                        checkEachother.setVisibility(View.VISIBLE);
                        checkEachother.clearCheck();
                    }
                    break;
                case "质检":
                    if (TextUtils.isEmpty(check.idx)) {//本地编辑产生的数据没有qualityIdx
                        tvQualityCheckRes.setVisibility(View.GONE);
                        qualityCheck.setVisibility(View.VISIBLE);
                        qualityCheck.clearCheck();
                    }
                    break;
                case "验收":
                    if (TextUtils.isEmpty(check.idx)) {//本地编辑产生的数据没有qualityIdx
                        tvAcceptanceCheckRes.setVisibility(View.GONE);
                        acceptanceCheck.setVisibility(View.VISIBLE);
                        acceptanceCheck.clearCheck();
                    }
                    break;
            }
        }
        ToastUtils.showShort("本地选择已撤销操作");

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

