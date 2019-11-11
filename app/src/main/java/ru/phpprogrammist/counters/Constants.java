package ru.phpprogrammist.counters;

import android.util.SparseIntArray;


public class Constants {
    public static final int ELECTRO_TYPE = 1;
    public static final int WATER_TYPE = 2;
    public static final int GAS_TYPE = 3;
    public static SparseIntArray TYPE_COLOR_MAP;

    public static int getColorByType(int type) {
        TYPE_COLOR_MAP = new SparseIntArray();
        if (TYPE_COLOR_MAP.size() == 0) {
            TYPE_COLOR_MAP.put(ELECTRO_TYPE, R.color.colorElectro);
            TYPE_COLOR_MAP.put(WATER_TYPE, R.color.colorWater);
            TYPE_COLOR_MAP.put(GAS_TYPE, R.color.colorGas);
        }
        return TYPE_COLOR_MAP.get(type);
    }
}
