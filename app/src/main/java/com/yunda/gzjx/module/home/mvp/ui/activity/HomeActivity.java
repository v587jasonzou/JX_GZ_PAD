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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import com.yunda.gzjx.app.utils.ProgressDialogUtils;
import com.yunda.gzjx.module.home.di.component.DaggerHomeComponent;
import com.yunda.gzjx.module.home.di.module.HomeModule;
import com.yunda.gzjx.module.home.mvp.adapter.HomeMenuAdapter;
import com.yunda.gzjx.module.home.mvp.contract.HomeContract;
import com.yunda.gzjx.module.home.mvp.presenter.HomePresenter;
import com.yunda.gzjx.module.hvTest.mvp.ui.activity.TrainTypeListActivity;
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

    public static String getCurRelationIdx() {
        return curRelationIdx;
    }

    private static String curRelationIdx = null;//首页当前选中的菜单项 - 其他界面会取值

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
                if (SysInfo.menus.get(position).getMenu().equals("高压试验")) {
                    if (data instanceof MenuSimpleBean) {
                        MenuSimpleBean menu = (MenuSimpleBean) data;
                        curRelationIdx = menu.getRelationIdx();
                        Intent intent = new Intent(HomeActivity.this, TrainTypeListActivity.class);
//                        intent.putExtra(BundleConstant.WORK_STATION_IDX, menu.getRelationIdx());//机车，对应工位idx
                        ArmsUtils.startActivity(intent);
                    }

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
        srRefresh.finishRefresh();
        ToastUtils.showShort(msg);

        // TODO: 2019/1/16 debug
        if (true) {
            String s ="[\n" + "        {\n" + "            \"createTime\": 1546963200000,\n" + "            \"idx\": \"14e6ebe3439f44e38ae3ef2ff76e444c\",\n" + "            \"relationType\": \"1 \",\n" + "            \"updateTime\": 1546963200000,\n" + "            \"updator\": \"wangdajun\",\n" + "            \"createrName\": \"王大军\",\n" + "            \"updatorName\": \"王大军\",\n" + "            \"creater\": \"wangdajun\",\n" + "            \"jxAppid\": \"2\",\n" + "            \"relationIdx\": \"c4208e93a2eb4027b13b8cb16d0854b2\",\n" + "            \"relationName\": \"高压试验\",\n" + "            \"menuUrl\": \"http://360\",\n" + "            \"menuName\": \"高压试验\"\n" + "        },\n" + "        {\n" + "            \"createTime\": 1546963200000,\n" + "            \"idx\": \"2d65f419da1c4e73872a744cb518a803\",\n" + "            \"relationType\": \"1 \",\n" + "            \"updateTime\": 1546963200000,\n" + "            \"updator\": \"wangdajun\",\n" + "            \"createrName\": \"王大军\",\n" + "            \"updatorName\": \"王大军\",\n" + "            \"creater\": \"wangdajun\",\n" + "            \"jxAppid\": \"2\",\n" + "            \"relationIdx\": \"03d8a3d0247f404f8b140d0f43e42725\",\n" + "            \"relationName\": \"低压试验\",\n" + "            \"menuUrl\": \"http://souhu.com\",\n" + "            \"menuName\": \"低压试验\"\n" + "        },\n" + "        {\n" + "            \"createTime\": 1546963200000,\n" + "            \"idx\": \"ab387afc1e89410995f9cdb48e6aac47\",\n" + "            \"relationType\": \"1 \",\n" + "            \"updateTime\": 1546963200000,\n" + "            \"updator\": \"wangdajun\",\n" + "            \"createrName\": \"王大军\",\n" + "            \"updatorName\": \"王大军\",\n" + "            \"creater\": \"wangdajun\",\n" + "            \"jxAppid\": \"2\",\n" + "            \"relationIdx\": \"68e9ddbeb3a74e63a6a9f53d072ce599\",\n" + "            \"relationName\": \"称重试验\",\n" + "            \"menuUrl\": \"cdsdscdscdacds\",\n" + "            \"menuName\": \"称重试验\"\n" + "        }\n" + "    ]";
            getMenuSuccess(new GsonBuilder().create().fromJson(s,new TypeToken<List<MenuSimpleBean>>(){}.getType()));
        }
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
