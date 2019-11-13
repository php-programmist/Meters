package ru.phpprogrammist.counters;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;

import java.util.List;

public class MainViewModel extends AndroidViewModel{

    private static RecordsDatabase database;

    private LiveData<List<Record>> records;
    private int recordType = Constants.ELECTRO_TYPE;
    private SharedPreferences prefs;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Context context = getApplication();
        database = RecordsDatabase.getInstance(context);
        records = database.recordsDao().getAllRecords();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Preferences getPreferences() {
        return new Preferences(prefs,Constants.getSuffixByType(recordType));
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    public int getRecordType() {
        return recordType;
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

    LiveData<List<Record>> getAllByType(){
        return database.recordsDao().getAllByType(recordType);
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

