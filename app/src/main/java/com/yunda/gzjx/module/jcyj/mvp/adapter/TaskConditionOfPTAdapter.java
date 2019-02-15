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
import com.yunda.gzjx.module.jcyj.entity.PerformanceTestEntity;

import java.util.List;

import butterknife.BindView;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  机车性能试验，试验结果列表适配器<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/16 13:12<br>
 */
public class TaskConditionOfPTAdapter extends DefaultAdapter<PerformanceTestEntity.ItemList> {
    private PerformanceTestEntity datas;


    public TaskConditionOfPTAdapter(PerformanceTestEntity datas, List<PerformanceTestEntity.ItemList> infos) {
        super(infos);
        this.datas = datas;
    }

    @NonNull
    @Override
    public BaseHolder getHolder(@NonNull View v, int viewType) {
        return new CheckConditionHolder(v, this);

    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_precheck_task_condition;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void updateData(List<PerformanceTestEntity.ItemList> detectResultList) {
        mInfos = detectResultList;
    }


    /**
     * 单选框选择
     */
    public class CheckConditionHolder extends BaseHolder<PerformanceTestEntity.ItemList> implements TextWatcher {

        @BindView(R.id.cb_condition)
        ImageView cbCondition;
        @BindView(R.id.etComment)
        EditText etComment;
        @BindView(R.id.tv_condition)
        TextView tvCondition;
        private PerformanceTestEntity.ItemList data;
        private TaskConditionOfPTAdapter adapter;
        private int position = -1;


        public CheckConditionHolder(View itemView, TaskConditionOfPTAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            etComment.addTextChangedListener(this);
            cbCondition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = (boolean) v.getTag();//之前的选中状态
                    if (!isChecked) {//后续改为->选中状态
                        PerformanceTestEntity.ItemList data = mInfos.get(position);
                        datas.badStateResult = data.detectItemContent;
                        if (data.detectItemContent.equals("其它")) {
                            etComment.setText(datas.badStateDesc);
                        }else {
                            etComment.setText("");
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public void setData(@NonNull PerformanceTestEntity.ItemList data, int position) {
            this.data = data;
            this.position = position;
            tvCondition.setText(data.detectItemContent != null ? data.detectItemContent : "");
            etComment.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(datas.badStateResult) && !TextUtils.isEmpty(datas.badStateResult.replaceAll(" ", "")) && datas.badStateResult.equals(data.detectItemContent)) {
                cbCondition.setTag(true);
                cbCondition.setImageResource(R.drawable.rec_checkbox_check);
                if (data.detectItemContent.equals("其它")) {
                    etComment.setVisibility(View.VISIBLE);
                    etComment.setText(datas.badStateDesc);//选择的"其它",回显 检查结果
                } else {
                    etComment.setText("");
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
            datas.badStateDesc = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
