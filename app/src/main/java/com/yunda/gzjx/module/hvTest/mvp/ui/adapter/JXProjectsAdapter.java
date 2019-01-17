package com.yunda.gzjx.module.hvTest.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yunda.gzjx.R;
import com.yunda.gzjx.module.hvTest.entry.JXProject;

import java.util.List;

import butterknife.BindView;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  略<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/11 16:05<br>
 */
public class JXProjectsAdapter extends DefaultAdapter<JXProject> {
    public JXProjectsAdapter(List<JXProject> infos) {
        super(infos);
    }

    @NonNull
    @Override
    public BaseHolder<JXProject> getHolder(@NonNull View v, int viewType) {
        return new JXProjectHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_jx_project;
    }

    public class JXProjectHolder extends BaseHolder<JXProject> {
        @BindView(R.id.tvProjectName)
        TextView tvProjectName;
        @BindView(R.id.progress)
        TextView progress;

        public JXProjectHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(JXProject data, int position) {
            if (data != null) {
                tvProjectName.setText(data.workCardName != null ? data.workCardName : "");
                progress.setText(data.unhandleCount+"/"+data.allCount);
            }
        }

    }

}


