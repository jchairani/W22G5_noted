package com.example.mvvm_run1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvm_run1.model.Note;
import com.example.mvvm_run1.repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private LiveData<List<Note>> mnotes;
    private NoteRepository repository;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        repository = new NoteRepository(application);
//        mnotes = repository.getAllNotes();
    }

    public void insertNote(Note note){
        repository.insertNote(note);
    }
    public LiveData<List<Note>> getNoteById(int id){
        return repository.getNotesById(id);
    }
    public LiveData<List<Note>> getAllNotes(){
        return mnotes;
    }


}
