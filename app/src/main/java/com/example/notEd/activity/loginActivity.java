package com.example.notEd.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notEd.R;
import com.example.notEd.model.User;
import com.example.notEd.viewmodel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class loginActivity extends AppCompatActivity {
    UserViewModel userViewModel;
    EditText etUsername, etPassword;
    Button login;
    TextView signup, reset;
    CheckBox checkBox;
    String username, password;
    List<User> userList;
    SharedPreferences sharedpreferences;
    int snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //disable nightmode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        boolean finish = getIntent().getBooleanExtra("finish", false);
        if (finish) {
            startActivity(new Intent(getApplicationContext(), loginActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.txtViewSignup);
        reset = findViewById(R.id.txtForgotPassword);
        checkBox = findViewById(R.id.cbSignIn);

        sharedpreferences = getApplicationContext().getSharedPreferences("Preferences", 0);
        String keepSigned = sharedpreferences.getString("LOGIN", null);
        int keepID = sharedpreferences.getInt("ID", 0);

        if (keepSigned != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("userid", keepID);
            startActivity(intent);
        }

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Intent getIntent = getIntent();
        snackbar = getIntent.getIntExtra("snackbar", 0);

        if (snackbar == 1) {
            Snackbar.make(findViewById(android.R.id.content), "Password changed.\nPlease log in to continue.", Snackbar.LENGTH_LONG).setAction("CLOSE", view12 -> {
            }).setActionTextColor(getResources().getColor(android.R.color.white)).show();
        }

        //store the state of checkbox in shared preferences
        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("LOGIN", "keepSigned");
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

        login.setOnClickListener(view -> {

            userList = userViewModel.getAllUser();
            username = etUsername.getText().toString();
            password = etPassword.getText().toString();

            if (username.equals("") || password.equals("")) {
                Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
            } else {
                if (userList.isEmpty()) {
                    Snackbar.make(findViewById(R.id.layout), "Username does not exist.", Snackbar.LENGTH_SHORT).show();
                    ;
                } else {
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getUsername().equals(username) && userList.get(i).getUserpass().equals(password)) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("ID", userList.get(i).getUserid());
                            editor.commit();

                            Intent intent = new Intent(loginActivity.this, MainActivity.class);
                            intent.putExtra("userid", userList.get(i).getUserid());
                            startActivity(intent);
                        } else if (userList.get(i).getUsername().equals(username) && !(userList.get(i).getUserpass().equals(password))) {
                            Snackbar.make(findViewById(R.id.layout), "Wrong password.", Snackbar.LENGTH_SHORT).show();
                        } else if (!usernameCheck(username)) {
                            Snackbar.make(findViewById(R.id.layout), "Username does not exist.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
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