package com.yunda.gzjx.module.settings.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.yunda.gzjx.R;
import com.yunda.gzjx.constant.ApiConstant;
import com.yunda.gzjx.module.settings.di.component.DaggerSettingComponent;
import com.yunda.gzjx.module.settings.mvp.contract.SettingContract;
import com.yunda.gzjx.module.settings.mvp.presenter.SettingPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/09/2019 17:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etServerAddress)
    EditText etServerAddress;
    @BindView(R.id.cvChangeServer)
    CardView cvChangeServer;

    @Inject
    OkHttpClient okHttpClient;
    @Inject
    IRepositoryManager repositoryManager;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingComponent //如找不到该类,请编译一下项目
                .builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_change_server; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String url = SPUtils.getInstance().getString("BaseUrl");
        if (!StringUtils.isTrimEmpty(url)) {
            etServerAddress.setText(url);
        } else {
            etServerAddress.setText(ApiConstant.BaseUrl);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.change_server_bar_menu, menu);
        //        menu.getItem(0).setTitle("设备巡检");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.iv_right) {
            if (etServerAddress.getText() == null || etServerAddress.getText().toString().equals("")) {
                ToastUtils.showShort("还未输入地址");
                return false;
            }
            String url = etServerAddress.getText().toString();
            String[] urls = url.split(":");
            if (urls == null || !urls[0].contains("http")) {
                url = "http://" + url;
            }
            SPUtils.getInstance().put("BaseUrl", url);
            if (url.charAt(url.length() - 1) != '/') {
                url = url + "/";
            }
            HttpUrl apiUrl = HttpUrl.parse(url);
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .serializeNulls()
                    .create();

            repositoryManager.setRetrofit(new Retrofit.Builder().baseUrl(apiUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build());
            ToastUtils.showShort("修改成功");
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
