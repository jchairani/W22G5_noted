package com.example.notEd.model;

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

    String alignment;

    int usercreatorid;

    String imagePath;

    public Note(String notetitle, String notecontent, String alignment, int usercreatorid) {
        this.notetitle = notetitle;
        this.notecontent = notecontent;
        this.alignment = alignment;
        this.usercreatorid = usercreatorid;
//        this.imagePath = imagePath;
    }

    public Note() {
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
