package com.yunda.gzjx.module.hvTest.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.yunda.gzjx.R;
import com.yunda.gzjx.module.hvTest.entry.FaultTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  略<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/21 13:45<br>
 */
public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketViewHolder> {
    private List<FaultTask> datas;

    public TicketsAdapter(List<FaultTask> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ticket_info, viewGroup, false);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
        lp.rightMargin = lp.leftMargin = SizeUtils.dp2px(10);
        lp.topMargin = lp.bottomMargin = SizeUtils.dp2px(3);
        return new TicketViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        holder.tvTicketContent.setText(datas.get(position).ticketContent);
        holder.tvTicketLocation.setText(datas.get(position).ticketLocation);
//        holder.tvResponseStation.setText(datas.get(position).ticketWorkStationName);
        holder.tvResponseStation.setText(datas.get(position).workStationName);
        holder.tvCreateTicketMan.setText(datas.get(position).createrName);
    }

    public void delItem(int pos) {
        datas.remove(pos);
        notifyItemRemoved(pos);
    }

    public FaultTask getItemData(int pos) {
        if (datas == null || datas.size() == 0) {
            return null;
        }
        return datas.get(pos);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void updateDatas(List<FaultTask> newDatas) {
        this.datas = newDatas;
        notifyDataSetChanged();
    }

    class TicketViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ticket_content)
        TextView tvTicketContent;
        @BindView(R.id.tv_ticket_location)
        TextView tvTicketLocation;
        @BindView(R.id.tv_response_station)
        TextView tvResponseStation;
        @BindView(R.id.tv_create_ticket_man)
        TextView tvCreateTicketMan;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            int r = R.layout.item_ticket_info;
        }
    }
}
