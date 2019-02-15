package com.yunda.gzjx.module.jcyj.mvp.adapter;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yunda.gzjx.R;
import com.yunda.gzjx.module.hvTest.entry.JXTask;

import java.util.List;

import butterknife.BindView;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  略<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/16 13:12<br>
 */
public class TaskConditionAdapter extends DefaultAdapter<JXTask.DetectResult> {
    private final int INPUT_FLAG = 0;
    private final int CHECK_FLAG = 1;
    private JXTask jxTask;


    public TaskConditionAdapter(JXTask jxTask, List<JXTask.DetectResult> infos) {
        super(infos);
        this.jxTask = jxTask;
    }

    @NonNull
    @Override
    public BaseHolder getHolder(@NonNull View v, int viewType) {
        if (viewType == CHECK_FLAG) {
            return new CheckConditionHolder(v, this);
        } else {
            return new InputConditionHolder(v);
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == CHECK_FLAG) {
            return R.layout.item_precheck_task_condition;
        } else {
            return R.layout.item_input_task_condition;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ("0".equals(mInfos.get(position).dictItemCode)) {//需要输入
            return INPUT_FLAG;
        } else {
            return CHECK_FLAG;
        }
    }

    public void updateData(List<JXTask.DetectResult> detectResultList) {
        mInfos = detectResultList;
    }

    /**
     * 手动输入
     */
    public class InputConditionHolder extends BaseHolder<JXTask.DetectResult> implements TextWatcher {

        @BindView(R.id.tv_jx_pos)
        TextView tvJxPos;
        @BindView(R.id.et_jx_pos_condition)
        EditText etJxPosCondition;
        @BindView(R.id.unit)
        TextView unit;
        private JXTask.DetectResult data;

        public InputConditionHolder(View itemView) {
            super(itemView);
            etJxPosCondition.addTextChangedListener(this);
        }

        @Override
        public void setData(@NonNull JXTask.DetectResult data, int position) {
            //            int i = R.layout.item_input_task_condition;
            this.data = data;
            tvJxPos.setText(data.detectItemContent != null ? data.detectItemContent : "");
            etJxPosCondition.setText(data.detectResult != null ? data.detectResult : "");
            unit.setText(data.unit != null ? data.unit : "");
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            data.detectResult = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * 单选框选择
     */
    public class CheckConditionHolder extends BaseHolder<JXTask.DetectResult> implements TextWatcher {

        @BindView(R.id.cb_condition)
        ImageView cbCondition;
        @BindView(R.id.etComment)
        EditText etComment;
        @BindView(R.id.tv_condition)
        TextView tvCondition;
        private JXTask.DetectResult data;
        private TaskConditionAdapter adapter;
        private int position = -1;


        public CheckConditionHolder(View itemView, TaskConditionAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            etComment.addTextChangedListener(this);
            cbCondition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = (boolean) v.getTag();//之前的选中状态
                    if (!isChecked) {//后续改为->X状态
                        JXTask.DetectResult data = mInfos.get(position);
                        for (JXTask.DetectResult mInfo : mInfos) {
                            mInfo.detectResult = "";
                        }
                        data.detectResult = data.detectItemContent;
                        jxTask.repairResult = data.detectResult;//检查结果
                        adapter.notifyDataSetChanged();
                    } else {
                        data.detectResult = "";
                    }
                }
            });
        }

        @Override
        public void setData(@NonNull JXTask.DetectResult data, int position) {
            this.data = data;
            this.position = position;
            tvCondition.setText(data.detectItemContent != null ? data.detectItemContent : "");
            etComment.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(data.detectResult.replaceAll(" ", ""))) {
                cbCondition.setTag(true);
                cbCondition.setImageResource(R.drawable.rec_checkbox_check);
                if (data.detectResult.equals("其它")||data.detectResult.equals("其他")) {
                    etComment.setVisibility(View.VISIBLE);
                    etComment.setText(jxTask.repairResult);//选择的"其它",回显 检查结果
                }
            } else {
                cbCondition.setTag(false);
                cbCondition.setImageResource(R.drawable.rec_checkbox_uncheck);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            jxTask.repairResult = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
