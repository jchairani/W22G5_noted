package com.example.mvvm_run1.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.model.User;
import com.example.mvvm_run1.viewmodel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class UpdateAccountActivity extends AppCompatActivity {
    int userid;
    Button btnUpdate;
    EditText etFirstName, etLastName, etUsername;
    UserViewModel userViewModel;
    List<User> userList;
    String username, firstname, lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        etUsername = findViewById(R.id.inputUsername);
        etFirstName = findViewById(R.id.inputFirstName);
        etLastName = findViewById(R.id.inputLastName);
        btnUpdate = findViewById(R.id.btnUpdate);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Intent getIntent = getIntent();
        userid = getIntent.getIntExtra("userid", 0);

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
                    for (int i = 0; i < userList.size(); i++) {
                        if ((userViewModel.getUsernameById(i).equals(etUsername))) {
                            Snackbar.make(findViewById(R.id.layout), "Username is taken.", Snackbar.LENGTH_SHORT).show();
                            break;
                        } else {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(UpdateAccountActivity.this);
                            builder1.setMessage("Do you want to change your username to " + etUsername.getText().toString() + "?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            Intent intent = new Intent(UpdateAccountActivity.this, MainActivity.class);
                                            intent.putExtra("userid", userid);
                                            intent.putExtra("snackbar", 2);
                                            startActivity(intent);
                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UpdateAccountActivity.this, MainActivity.class);
        intent.putExtra("userid", userid);
        startActivity(intent);
    }
}