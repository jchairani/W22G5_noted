package com.example.mvvm_run1.activity;

import androidx.appcompat.app.AppCompatActivity;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_acitivity);

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid",0);

        etTitle = findViewById(R.id.editTextTitle);
        etContent = findViewById(R.id.editTextContent);
        back = findViewById(R.id.backButton);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);


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

        if (!(title.equals("") && content.equals(""))) {
            Note temp = new Note(title, content, userid);
            noteViewModel.insertNote(temp);
            startActivity(i);
        }

//        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
//        editor.putString("title", etTitle.getText().toString());
//        editor.putString("content", etContent.getText().toString());
//        editor.commit();

        super.onBackPressed();
    }

}