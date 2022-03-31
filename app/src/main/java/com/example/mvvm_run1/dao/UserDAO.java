package com.example.mvvm_run1.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mvvm_run1.model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user_table WHERE userid = :userid")
    public User getUserById(int userid);

    @Query("SELECT * FROM user_table")
    public List<User> getAllUser();
}
