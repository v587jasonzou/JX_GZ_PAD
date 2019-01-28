package com.yunda.gzjx.module.hvTest.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunda.gzjx.R;
import com.yunda.gzjx.app.utils.ProgressDialogUtils;
import com.yunda.gzjx.module.hvTest.di.component.DaggerJXRecordProjectsComponent;
import com.yunda.gzjx.module.hvTest.entry.JXProject;
import com.yunda.gzjx.module.hvTest.entry.ReqJXProjcetsParm;
import com.yunda.gzjx.module.hvTest.mvp.contract.JXRecordProjectsContract;
import com.yunda.gzjx.module.hvTest.mvp.presenter.JXRecordProjectsPresenter;
import com.yunda.gzjx.module.hvTest.mvp.ui.adapter.JXProjectsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.schedulers.Schedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:检修记录 - 作业项目列表
 * ================================================
 */
public class JXRecordProjectsActivity extends BaseActivity<JXRecordProjectsPresenter> implements JXRecordProjectsContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.projects)
    RecyclerView rvProjects;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    public  static final int pageSize = 20;
    private String trainIDX;//机车IDX
    private String relationIdx;//菜单项relationIdx
    private ReqJXProjcetsParm parm;
    private List<JXProject> jxProjects = new ArrayList<>();
    private JXProjectsAdapter adapter;
    private String trainTypeNoStr;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerJXRecordProjectsComponent //如找不到该类,请编译一下项目
                .builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_jxrecord_projects; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        trainIDX = getIntent().getStringExtra("trainIDX");
        relationIdx = getIntent().getStringExtra("relationIdx");
        trainTypeNoStr = getIntent().getStringExtra("trainTypeNo");

        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        menuTp.setTitle(trainTypeNoStr);
        parm = new ReqJXProjcetsParm(trainIDX, relationIdx, 1, pageSize);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && event.getAction() == KeyEvent.ACTION_DOWN) {
                    showLoading();
                    parm.setKeywords(v.getText().toString());
                    mPresenter.queryJXProjects(parm,true);
                }
                return false;
            }
        });
        RxTextView.textChanges(etSearch).debounce(400, TimeUnit.MILLISECONDS).observeOn(Schedulers.io()).subscribe(charSequence -> {
            parm.setKeywords(charSequence.toString());
            if (charSequence.length()==0) {//检索时，取消加载功能，只保留刷新功能
                srl.setEnableLoadMore(true);
            }else {
                srl.setEnableLoadMore(false);
            }
            mPresenter.queryJXProjects(parm,true);

        });

        ArmsUtils.configRecyclerView(rvProjects, new LinearLayoutManager(this));
        adapter = new JXProjectsAdapter(jxProjects);
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int viewType, @NonNull Object data, int position) {
                Intent intent = new Intent(getBaseContext(),JXTasksOfProjectActivity.class);
                intent.putExtra("workCardIdx", jxProjects.get(position).workCardIdx);//作业项目idx
                ArmsUtils.startActivity(intent);
            }
        });
        rvProjects.setAdapter(adapter);

        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                parm.setKeywords(etSearch.getText().toString());
                parm.setPageNumber(1);
                mPresenter.queryJXProjects(parm,true);
            }
        });
        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                parm.setPageNumber(jxProjects.size()+1);
                mPresenter.queryJXProjects(parm,false);
            }
        });

        showLoading();
        mPresenter.queryJXProjects(parm,true);

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void getProjectsSuccess(List<JXProject> jxProjects, boolean isRefresh) {
        hideLoading();
        if (isRefresh) {
            srl.finishRefresh();
            this.jxProjects.clear();
        }else {
            srl.finishLoadMore();
            if (jxProjects.size()==0) {
                ToastUtils.showShort("没有更多数据了");
                return;
            }
        }
        this.jxProjects.addAll(jxProjects);
        adapter.notifyDataSetChanged();
//        if (jxProjects==null||jxProjects.size()==0) { }
    }

    @Override
    public void getProjectsFail(String message) {
        hideLoading();
        ToastUtils.showShort("数据加载失败！" + message);
    }
}
