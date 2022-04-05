package com.example.mvvm_run1.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table", indices = @Index(value = {"username"}, unique = true))
public class User {

    @PrimaryKey(autoGenerate = true)
    int userid;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "firstname")
    private String firstname;

    @ColumnInfo(name = "lastname")
    private String lastname;

    @ColumnInfo(name = "userpass")
    private String userpass;

    public User() {
    }

    public User(String username, String firstname, String lastname, String userpass) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.userpass = userpass;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }
}
