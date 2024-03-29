package ru.phpprogrammist.counters.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecordsDao {
    @Query("SELECT * FROM records ORDER BY date Desc")
    LiveData<List<Record>> getAllRecords();

    @Query("SELECT * FROM records WHERE type = :type ORDER BY date Desc")
    LiveData<List<Record>> getAllByType(int type);

    @Query("SELECT * FROM records WHERE id = :id")
    LiveData<Record> getOneById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    void insertRecord(Record record);

    @Delete
    void deleteRecord(Record record);

    @Query("DELETE FROM records")
    void deleteAllRecords();
}
