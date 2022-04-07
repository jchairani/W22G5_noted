package com.example.mvvm_run1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.model.User;
import com.example.mvvm_run1.viewmodel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class SignupActivity extends AppCompatActivity {
    EditText username, password, repassword, firstname, lastname;
    String user, pass, repass, fname, lname;
    Button signup;
    List<User> userList;
    UserViewModel userViewModel;

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

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        setupFloatingLabelError();

        signup.setOnClickListener(view -> {
            userList = userViewModel.getAllUser();
            user = username.getText().toString();
            pass = password.getText().toString();
            repass = repassword.getText().toString();
            fname = firstname.getText().toString();
            lname = lastname.getText().toString();

            if (user.equals("") || pass.equals("") || repass.equals("")) {
                Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
            } else {
                if (usernameCheck(user)==true) {
                    Snackbar.make(findViewById(R.id.layout), "Username is taken.", Snackbar.LENGTH_SHORT).show();
                }
                else if (pass.length() > 7) {
                    if (pass.equals(repass)) {
                        Snackbar.make(findViewById(R.id.layout), "Successfully registered.", Snackbar.LENGTH_SHORT).show();
                        userViewModel.insertUser(new User(user, fname, lname, pass));

                        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        boolean b = true;
                        editor.putBoolean("isChecked", b);
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("userid", userViewModel.getIdByUsername(user));
                        startActivity(intent);


                    } else {
                        Snackbar.make(findViewById(R.id.layout), "Password does not match.", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(findViewById(R.id.layout), "Password has to be 8 characters minimum.", Snackbar.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void setupFloatingLabelError() {
        final TextInputLayout floatingPassword = (TextInputLayout) findViewById(R.id.floatingPassword);
        floatingPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() < 8) {
                    floatingPassword.setError("Password has to be 8 characters minimum.");
                    floatingPassword.setErrorEnabled(true);
                } else {
                    floatingPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final TextInputLayout floatingPasswordConfirm = (TextInputLayout) findViewById(R.id.floatingPasswordConfirm);
        floatingPasswordConfirm.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() < 8) {
                    floatingPasswordConfirm.setError("Password has to be 8 characters minimum.");
                    floatingPasswordConfirm.setErrorEnabled(true);
                } else {
                    floatingPasswordConfirm.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public boolean usernameCheck(String username) {
        for (int i = 0; i < userList.size(); i++) {
            if ((userList.get(i).getUsername().equals(username))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignupActivity.this, loginActivity.class);
        startActivity(intent);
    }

}