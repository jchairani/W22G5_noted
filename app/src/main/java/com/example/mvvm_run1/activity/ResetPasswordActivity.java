package com.example.mvvm_run1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.model.User;
import com.example.mvvm_run1.viewmodel.UserViewModel;

import java.util.List;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button resetButton;
    UserViewModel userViewModel;
    List<User> users;
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