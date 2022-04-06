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
    int snackbar;

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

        Intent getIntent = getIntent();
        snackbar = getIntent.getIntExtra("snackbar", 0);

        if (snackbar == 1) {
            Snackbar.make(findViewById(android.R.id.content), "Password changed.\nPlease log in to continue.", Snackbar.LENGTH_LONG).setAction("CLOSE", view12 -> {
            }).setActionTextColor(getResources().getColor(android.R.color.white)).show();
        }

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
            intent.putExtra("fromLogin", 1);
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
                    if (userList.isEmpty()) {
                        Snackbar.make(findViewById(R.id.layout), "Username does not exist.", Snackbar.LENGTH_SHORT).show();;
                    } else {
                        for (int i = 0; i < userList.size(); i++) {
                            if (userList.get(i).getUsername().equals(username) && userList.get(i).getUserpass().equals(password)) {
                                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                                intent.putExtra("userid", userList.get(i).getUserid());
                                startActivity(intent);
                            } else if (userList.get(i).getUsername().equals(username) && !(userList.get(i).getUserpass().equals(password))) {
                                Snackbar.make(findViewById(R.id.layout), "Wrong password.", Snackbar.LENGTH_SHORT).show();
                            } else if (usernameCheck(username)==false) {
                                Snackbar.make(findViewById(R.id.layout), "Username does not exist.", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }
    }

    public boolean usernameCheck(String username) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}