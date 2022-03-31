package com.example.mvvm_run1.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mvvm_run1.dao.NoteDAO;
import com.example.mvvm_run1.database.NoteDatabase;
import com.example.mvvm_run1.model.Note;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {

    private static NoteRepository instance;
    private NoteDatabase db;
    private LiveData<List<Note>> mnotes;
    private NoteDAO dao;

    public NoteRepository(Application application){
        db = NoteDatabase.getInstance(application);
        dao = db.noteDAO();
//        mnotes = dao.getAllNotes();
    }

    public void insertNote(Note note){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertNote(note);
            }
        });

    }

    public LiveData<List<Note>> getNotesById(int id){
        return dao.getNotesById(id);
    }

    public LiveData<List<Note>> getAllNotes(){
        return mnotes;
    }

}
