package com.example.dailylist.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.alibaba.fastjson.JSONObject;
import com.example.dailylist.Bean.UserBean;
import com.example.dailylist.R;
import com.example.dailylist.utils.Api.UserApi;

import java.util.concurrent.*;

/**
 * @Author 夜雨
 * @ClassName RegisterFragment
 * @Description TODO
 * @Date 2022/12/2 21:19
 * @Version 1.0
 */

public class RegisterActivity extends Activity {

    Context context;
    private int index = 0;
    private EditText etUsername, etPassword1, etPassword2;
    private Button btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        initViews();
        initEvents();
    }


    private void initViews() {
        etUsername = findViewById(R.id.et_username);
        etPassword1 = findViewById(R.id.et_password1);
        etPassword2 = findViewById(R.id.et_password2);
        btnNext = findViewById(R.id.btn_next);
        context = getApplicationContext();
    }

    private void initEvents() {
        btnNext.setOnClickListener(v -> {
            switch (index) {
                case 0:
                case 1: {
                    index++;
                    setVisibility();
                    break;
                }
                case 2: {
                    String username = etUsername.getText().toString();
                    String password1 = etPassword1.getText().toString();
                    String password2 = etPassword2.getText().toString();
                    etPassword1.setText("");
                    etPassword2.setText("");
                    if (password1.equals(password2)) {
                        index = 0;
                        etUsername.setText("");
                        setVisibility();
                        UserBean userBean = new UserBean();
                        userBean.setUsername(username);
                        userBean.setPassword(password1);
                        register(userBean);
                    } else {
                        index = 1;
                        Toast.makeText(context, "两次密码不匹配", Toast.LENGTH_LONG).show();
                        setVisibility();
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        });
    }


    private void register(UserBean userBean) {
        ExecutorService threadPool = new ThreadPoolExecutor(2, 5,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        threadPool.execute(() -> {
            JSONObject reponse = UserApi.register(userBean);
            if (reponse.getInteger("code") == 0) {
                runOnUiThread(() -> {
                    Toast.makeText(context, reponse.getString("message"), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(context, reponse.getString("message"), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void setVisibility() {
        switch (index) {
            case 0: {
                etUsername.setVisibility(View.VISIBLE);
                etPassword1.setVisibility(View.GONE);
                etPassword2.setVisibility(View.GONE);
                break;
            }
            case 1: {
                etUsername.setVisibility(View.GONE);
                etPassword1.setVisibility(View.VISIBLE);
                etPassword2.setVisibility(View.GONE);
                break;
            }
            case 2: {
                etUsername.setVisibility(View.GONE);
                etPassword1.setVisibility(View.GONE);
                etPassword2.setVisibility(View.VISIBLE);
                break;
            }
            default: {
                break;
            }
        }
    }

}
