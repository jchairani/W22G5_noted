package com.example.mvvm_run1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.model.User;
import com.example.mvvm_run1.viewmodel.UserViewModel;

import java.util.List;

public class loginActivity extends AppCompatActivity {
    UserViewModel userViewModel;
    EditText etUsername;
    EditText etPassword;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        login = findViewById(R.id.loginButton);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> userList = userViewModel.getAllUser();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                userViewModel.insertUser(new User("billie","aja"));

                for(int i=0;i<userList.size();i++){
                    if(userList.get(i).getUsername().equals(username) && userList.get(i).getUserpass().equals(password)){
                        Intent intent = new Intent(loginActivity.this, MainActivity.class);
                        intent.putExtra("userid",userList.get(i).getUserid());
                        startActivity(intent);

                    }
                }
            }
        });

    }
}