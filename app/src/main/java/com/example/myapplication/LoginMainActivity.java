package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class LoginMainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private TextView tv_password;
    private EditText et_password;
    private Button btn_forget;
    private CheckBox ck_remember;
    private EditText et_phone;
    private RadioButton rb_password;
    private RadioButton rb_verifycode;
    private ActivityResultLauncher<Intent> register;
    private Button btn_login;
    private String mPassword = "111111";
    private String mVerifyCode = "111111";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_main);
        RadioGroup rb_login = findViewById(R.id.rg_login);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);
        et_phone = findViewById(R.id.et_phone);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        //monitor for forget password
        btn_forget.setOnClickListener(this);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        rb_login.setOnCheckedChangeListener(this);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_password){
            tv_password.setText(getString(R.string.login_password));
            et_password.setHint(getString(R.string.input_password));
            btn_forget.setText(getString(R.string.forget_password));
            ck_remember.setVisibility(View.VISIBLE);
        }
        else {
            tv_password.setText(getString(R.string.verifycode));
            et_password.setHint(getString(R.string.input_verifycode));
            btn_forget.setText(getString(R.string.get_verifycode));
            ck_remember.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();

        if (v.getId() == R.id.btn_login && phone.length() < 11) {
            Toast.makeText(this, "Please enter the right phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (v.getId() == R.id.btn_forget) {
            if (rb_password.isChecked()) {

                Intent intent = new Intent(this, LoginForgetActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent);
            } else if (rb_verifycode.isChecked()) {

                mVerifyCode = String.format("%06d", new Random().nextInt(999999));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Please remember your verifycode");
                builder.setMessage("Phone: " + phone + " , the verifycode is: " + mVerifyCode);
                builder.setPositiveButton("OK", null);
                builder.create().show();
            }
        } else if (v.getId() == R.id.btn_login) {
            if (rb_password.isChecked()) {
                if (!mPassword.equals(et_password.getText().toString())) {
                    Toast.makeText(this, "Please enter the correct password", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginSuccess();
            } else if (rb_verifycode.isChecked()) {
                if (!mVerifyCode.equals(et_password.getText().toString())) {
                    Toast.makeText(this, "Please enter the correct verifycode", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginSuccess();
            }
        }
    }


    private void loginSuccess() {
        String desc = String.format("Phone %s, login successfully, press 'OK' to get back to the previous page",et_phone.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("login success!");
        builder.setMessage(desc);
        builder.setPositiveButton("OK", (dialog, which) -> {
            Intent intent = new Intent(this, My_account.class);
            intent.putExtra("phone", et_phone.getText().toString()); // 传递手机号
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("cancel",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}