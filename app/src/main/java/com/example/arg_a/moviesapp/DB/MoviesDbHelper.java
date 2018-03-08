package com.example.arg_a.moviesapp.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.arg_a.moviesapp.DB.MoviesContract.MoviesTable;

/**
 * Created by arg-a on 08/03/2018.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 1;


    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " +  MoviesTable.TABLE_NAME + "(" +
                MoviesTable._ID + " INTEGER PRIMARY KEY," +
                MoviesTable.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL," +
                MoviesTable.COLUMN_POSTER_IMAGE + " TEXT NOT NULL," +
                MoviesTable.COLUMN_RATING + " TEXT NOT NULL," +
                MoviesTable.COLUMN_RELEASE_DATE + " TEXT NOT NULL," +
                MoviesTable.COLUMN_SYNOPSIS + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
