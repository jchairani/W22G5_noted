package com.example.notEd.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.notEd.R;
import com.example.notEd.model.Note;

import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private List<Note> notes;
    private String storedContent;

    public NoteAdapter(List<Note> notes){
        this.notes = notes;
    }
    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
            view = li.inflate(R.layout.note_item,viewGroup,false);
        }

        TextView title = view.findViewById(R.id.noteTItle);
        TextView content = view.findViewById(R.id.noteContent);

        title.setText(notes.get(i).getNotetitle());
        content.setText(notes.get(i).getNotecontent());

        storedContent = content.getText().toString();
        content.setText(Html.fromHtml(storedContent));

        return view;
    }
}
