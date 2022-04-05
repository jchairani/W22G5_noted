package com.example.mvvm_run1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.database.NoteDatabase;
import com.example.mvvm_run1.model.User;
import com.example.mvvm_run1.viewmodel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class loginActivity extends AppCompatActivity {
    UserViewModel userViewModel;
    EditText etUsername, etPassword;
    Button login;
    TextView signup, reset;
    CheckBox checkBox;
    boolean isChecked;
    String username, password;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.txtViewSignup);
        reset = findViewById(R.id.txtForgotPassword);
        checkBox = findViewById(R.id.cbSignIn);
        isChecked = false;

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //store the state of checkbox in shared preferences
        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isChecked", b);
            editor.commit();
        });

        signup.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
        });

        reset.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
            startActivity(intent);
        });

        if (isChecked) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            login.setOnClickListener(view -> {

                userList = userViewModel.getAllUser();
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getUsername().equals(username) && userList.get(i).getUserpass().equals(password)) {
                            Intent intent = new Intent(loginActivity.this, MainActivity.class);
                            intent.putExtra("userid", userList.get(i).getUserid());
                            startActivity(intent);
                        }
                        if (userList.get(i).getUsername().equals(username) && !(userList.get(i).getUserpass().equals(password))) {
                            Snackbar.make(findViewById(R.id.layout), "Wrong password.", Snackbar.LENGTH_SHORT).show();
                            break;
                        }
                        if(!(userViewModel.getUsernameById(i).equals(username))){
                            Snackbar.make(findViewById(R.id.layout), "Username does not exist.", Snackbar.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            });
        }

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (isChecked) {
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    List<User> userList = userViewModel.getAllUser();
//                    String username = etUsername.getText().toString();
//                    String password = etPassword.getText().toString();
//
//                    for (int i = 0; i < userList.size(); i++) {
//                        if (etUsername.equals("") || etPassword.equals("")) {
//                            Toast.makeText(loginActivity.this, "Input cannot be empty. Please input username or password correctly.", Toast.LENGTH_SHORT).show();
//                        } else {
//                            if (!(userList.get(i).getUsername().equals(username))) {
//                                Toast.makeText(loginActivity.this, "Username does not exist. Please create an account.", Toast.LENGTH_SHORT).show();
//                            } else {
//                                if (userList.get(i).getUsername().equals(username) && !(userList.get(i).getUserpass().equals(password))) {
//                                    Toast.makeText(loginActivity.this, "Wrong password.", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    if (userList.get(i).getUsername().equals(username) && userList.get(i).getUserpass().equals(password)) {
//                                        Toast.makeText(loginActivity.this, "Successfully logged in.", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(loginActivity.this, MainActivity.class);
//                                        intent.putExtra("userid", userList.get(i).getUserid());
//                                        startActivity(intent);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
////                List<User> userList = userViewModel.getAllUser();
////                String username = etUsername.getText().toString();
////                String password = etPassword.getText().toString();
//
////                for(int i=0;i<userList.size();i++){
////                    if(userList.get(i).getUsername().equals(username) && userList.get(i).getUserpass().equals(password)){
////                        Intent intent = new Intent(loginActivity.this, MainActivity.class);
////                        intent.putExtra("userid",userList.get(i).getUserid());
////                        startActivity(intent);
////                    }
////                }
//
//            }
//        });

    }
}