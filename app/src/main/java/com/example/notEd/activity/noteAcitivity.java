package com.example.notEd.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static java.lang.String.format;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Html;
import android.text.InputType;
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

import com.example.notEd.R;
import com.example.notEd.model.Note;
import com.example.notEd.viewmodel.NoteViewModel;

import java.util.List;
import java.util.Locale;

public class noteAcitivity extends AppCompatActivity {
    NoteViewModel noteViewModel;
    EditText etTitle, etContent;
    ImageButton spchToText, btnBold, btnItalics, btnUnderline, btnCenter, btnLeft, btnRight;
    List<Note> notes;
    String storedContent, alignment, storedTitle, content, title, storedAlignment;
    //ImageView imageView;
    int userid, noteId, position = 0;
    boolean hasNote;

    //static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    //static final int REQUEST_CODE_SELECT_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //to adjust window content/size when keyboard pops up
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_acitivity);

        //NoteViewModel
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        //get intent from previous activity
        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", 0); //get userid
        position = intent.getIntExtra("position", 0); //note position from listview
        hasNote = intent.getBooleanExtra("hasNote", false); //check if user has notes in db

        etTitle = findViewById(R.id.editTextTitle);
        etContent = findViewById(R.id.editTextContent);
        spchToText = findViewById(R.id.imgViewSpeechToTxt);
        btnBold = findViewById(R.id.btnBold);
        btnItalics = findViewById(R.id.btnItalics);
        btnUnderline = findViewById(R.id.btnUnderline);
        btnCenter = findViewById(R.id.btnCenter);
        btnRight = findViewById(R.id.btnRight);
        btnLeft = findViewById(R.id.btnLeft);
        //btnImage = findViewById(R.id.btnImage);
        //imageView = findViewById(R.id.imageNote);

        notes = noteViewModel.getAllNoteById(userid); //get all notes from db by userid

        //initialize text alignment to left side
        alignment = "View.TEXT_ALIGNMENT_TEXT_START";

        //selectedImagePath = "";

        //check if user has notes from db
        if (hasNote) {
            noteId = notes.get(position).getNoteid(); //get noteid from db

            etTitle.setText(notes.get(position).getNotetitle()); //get and set note title from db
            storedTitle = etTitle.getText().toString(); //save the note title to string

            etContent.setText(notes.get(position).getNotecontent()); //get and set note content from db
            storedContent = etContent.getText().toString(); //save note content to string
            etContent.setText(Html.fromHtml(storedContent)); //convert html format and set content text

            storedAlignment = notes.get(position).getAlignment(); //get content alignment from db
            Spannable spannableString = new SpannableStringBuilder(etContent.getText()); //spannable content

            //set alignment
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

        } else { //if user has no notes set title and content to ""
            etTitle.setText("");
            etContent.setText("");
        }

//        btnImage.setOnClickListener(view -> {
//            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(noteAcitivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
//            } else {
//                addImage();
//            }
//        });

        //setOnClickListener for speech-to-text button
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

//    public void addImage() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
//        }
//    }

    //method for bold button
    public void buttonBold(View view) {
        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), etContent.getSelectionStart(), etContent.getSelectionEnd(), 0);
        etContent.setText(spannableString);

//        Spannable spannableString = etContent.getText();
//        StyleSpan[] spans = spannableString.getSpans(etContent.getSelectionStart(), etContent.getSelectionEnd(), StyleSpan.class);
//        if (spans.length == 0) {
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), etContent.getSelectionStart(), etContent.getSelectionEnd(), SPAN_EXCLUSIVE_EXCLUSIVE);
//        } else {
//            for (StyleSpan span : spans) {
//                spannableString.removeSpan(span);
//            }
//        }

    }

    //method for italics button
    public void buttonItalics(View view) {
        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        spannableString.setSpan(new StyleSpan(Typeface.ITALIC), etContent.getSelectionStart(), etContent.getSelectionEnd(), 0);
        etContent.setText(spannableString);

        //code below enable user to bold and unbold text but it does not work well if there is another typeface in the text
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

    //method for underline button
    public void buttonUnderline(View view) {
        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        spannableString.setSpan(new UnderlineSpan(), etContent.getSelectionStart(), etContent.getSelectionEnd(), 0);
        etContent.setText(spannableString);
    }

    //method for reset button
    public void buttonResetFormat(View view) {
        String stringText = etContent.getText().toString();
        etContent.setText(stringText);
    }

    //method for left alignment button
    public void buttonLeft(View view) {
        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        etContent.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        etContent.setText(spannableString);
        alignment = "View.TEXT_ALIGNMENT_TEXT_START";
    }

    //method for center alignment button
    public void buttonCenter(View view) {
        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        etContent.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        etContent.setText(spannableString);
        alignment = "View.TEXT_ALIGNMENT_CENTER";
    }

    //method for right alignment button
    public void buttonRight(View view) {
        Spannable spannableString = new SpannableStringBuilder(etContent.getText());
        etContent.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        etContent.setText(spannableString);
        alignment = "View.TEXT_ALIGNMENT_TEXT_END";
    }

    //request external storage permission access for adding image to note
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                addImage();
//            } else {
//                Snackbar.make(findViewById(R.id.constraintLayout), "Permission denied.", Snackbar.LENGTH_SHORT).show();
//            }
//        }
//    }

    //save note onBackPressed
    @Override
    public void onBackPressed() {
        title = etTitle.getText().toString(); //current note title to string
        content = Html.toHtml(etContent.getText()); //convert content to html to save the typeface (bold, italics, underline)

        Log.d(TAG, "" + hasNote);

        //check if user has notes
        if (hasNote) {
            //if there is no changes in title, content, and alignment -> do not save/update the note
            if (title.equals(storedTitle) && content.equals(storedContent) && alignment.equals(storedAlignment)) {
                Intent i = new Intent(noteAcitivity.this, MainActivity.class);
                i.putExtra("userid", userid);
                startActivity(i);
            } else { //else, save update the note
                //noteViewModel.updateNoteById(title, content, alignment, noteId); //commented because not working as expected
                noteViewModel.insertNote(new Note(title, content, alignment, userid)); //insert updated note
                noteViewModel.deleteNoteById(noteId); //delete old version of the note
                Intent i = new Intent(noteAcitivity.this, MainActivity.class);
                i.putExtra("userid", userid);
                startActivity(i);
            }
        } else { //else or if user has no note and just open the activity (does not make any input/changes) -> do not save note
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
//        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
//            if (data != null) {
//                Uri selectedImage = data.getData();
//                if (selectedImage != null) {
//                    try {
//                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
//                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                        imageView.setImageBitmap(bitmap);
//                        imageView.setVisibility(View.VISIBLE);
//
//                        selectedImagePath = getPathFromUri(selectedImage);
//
//                    } catch (Exception exception) {
//                        Snackbar.make(findViewById(R.id.constraintLayout), exception.getMessage(), Snackbar.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }
    }

//    private String getPathFromUri(Uri uri) {
//        String filePath;
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//
//        if (cursor == null) {
//            filePath = uri.getPath();
//        } else {
//            cursor.moveToFirst();
//            int i = cursor.getColumnIndex("data");
//            filePath = cursor.getString(i);
//        }
//        return filePath;
//    }

}