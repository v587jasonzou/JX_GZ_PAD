package com.yunda.gzjx.module.home.mvp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yunda.gzjx.R;
import com.yunda.gzjx.module.login.repository.entity.MenuSimpleBean;

import java.util.List;

import butterknife.BindView;


public class HomeMenuAdapter extends DefaultAdapter<MenuSimpleBean> {

    public HomeMenuAdapter(List<MenuSimpleBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MenuSimpleBean> getHolder(View v, int viewType) {
        return new MenuHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_menu_home;
    }

    public class MenuHolder extends BaseHolder<MenuSimpleBean> {
        @BindView(R.id.ivMenu)
        ImageView ivMenu;
        @BindView(R.id.tvMenuName)
        TextView tvMenuName;
        public MenuHolder(View itemView) {
            super(itemView);
        }
        @Override
        public void setData(MenuSimpleBean data, int position) {
            if (data != null) {
                if(!StringUtils.isTrimEmpty(data.getMenu())){
                    tvMenuName.setText(data.getMenu());
//                    if(data.getMenu().equals("车间的")){
//                        ivMenu.setImageResource(R.mipmap.part_down_menu_icon);
//                    }else if(data.getMenu().equals("配件接收登记")){
//                        ivMenu.setImageResource(R.mipmap.get_part_menu_icon);
//                    }else if(data.getMenu().equals("配件上车登记")){
//                        ivMenu.setImageResource(R.mipmap.up_part_menu_icon);
//                    }else if(data.getMenu().equals("大部件拆解清单登记")){
//                        ivMenu.setImageResource(R.mipmap.disassembly_parts_menu_icon);
//                    }else if(data.getMenu().equals("大部件组装清单登记")){
//                        ivMenu.setImageResource(R.mipmap.assembly_parts_menu_icon);
//                    }
                }
            }
        }
    }

}
