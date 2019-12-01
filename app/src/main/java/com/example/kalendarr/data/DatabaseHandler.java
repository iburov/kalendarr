package com.example.kalendarr.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kalendarr.R;
import com.example.kalendarr.model.Event;
import com.example.kalendarr.util.Util;

import java.util.ArrayList;
import java.util.List;

import static com.example.kalendarr.util.Util.DATABASE_NAME;
import static com.example.kalendarr.util.Util.DATABASE_VERSION;
import static com.example.kalendarr.util.Util.TABLE_NAME;
import static com.example.kalendarr.util.Util.KEY_ID;
import static com.example.kalendarr.util.Util.KEY_TITLE;
import static com.example.kalendarr.util.Util.KEY_DESCRIPTION;
import static com.example.kalendarr.util.Util.KEY_DATE;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                "event_id INTERGER, " +
                "event_title TEXT, " +
                "event_description TEXT, " +
                "event_date TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{DATABASE_NAME});

        onCreate(db);
    }

    public void addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, event.getId());
        values.put(KEY_TITLE, event.getTitle());
        values.put(KEY_DESCRIPTION, event.getDescription());
        values.put(KEY_DATE, event.getDate());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public Event getEvent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                Util.TABLE_NAME,
                new String[]{
                        KEY_ID,
                        KEY_TITLE,
                        KEY_DESCRIPTION,
                        KEY_DATE
                },
                KEY_ID + "=?",
                new String[]{
                        String.valueOf(id)
                },
                null,
                null,
                null
        );

        if(cursor != null) {
            cursor.moveToFirst();
            Event e = new Event();
            e.setId(Integer.parseInt(cursor.getString(0)));
            e.setTitle(cursor.getString(1));
            e.setDescription(cursor.getString(2));
            e.setDate(cursor.getString(3));
            db.close();
            return e;
        } else {
            db.close();
            return null;
        }
    }

    public List<Event> getEvents() {
        List<Event> events = new ArrayList<Event>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAll = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(selectAll, null);

        if(cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(Integer.parseInt(cursor.getString(0)));
                event.setTitle(cursor.getString(1));
                event.setDescription(cursor.getString(2));
                event.setDate(cursor.getString(3));
                events.add(event);
            } while(cursor.moveToNext());
        }
        db.close();
        return events;
    }

    public List<Event> getSpecificEvents(String condition) {
        List<Event> events = new ArrayList<Event>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAll = "SELECT * FROM " + TABLE_NAME + " WHERE " + condition;

        Cursor cursor = db.rawQuery(selectAll, null);

        if(cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(Integer.parseInt(cursor.getString(0)));
                event.setTitle(cursor.getString(1));
                event.setDescription(cursor.getString(2));
                event.setDate(cursor.getString(3));
                events.add(event);
            } while(cursor.moveToNext());
        }
        db.close();
        return events;
    }

    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, event.getTitle());
        values.put(KEY_DESCRIPTION, event.getDescription());

        //update the row
        return db.update(
                Util.TABLE_NAME,
                values,
                KEY_ID + "=?",
                new String[]{String.valueOf(event.getId())}
        );
    }

    public int getEventsCount() {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + Util.TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        db.close();
        return cursor.getCount();
    }

    public void deleteEvent(int id) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "DELETE FROM " + Util.TABLE_NAME + " WHERE " + KEY_ID + "=" + id;

        db.execSQL(query);

        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = getReadableDatabase();

        String query = "DELETE FROM " + Util.TABLE_NAME;

        db.execSQL(query);

        db.close();
    }
}
