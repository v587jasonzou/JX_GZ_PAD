package com.yunda.gzjx.module.jcyj.mvp.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yunda.gzjx.R;
import com.yunda.gzjx.module.hvTest.entry.JXTask;
import com.yunda.gzjx.module.jcyj.mvp.contract.PrecheckRecordsContract;
import com.yunda.gzjx.module.jcyj.mvp.holder.PrecheckRecordsHolder;
import com.yunda.gzjx.module.jcyj.mvp.presenter.PrecheckRecordsPresenter;

import java.util.List;

/**
 * 项目:  JX_GZ_PAD <br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/11 16:05<br>
 */
public class PrecheckRecordsAdapter extends DefaultAdapter<JXTask> implements View.OnClickListener {
    private PrecheckRecordsPresenter presenter;
    private PrecheckRecordsContract.View view;
    private List<JXTask> datas;
    public PrecheckRecordsAdapter(PrecheckRecordsContract.View view , PrecheckRecordsPresenter presenter, List<JXTask> infos) {
        super(infos);
        datas = infos;
        this.view = view;
        this.presenter = presenter;
    }

    public void updateData(List<JXTask> infos) {
        datas = this.mInfos = infos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseHolder<JXTask> getHolder(@NonNull View v, int viewType) {
        return new PrecheckRecordsHolder(view,presenter,v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_precheck_record;
    }

    @Override
    public int getItemCount() {
        return getInfos() != null ? getInfos().size() : 0;
    }

    @Override
    public void onClick(View v) {

    }
}


