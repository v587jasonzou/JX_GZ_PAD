package com.yunda.gzjx.module.hvTest.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.yunda.gzjx.R;
import com.yunda.gzjx.module.hvTest.entry.TrainType;

import butterknife.BindView;


public class TrainTypeHolder extends BaseHolder<TrainType> {
    @BindView(R.id.tvTrainNo)
    TextView tvTrainNo;
    public TrainTypeHolder(View itemView) {
        super(itemView);
    }
    @Override
    public void setData(TrainType data, int position) {
        if (data!=null){
            String str = "";
            if(data.trainTypeShortname!=null){
                str = data.trainTypeShortname+" ";
            }
            if (data.trainNo!=null){
                str = str+data.trainNo;
            }
            tvTrainNo.setText(str);
        }
    }

}
