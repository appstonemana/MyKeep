package com.mb.mykeep.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mb.mykeep.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manabendu on 2019-09-19
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "NOTE.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Note";
    public static final String COL_ID = "ID";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_TEXT = "CONTENT";

    Context context;


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,CONTENT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public long insertData(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TITLE, title);
        contentValues.put(COL_TEXT, content);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT);

        return result;
    }


    public List<Note> getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);

        List<Note> notes = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.id = cursor.getString(cursor.getColumnIndex(COL_ID));
                note.title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                note.content = cursor.getString(cursor.getColumnIndex(COL_TEXT));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();
        return notes;
    }

    public boolean updateData(String id, String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, id);
        contentValues.put(COL_TITLE, title);
        contentValues.put(COL_TEXT, content);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        db.close();
        return true;
    }

    public void deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID = ?", new String[]{id});
        db.close();

    }

}
