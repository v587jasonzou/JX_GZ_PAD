package com.yunda.gzjx.module.login.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.CacheDiskUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.yunda.gzjx.R;
import com.yunda.gzjx.app.utils.ProgressDialogUtils;
import com.yunda.gzjx.module.login.di.component.DaggerLoginComponent;
import com.yunda.gzjx.module.login.mvp.contract.LoginContract;
import com.yunda.gzjx.module.login.mvp.presenter.LoginPresenter;
import com.yunda.gzjx.view.ClearEditText;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * ================================================
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.usernameText)
    ClearEditText usernameText;
    @BindView(R.id.ivSpring)
    ImageView ivSpring;
    @BindView(R.id.ivDelete)
    ImageView ivDelete;
    @BindView(R.id.input_account)
    AutoLinearLayout inputAccount;
    @BindView(R.id.passwordText)
    ClearEditText passwordText;
    @BindView(R.id.rememberAccount)
    CheckBox rememberAccount;
    @BindView(R.id.settingTxv)
    TextView settingTxv;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.version)
    TextView version;

    PopupMenu popupMenu;
    List<String> users = new ArrayList<>();//历史登录用户


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArrayList<String> list = (ArrayList<String>) CacheDiskUtils.getInstance().getSerializable("users");
        if (list != null && list.size() > 0) {
            users.addAll(list);
        }

        usernameText.setText("wangdajun");
        passwordText.setText("000000");

        popupMenu = new PopupMenu(this, inputAccount);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                usernameText .setText(item.getTitle());
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(inputAccount.getWindowToken(), 0);
                }
                popupMenu.dismiss();
                return false;
            }
        });
    }

    @OnClick(R.id.loginBtn)
    void login() {
        if (usernameText.getText() == null || StringUtils.isTrimEmpty(usernameText.getText().toString())) {
            ToastUtils.showShort("请输入用户名！");
            return;
        }
        if (passwordText.getText() == null || StringUtils.isTrimEmpty(passwordText.getText().toString())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }
        showLoading();
        if (rememberAccount.isChecked()) {
            mPresenter.login(usernameText.getText().toString(), passwordText.getText().toString(), true);
        } else {
            mPresenter.login(usernameText.getText().toString(), passwordText.getText().toString(), false);
        }
    }

    @OnClick(R.id.ivSpring)
    void ShowPopup(){
        if(users.size()>0){
            Menu menu_more = popupMenu.getMenu();
            menu_more.clear();
            int size = users.size();
            for (int i = 0; i < size; i++) {
                menu_more.add(Menu.NONE, i, i, users.get(i));
            }
            popupMenu.show();
        }else {
            ToastUtils.showShort("无保存用户信息");
        }
    }

    @OnClick(R.id.settingTxv)
    void toSetting() {
//        ArmsUtils.startActivity(new Intent(this, ChangeServerActivity.class));
        ToastUtils.showShort("toSetting");
    }

    @Override
    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressDialogUtils.showProgressDialog(LoginActivity.this, "正在登录中，请稍后");
            }
        });
    }



    @Override
    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressDialogUtils.dismissProgressDialog();
            }
        });
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
//        ArmsUtils.snackbarText(message);
        ToastUtils.showShort(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);//整理activity
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void toMainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
    }
}
