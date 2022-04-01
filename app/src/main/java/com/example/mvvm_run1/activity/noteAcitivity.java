package com.example.mvvm_run1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.model.Note;
import com.example.mvvm_run1.viewmodel.NoteViewModel;

public class noteAcitivity extends AppCompatActivity {
    NoteViewModel noteViewModel;
    Button back;
    EditText etTitle;
    EditText etContent;
    int userid;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_acitivity);

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", 0);
        position = intent.getIntExtra("position", 0);

        etTitle = findViewById(R.id.editTextTitle);
        etContent = findViewById(R.id.editTextContent);
        back = findViewById(R.id.backButton);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        if (position != 0) {
            noteViewModel.getNoteTitleByNoteId(position).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    etTitle.setText(s);
                }
            });
            noteViewModel.getNoteContentByNoteId(position).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    etContent.setText(s);
                }
            });
        }


//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String title = etTitle.getText().toString();
//                String content = etContent.getText().toString();
//                Note temp = new Note(title,content,userid);
//                noteViewModel.insertNote(temp);
//                Intent i = new Intent(noteAcitivity.this, MainActivity.class);
//                i.putExtra("userid",userid);
//                startActivity(i);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(noteAcitivity.this, MainActivity.class);
        i.putExtra("userid", userid);

        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        Note temp = new Note(title, content, userid);

        if (!(title.equals("") && content.equals(""))) {
            if (position == 0) {
                noteViewModel.insertNote(temp);
                startActivity(i);
            }
        }


//        else {
//            if(position!=-1){
//                Note update = new Note(title, content, userid);
//                noteViewModel.updateNote(update);
//            }
//        }

//        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
//        editor.putString("title", etTitle.getText().toString());
//        editor.putString("content", etContent.getText().toString());
//        editor.commit();

        super.onBackPressed();
    }

}