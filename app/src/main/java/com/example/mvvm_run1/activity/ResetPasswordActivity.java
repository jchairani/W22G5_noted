package com.example.mvvm_run1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mvvm_run1.R;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ResetPasswordActivity.this, loginActivity.class);
        startActivity(intent);
    }
}