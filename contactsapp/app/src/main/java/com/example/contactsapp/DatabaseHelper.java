package com.example.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contactapp.db";
    private static final int DATABASE_VERSION = 8;
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_PHOTO_PATH = "photo_path";

    // SQL Create Table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PHONE + " TEXT, " +
                    COLUMN_PHOTO_PATH + " TEXT" + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean insertUser(user user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PHONE, user.getNumber());
        values.put(COLUMN_PHOTO_PATH, user.getPhotoPath());

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    public ArrayList<user> getAllUsers() {
        ArrayList<user> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
                int photoPathIndex = cursor.getColumnIndex(COLUMN_PHOTO_PATH);

                if (idIndex != -1 && nameIndex != -1 && phoneIndex != -1 && photoPathIndex != -1) {
                    do {
                        int id = cursor.getInt(idIndex);
                        String name = cursor.getString(nameIndex);
                        String number = cursor.getString(phoneIndex);
                        String photoPath = cursor.getString(photoPathIndex);
                        userList.add(new user(id, name, number, photoPath));
                    } while (cursor.moveToNext());
                } else {
                    // Handle the error case where columns are not found
                    throw new RuntimeException("One or more columns not found in cursor");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return userList;
    }

    public user getUserById(int id) {
        user user = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
                int photoPathIndex = cursor.getColumnIndex(COLUMN_PHOTO_PATH);

                if (nameIndex != -1 && phoneIndex != -1 && photoPathIndex != -1) {
                    String name = cursor.getString(nameIndex);
                    String number = cursor.getString(phoneIndex);
                    String photoPath = cursor.getString(photoPathIndex);
                    user = new user(id, name, number, photoPath);
                } else {
                    // Handle the error case where columns are not found
                    throw new RuntimeException("One or more columns not found in cursor");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return user;
    }

    public void updateUser(user user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, user.getName());
        contentValues.put(COLUMN_PHONE, user.getNumber());
        contentValues.put(COLUMN_PHOTO_PATH, user.getPhotoPath());

        int rowsAffected = db.update(TABLE_USERS, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();

        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to update user. No rows affected.");
        }
    }


    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, null, null);
        db.close();
    }
}
