package com.example.notEd.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notEd.dao.NoteDAO;
import com.example.notEd.dao.UserDAO;
import com.example.notEd.model.Note;
import com.example.notEd.model.User;

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
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
