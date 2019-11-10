package ru.phpprogrammist.counters;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "records")
@TypeConverters(DateConverter.class)
public class Record {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private int readings;

    public Record(int id,  Date date, int readings) {
        this.id = id;
        this.date = date;
        this.readings = readings;
    }
    @Ignore
    public Record(Date date, int readings) {
        this.date = date;
        this.readings = readings;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setReadings(int readings) {
        this.readings = readings;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getReadings() {
        return readings;
    }



}
