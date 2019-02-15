package com.yunda.gzjx.module.jcyj.mvp.ui.activity;

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
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.kyleduo.switchbutton.SwitchButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunda.gzjx.R;
import com.yunda.gzjx.app.utils.ProgressDialogUtils;
import com.yunda.gzjx.module.jcyj.di.component.DaggerTrainPerformanceTestComponent;
import com.yunda.gzjx.module.jcyj.entity.PerformanceTestEntity;
import com.yunda.gzjx.module.jcyj.mvp.adapter.PerformanceTestAdapter;
import com.yunda.gzjx.module.jcyj.mvp.contract.TrainPerformanceTestContract;
import com.yunda.gzjx.module.jcyj.mvp.presenter.TrainPerformanceTestPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public class TrainPerformanceTestActivity extends BaseActivity<TrainPerformanceTestPresenter> implements TrainPerformanceTestContract.View {
    @BindView(R.id.menu)
    Toolbar menuTp;
    @BindView(R.id.sbStatus)
    SwitchButton sbStatus;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tasks)
    RecyclerView rvTasks;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    private List<PerformanceTestEntity> datas = new ArrayList<>();//接口请求的数据
    private PerformanceTestAdapter adapter;
    private String workPlanIdx;
    private int decisionType = 2;
    private boolean ignoreOnce;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTrainPerformanceTestComponent //如找不到该类,请编译一下项目
                .builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_train_performance_test; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        workPlanIdx = getIntent().getStringExtra("workPlanIdx");
        decisionType = getIntent().getIntExtra("decisionType", 2);//类型：1.加装改造，2性能试验
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
                if (datas != null && actionId == EditorInfo.IME_ACTION_SEARCH && event != null && event.getAction() == KeyEvent.ACTION_DOWN) {
                    showLoading();
                    mPresenter.searchJXTasksOfProjectOnLocal(datas, etSearch.getText().toString().trim(), sbStatus.isChecked());
                }
                return false;
            }
        });
        RxTextView.textChanges(etSearch).skip(1).debounce(400, TimeUnit.MILLISECONDS).filter(new Func1<CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence) {
                boolean tmp = ignoreOnce;//忽略一次
                ignoreOnce = false;
                return !tmp;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(charSequence -> {
            showLoading();
            mPresenter.searchJXTasksOfProjectOnLocal(datas, charSequence.toString().trim(), sbStatus.isChecked());
        });

        sbStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showLoading();
            etSearch.setText("");
            ignoreOnce = true;
            List<PerformanceTestEntity> list = mPresenter.filterList(datas, isChecked);
            Observable.just(1).delay(300, TimeUnit.MILLISECONDS).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(integer -> {
                hideLoading();
                adapter.updateData(list);
            });
        });

        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PerformanceTestAdapter(this, mPresenter, datas);
        rvTasks.setAdapter(adapter);

        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading();
                mPresenter.getList(workPlanIdx, decisionType);
            }
        });


        showLoading();
        mPresenter.getList(workPlanIdx, decisionType);

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
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "数据加载中，请稍后");

    }

    @Override
    public void hideLoading() {
        ProgressDialogUtils.dismissProgressDialog();
    }

    @Override
    public void searchSuccess(List<PerformanceTestEntity> results) {
        hideLoading();
        adapter.updateData(results);//检索的数据
    }

    @Override
    public void getListSuccess(List<PerformanceTestEntity> list, String msg) {
        etSearch.setText("");
        ignoreOnce = true;

        srl.finishRefresh();
        this.datas.clear();
        this.datas.addAll(list);
        adapter.updateData(mPresenter.filterList(this.datas, sbStatus.isChecked()));
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void getListFail(String errMsg) {
        hideLoading();
        srl.finishRefresh();
        ToastUtils.showShort("数据加载失败！" + errMsg);
    }

    @Override
    public void updateListItemSuccess(String msg) {
        ToastUtils.showShort(msg);
        srl.autoRefresh();
    }

    @Override
    public void updateListItemFail(String errMsg) {
        hideLoading();
        ToastUtils.showShort(errMsg);
    }

    @Override
    public boolean getIsJZGZ() {
        return decisionType == 1;//类型：1.加装改造，2性能试验
    }
}
