package com.example.mvvm_run1.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvvm_run1.model.Note;
import com.example.mvvm_run1.model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("SELECT username FROM user_table WHERE userid = :userid")
    public LiveData<String> getUsernameById(int userid);

    @Query("SELECT firstname FROM user_table WHERE userid = :userid")
    public LiveData<String> getFirstNameById(int userid);

    @Query("SELECT lastname FROM user_table WHERE userid = :userid")
    public LiveData<String> getLastNameById(int userid);

    @Query("SELECT username FROM user_table WHERE username = :user")
    public LiveData<String> getUsernameByString(String user);

    @Query("SELECT * FROM user_table")
    public List<User> getAllUser();

    @Query("SELECT * from user_table WHERE userid= :userid")
    List<User> getUserById(int userid);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT userid from user_table WHERE username = :name")
    public int getIdByUsername(String name);

    @Query("UPDATE user_table SET userpass =:pass WHERE userid=:id")
    void changePassword(int id,String pass);

    @Query("UPDATE user_table SET username =:user WHERE userid=:id")
    void changeUsername(int id,String user);

    @Query("UPDATE user_table SET lastname =:lname WHERE userid=:id")
    void changeLastName(int id,String lname);

    @Query("UPDATE user_table SET firstname =:fname WHERE userid=:id")
    void changeFirstName(int id,String fname);

}
