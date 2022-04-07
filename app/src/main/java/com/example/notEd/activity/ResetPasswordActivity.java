package com.example.notEd.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notEd.R;
import com.example.notEd.model.User;
import com.example.notEd.viewmodel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText editText, editText2;
    TextView txtView, txtView2;
    Button resetButton;
    UserViewModel userViewModel;
    String input, newpass, matchpass, firstname, lastname, username;
    List<User> userList;
    int userid, fromLogin;

    private static ResetPasswordActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //UserViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //get intent from previous activity
        Intent getIntent = getIntent();
        userid = getIntent.getIntExtra("userid", 0);
        fromLogin = getIntent.getIntExtra("fromLogin", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetButton = findViewById(R.id.btnReset);
        txtView = findViewById(R.id.textViewReset);
        txtView2 = findViewById(R.id.textViewPass);
        editText = findViewById(R.id.editTextReset);
        editText2 = findViewById(R.id.etPassword);

        //get all user from db
        userList = userViewModel.getAllUser();

        //check if user comes from MainActivity by using userid
        if (userid > 0) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance()); //change EditText input type to password
            txtView.setText("Enter new password");
            editText.setText("");
            editText.setHint("New password");

            //set input for confirming new password to visible
            txtView2.setVisibility(View.VISIBLE);
            editText2.setVisibility(View.VISIBLE);

            resetButton.setText("Change password");

            username = userList.get(userid - 1).getUsername();
            firstname = userList.get(userid - 1).getFirstname();
            lastname = userList.get(userid - 1).getLastname();
            Log.d(TAG, username);

//            userViewModel.getUsernameById(userid).observe(this, s -> username = s);
//            userViewModel.getFirstNameById(userid).observe(this, s -> firstname = s);
//            userViewModel.getLastNameById(userid).observe(this, s -> lastname = s);
        }

        //reset button OnCLickListener
        resetButton.setOnClickListener(view -> {
            input = editText.getText().toString();

            //if db empty and user click the button
            if (userList.size() == 0) {
                Snackbar.make(findViewById(R.id.layout), "Username does not exist.", Snackbar.LENGTH_SHORT).show();
            }
            //check if input is empty
            if (input.equals("")) {
                Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
            } else {
                //if user comes from MainActivity (already signed in)
                if (userid > 0) {
                    Log.d(TAG, "" + userid);
                    newpass = editText.getText().toString();
                    matchpass = editText2.getText().toString();

                    //for loop users db
                    for (int i = 0; i < userList.size(); i++) {
                        //check if input is empty
                        if (newpass.equals("") || matchpass.equals("")) {
                            Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
                        } else {
                            //check if new password equals to old/current password
                            if (userList.get(i).getUserpass().equals(newpass)) {
                                Snackbar.make(findViewById(R.id.layout), "New password cannot be the same as current password.", Snackbar.LENGTH_SHORT).show();
                                break;
                            } else {
                                //check if password inputs match each other
                                if (!(newpass.equals(matchpass))) {
                                    Snackbar.make(findViewById(R.id.layout), "Password does not match.", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    if (newpass.length() > 7) { //check if password has at least 8 characters
                                        userViewModel.changePassword(userid, newpass);
                                        Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                                        intent.putExtra("userid", userid);
                                        intent.putExtra("snackbar", 1);
                                        startActivity(intent);
                                    } else {
                                        Snackbar.make(findViewById(R.id.layout), "Password has to be 8 characters minimum.", Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                } else { //else, if user comes from loginActivity
                    if (usernameCheck(input)==false) { //check if username exists
                        Snackbar.make(findViewById(R.id.layout), "Username does not exist.", Snackbar.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < userList.size(); i++) {
                        //if username exists in db
                        if (userList.get(i).getUsername().equals(input)) {
                            username = input;

                            txtView.setText("Enter first name");
                            editText.setText("");
                            editText.setHint("First name");
                            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS); //Auto capitalize

                            int finalI = i; //save the index

                            //if button clicked for the second time
                            resetButton.setOnClickListener(view1 -> {
                                input = editText.getText().toString();
                                userList = userViewModel.getAllUser();

                                //check if input is empty
                                if (input.equals("")) {
                                    Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    if (userList.get(finalI).getFirstname().equals(input)) {
                                        firstname = input;

                                        txtView.setText("Enter last name");
                                        editText.setText("");
                                        editText.setHint("Last name");
                                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

                                        //if button clicked for the third time
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

                                                    //if button clicked for the fourth time
                                                    resetButton.setOnClickListener(view3 -> {
                                                        newpass = editText.getText().toString();
                                                        matchpass = editText2.getText().toString();

                                                        //check if input is empty
                                                        if (newpass.equals("") || matchpass.equals("")) {
                                                            Snackbar.make(findViewById(R.id.layout), "Input cannot be empty.", Snackbar.LENGTH_SHORT).show();
                                                        } else {
                                                            //check if new and old password match
                                                            if (userList.get(finalI).getUserpass().equals(newpass)) {
                                                                Snackbar.make(findViewById(R.id.layout), "New password cannot be the same as current password.", Snackbar.LENGTH_SHORT).show();
                                                            } else {
                                                                //check if password inputs match
                                                                if (!(newpass.equals(matchpass))) {
                                                                    Snackbar.make(findViewById(R.id.layout), "Password does not match.", Snackbar.LENGTH_SHORT).show();
                                                                } else {
                                                                    if (newpass.length() > 7) {
                                                                        userid = userList.get(finalI).getUserid();
                                                                        userViewModel.changePassword(userid, newpass);

                                                                        Intent intent2 = new Intent(ResetPasswordActivity.this, loginActivity.class);
                                                                        intent2.putExtra("snackbar", 1);
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
                        }
                    }
                }
            }
        });
    }

    //username check method
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
        super.onBackPressed();
        //if user comes from loginActivity go back to loginActivity (does not apply to user that comes from MainActivity)
        if (fromLogin == 1) {
            Intent intent = new Intent(ResetPasswordActivity.this, loginActivity.class);
            startActivity(intent);
        }
    }
}