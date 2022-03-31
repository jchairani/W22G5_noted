package com.example.mvvm_run1.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table",
        foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "userid",
        childColumns = "usercreatorid",
        onDelete = ForeignKey.CASCADE)
})
public class Note {
    @PrimaryKey(autoGenerate = true)
    int noteid;

    String notetitle;

    String notecontent;

    int usercreatorid;

    public Note(String notetitle, String notecontent,int usercreatorid) {
        this.notetitle = notetitle;
        this.notecontent = notecontent;
        this.usercreatorid = usercreatorid;
    }

    public int getUsercreatorid() {
        return usercreatorid;
    }

    public void setUsercreatorid(int usercreatorid) {
        this.usercreatorid = usercreatorid;
    }

    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotecontent() {
        return notecontent;
    }

    public void setNotecontent(String notecontent) {
        this.notecontent = notecontent;
    }
}
