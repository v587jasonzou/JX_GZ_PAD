package com.yunda.gzjx.module.jcyj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunda.gzjx.R;
import com.yunda.gzjx.app.utils.ProgressDialogUtils;
import com.yunda.gzjx.module.hvTest.entry.JXTask;
import com.yunda.gzjx.module.jcyj.di.component.DaggerPrecheckRecordsComponent;
import com.yunda.gzjx.module.jcyj.mvp.adapter.PrecheckRecordsAdapter;
import com.yunda.gzjx.module.jcyj.mvp.contract.PrecheckRecordsContract;
import com.yunda.gzjx.module.jcyj.mvp.presenter.PrecheckRecordsPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>预检记录
 * ================================================
 */
public class PrecheckRecordsActivity extends BaseActivity<PrecheckRecordsPresenter> implements PrecheckRecordsContract.View {
    @BindView(R.id.menu)
    Toolbar menuTp;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tasks)
    RecyclerView rvTasks;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    private List<JXTask> jxTasks = new ArrayList<>();//接口请求的数据
    private PrecheckRecordsAdapter adapter;
    private String workCardIdx;//作业项目idx;
    private boolean isSaveAll = false;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPrecheckRecordsComponent //如找不到该类,请编译一下项目
                .builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_precheck_records; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        workCardIdx = getIntent().getStringExtra("workCardIdx");
        String title = getIntent().getStringExtra("title");
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        menuTp.setTitle(title);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (jxTasks != null && actionId == EditorInfo.IME_ACTION_SEARCH && event != null && event.getAction() == KeyEvent.ACTION_DOWN) {
                    mPresenter.searchJXTasksOfProjectOnLocal(jxTasks, etSearch.getText().toString().trim());
                }
                return false;
            }
        });
        RxTextView.textChanges(etSearch).skip(1).debounce(400, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(charSequence -> {
            mPresenter.searchJXTasksOfProjectOnLocal(jxTasks, charSequence.toString().trim());
        });

        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PrecheckRecordsAdapter(this,mPresenter,jxTasks);
        rvTasks.setAdapter(adapter);

        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryJXTasksOfProject(workCardIdx);
            }
        });

        showLoading();
        mPresenter.queryJXTasksOfProject(workCardIdx);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tasks_of_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveAll:
                List<JXTask> updates = new ArrayList<>();
                for (JXTask jxTask : adapter.getInfos()) {
                    if (jxTask.isSaveLaterChecked) {
                        updates.add(jxTask);
                    }
                }
                showLoading();
                mPresenter.updateTaskInfo(updates);
                break;
        }
        return true;
    }

    @Override
    public void getTasksSuccess(List<JXTask> jxTasks) {
        hideLoading();
        srl.finishRefresh();
        this.jxTasks.clear();
        this.jxTasks.addAll(jxTasks);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getTasksFail(String message) {
        hideLoading();
        srl.finishRefresh();
        ToastUtils.showShort("数据加载失败！" + message);
    }

    @Override
    public void updateTasksSuccess(String message) {
        hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (isSaveAll) {
            for (JXTask jxTask : adapter.getInfos()) {
                jxTask.isSaveLaterChecked = false;
            }
            isSaveAll = false;
        }
        srl.autoRefresh();
    }

    @Override
    public void updateTaskFail(String errMsg) {
        hideLoading();
        ToastUtils.showShort(errMsg);
    }

    @Override
    public void searchSuccess(List<JXTask> results) {
        adapter.updateData(results);//检索的数据
    }
}
