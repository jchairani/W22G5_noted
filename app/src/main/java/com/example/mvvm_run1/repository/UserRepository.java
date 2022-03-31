package com.example.mvvm_run1.repository;

import android.app.Application;

import com.example.mvvm_run1.dao.UserDAO;
import com.example.mvvm_run1.database.NoteDatabase;
import com.example.mvvm_run1.model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private static UserRepository instance;
    private NoteDatabase db;
    private UserDAO dao;
    private List<User> users;
    public UserRepository(Application application){
        db = NoteDatabase.getInstance(application);
        dao = db.userDAO();
        ExecutorService executors = Executors.newSingleThreadExecutor();
        executors.execute(new Runnable() {
            @Override
            public void run() {
                users = dao.getAllUser();
            }
        });
    }
    public void insertUser(User user){
        ExecutorService executors = Executors.newSingleThreadExecutor();
        executors.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertUser(user);
            }
        });
    }
    public List<User> getAllUser(){
        return users;
    }
}
