package ru.phpprogrammist.counters;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Record.class}, version = 2, exportSchema = false)
public abstract class RecordsDatabase extends RoomDatabase {
    private static RecordsDatabase database;
    private static final String DB_NAME = "records.db";
    private static final Object LOCK = new Object();
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE records ADD COLUMN type INTEGER DEFAULT 1 NOT NULL");
        }
    };
    public static RecordsDatabase getInstance(Context context) {

        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, RecordsDatabase.class, DB_NAME)
                        .addMigrations(RecordsDatabase.MIGRATION_1_2)
                        .build();
            }
        }
        return database;
    }

    public abstract RecordsDao recordsDao();
}
