package com.yunda.gzjx.module.jcyj.mvp.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yunda.gzjx.R;
import com.yunda.gzjx.module.jcyj.entity.PerformanceTestEntity;
import com.yunda.gzjx.module.jcyj.mvp.contract.TrainPerformanceTestContract;
import com.yunda.gzjx.module.jcyj.mvp.holder.PerformanceTestHolder;
import com.yunda.gzjx.module.jcyj.mvp.presenter.TrainPerformanceTestPresenter;

import java.util.List;

/**
 * 项目:  JX_GZ_PAD <br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/11 16:05<br>
 */
public class PerformanceTestAdapter extends DefaultAdapter<PerformanceTestEntity> implements View.OnClickListener {
    private TrainPerformanceTestPresenter presenter;
    private TrainPerformanceTestContract.View view;
    public PerformanceTestAdapter(TrainPerformanceTestContract.View view , TrainPerformanceTestPresenter presenter, List<PerformanceTestEntity> infos) {
        super(infos);
        this.view = view;
        this.presenter = presenter;
    }

    public void updateData(List<PerformanceTestEntity> infos) {
        this.mInfos = infos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseHolder<PerformanceTestEntity> getHolder(@NonNull View v, int viewType) {
        return new PerformanceTestHolder(view,presenter,v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_performance_test;
    }

    @Override
    public int getItemCount() {
        return getInfos() != null ? getInfos().size() : 0;
    }

    @Override
    public void onClick(View v) {

    }
}


