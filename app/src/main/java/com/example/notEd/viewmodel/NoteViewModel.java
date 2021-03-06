package com.example.notEd.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notEd.model.Note;
import com.example.notEd.repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private LiveData<List<Note>> mnotes;
    private NoteRepository repository;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        repository = new NoteRepository(application);

    }

    public void insertNote(Note note) {
        repository.insertNote(note);
    }

    public void updateNote(Note note) {
        repository.updateNote(note);
    }

    public LiveData<List<Note>> getNoteById(int id) {
        return repository.getNotesById(id);
    }

    public LiveData<List<Note>> getAllNotes() {
        return mnotes;
    }

    public List<Note> getAllNoteById(int usercreatorid) {
        return repository.getAllNoteById(usercreatorid);
    }

    public LiveData<String> getNoteTitleByNoteId(int id) {
        return repository.getNoteTitleByNoteId(id);
    }

    public LiveData<String> getNoteContentByNoteId(int id) {
        return repository.getNoteContentByNoteId(id);
    }

    public void updateNoteById(String title, String content, String alignment, int id) {
        repository.updateNoteById(title, content, alignment, id);
    }

    public void deleteNoteById(int id) {
        repository.deleteNoteById(id);
    }

    public List<Note> findAllNotes() {
        return repository.findAllNotes();
    }
}
