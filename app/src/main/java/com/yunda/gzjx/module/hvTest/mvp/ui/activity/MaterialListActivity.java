package com.yunda.gzjx.module.hvTest.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yunda.gzjx.R;
import com.yunda.gzjx.app.utils.ProgressDialogUtils;
import com.yunda.gzjx.module.home.mvp.ui.activity.HomeActivity;
import com.yunda.gzjx.module.hvTest.di.component.DaggerMaterialListComponent;
import com.yunda.gzjx.module.hvTest.entry.Material;
import com.yunda.gzjx.module.hvTest.mvp.contract.MaterialListContract;
import com.yunda.gzjx.module.hvTest.mvp.presenter.MaterialListPresenter;
import com.yunda.gzjx.module.hvTest.mvp.ui.adapter.MaterialsAdapter;
import com.yunda.gzjx.view.SimpleDividerDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:高压试验 - 物料清单
 * ================================================
 */
public class MaterialListActivity extends BaseActivity<MaterialListPresenter> implements MaterialListContract.View {

    public static Material curToUpdateMaterial = null;
    @BindView(R.id.menu)
    Toolbar menu;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.materials)
    SwipeMenuRecyclerView materials;
    @BindView(R.id.srlLayout)
    SmartRefreshLayout srlLayout;
    @BindView(R.id.addMaterial)
    ImageView addMaterial;

    public static List<Material> getSourceData() {
        return sourceData;
    }

    public static List<Material> sourceData = new ArrayList<>();
    private static List<Material> searchedData;
    private String trainIdx;//机车IDX
    private MaterialsAdapter adapter;
    private Context mContext;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMaterialListComponent //如找不到该类,请编译一下项目
                .builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_material_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mContext = this;
        trainIdx = getIntent().getStringExtra("trainIDX");

        setSupportActionBar(menu);
        menu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                killMyself();
            }
        });

        srlLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading();
                mPresenter.getMaterialList(trainIdx, HomeActivity.getCurRelationIdx());
            }
        });

        materials.setLayoutManager(new LinearLayoutManager(this));
        materials.addItemDecoration(new SimpleDividerDecoration(Color.TRANSPARENT, SizeUtils.dp2px(10), 0));
        materials.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                toUpdateMaterialPage(adapter.getItemData(position));
            }
        });
        materials.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem del = new SwipeMenuItem(mContext);
                del.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                del.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                del.setTextColor(Color.WHITE);
                del.setText("删除");
                del.setWidth(SizeUtils.dp2px(80));
                rightMenu.addMenuItem(del);
            }
        });
        materials.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                menuBridge.closeMenu();
                mPresenter.delMaterial(adapter.getItemData(position).matIdx, position);
            }
        });
        adapter = new MaterialsAdapter(sourceData);
        materials.setAdapter(adapter);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (sourceData != null && actionId == EditorInfo.IME_ACTION_SEARCH && event != null && event.getAction() == KeyEvent.ACTION_DOWN) {
                    showLoading();
                    mPresenter.searchMaterialInLocal(etSearch.getText().toString().trim(), sourceData);
                }
                return false;
            }
        });
        RxTextView.textChanges(etSearch).skip(1).debounce(400, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(charSequence -> {
            mPresenter.searchMaterialInLocal(charSequence.toString().trim(), sourceData);
        });

        showLoading();
        mPresenter.getMaterialList(trainIdx, HomeActivity.getCurRelationIdx());
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "数据加载中，请稍后");
    }

    @Override
    public void hideLoading() {
        ProgressDialogUtils.dismissProgressDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void getMaterialListSuccess(List<Material> material) {
        hideLoading();
        srlLayout.finishRefresh();
        sourceData.clear();
        for (Material m : material) {
            sourceData.add(m);
        }
        adapter.updateDatas(sourceData);
    }

    @Override
    public void getMaterialListFail(String msg) {
        hideLoading();
        srlLayout.finishRefresh();
        ToastUtils.showShort(msg);
    }

    @Override
    public void delMaterialSuccess(int posDeleted) {
        hideLoading();
        adapter.delItem(posDeleted);
        ToastUtils.showShort("物料删除成功");
    }

    @Override
    public void delMaterialFail(String msg) {
        hideLoading();
        ToastUtils.showShort(msg);
    }

    @Override
    public void searchMaterialsSuccess(List<Material> list) {
        hideLoading();
        if (searchedData == null) {
            searchedData = new ArrayList<>();
        }
        searchedData.clear();
        if (list != null) {
            searchedData.addAll(list);
        }
        adapter.updateDatas(searchedData);
    }

    @Override
    public void toAddMaterialPage() {
        Intent intent = new Intent(this, SaveOrUpdateMaterialActivity.class);
        MaterialListActivity.curToUpdateMaterial = null;//置空静态变量
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void toUpdateMaterialPage(Material material) {
        Intent intent = new Intent(this, SaveOrUpdateMaterialActivity.class);
        MaterialListActivity.curToUpdateMaterial = material;//通过静态变量，共享数据
        ArmsUtils.startActivity(intent);
    }


    @OnClick(R.id.addMaterial)
    public void onViewClicked() {
        toAddMaterialPage();
    }
}
