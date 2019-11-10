package ru.phpprogrammist.counters;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static RecordsDatabase database;

    private LiveData<List<Record>> records;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = RecordsDatabase.getInstance(getApplication());
        records = database.recordsDao().getAllRecords();
    }

    public LiveData<List<Record>> getRecords() {
        return records;
    }

    public void insertRecord(Record record) {
        new InsertTask().execute(record);
    }

    public void deleteRecord(Record record) {
        new DeleteTask().execute(record);
    }

    public void deleteAllRecords() {
        new DeleteAllTask().execute();
    }

    private static class InsertTask extends AsyncTask<Record, Void, Void> {
        @Override
        protected Void doInBackground(Record... records) {
            if (records != null && records.length > 0) {
                database.recordsDao().insertRecord(records[0]);
            }
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Record, Void, Void> {
        @Override
        protected Void doInBackground(Record... records) {
            if (records != null && records.length > 0) {
                database.recordsDao().deleteRecord(records[0]);
            }
            return null;
        }
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... records) {
            database.recordsDao().deleteAllRecords();
            return null;
        }
    }
}

