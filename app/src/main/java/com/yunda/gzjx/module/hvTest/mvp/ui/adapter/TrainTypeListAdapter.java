package com.yunda.gzjx.module.hvTest.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yunda.gzjx.R;
import com.yunda.gzjx.module.hvTest.entry.TrainType;
import com.yunda.gzjx.module.hvTest.mvp.ui.holder.TrainTypeHolder;

import java.util.List;


public class TrainTypeListAdapter extends DefaultAdapter<TrainType> {



    public TrainTypeListAdapter(List<TrainType> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<TrainType> getHolder(View v, int viewType) {
        return new TrainTypeHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_parts_train;
    }
}
