package com.example.dailylist.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.alibaba.fastjson.JSONObject;
import com.example.dailylist.Bean.UserBean;
import com.example.dailylist.MainActivity;
import com.example.dailylist.R;
import com.example.dailylist.UserConfig;
import com.example.dailylist.utils.Api.UserApi;

import java.util.concurrent.*;

/**
 * @Author: 夜雨
 * @ClassName: LoginFragment
 * @Description: TODO
 * @Date: 2022/12/2 13:52
 * @Version: 1.0
 */

public class LoginActivity extends Activity {
    Context context;
    private EditText etUsername, etPassword;
    private Button btnLogin;

    private TextView tvRegister, tvFindPsw;
    private ExecutorService threadPool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initViews();
        initEvents();
    }

    private void initViews() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        context = getApplicationContext();
        tvRegister = findViewById(R.id.tv_register);
        tvFindPsw = findViewById(R.id.tv_find_psw);

        threadPool = new ThreadPoolExecutor(2, 5,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    private void initEvents() {
        btnLogin.setOnClickListener(v -> {
            UserBean userBean = new UserBean();
            userBean.setUsername(etUsername.getText().toString());
            userBean.setPassword(etPassword.getText().toString());
            login(userBean);
        });
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        tvFindPsw.setOnClickListener(v -> {
            Log.i("heiye", "忘记密码");
        });

    }

    private void login(UserBean userBean) {

        threadPool.execute(() -> {
            JSONObject response = UserApi.login(userBean);
            if (response == null) {
                runOnUiThread(() -> {
                    Toast.makeText(context, "服务器连接失败", Toast.LENGTH_LONG).show();
                });
            } else if (response.getInteger("code") == 0) {
                UserConfig.token = response.getJSONObject("result").getString("token");
                UserConfig.uid = response.getJSONObject("result").getString("id");
                Log.i("uid", response.getJSONObject("result").getString("id"));
                runOnUiThread(() -> {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(context, response.getString("message"), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

}
