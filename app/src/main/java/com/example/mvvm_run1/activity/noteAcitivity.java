package com.example.mvvm_run1.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.model.Note;
import com.example.mvvm_run1.viewmodel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class noteAcitivity extends AppCompatActivity {
    NoteViewModel noteViewModel;
    EditText etTitle, etContent;
    ImageView spchToText;
    int userid, position = 0, count1 = 0, count2 = 0, count3 = 0;
    Button btnBold, btnItalics, btnUnderline;
    public static boolean boldClicked = false, italicsClicked = false, underlinedClicked = false;
    CharacterStyle styleBold, styleItalic, styleNormal, underline;
    List<Note> notes;
    int noteId;
    boolean hasNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_acitivity);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", 0);
        position = intent.getIntExtra("position", 0);
        hasNote = intent.getBooleanExtra("hasNote",false);

        etTitle = findViewById(R.id.editTextTitle);
        etContent = findViewById(R.id.editTextContent);
        spchToText = findViewById(R.id.imgViewSpeechToTxt);
        btnBold = findViewById(R.id.btnBold);
        btnItalics = findViewById(R.id.btnItalics);
        btnUnderline = findViewById(R.id.btnUnderline);

        styleBold = new StyleSpan(Typeface.BOLD);
        styleNormal = new StyleSpan(Typeface.NORMAL);
        styleItalic = new StyleSpan(Typeface.ITALIC);
        underline = new UnderlineSpan();

        notes = noteViewModel.getAllNoteById(userid);

        if(hasNote){
            etTitle.setText(notes.get(position).getNotetitle());
            etContent.setText(notes.get(position).getNotecontent());
            noteId = notes.get(position).getNoteid();
        }else{
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

    }

    public void buttonItalics(View view) {

        String wholeText = etContent.getText().toString();

        CharacterStyle passedStyle;
        SpannableStringBuilder sb = new SpannableStringBuilder(wholeText);

        List<Integer> savedSelection = new ArrayList<>();

        italicsClicked = true;

        if (etContent.hasSelection()) {
            int start = etContent.getSelectionStart();
            int end = etContent.getSelectionEnd();

            if (boldClicked && italicsClicked && underlinedClicked) {
                passedStyle = styleNormal;
                sb.setSpan(passedStyle, start, end, 0);
                etContent.setText(sb);
                italicsClicked = false;

                passedStyle = styleBold;
                sb.setSpan(passedStyle, start, end, 0);
                etContent.setText(sb);

                passedStyle = underline;
                sb.setSpan(passedStyle, start, end, 0);
                etContent.setText(sb);
            } else if (boldClicked && italicsClicked) {
                passedStyle = styleNormal;
                sb.setSpan(passedStyle, start, end, 0);
                etContent.setText(sb);
                italicsClicked = false;

                passedStyle = styleBold;
                sb.setSpan(passedStyle, start, end, 0);
                etContent.setText(sb);
            } else if (italicsClicked && underlinedClicked) {
                passedStyle = styleNormal;
                sb.setSpan(passedStyle, start, end, 0);
                etContent.setText(sb);
                italicsClicked = false;

                passedStyle = underline;
                sb.setSpan(passedStyle, start, end, 0);
                etContent.setText(sb);
            } else if (italicsClicked) {
                if (savedSelection.size() > 0) {
                    passedStyle = styleNormal;
                    sb.setSpan(passedStyle, start, end, 0);
                    etContent.setText(sb);
                    italicsClicked = false;

                }

            } else {
                SpannableStringBuilder spannableString = new SpannableStringBuilder(etContent.getText());
                spannableString.setSpan(new StyleSpan(Typeface.ITALIC), etContent.getSelectionStart(), etContent.getSelectionEnd(), 0);
                etContent.setText(spannableString);
                savedSelection.add(start);
            }

        } else {
            italicsClicked = false;
        }


    }

    public void buttonUnderline(View view) {

        String wholeText = etContent.getText().toString();
        int start = etContent.getSelectionStart();
        int end = etContent.getSelectionEnd();

        CharacterStyle passedStyle;
        SpannableStringBuilder sb = new SpannableStringBuilder(wholeText);

        if (boldClicked && italicsClicked && underlinedClicked) {
            passedStyle = styleNormal;
            sb.setSpan(passedStyle, start, end, 0);
            etContent.setText(sb);
            boldClicked = false;

            passedStyle = styleBold;
            sb.setSpan(passedStyle, start, end, 0);
            etContent.setText(sb);

            passedStyle = styleItalic;
            sb.setSpan(passedStyle, start, end, 0);
            etContent.setText(sb);
        } else if (underlinedClicked && italicsClicked) {
            passedStyle = styleNormal;
            sb.setSpan(passedStyle, start, end, 0);
            etContent.setText(sb);
            boldClicked = false;

            passedStyle = styleItalic;
            sb.setSpan(passedStyle, start, end, 0);
            etContent.setText(sb);
        } else if (boldClicked && underlinedClicked) {
            passedStyle = styleNormal;
            sb.setSpan(passedStyle, start, end, 0);
            etContent.setText(sb);
            boldClicked = false;

            passedStyle = styleBold;
            sb.setSpan(passedStyle, start, end, 0);
            etContent.setText(sb);
        } else if (underlinedClicked) {
            passedStyle = styleNormal;
            sb.setSpan(passedStyle, start, end, 0);
            etContent.setText(sb);
            italicsClicked = false;
        } else {
            SpannableStringBuilder spannableString = new SpannableStringBuilder(etContent.getText());
            spannableString.setSpan(new UnderlineSpan(), etContent.getSelectionStart(), etContent.getSelectionEnd(), 0);
            etContent.setText(spannableString);
            italicsClicked = true;
        }
    }

    @Override
    public void onBackPressed() {


        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        Note temp = new Note(title, content, userid);

        Log.d("josjos",""+hasNote);

        if(hasNote){
            if (!(title.equals("") && content.equals(""))) {
                noteViewModel.updateNoteById(title,content,noteId);
                Intent i = new Intent(noteAcitivity.this, MainActivity.class);
                i.putExtra("userid", userid);
                startActivity(i);
            }
        }else{
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
            if (requestCode == RESULT_OK && data != null) {
                etContent.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
            }
        }
    }

}