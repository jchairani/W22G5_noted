package com.example.mvvm_run1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.mvvm_run1.model.User;
import com.example.mvvm_run1.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public void insertUser(User user){
        repository.insertUser(user);
    }
    public List<User> getAllUser(){
        return repository.getAllUser();
    }
}
