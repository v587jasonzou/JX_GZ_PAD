package com.yunda.gzjx.module.jcyj.mvp.holder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.ArmsUtils;
import com.yunda.gzjx.R;
import com.yunda.gzjx.module.jcyj.entity.PerformanceTestEntity;
import com.yunda.gzjx.module.jcyj.mvp.adapter.TaskConditionOfPTAdapter;
import com.yunda.gzjx.module.jcyj.mvp.contract.TrainPerformanceTestContract;
import com.yunda.gzjx.module.jcyj.mvp.presenter.TrainPerformanceTestPresenter;

import butterknife.BindView;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  略<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/20 14:25<br>
 */
public class PerformanceTestHolder extends BaseHolder<PerformanceTestEntity> implements TextWatcher {
    @BindView(R.id.tvOrder)
    TextView tvOrder;
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
    private PerformanceTestEntity data;

    public PerformanceTestHolder(TrainPerformanceTestContract.View view, TrainPerformanceTestPresenter presenter, View itemView) {
        super(itemView);
        /*保存*/
        rbSaveSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.showLoading();
                presenter.updateListItem(data,view.getIsJZGZ());
            }
        });
    }


    @Override
    public void setData(PerformanceTestEntity data, int position) {
        this.data = data;
        tvOrder.setText(data.seqNo + "");
        tvTaskName.setText(data.item);
        etComment.setText(data.remarks);
        etComment.addTextChangedListener(this);
        /*作业者...*/
        StringBuffer sb = new StringBuffer();
        sb.append(data.workEmpName != null ? data.workEmpName : "");
        if (data.workEmpTime != null) {
            sb.append(" ").append(data.workEmpTime);
        }
        tvHandleMan.setText(sb.toString());
        ArmsUtils.configRecyclerView(rvTaskCondition, new LinearLayoutManager(itemView.getContext()));
        rvTaskCondition.setAdapter(new TaskConditionOfPTAdapter(data, data.itemList));

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

