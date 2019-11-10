package ru.phpprogrammist.counters;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecordsDao {
    @Query("SELECT * FROM records ORDER BY date Desc")
    LiveData<List<Record>> getAllRecords();

    @Insert
    void insertRecord(Record record);

    @Delete
    void deleteRecord(Record record);

    @Query("DELETE FROM records")
    void deleteAllRecords();
}
