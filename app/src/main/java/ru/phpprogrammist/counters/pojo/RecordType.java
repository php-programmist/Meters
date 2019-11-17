package ru.phpprogrammist.counters.pojo;

public class RecordType {
    private int id;
    private int color;
    private String suffix;
    private int units;

    public RecordType(int id, int color, String suffix, int units) {
        this.id = id;
        this.color = color;
        this.suffix = suffix;
        this.units = units;
    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public String getSuffix() {
        return suffix;
    }

    public int getUnits() {
        return units;
    }
}
