package com.amielskywalker.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Amiel Skywalker on 9/4/2016.
 */
public class NotebookDBAdapter {
    // Database info
    private static final String DATABASE_NAME = "notebook.db";
    private static final int DATABASE_VERSION = 1;

    // Note Values
    public static final String NOTE_TABLE = "note";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATE = "date";

    private String[] allColumns = {COLUMN_ID, COLUMN_TITLE, COLUMN_MESSAGE, COLUMN_CATEGORY, COLUMN_DATE};

    // SQL query to make database table and column
    public static final String CREATE_TABLE_NOTE = "CREATE TABLE " + NOTE_TABLE + " ( "
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " TEXT NOT NULL, "
            + COLUMN_MESSAGE + " TEXT NOT NULL, "
            + COLUMN_CATEGORY + " INTEGER NOT NULL, "
            + COLUMN_DATE + " );";

    // Create database
    private SQLiteDatabase sqlDB;
    private Context context;

    // Instance of NoteBookDBHelper
    private NotebookDBHelper notebookDBHelper;

    public NotebookDBAdapter(Context ctx) {
        context = ctx;
    }

    // Grabs a database and set it to local sqlDB variable
    public NotebookDBAdapter open() throws android.database.SQLException {
        notebookDBHelper = new NotebookDBHelper(context);
        sqlDB = notebookDBHelper.getWritableDatabase();
        return this;
    }

    // Close database
    public void close(){
        notebookDBHelper.close();
    }

    // Create a new note
    public Note createNote(String title, String message, Note.Category category){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_CATEGORY, category.name());
        values.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis()+ "");

        long insertId = sqlDB.insert(NOTE_TABLE, null, values);

        Cursor cursor = sqlDB.query(NOTE_TABLE,
                allColumns, COLUMN_ID + " = " + insertId, null, null, null, null );

        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();

        return newNote;
    }

    // Updatae a note
    public long updateNote(long idToUpdate, String newTitle, String newMessage, Note.Category newCategory) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newTitle);
        values.put(COLUMN_MESSAGE, newMessage);
        values.put(COLUMN_CATEGORY, newCategory.name());
        values.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis()+ "");

        return sqlDB.update(NOTE_TABLE, values, COLUMN_ID + " = " + idToUpdate, null );
    }

    // Delete note from database
    public long deleteNote (long idToDelete) {
        return sqlDB.delete(NOTE_TABLE, COLUMN_ID + " = " + idToDelete, null);
    }


    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<Note>();

        // Get all information in the database of notes
        Cursor cursor = sqlDB.query(NOTE_TABLE, allColumns, null, null, null, null, null);

        /* Loop through all the rows (notes) in the database and create new objects from the
        * rows and add them to the ArrayList*/
        for(cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()){
            Note note = cursorToNote(cursor);
            notes.add(note);
        }

        // Close the cursor
        cursor.close();

        // Return the ArrayList with all the notes from the database
        return notes;

    }// END OF getAllNotes

    // Give a cursor that returns a note object
    public Note cursorToNote(Cursor cursor) {
        Note newNote = new Note( cursor.getString(1), cursor.getString(2),
                Note.Category.valueOf(cursor.getString(3)), cursor.getLong(0), cursor.getLong(4));
        return newNote;
    }




    // Inner class
    private static class NotebookDBHelper extends SQLiteOpenHelper {

        NotebookDBHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }// END OF CONSTRUCTOR

        @Override
        public void onCreate(SQLiteDatabase db){
            // Create note table when database is created
            db.execSQL(CREATE_TABLE_NOTE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w(NotebookDBHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                        + newVersion +", and will destroy al old data.");
            // Destroy table
            db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE );

            // Create a new database
            onCreate(db);
        }

    }// END OF INNER CLASS NotebookDBHelper

}
