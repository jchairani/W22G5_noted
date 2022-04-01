package com.example.mvvm_run1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.mvvm_run1.adapter.NoteAdapter;
import com.example.mvvm_run1.model.Note;
import com.example.mvvm_run1.viewmodel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    NoteViewModel noteViewModel;
    ListView listView;
    NoteAdapter noteAdapter;
    ListView lv;
    Button add;
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid",0);
        Log.d("josjos",""+userid);
        lv = findViewById(R.id.listVIewNotes);
        add = findViewById(R.id.button);
        initListView();

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getNoteById(userid).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //make changes here
                noteAdapter.setNotes(notes);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,noteAcitivity.class);
                i.putExtra("userid",userid);
                startActivity(i);

            }
        });
//        test
    }

    private void initListView(){
        List<Note> notes = new ArrayList<>();
        noteAdapter = new NoteAdapter(notes);
        lv.setAdapter(noteAdapter);
    }


}