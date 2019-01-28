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
import com.yunda.gzjx.app.EventBusTags;
import com.yunda.gzjx.app.utils.ProgressDialogUtils;
import com.yunda.gzjx.module.hvTest.di.component.DaggerFaultTaskListComponent;
import com.yunda.gzjx.module.hvTest.entry.FaultTask;
import com.yunda.gzjx.module.hvTest.mvp.contract.FaultTaskListContract;
import com.yunda.gzjx.module.hvTest.mvp.presenter.FaultTaskListPresenter;
import com.yunda.gzjx.module.hvTest.mvp.ui.adapter.TicketsAdapter;
import com.yunda.gzjx.view.SimpleDividerDecoration;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public class FaultTaskListActivity extends BaseActivity<FaultTaskListPresenter> implements FaultTaskListContract.View {

    private static FaultTask curToUpdateTicket;
    private static List<FaultTask> sourceData = new ArrayList<>();
    @BindView(R.id.menu)
    Toolbar menu;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.smr_faults)
    SwipeMenuRecyclerView smrFaults;
    @BindView(R.id.srlLayout)
    SmartRefreshLayout srlLayout;
    @BindView(R.id.iv_add_fault)
    ImageView ivAddFault;
    private boolean needRefreshData;
    private Context mContext;
    private String trainIdx;//机车IDX
    private String relationIdx;//工位IDX
    private TicketsAdapter adapter;

    public static FaultTask getCurToUpdateTicket() {
        return curToUpdateTicket;
    }

    public static List<FaultTask> getSourceData() {
        return sourceData;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFaultTaskListComponent //如找不到该类,请编译一下项目
                .builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_fault_task_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        needRefreshData = false;
        mContext = this;
        trainIdx = getIntent().getStringExtra("trainIDX");
        relationIdx = getIntent().getStringExtra("relationIdx");

        setSupportActionBar(menu);
        menu.setNavigationOnClickListener(v -> {
            finish();
        });

        srlLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading();
                mPresenter.getTicketList(trainIdx, relationIdx);
            }
        });

        smrFaults.setLayoutManager(new LinearLayoutManager(this));
        smrFaults.addItemDecoration(new SimpleDividerDecoration(Color.TRANSPARENT, SizeUtils.dp2px(10), 0));
        smrFaults.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                toUpdateFaultTaskPage(adapter.getItemData(position));
            }
        });
        smrFaults.setSwipeMenuCreator(new SwipeMenuCreator() {
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
        smrFaults.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                menuBridge.closeMenu();
                showLoading();
                mPresenter.delTicket(adapter.getItemData(position).ticketIdx, position);
            }
        });
        adapter = new TicketsAdapter(sourceData);
        smrFaults.setAdapter(adapter);

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
            if (!srlLayout.getState().isOpening) {
                mPresenter.searchMaterialInLocal(charSequence.toString().trim(), sourceData);
            }
        });

        showLoading();
        mPresenter.getTicketList(trainIdx, relationIdx);


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
    public void getTicketListSuccess(List<FaultTask> tickets) {
        hideLoading();
        srlLayout.finishRefresh();
        sourceData.clear();
        for (FaultTask m : tickets) {
            sourceData.add(m);
        }
        adapter.updateDatas(sourceData);
    }

    @Override
    public void getTicketListFail(String msg) {
        hideLoading();
        srlLayout.finishRefresh();
        ToastUtils.showShort(msg);
    }

    @Override
    public void delTicketSuccess(int posDeleted) {
        hideLoading();
        adapter.delItem(posDeleted);
        ToastUtils.showShort("删除成功");
    }

    @Override
    public void delTicketFail(String msg) {
        hideLoading();
        ToastUtils.showShort(msg);
    }

    @Override
    public void searchTicketSuccess(List<FaultTask> list) {
        hideLoading();
        adapter.updateDatas(list);
    }

    @Override
    public void toAddFaultTaskPage() {
        Intent intent = new Intent(this, SaveOrUpdateTicketActivity.class);
        intent.putExtra("trainIdx", trainIdx);
        intent.putExtra("relationIdx", relationIdx);
        FaultTaskListActivity.curToUpdateTicket = null;//置空静态变量
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void toUpdateFaultTaskPage(FaultTask ticket) {
        Intent intent = new Intent(this, SaveOrUpdateTicketActivity.class);
        intent.putExtra("trainIdx", trainIdx);
        intent.putExtra("relationIdx", relationIdx);
        FaultTaskListActivity.curToUpdateTicket = ticket;//通过静态变量，共享数据
        ArmsUtils.startActivity(intent);
    }

    @OnClick(R.id.iv_add_fault)
    public void onViewClicked() {
        toAddFaultTaskPage();
    }

    @Subscriber(tag = EventBusTags.NEED_TO_REFRESH_TICKET_LIST)
    private void toUpdateData(FaultTask ticket) {
        needRefreshData = true;//回到界面时刷新

        /*int post = sourceData.indexOf(curToUpdateMaterial);
        if (post != -1) {
            curToUpdateMaterial = material;
            sourceData.set(post, material);
        } else {
            curToUpdateMaterial = null;
            sourceData.add(material);
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (needRefreshData) {
            srlLayout.autoRefresh();
        }
    }
}
