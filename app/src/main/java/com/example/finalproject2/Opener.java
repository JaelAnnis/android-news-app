package com.example.finalproject2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Opener.
 *
 * This is used to interact with the database.
 */
public class Opener extends SQLiteOpenHelper {

    // Set the database attributes.
    protected final static String DATABASE_NAME = "FinalProject";
    protected final static int VERSION_NUM = 4;
    public final static String TABLE_NAME = "favourites";
    public final static String TITLE = "title";
    public final static String SECTION = "section";
    public final static String ITEM_ID = "item_id";
    public final static String URL = "url";

    /**
     * Constructor.
     *
     * @param ctx
     */
    public Opener(Context ctx) {

        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * On create.
     *
     * @param db
     */
    public void onCreate(SQLiteDatabase db) {

        // Create the database table.
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(item_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT,"
                + SECTION + " TEXT,"
                + URL + " TEXT);");
    }

    /**
     * On upgrade.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }
}