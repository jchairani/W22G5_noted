package com.example.mvvm_run1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvvm_run1.model.Note;

import java.util.List;
@Dao
public interface NoteDAO {

        @Insert
        void insertNote(Note note);

        @Update
        void updateNote(Note note);

        @Delete
        void deleteNote(Note note);

        @Query("SELECT * FROM note_table WHERE notetitle =:title AND notecontent=:content")
        List<Note> getNoteByTitleContent(String title,String content);

        @Query("SELECT * FROM note_table WHERE usercreatorid =:usercreatorid")
        List<Note> getAllNotesById(int usercreatorid);

        @Query("SELECT * FROM note_table WHERE usercreatorid =:usercreatorid")
        LiveData<List<Note>> getNotesById(int usercreatorid);

        @Query("SELECT notetitle FROM note_table WHERE noteid =:noteid")
        LiveData<String> getNoteTitleByNoteId(int noteid);

        @Query("SELECT notecontent FROM note_table WHERE noteid =:noteid")
        LiveData<String> getNoteContentByNoteId(int noteid);

        @Query("UPDATE note_table SET notetitle=:title , notecontent=:content WHERE noteid =:id")
        void updateNoteById(String title,String content,int id);


}
