package ru.phpprogrammist.counters.screens.record;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;

import java.util.List;

import ru.phpprogrammist.counters.data.Record;
import ru.phpprogrammist.counters.data.RecordsDatabase;
import ru.phpprogrammist.counters.pojo.Constants;
import ru.phpprogrammist.counters.pojo.Preferences;
import ru.phpprogrammist.counters.pojo.RecordType;
import ru.phpprogrammist.counters.pojo.RecordTypes;

public class RecordViewModel extends AndroidViewModel{

    private static RecordsDatabase database;

    private LiveData<List<Record>> records;
    private SparseArray<RecordType> recordTypes = (new RecordTypes()).getTypes();
    private RecordType recordType = recordTypes.get(Constants.ELECTRO_TYPE);
    private SharedPreferences prefs;

    public RecordViewModel(@NonNull Application application) {
        super(application);
        Context context = getApplication();
        database = RecordsDatabase.getInstance(context);
        records = database.recordsDao().getAllRecords();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Preferences getPreferences() {
        return new Preferences(prefs,recordType.getSuffix());
    }

    public void setRecordType(int recordType) {
        this.recordType = recordTypes.get(recordType);
    }

    public RecordType getRecordType() {
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
        return database.recordsDao().getAllByType(recordType.getId());
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

