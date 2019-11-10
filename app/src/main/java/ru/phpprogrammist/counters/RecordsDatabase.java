package ru.phpprogrammist.counters;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Record.class}, version = 1, exportSchema = false)
public abstract class RecordsDatabase extends RoomDatabase {
    private static RecordsDatabase database;
    private static final String DB_NAME = "records.db";
    private static final Object LOCK = new Object();

    public static RecordsDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, RecordsDatabase.class, DB_NAME)
                        .build();
            }
        }
        return database;
    }

    public abstract RecordsDao recordsDao();
}
