package com.yunda.gzjx.module.hvTest.mvp.ui.activity;

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
import com.yunda.gzjx.module.hvTest.di.component.DaggerJXTasksOfProjectComponent;
import com.yunda.gzjx.module.hvTest.entry.JXTask;
import com.yunda.gzjx.module.hvTest.mvp.contract.JXTasksOfProjectContract;
import com.yunda.gzjx.module.hvTest.mvp.presenter.JXTasksOfProjectPresenter;
import com.yunda.gzjx.module.hvTest.mvp.ui.adapter.JXTasksAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 *（检修记录列表页面）
 * @author 邹旭
 * ================================================
 */
public class JXTasksOfProjectActivity extends BaseActivity<JXTasksOfProjectPresenter> implements JXTasksOfProjectContract.View {
    @BindView(R.id.menu)
    Toolbar menuTp;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tasks)
    RecyclerView rvTasks;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    private List<JXTask> jxTasks = new ArrayList<>();//接口请求的数据
    private JXTasksAdapter adapter;
    private String workCardIdx;//作业项目idx;
    private boolean isSaveAll = false;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerJXTasksOfProjectComponent //如找不到该类,请编译一下项目
                .builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_jxtasks_of_project; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        workCardIdx = getIntent().getStringExtra("workCardIdx");
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        adapter = new JXTasksAdapter(this,mPresenter,jxTasks);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
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
                Toast.makeText(this, "保存所有", Toast.LENGTH_SHORT).show();

                List<JXTask> updates = new ArrayList<>();
                for (JXTask jxTask : adapter.getInfos()) {
                    if (jxTask.isSaveLaterChecked) {
                        updates.add(jxTask);
                    }
                }
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
        // TODO: 2019/1/15 删掉模拟数据
//        if (false) {//模拟数据
//            hideLoading();
//            srl.finishRefresh();
//            String s = "[{\"idx\":\"B7A7365512C14C1B9D8D5F3D7680184C\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"制动监测子系统试验：\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"C4BC0E4DE2454329A273BDD135AA572F\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"确保机车在试验之前泄露试验良好，停放缸压力传感器，均衡缸压力传感器，折角塞门关闭流量传感器安装良好\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"30060C4C532B463EA9D7DF0ADD7BFC21\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"机车进行紧急制动试验时，列车管彻底减压后，在6A 音视频显示终端，列车管压力为0kPa（参考值大于等于0kPa、小于等于4kPa）。\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"C17E65D574AB40B0A075A9DBD2107FCF\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"机车进行紧急制动试验时，均衡缸彻底减压后，在6A 音视频显示终端，均衡缸压力为0kPa（参考值大于等于0kPa、小于等于4kPa）。\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"04AE556037F145F58D6801F382919D0E\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"机车无充排风时，在6A 音视频显示终端界面，当前状态为“无充排风”，当前流量为0，流量计状态“正常”，贯通辆数状态为“----”\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"79C9C11AA2614727868D4559B7DC3812\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"将机车自阀移至运转位，将列车制动管风压充至定压（500kPa或600kPa）并稳定后，自阀减压140Kpa或170Kpa，查看并记录音视频显示终端【监控数据】【制动】界面显示的贯通辆数，应为0-3；\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"3A18243A43DF45BFA8BD04397BC02C29\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"减压操作并显示贯通辆数10s后，自阀再充风至定压并稳定，查看并记录音视频显示终端【监控数据】【制动】界面显示的贯通辆数，应为0-3。\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"F25689B15C754B27972AAC4991807E02\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"视频监控子系统\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"5987E829401641959926F7A058C93DCD\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"在音视频显示终端【视频图像】界面能够看到各摄像头监控区域的视频图像。\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"E6A7793F0CF544DC8A4DFD20F8B5C19B\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"在【视频图像】界面点击【单画面】，依次查看各通道视频信号，应清晰稳定无闪烁。\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"D7FCBB56E98D4D6BAE3F4E45053596EE\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"在【视频图像】界面点击【四画面】，查看各通道的视频信号，应清晰稳定无闪烁。\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"142497EFC5D74F069252489B66A4FD96\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"在音视频显示终端的主界面无摄像头故障信息。\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"1E5D49C0AC374C26A2C9BB132B33EAA0\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"绝缘检测箱试验\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"6C2B5E7DA2B94DAA9D68B42859239FA6\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"确认机车状态完好；6A 系统供电正常；机车电钥匙在0 位；\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"223460949E7146EBBFF0C113F094DC62\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"使用机车蓝钥匙，打开绝缘检测箱的电源，系统进入自检状态，一分钟内自检完成，系统应无故障发生。\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"EE02F34307A748B8A36EF567A16D22E7\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"点击【出库检测】按钮，绝缘箱能够正常开始检测，若车顶绝缘状态不良，则应绝缘报警灯亮起，否则不亮。\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"1A78D04FF5384905ABF735F7F2E2FC1E\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"走行监测子系统试验\",\"remarks\":\" \",\"detectResultList\":[]},{\"idx\":\"E881AE0029AE42E9B1A8ADFAC5DB8AD5\",\"workEmpId\":\" \",\"status\":\"10\",\"qualityList\":[],\"workEmpName\":\" \",\"repairContent\":\"在音视频显示终端【监控数据】【走行1】界面看到数据接收时间更新，无传感器开路显示。\",\"remarks\":\" \",\"detectResultList\":[]}]";
//            List<JXTask> jxTasks = new GsonBuilder().create().fromJson(s,new TypeToken<List<JXTask>>(){}.getType());
//            this.jxTasks.clear();
//            this.jxTasks.addAll(jxTasks);
//            adapter.notifyDataSetChanged();
//            return;
//        }

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
//        adapter.notifyDataSetChanged();
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
