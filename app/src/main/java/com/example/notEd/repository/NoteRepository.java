package com.example.notEd.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.notEd.dao.NoteDAO;
import com.example.notEd.database.NoteDatabase;
import com.example.notEd.model.Note;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {

    private static NoteRepository instance;
    private NoteDatabase db;
    private LiveData<List<Note>> mnotes;
    private NoteDAO dao;

    public NoteRepository(Application application) {
        db = NoteDatabase.getInstance(application);
        dao = db.noteDAO();
//        mnotes = dao.getAllNotes();
    }

    public void insertNote(Note note) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertNote(note);
            }
        });

    }

    public void updateNote(Note note) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateNote(note);
            }
        });
    }

    public LiveData<List<Note>> getNotesById(int id) {
        return dao.getNotesById(id);
    }

    public LiveData<List<Note>> getAllNotes() {
        return mnotes;
    }

    public List<Note> getAllNoteById(int usercreatorid) {
        return dao.getAllNotesById(usercreatorid);
    }

    public LiveData<String> getNoteTitleByNoteId(int id) {
        return dao.getNoteTitleByNoteId(id);
    }

    public LiveData<String> getNoteContentByNoteId(int id) {
        return dao.getNoteContentByNoteId(id);
    }

    public void updateNoteById(String title, String content, String alignment, int id) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateNoteById(title, content, alignment, id);
            }
        });
    }

    public void deleteNoteById(int id) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteNoteById(id);
            }
        });
    }

    public List<Note> findAllNotes() {
        return dao.findAllNotes();
    }

}
