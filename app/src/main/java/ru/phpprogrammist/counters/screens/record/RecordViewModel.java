package ru.phpprogrammist.counters.screens.record;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;

import java.util.Calendar;
import java.util.List;

import ru.phpprogrammist.counters.data.Record;
import ru.phpprogrammist.counters.data.RecordsDatabase;
import ru.phpprogrammist.counters.pojo.Constants;
import ru.phpprogrammist.counters.pojo.Preferences;
import ru.phpprogrammist.counters.pojo.RecordType;
import ru.phpprogrammist.counters.pojo.RecordTypes;

public class RecordViewModel extends AndroidViewModel{

    private static RecordsDatabase database;

    private SparseArray<RecordType> recordTypes = (new RecordTypes()).getTypes();
    private RecordType recordType = recordTypes.get(Constants.ELECTRO_TYPE);
    private MutableLiveData<Record> currentRecord = new MutableLiveData<>();

    public RecordViewModel(@NonNull Application application) {
        super(application);
        Context context = getApplication();
        database = RecordsDatabase.getInstance(context);
    }

    public LiveData<Record> getRecordById(int recordId){
        if (recordId > 0){
            return database.recordsDao().getOneById(recordId);
        }else{
            Calendar now = Calendar.getInstance();
            currentRecord.setValue(new Record(now.getTime(),0,recordType.getId()));
            return currentRecord;
        }
    }


    public void setRecordType(int recordType) {
        this.recordType = recordTypes.get(recordType);
    }

    public RecordType getRecordType() {
        return recordType;
    }


    public void insertRecord(Record record) {
        new InsertTask().execute(record);
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
}

