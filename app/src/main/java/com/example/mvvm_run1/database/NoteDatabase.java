package com.example.mvvm_run1.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mvvm_run1.dao.NoteDAO;
import com.example.mvvm_run1.dao.UserDAO;
import com.example.mvvm_run1.model.Note;
import com.example.mvvm_run1.model.User;

@Database(entities={Note.class, User.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public static NoteDatabase instance;
    public abstract NoteDAO noteDAO();
    public abstract UserDAO userDAO();

    public static synchronized NoteDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,
                    "NoteDatabase")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
