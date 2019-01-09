package com.yunda.gzjx.module.home.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunda.gzjx.R;
import com.yunda.gzjx.app.SysInfo;
import com.yunda.gzjx.module.home.di.component.DaggerHomeComponent;
import com.yunda.gzjx.module.home.di.module.HomeModule;
import com.yunda.gzjx.module.home.mvp.adapter.HomeMenuAdapter;
import com.yunda.gzjx.module.home.mvp.contract.HomeContract;
import com.yunda.gzjx.module.home.mvp.presenter.HomePresenter;
import com.yunda.gzjx.module.login.repository.entity.MenuSimpleBean;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * ================================================
 * 主页面(菜单页面)
 * ================================================
 */
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {

    @BindView(R.id.tvUserRole)
    TextView tvUserRole;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvEmpId)
    TextView tvEmpId;
    @BindView(R.id.tvChangeUser)
    TextView tvChangeUser;
    @BindView(R.id.rlMenus)
    RecyclerView rlMenus;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    HomeMenuAdapter adapter;
    boolean mBackKeyPressed = false;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder().appComponent(appComponent).homeModule(new HomeModule(this)).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ClassicsHeader header = new ClassicsHeader(this);
        header.setGravity(Gravity.CENTER);
        header.setEnableLastTime(false);
        srRefresh.setRefreshHeader(header);
        srRefresh.setEnableLoadMore(false);
        srRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    showLoading();
                    mPresenter.getMenus("mobileClient");
                }
            }
        });
        if (SysInfo.emp != null) {//登录接口返回,详情见登录接口
            if (SysInfo.emp.getEmpname() != null) {
                tvUsername.setText(SysInfo.emp.getEmpname());
            }
            if (SysInfo.emp.getEmpcode() != null) {
                tvEmpId.setText(SysInfo.emp.getEmpcode());
            }
            if (SysInfo.org != null) {
                if (SysInfo.org.getOrgname() != null) {
                    tvUserRole.setText(SysInfo.org.getOrgname());
                }
            }
        }
        ArmsUtils.configRecyclerView(rlMenus, new GridLayoutManager(HomeActivity.this, 3, OrientationHelper.VERTICAL, false));
        adapter = new HomeMenuAdapter(SysInfo.menus);
        rlMenus.setAdapter(adapter);
        if (mPresenter != null) {
            showLoading();
            mPresenter.getMenus("mobileClient");
        }
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                if (SysInfo.menus.get(position).getMenu().equals("配件下车登记")) {
                    //                    ArmsUtils.startActivity(new Intent(HomeActivity.this, PartsRecanditionActivity.class));
                } else if (SysInfo.menus.get(position).getMenu().equals("配件接收登记")) {
                    //                    ArmsUtils.startActivity(new Intent(HomeActivity.this, GetParstListActivity.class));
                } else if (SysInfo.menus.get(position).getMenu().equals("配件上车登记")) {
                    //                    ArmsUtils.startActivity(new Intent(HomeActivity.this, UpPartsListActivity.class));
                } else if (SysInfo.menus.get(position).getMenu().equals("大部件拆解清单登记")) {
                    //                    ArmsUtils.startActivity(new Intent(HomeActivity.this, BigPartsListActivity.class));
                } else if (SysInfo.menus.get(position).getMenu().equals("大部件组装清单登记")) {
                    //                    ArmsUtils.startActivity(new Intent(HomeActivity.this, AssemblyBigPartsListActivity.class));
                }

            }
        });
    }

    @Override
    public void showLoading() {
        //ProgressDialogUtils.showProgressDialog(this, "加载中...");
    }

    @Override
    public void hideLoading() {
//        ProgressDialogUtils.dismissProgressDialog();
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

    @OnClick(R.id.tvChangeUser)
    void ChangeUser() {

    }

    @Override
    public void getMenuSuccess(List<MenuSimpleBean> menus) {
        hideLoading();
        srRefresh.finishRefresh();
        SysInfo.menus.clear();
        if (menus != null && menus.size() > 0) {
            SysInfo.menus.addAll(menus);
        }
        adapter.notifyDataSetChanged();
        ToastUtils.showShort("获取权限成功");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SysInfo.cookieStore.clear();
    }

    @OnClick(R.id.tvChangeUser)
    void CheckUser() {
        new AlertDialog.Builder(this).setTitle("提示！").setMessage("确定退出当前用户？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("取消", null).show();
    }

    @Override
    public void OnLoadFaild(String msg) {
        hideLoading();
        ToastUtils.showShort(msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!mBackKeyPressed) {
                Toast.makeText(this, "再按一次退出主页", Toast.LENGTH_SHORT).show();
                mBackKeyPressed = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mBackKeyPressed = false;
                    }
                }, 2000);
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}