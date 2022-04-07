package com.example.notEd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.notEd.R;
import com.example.notEd.adapter.NoteAdapter;
import com.example.notEd.model.Note;
import com.example.notEd.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    NoteViewModel noteViewModel;
    NoteAdapter noteAdapter;
    ListView listView;
    FloatingActionButton addButton;
    int userid, snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get intent from previous activity
        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", 0);
        snackbar = intent.getIntExtra("snackbar", 0);

        listView = findViewById(R.id.listVIewNotes);
        addButton = findViewById(R.id.addButton);

        //set listview method
        initListView();

        //show message if user successfully change password from ResetPasswordActivity
        if (snackbar == 1) {
            Snackbar.make(findViewById(android.R.id.content), "Password changed.", Snackbar.LENGTH_LONG).setAction("CLOSE", view12 -> {
            }).setActionTextColor(getResources().getColor(android.R.color.white)).show();
        }

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        //showing the notes from db
        noteViewModel.getNoteById(userid).observe(this, notes -> {
            noteAdapter.setNotes(notes);
        });

        //add note button
        addButton.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, noteAcitivity.class);
            i.putExtra("userid", userid);
            startActivity(i);
        });

        //listview item click
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent1 = new Intent(getApplicationContext(), noteAcitivity.class);
            intent1.putExtra("position", i);
            intent1.putExtra("userid", userid);
            intent1.putExtra("hasNote", true);
            startActivity(intent1);
        });

        //to make device vibrate when user wants to delete note
        final Vibrator vibe = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

        //listview item long click
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            vibe.vibrate(80); //vibrate deviceee

            int item_pos = i;

            //dialog to confirm deletion
            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(android.R.drawable.ic_delete)
                    .setTitle("Delete Note")
                    .setMessage("Do you want to delete this note?")
                    .setPositiveButton("Yes", (dialogInterface, i1) -> {
                        List<Note> notes = noteViewModel.getAllNoteById(userid);
                        int deleteId = notes.get(item_pos).getNoteid();
                        noteViewModel.deleteNoteById(deleteId); //delete note
                        noteAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    private void initListView() {
        List<Note> notes = new ArrayList<>();
        noteAdapter = new NoteAdapter(notes);
        listView.setAdapter(noteAdapter);
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true); //save activity so user is not logged out
    }

    public void logout() {
        Intent intent = new Intent(MainActivity.this, loginActivity.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    //menu to access UpdateAccountActivity, ResetPasswordActivity, and log out
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.updateAccount) {
            Intent intent = new Intent(MainActivity.this, UpdateAccountActivity.class);
            intent.putExtra("userid", userid);
            startActivity(intent);
        } else if (item.getItemId() == R.id.changePassword) {
            Intent intent = new Intent(MainActivity.this, ResetPasswordActivity.class);
            intent.putExtra("userid", userid);
            startActivity(intent);
        } else if (item.getItemId() == R.id.logout) {
            logout();
        }
        return true;
    }

    //inflate menu
    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }
}