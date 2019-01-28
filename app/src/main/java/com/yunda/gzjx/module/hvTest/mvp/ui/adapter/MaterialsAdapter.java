package com.yunda.gzjx.module.hvTest.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.yunda.gzjx.R;
import com.yunda.gzjx.module.hvTest.entry.Material;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目:  JX_GZ_PAD <br>
 * 描述:  略<br>
 * 创建人: 邹旭<br>
 * 创建时间: 2019/1/21 13:45<br>
 */
public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsAdapter.MaterialViewHolder> {
    private List<Material> datas;

    public MaterialsAdapter(List<Material> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_material_info, viewGroup, false);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
        lp.rightMargin = lp.leftMargin = SizeUtils.dp2px(10);
        lp.topMargin = lp.bottomMargin = SizeUtils.dp2px(5);
        return new MaterialViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        Material data = datas.get(position);
        holder.tvMaterialName.setText(data.matName);
        holder.tvCount.setText(String.valueOf(data.qty));
        holder.tvSpec.setText(data.modelsSpecifications);
        holder.tvIsOptionPart.setText("2".equals(data.isNeedChange) ? "偶换" : "必换");

        holder.tvGoodsNo.setText(TextUtils.isEmpty(data.matCode) ? "" : data.matCode);
        boolean acceptOrNot = "3".equals(data.requisitionStatus) ? true : false;
        holder.cbAcceptStatus.setChecked(acceptOrNot);
        holder.cbAcceptStatus.setText(acceptOrNot ? "已领" : "未领");
    }

    public void delItem(int pos) {
        datas.remove(pos);
        notifyItemRemoved(pos);
    }

    public Material getItemData(int pos) {
        if (datas == null || datas.size() == 0) {
            return null;
        }
        return datas.get(pos);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void updateDatas(List<Material> newDatas) {
        this.datas = newDatas;
        notifyDataSetChanged();
    }

    class MaterialViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_material_name)
        TextView tvMaterialName;
        @BindView(R.id.tv_spec)
        TextView tvSpec;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_is_option_part)
        TextView tvIsOptionPart;
        @BindView(R.id.tv_goods_no)
        TextView tvGoodsNo;
        @BindView(R.id.cb_accept_status)
        CheckBox cbAcceptStatus;

        public MaterialViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //            int r = R.layout.item_material_info;
        }
    }
}
