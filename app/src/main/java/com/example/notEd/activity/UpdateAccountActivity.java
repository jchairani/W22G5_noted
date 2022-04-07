package com.example.notEd.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.notEd.R;
import com.example.notEd.model.User;
import com.example.notEd.viewmodel.UserViewModel;
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
            position = getIndex(userid); //get user index from db
        } catch (Exception exception) {
            exception.printStackTrace();
        }

//        username = userList.get(position).getUsername();
//        firstname = userList.get(position).getFirstname();
//        lastname = userList.get(position).getLastname();

//        etUsername.setText(username);
//        etFirstName.setText(firstname);
//        etLastName.setText(lastname);

        //livedata string to show update result right away
        userViewModel.getUsernameById(userid).observe(this, s -> username = s);
        userViewModel.getFirstNameById(userid).observe(this, s -> firstname = s);
        userViewModel.getLastNameById(userid).observe(this, s -> lastname = s);

        userViewModel.getUsernameById(userid).observe(this, s -> etUsername.setText(s));
        userViewModel.getFirstNameById(userid).observe(this, s -> etFirstName.setText(s));
        userViewModel.getLastNameById(userid).observe(this, s -> etLastName.setText(s));

        //update button ClickListener
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if input is empty
                if (etUsername.equals("") || etFirstName.equals("") || etLastName.equals("")) {
                    Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
                } else {
                    //get all user from db
                    userList = userViewModel.getAllUser();
                    //check if name contains digit
                    if (etFirstName.getText().toString().matches(".*\\d.*") || etLastName.getText().toString().matches(".*\\d.*")) {
                        Snackbar.make(findViewById(R.id.layout), "Name contains number!\nPlease input alphabetical values.", Snackbar.LENGTH_SHORT).show();
                    }
                    //check if new username input equals current username and check if username is taken
                    if (!(etUsername.getText().toString().equals(username)) && usernameCheck(etUsername.getText().toString(), position) == true) {
                        Snackbar.make(findViewById(R.id.layout), "Username is taken.", Snackbar.LENGTH_SHORT).show();
                    } else {
                        //showing dialog to confirm input
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateAccountActivity.this);

                        //if user changes username
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

                        //if user changes first name
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

                        //if user changes last name
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

    //get index from db method
    public int getIndex(int userid) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserid() == userid) {
                return i;
            }
        }
        return -1;
    }

    //check username method
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