package com.example.mvvm_run1.activity;

import static java.lang.String.format;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.model.Note;
import com.example.mvvm_run1.viewmodel.NoteViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class noteAcitivity extends AppCompatActivity {
    NoteViewModel noteViewModel;
    EditText etTitle, etContent;
    ImageButton spchToText, btnBold, btnItalics, btnUnderline, btnCenter, btnLeft, btnRight, btnImage;
    List<Note> notes;
    String storedContent, selectedImagePath, alignment, storedTitle, content, title, storedAlignment;
    ImageView imageView;
    int userid, noteId, position = 0;
    boolean hasNote;

    static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    static final int REQUEST_CODE_SELECT_IMAGE = 2;

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
        btnImage = findViewById(R.id.btnImage);
        imageView = findViewById(R.id.imageNote);

        notes = noteViewModel.getAllNoteById(userid);

        alignment = "View.TEXT_ALIGNMENT_TEXT_START";
        //selectedImagePath = "";

        if (hasNote) {
            noteId = notes.get(position).getNoteid();

            etTitle.setText(notes.get(position).getNotetitle());
            storedTitle = etTitle.getText().toString();

            etContent.setText(notes.get(position).getNotecontent());
            storedContent = etContent.getText().toString();
            etContent.setText(Html.fromHtml(storedContent));

            storedAlignment = notes.get(position).getAlignment();
            Spannable spannableString = new SpannableStringBuilder(etContent.getText());

            if (storedAlignment.equals("View.TEXT_ALIGNMENT_CENTER")) {
                etContent.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                etContent.setText(spannableString);
                alignment = "View.TEXT_ALIGNMENT_CENTER";

            } else if (storedAlignment.equals("View.TEXT_ALIGNMENT_TEXT_END")) {
                etContent.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                etContent.setText(spannableString);
                alignment = "View.TEXT_ALIGNMENT_TEXT_END";

            } else {
                etContent.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                etContent.setText(spannableString);
                alignment = "View.TEXT_ALIGNMENT_TEXT_START";
            }

            //selectedImagePath = notes.get(position).getImagePath();
        } else {
            etTitle.setText("");
            etContent.setText("");
        }

        btnImage.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(noteAcitivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
            } else {
                addImage();
            }
        });

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

    public void addImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
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
        alignment = "View.TEXT_ALIGNMENT_TEXT_START";
    }

    public void buttonCenter(View view) {
        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        etContent.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        etContent.setText(spannableString);
        alignment = "View.TEXT_ALIGNMENT_CENTER";
    }

    public void buttonRight(View view) {
        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        etContent.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        etContent.setText(spannableString);
        alignment = "View.TEXT_ALIGNMENT_TEXT_END";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addImage();
            } else {
                Snackbar.make(findViewById(R.id.constraintLayout), "Permission denied.", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        title = etTitle.getText().toString();
        content = Html.toHtml(etContent.getText());

        Log.d("josjos", "" + hasNote);

        if (hasNote) {
            if (title.equals(storedTitle) && content.equals(storedContent)  && alignment.equals(storedAlignment)) {
                Intent i = new Intent(noteAcitivity.this, MainActivity.class);
                i.putExtra("userid", userid);
                startActivity(i);
            } else {
                //noteViewModel.updateNoteById(title, content, alignment, noteId);
                noteViewModel.insertNote(new Note(title, content, alignment, userid));
                noteViewModel.deleteNoteById(noteId);
                Intent i = new Intent(noteAcitivity.this, MainActivity.class);
                i.putExtra("userid", userid);
                startActivity(i);
            }
        } else {
            if (!(title.equals("") && content.equals(""))) {
                Intent i = new Intent(noteAcitivity.this, MainActivity.class);
                i.putExtra("userid", userid);
                noteViewModel.insertNote(new Note(title, content, alignment, userid));
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
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
                        imageView.setVisibility(View.VISIBLE);

                        selectedImagePath = getPathFromUri(selectedImage);

                    } catch (Exception exception) {
                        Snackbar.make(findViewById(R.id.constraintLayout), exception.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private String getPathFromUri(Uri uri) {
        String filePath;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor == null) {
            filePath = uri.getPath();
        } else {
            cursor.moveToFirst();
            int i = cursor.getColumnIndex("data");
            filePath = cursor.getString(i);
        }
        return filePath;
    }

}