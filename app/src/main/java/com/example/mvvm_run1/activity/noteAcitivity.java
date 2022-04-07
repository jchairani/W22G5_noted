package com.example.mvvm_run1.activity;

import static java.lang.String.format;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.model.Note;
import com.example.mvvm_run1.viewmodel.NoteViewModel;

import java.util.List;
import java.util.Locale;

public class noteAcitivity extends AppCompatActivity {
    NoteViewModel noteViewModel;
    EditText etTitle, etContent;
    ImageButton spchToText, btnBold, btnItalics, btnUnderline, btnCenter, btnLeft, btnRight;
    List<Note> notes;
    String storedContent;
    int userid, noteId, position = 0;
    boolean hasNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_acitivity);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", 0);
        position = intent.getIntExtra("position", 0);
        hasNote = intent.getBooleanExtra("hasNote", false);

        etTitle = findViewById(R.id.editTextTitle);
        etContent = findViewById(R.id.editTextContent);
        spchToText = findViewById(R.id.imgViewSpeechToTxt);
        btnBold = findViewById(R.id.btnBold);
        btnItalics = findViewById(R.id.btnItalics);
        btnUnderline = findViewById(R.id.btnUnderline);
        btnCenter = findViewById(R.id.btnCenter);
        btnRight = findViewById(R.id.btnRight);
        btnLeft = findViewById(R.id.btnLeft);

        notes = noteViewModel.getAllNoteById(userid);



        if (hasNote) {
            noteId = notes.get(position).getNoteid();
            etTitle.setText(notes.get(position).getNotetitle());
            etContent.setText(notes.get(position).getNotecontent());
            storedContent = etContent.getText().toString();
            etContent.setText(Html.fromHtml(storedContent));
        } else {
            etTitle.setText("");
            etContent.setText("");
        }

        spchToText.setOnClickListener(view -> {
            Intent intent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent1.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start speaking...");

            try {
                startActivityForResult(intent1, 10);
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(noteAcitivity.this, "Your device Does Not Support Speech Input", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }
        });
    }

    public void buttonBold(View view) {
//        Spannable spannableString = etContent.getText();
//        StyleSpan[] spans = spannableString.getSpans(etContent.getSelectionStart(), etContent.getSelectionEnd(), StyleSpan.class);
//        if (spans.length == 0) {
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), etContent.getSelectionStart(), etContent.getSelectionEnd(), SPAN_EXCLUSIVE_EXCLUSIVE);
//        } else {
//            for (StyleSpan span : spans) {
//                spannableString.removeSpan(span);
//            }
//        }

        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), etContent.getSelectionStart(), etContent.getSelectionEnd(), 0);
        etContent.setText(spannableString);
    }

    public void buttonItalics(View view) {
        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        spannableString.setSpan(new StyleSpan(Typeface.ITALIC), etContent.getSelectionStart(), etContent.getSelectionEnd(), 0);
        etContent.setText(spannableString);

//        Spannable spannableString = etContent.getText();
//        StyleSpan[] spans = spannableString.getSpans(etContent.getSelectionStart(), etContent.getSelectionEnd(), StyleSpan.class);
//        if (spans.length == 0) {
//            spannableString.setSpan(new StyleSpan(Typeface.ITALIC), etContent.getSelectionStart(), etContent.getSelectionEnd(), SPAN_EXCLUSIVE_EXCLUSIVE);
//        } else {
//            for (StyleSpan span : spans) {
//                spannableString.removeSpan(span);
//            }
//        }
    }

    public void buttonUnderline(View view) {
        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        spannableString.setSpan(new UnderlineSpan(), etContent.getSelectionStart(), etContent.getSelectionEnd(), 0);
        etContent.setText(spannableString);
    }

    public void buttonResetFormat(View view) {
        String stringText = etContent.getText().toString();
        etContent.setText(stringText);
    }

    public void buttonLeft(View view) {
        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        etContent.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        etContent.setText(spannableString);
    }

    public void buttonCenter(View view) {
        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        etContent.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        etContent.setText(spannableString);
    }

    public void buttonRight(View view) {
        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        etContent.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        etContent.setText(spannableString);
    }

    @Override
    public void onBackPressed() {
        String title = etTitle.getText().toString();
        String content = Html.toHtml(etContent.getText());

        Note temp = new Note(title, content, userid);

        Log.d("josjos", "" + hasNote);

        if (hasNote) {
            if (!(title.equals("") && content.equals(""))) {
                noteViewModel.insertNote(new Note(title, content, userid));
                noteViewModel.deleteNoteById(noteId);
                Intent i = new Intent(noteAcitivity.this, MainActivity.class);
                i.putExtra("userid", userid);
                startActivity(i);
            }
        } else {
            if (!(title.equals("") && content.equals(""))) {

                Intent i = new Intent(noteAcitivity.this, MainActivity.class);
                i.putExtra("userid", userid);
                noteViewModel.insertNote(temp);
                startActivity(i);
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            etContent.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
        }
    }

}