package com.example.mvvm_run1.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.model.User;
import com.example.mvvm_run1.viewmodel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class UpdateAccountActivity extends AppCompatActivity {
    int userid, position;
    Button btnUpdate;
    EditText etFirstName, etLastName, etUsername;
    UserViewModel userViewModel;
    List<User> userList;
    String username, firstname, lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        etUsername = findViewById(R.id.inputUsername);
        etFirstName = findViewById(R.id.inputFirstName);
        etLastName = findViewById(R.id.inputLastName);
        btnUpdate = findViewById(R.id.btnUpdate);

        userList = userViewModel.getAllUser();

        Intent getIntent = getIntent();
        userid = getIntent.getIntExtra("userid", 0);

        try {
            position = getIndex(userid);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

//        username = userList.get(position).getUsername();
//        firstname = userList.get(position).getFirstname();
//        lastname = userList.get(position).getLastname();

//        etUsername.setText(username);
//        etFirstName.setText(firstname);
//        etLastName.setText(lastname);

        userViewModel.getUsernameById(userid).observe(this, s -> username = s);
        userViewModel.getFirstNameById(userid).observe(this, s -> firstname = s);
        userViewModel.getLastNameById(userid).observe(this, s -> lastname = s);

        userViewModel.getUsernameById(userid).observe(this, s -> etUsername.setText(s));
        userViewModel.getFirstNameById(userid).observe(this, s -> etFirstName.setText(s));
        userViewModel.getLastNameById(userid).observe(this, s -> etLastName.setText(s));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etUsername.equals("") || etFirstName.equals("") || etLastName.equals("")) {
                    Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
                } else {
                    userList = userViewModel.getAllUser();
                    if (etFirstName.getText().toString().matches(".*\\d.*") || etLastName.getText().toString().matches(".*\\d.*")) {
                        Snackbar.make(findViewById(R.id.layout), "Name contains number!\nPlease input alphabetical values.", Snackbar.LENGTH_SHORT).show();
                    }
                    if (!(etUsername.getText().toString().equals(username)) && usernameCheck(etUsername.getText().toString(), position) == true) {
                        Snackbar.make(findViewById(R.id.layout), "Username is taken.", Snackbar.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateAccountActivity.this);

                        if (!(etUsername.getText().toString().equals(username))) {
                            builder.setMessage("Do you want to change your username to " + etUsername.getText().toString() + "?");
                            builder.setCancelable(true);

                            builder.setPositiveButton(
                                    "Yes",
                                    (dialog, id) -> {
                                        dialog.cancel();
                                        userViewModel.changeUsername(userid, etUsername.getText().toString());
                                        userViewModel.changeFirstName(userid, etFirstName.getText().toString());
                                        userViewModel.changeLastName(userid, etLastName.getText().toString());
                                        Snackbar.make(findViewById(android.R.id.content), "Account information updated.", Snackbar.LENGTH_SHORT).show();
                                    });

                            builder.setNegativeButton(
                                    "No",
                                    (dialog, id) -> {
                                        dialog.cancel();
                                    });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "No changes made.", Snackbar.LENGTH_SHORT).show();
                        }

                        if (!(etFirstName.getText().toString().equals(firstname))) {
                            builder.setMessage("Do you want to change your first name to " + etFirstName.getText().toString() + "?");
                            builder.setCancelable(true);

                            builder.setPositiveButton(
                                    "Yes",
                                    (dialog, id) -> {
                                        dialog.cancel();
                                        userViewModel.changeUsername(userid, etUsername.getText().toString());
                                        userViewModel.changeFirstName(userid, etFirstName.getText().toString());
                                        userViewModel.changeLastName(userid, etLastName.getText().toString());
                                        Snackbar.make(findViewById(android.R.id.content), "Account information updated.", Snackbar.LENGTH_SHORT).show();
                                    });

                            builder.setNegativeButton(
                                    "No",
                                    (dialog, id) -> {
                                        dialog.cancel();
                                    });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "No changes made.", Snackbar.LENGTH_SHORT).show();
                        }

                        if (!(etLastName.getText().toString().equals(lastname))) {
                            builder.setMessage("Do you want to change your last name to " + etLastName.getText().toString() + "?");
                            builder.setCancelable(true);

                            builder.setPositiveButton(
                                    "Yes",
                                    (dialog, id) -> {
                                        dialog.cancel();
                                        userViewModel.changeUsername(userid, etUsername.getText().toString());
                                        userViewModel.changeFirstName(userid, etFirstName.getText().toString());
                                        userViewModel.changeLastName(userid, etLastName.getText().toString());
                                        Snackbar.make(findViewById(android.R.id.content), "Account information updated.", Snackbar.LENGTH_SHORT).show();
                                    });

                            builder.setNegativeButton(
                                    "No",
                                    (dialog, id) -> {
                                        dialog.cancel();
                                    });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "No changes made.", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

    }

    public int getIndex(int userid) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserid() == userid) {
                return i;
            }
        }
        return -1;
    }

    public boolean usernameCheck(String username, int position) {
        for (int i = 0; i < userList.size(); i++) {
            if (i != position && userList.get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UpdateAccountActivity.this, MainActivity.class);
        intent.putExtra("userid", userid);
        startActivity(intent);
    }
}