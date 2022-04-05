package com.example.mvvm_run1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.dao.UserDAO;
import com.example.mvvm_run1.model.User;
import com.example.mvvm_run1.viewmodel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText editText, editText2;
    TextView txtView, txtView2;
    Button resetButton;
    UserViewModel userViewModel;
    String input, newpass, matchpass, firstname, lastname, username;
    List<User> userList;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Intent getIntent = getIntent();
        userid = getIntent.getIntExtra("userid", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetButton = findViewById(R.id.btnReset);
        txtView = findViewById(R.id.textViewReset);
        txtView2 = findViewById(R.id.textViewPass);
        editText = findViewById(R.id.editTextReset);
        editText2 = findViewById(R.id.etPassword);

        ContentValues cv = new ContentValues();

        if (userid > 0) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            txtView.setText("Enter new password");
            editText.setText("");
            editText.setHint("New password");

            txtView2.setVisibility(View.VISIBLE);
            editText2.setVisibility(View.VISIBLE);

            resetButton.setText("Change password");

            userViewModel.getUsernameById(userid).observe(this, s -> username = s);
            userViewModel.getFirstNameById(userid).observe(this, s -> firstname = s);
            userViewModel.getLastNameById(userid).observe(this, s -> lastname = s);
        }

        resetButton.setOnClickListener(view -> {
            input = editText.getText().toString();
            userList = userViewModel.getAllUser();

            if (input.equals("")) {
                Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
            } else {
                if (userid > 0) {
                    newpass = editText.getText().toString();
                    matchpass = editText2.getText().toString();

                    for (int i = 0; i < userList.size(); i++) {
                        if (newpass.equals("") || matchpass.equals("")) {
                            Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
                        } else {
                            if (userList.get(i).getUserpass().equals(newpass)) {
                                Snackbar.make(findViewById(R.id.layout), "New password cannot be the same as current password.", Snackbar.LENGTH_SHORT).show();
                                break;
                            } else {
                                if (!(newpass.equals(matchpass))) {
                                    Snackbar.make(findViewById(R.id.layout), "Password does not match.", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    if (newpass.length() > 7) {
                                        userViewModel.updateUser(new User(username, firstname, lastname, newpass));
                                        Snackbar.make(findViewById(R.id.layout), "Password changed.", Snackbar.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Snackbar.make(findViewById(R.id.layout), "Password has to be 8 characters minimum.", Snackbar.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        }

                    }
                } else {
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getUsername().equals(input)) {
                            username = input;

                            txtView.setText("Enter first name");
                            editText.setText("");
                            editText.setHint("First name");

                            int finalI = i;

                            resetButton.setOnClickListener(view1 -> {
                                input = editText.getText().toString();
                                userList = userViewModel.getAllUser();

                                if (input.equals("")) {
                                    Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    if (userList.get(finalI).getFirstname().equals(input)) {
                                        firstname = input;

                                        txtView.setText("Enter last name");
                                        editText.setText("");
                                        editText.setHint("Last name");

                                        resetButton.setOnClickListener(view2 -> {
                                            input = editText.getText().toString();
                                            userList = userViewModel.getAllUser();

                                            if (input.equals("")) {
                                                Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
                                            } else {
                                                if (userList.get(finalI).getLastname().equals(input)) {
                                                    lastname = input;

                                                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                                    txtView.setText("Enter new password");
                                                    editText.setText("");
                                                    editText.setHint("New password");

                                                    txtView2.setVisibility(View.VISIBLE);
                                                    editText2.setVisibility(View.VISIBLE);

                                                    resetButton.setText("Change password");

                                                    resetButton.setOnClickListener(view3 -> {
                                                        newpass = editText.getText().toString();
                                                        matchpass = editText2.getText().toString();

                                                        if (newpass.equals("") || matchpass.equals("")) {
                                                            Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
                                                        } else {
                                                            if (userList.get(finalI).getUserpass().equals(newpass)) {
                                                                Snackbar.make(findViewById(R.id.layout), "New password cannot be the same as current password.", Snackbar.LENGTH_SHORT).show();
                                                            } else {
                                                                if (!(newpass.equals(matchpass))) {
                                                                    Snackbar.make(findViewById(R.id.layout), "Password does not match.", Snackbar.LENGTH_SHORT).show();
                                                                } else {
                                                                    if (newpass.length() > 7) {
                                                                        userViewModel.updateUser(new User(username, firstname, lastname, newpass));
                                                                        Snackbar.make(findViewById(R.id.layout), "Password changed. Please log in to continue.", Snackbar.LENGTH_SHORT).show();
                                                                        Intent intent2 = new Intent(ResetPasswordActivity.this, loginActivity.class);
                                                                        startActivity(intent2);
                                                                    } else {
                                                                        Snackbar.make(findViewById(R.id.layout), "Password has to be 8 characters minimum.", Snackbar.LENGTH_SHORT).show();
                                                                    }
                                                                }

                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Snackbar.make(findViewById(R.id.layout), "Input does not match with record on database.", Snackbar.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Snackbar.make(findViewById(R.id.layout), "Input does not match with record on database.", Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Snackbar.make(findViewById(R.id.layout), "Username does not exist.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}