package com.example.mvvm_run1.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.model.Note;
import com.example.mvvm_run1.viewmodel.NoteViewModel;

import java.util.Locale;

public class noteAcitivity extends AppCompatActivity {
    NoteViewModel noteViewModel;
    EditText etTitle;
    EditText etContent;
    ImageView spchToText;
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
        spchToText = findViewById(R.id.imgViewSpeechToTxt);

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

        spchToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start speaking...");

                try {
                    startActivityForResult(intent, 10);
                }catch (ActivityNotFoundException ex){
                    Toast.makeText(noteAcitivity.this, "Your device Does Not Support Speech Input", Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }
        });


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

//        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
//        editor.putString("title", etTitle.getText().toString());
//        editor.putString("content", etContent.getText().toString());
//        editor.commit();

        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 ){
            if (requestCode == RESULT_OK && data != null){
                etContent.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
            }
        }
    }

}