package com.example.mvvm_run1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.example.mvvm_run1.model.User;
import com.example.mvvm_run1.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }

    public void updateUser(User user) {
        repository.updateUser(user);
    }

    public List<User> getAllUser() {
        return repository.getAllUser();
    }

    public LiveData<String> getUsernameById(int userid){
        return repository.getUsernameById(userid);
    }

    public LiveData<String> getFirstNameById(int userid){
        return repository.getFirstNameById(userid);
    }

    public LiveData<String> getLastNameById(int userid){
        return repository.getLastNameById(userid);
    }

    public LiveData<String> getUsernameByString(String user){
        return repository.getUsernameByString(user);
    }
    public void changePassword(int id,String pass){
        repository.changePassword(id,pass);
    }

    public void changeUsername(int id,String user){
        repository.changeUsername(id,user);
    }

    public void changeLastName(int id,String lname){
        repository.changeLastName(id,lname);
    }

    public  void changeFirstName(int id,String fname){
        repository.changeFirstName(id,fname);
    }

}
