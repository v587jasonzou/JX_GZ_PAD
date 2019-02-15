package com.yunda.gzjx.module.jcyj.mvp.holder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.ArmsUtils;
import com.yunda.gzjx.R;
import com.yunda.gzjx.module.hvTest.entry.JXTask;
import com.yunda.gzjx.module.jcyj.mvp.adapter.TaskConditionAdapter;
import com.yunda.gzjx.module.jcyj.mvp.contract.PrecheckRecordsContract;
import com.yunda.gzjx.module.jcyj.mvp.presenter.PrecheckRecordsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  略<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/20 14:25<br>
 */
public class PrecheckRecordsHolder extends BaseHolder<JXTask> implements TextWatcher {
    private final PrecheckRecordsContract.View view;
    private final PrecheckRecordsPresenter presenter;
    @BindView(R.id.tvOrder)
    TextView tvOrder;
    @BindView(R.id.tvCount)
    TextView tvCount;
    @BindView(R.id.tv_task_name)
    TextView tvTaskName;
    @BindView(R.id.rv_task_condition)
    RecyclerView rvTaskCondition;
    @BindView(R.id.tv_handle_man)
    TextView tvHandleMan;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.rb_saveSingle)
    Button rbSaveSingle;
    @BindView(R.id.rb_saveLater)
    CheckBox rbSaveLater;
    @BindView(R.id.rg_save)
    LinearLayout rgSave;
    private JXTask data;
    private boolean isRefreshing;//正在加载数据,不响RadioGroup应事件监听

    public PrecheckRecordsHolder(PrecheckRecordsContract.View view, PrecheckRecordsPresenter presenter, View itemView) {
        super(itemView);
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


    @Override
    public void setData(JXTask data, int position) {
        this.data = data;
        tvOrder.setText(data.workStepSeq);
        tvCount.setText(data.repairContent);
        tvTaskName.setText(data.workTaskName);
        etComment.setText(data.remarks);
        etComment.addTextChangedListener(this);
        rbSaveLater.setChecked(data.isSaveLaterChecked);

        /*作业者...*/
        tvHandleMan.setText(data.workEmpName != null ? data.workEmpName : "");

        ArmsUtils.configRecyclerView(rvTaskCondition, new LinearLayoutManager(itemView.getContext()));
        rvTaskCondition.setAdapter(new TaskConditionAdapter(data, data.detectResultList));
        isRefreshing = false;
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

