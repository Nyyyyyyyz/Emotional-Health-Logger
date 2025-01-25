package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityLoginForgetBinding;

import java.util.Random;

public class LoginForgetActivity extends AppCompatActivity implements View.OnClickListener {

    private String mPhone;
    private String mVerifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
        mPhone = getIntent().getStringExtra("phone");
        findViewById(R.id.btn_verifycode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (R.id.btn_verifycode == v.getId()){
            mVerifyCode = String.format("%06d",new Random().nextInt(999999));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please remember your verifycode");
            builder.setMessage("Phone "+mPhone+" ,the verifycode is "+ mVerifyCode+" Please enter verifycode");
            builder.setPositiveButton("OK",null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}