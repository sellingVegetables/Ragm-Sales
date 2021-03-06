package com.sooft_sales.uis.activity_login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.sooft_sales.R;
import com.sooft_sales.databinding.ActivityLoginBinding;
import com.sooft_sales.language.Language;
import com.sooft_sales.model.LoginModel;
import com.sooft_sales.model.UserSettingsModel;
import com.sooft_sales.mvvm.ActivityLoginMvvm;
import com.sooft_sales.preferences.Preferences;
import com.sooft_sales.share.Common;
import com.sooft_sales.uis.activity_base.BaseActivity;
import com.sooft_sales.uis.activity_home.HomeActivity;

import io.paperdb.Paper;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private LoginModel model;
    private ActivityLoginMvvm activityLoginMvvm;
    private UserSettingsModel userSettingsModel;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }


    private void initView() {
        preferences = Preferences.getInstance();
        userSettingsModel = new UserSettingsModel();
        userSettingsModel.setIs_first(true);
        preferences.create_update_user_settings(this, userSettingsModel);
        activityLoginMvvm = ViewModelProviders.of(this).get(ActivityLoginMvvm.class);
        activityLoginMvvm.onLoginSuccess().observe(this, userModel -> {
            setUserModel(userModel);
            navigateToHomActivity();
        });

        model = new LoginModel();
        binding.setModel(model);
        model.setLang(getLang());
        if (getLang().equals("ar")) {
            binding.spinner.setSelection(0);
        } else {
            binding.spinner.setSelection(1);
        }
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                    if (getLang().equals("en")) {
                        model.setLang("ar");

                        refreshActivity(model.getLang());
                    }
                } else {
                    if (getLang().equals("ar")) {
                        model.setLang("en");

                        refreshActivity(model.getLang());
                    }
                }
                binding.setModel(model);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btLogin.setOnClickListener(v -> {
            if (model.isDataValid(this)) {
                Common.CloseKeyBoard(this, binding.edPassword);
                activityLoginMvvm.login(this, model);
            }
        });


    }

    private void navigateToHomActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    public void refreshActivity(String lang) {
        Paper.book().write("lang", lang);
        Language.setNewLocale(this, lang);
        new Handler()
                .postDelayed(() -> {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 500);


    }

}