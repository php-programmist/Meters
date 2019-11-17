package ru.phpprogrammist.counters.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;


import ru.phpprogrammist.counters.converters.DateConverter;

@Entity(tableName = "records")
@TypeConverters(DateConverter.class)
public class Record extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private int readings;
    private int type = 1;

    public Record(int id,  Date date, int readings, int type) {
        this.id = id;
        this.date = date;
        this.readings = readings;
        this.type = type;
    }
    @Ignore
    public Record(Date date, int readings, int type) {
        this.date = date;
        this.readings = readings;
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    public void setReadings(int readings) {
        this.readings = readings;
        notifyPropertyChanged(BR.readings);
    }

    public int getId() {
        return id;
    }
    @Bindable
    public Date getDate() {
        return date;
    }
    @Bindable
    public int getReadings() {
        return readings;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
