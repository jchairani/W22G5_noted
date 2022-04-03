package com.example.mvvm_run1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.model.User;
import com.example.mvvm_run1.viewmodel.UserViewModel;

import java.util.List;

public class SignupActivity extends AppCompatActivity {
    EditText username, password, repassword, firstname, lastname;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        repassword = findViewById(R.id.inputPasswordConfirm);
        firstname = findViewById(R.id.inputFirstName);
        lastname = findViewById(R.id.inputLastName);
        signup = findViewById(R.id.btnSignup);

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        signup.setOnClickListener(view -> {
            List<User> userList = userViewModel.getAllUser();
            String user = username.getText().toString();
            String pass = password.getText().toString();
            String repass = repassword.getText().toString();
            String fname = firstname.getText().toString();
            String lname = lastname.getText().toString();

            if (user.equals("") || pass.equals("") || repass.equals("")) {
                Toast.makeText(SignupActivity.this, "Input cannot be empty. Please input username or password correctly.", Toast.LENGTH_SHORT).show();
            } else {
                if (pass.equals(repass)) {
                    if (pass.length() > 7) {
                        Toast.makeText(SignupActivity.this, "Successfully registered.", Toast.LENGTH_SHORT).show();

                        userViewModel.insertUser(new User(user, fname, lname, pass));

                        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        boolean b = true;
                        editor.putBoolean("isChecked", b);
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("userid", userList.size() + 1);
                        startActivity(intent);


                    } else {
                        Toast.makeText(SignupActivity.this, "Password has to be 8 characters minimum.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Password does not match! Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignupActivity.this, loginActivity.class);
        startActivity(intent);
    }

}