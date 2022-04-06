package com.example.mvvm_run1.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mvvm_run1.dao.UserDAO;
import com.example.mvvm_run1.database.NoteDatabase;
import com.example.mvvm_run1.model.Note;
import com.example.mvvm_run1.model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private static UserRepository instance;
    private NoteDatabase db;
    private UserDAO dao;
    private List<User> users;

    public UserRepository(Application application) {
        db = NoteDatabase.getInstance(application);
        dao = db.userDAO();
        ExecutorService executors = Executors.newSingleThreadExecutor();
        executors.execute(() -> users = dao.getAllUser());
    }

    public void insertUser(User user) {
        ExecutorService executors = Executors.newSingleThreadExecutor();
        executors.execute(() -> dao.insertUser(user));
    }

    public void updateUser(User user) {
        ExecutorService executors = Executors.newSingleThreadExecutor();
        executors.execute(() -> dao.updateUser(user));
    }

    public List<User> getAllUser() {
        return users;
    }

    public LiveData<String> getUsernameById(int userid) {
        return dao.getUsernameById(userid);
    }

    public LiveData<String> getFirstNameById(int userid) {
        return dao.getFirstNameById(userid);
    }

    public LiveData<String> getLastNameById(int userid) {
        return dao.getLastNameById(userid);
    }

    public LiveData<String> getUsernameByString(String user) {
        return dao.getUsernameByString(user);
    }

    public void changePassword(int id, String pass) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                dao.changePassword(id, pass);
            }
        });
    }

    public void changeUsername(int id, String user) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> dao.changeUsername(id, user));
    }

    public void changeLastName(int id, String lname) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> dao.changeLastName(id, lname));
    }

    public void changeFirstName(int id, String fname) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> dao.changeFirstName(id, fname));
    }


}
