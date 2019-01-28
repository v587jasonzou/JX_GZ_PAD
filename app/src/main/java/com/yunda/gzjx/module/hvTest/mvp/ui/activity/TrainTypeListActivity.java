package com.yunda.gzjx.module.hvTest.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunda.gzjx.R;
import com.yunda.gzjx.app.utils.ProgressDialogUtils;
import com.yunda.gzjx.module.hvTest.di.component.DaggerTrainTypeListComponent;
import com.yunda.gzjx.module.hvTest.entry.TrainType;
import com.yunda.gzjx.module.hvTest.mvp.contract.TrainTypeListContract;
import com.yunda.gzjx.module.hvTest.mvp.presenter.TrainTypeListPresenter;
import com.yunda.gzjx.module.hvTest.mvp.ui.adapter.TrainTypeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 高压试验 - 车型选择列表
 *
 * @author 邹旭
 */
public class TrainTypeListActivity extends BaseActivity<TrainTypeListPresenter> implements TrainTypeListContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.rvTrains)
    RecyclerView rvTrains;
    @BindView(R.id.svRefresh)
    SmartRefreshLayout svRefresh;
    @BindView(R.id.btNext)
    Button btNext;
    TrainTypeListAdapter adapter;
    List<TrainType> mList = new ArrayList<>();
    List<TrainType> tempList = new ArrayList<>();
//    private String workStationIdx;//

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
         //如找不到该类,请编译一下项目
                DaggerTrainTypeListComponent.builder()
                .view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_train_type_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        workStationIdx = getIntent().getStringExtra(BundleConstant.WORK_STATION_IDX);
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        svRefresh.setRefreshHeader(new ClassicsHeader(this));
        svRefresh.setRefreshFooter(new ClassicsFooter(this));
        svRefresh.setNoMoreData(true);
        svRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    etSearch.setText("");
                    showLoading();
                    mPresenter.getTrainList();
                }
            }
        });
        ArmsUtils.configRecycleView(rvTrains, new LinearLayoutManager(this));
        adapter = new TrainTypeListAdapter(mList);
        rvTrains.setAdapter(adapter);
        if (mPresenter != null) {
            showLoading();
            mPresenter.getTrainList();
        }
        RxTextView.textChanges(etSearch).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                mList.clear();
                String str = charSequence.toString();
                if(!StringUtils.isTrimEmpty(str)){
                    for(TrainType bean:tempList){
                        if((bean.trainNo!=null&&bean.trainNo.contains(str))||(bean.trainTypeShortname!=null&&bean.trainTypeShortname.contains(str))){
                            mList.add(bean);
                        }
                    }
                }else {
                    mList.addAll(tempList);
                }
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                if(mList.get(position)!=null){
                    Intent intent = new Intent(getBaseContext(),HVTestBaseInfoActivity.class);
                    intent.putExtra("idx", mList.get(position).idx);
                    /*intent.putExtra("idx",mList.get(position).getIdx());
                    if(mList.get(position).getTrainNo()!=null){
                        intent.putExtra("trainNo",mList.get(position).getTrainNo());
                    }
                    if(mList.get(position).getTrainTypeShortname()!=null){
                        intent.putExtra("name",mList.get(position).getTrainTypeShortname());
                    }*/
                    ArmsUtils.startActivity(intent);
                }
            }
        });
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "加载中...");
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
    public void OnLoadTrainSuccess(List<TrainType> list) {
        svRefresh.finishRefresh();
        mList.clear();
        tempList.clear();
        if(list!=null&&list.size()>0){
            mList.addAll(list);
            tempList.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnLoadFaild(String msg) {
        svRefresh.finishRefresh();
        ToastUtils.showShort(msg);
    }
}
