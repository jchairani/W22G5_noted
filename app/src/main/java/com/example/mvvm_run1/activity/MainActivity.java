package com.example.mvvm_run1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.mvvm_run1.R;
import com.example.mvvm_run1.adapter.NoteAdapter;
import com.example.mvvm_run1.model.Note;
import com.example.mvvm_run1.viewmodel.NoteViewModel;
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

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", 0);
        snackbar = intent.getIntExtra("snackbar", 0);

        listView = findViewById(R.id.listVIewNotes);
        addButton = findViewById(R.id.addButton);

        initListView();

        if (snackbar == 1) {
            Snackbar.make(findViewById(android.R.id.content), "Password changed.", Snackbar.LENGTH_LONG).setAction("CLOSE", view12 -> {
            }).setActionTextColor(getResources().getColor(android.R.color.white)).show();
        }
        if (snackbar == 2) {
            Snackbar.make(findViewById(android.R.id.content), "Account information updated.", Snackbar.LENGTH_LONG).setAction("CLOSE", view12 -> {
            }).setActionTextColor(getResources().getColor(android.R.color.white)).show();
        }

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getNoteById(userid).observe(this, notes -> {
            noteAdapter.setNotes(notes);
        });

        addButton.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, noteAcitivity.class);
            i.putExtra("userid", userid);
            startActivity(i);
        });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent1 = new Intent(getApplicationContext(), noteAcitivity.class);
            intent1.putExtra("position", i + 1);
            startActivity(intent1);
        });
    }

    private void initListView() {
        List<Note> notes = new ArrayList<>();
        noteAdapter = new NoteAdapter(notes);
        listView.setAdapter(noteAdapter);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.updateAccount) {
            Intent intent = new Intent(MainActivity.this, UpdateAccountActivity.class);
            intent.putExtra("userid", userid);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.changePassword) {
            Intent intent = new Intent(MainActivity.this, ResetPasswordActivity.class);
            intent.putExtra("userid", userid);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.logout) {
            SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isChecked", false);
            editor.commit();

            Intent intent = new Intent(MainActivity.this, loginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }
}