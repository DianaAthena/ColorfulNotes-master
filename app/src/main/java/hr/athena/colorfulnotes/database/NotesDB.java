package hr.athena.colorfulnotes.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import hr.athena.colorfulnotes.model.Note;


@Database(entities = Note.class, version = 2, exportSchema = false)
public abstract class NotesDB extends RoomDatabase {

    public abstract NotesDao notesDao();

    private static final String DATABASE_NAME = "notesDb2";

    private static NotesDB instance;

    public static NotesDB getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, NotesDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }


}

