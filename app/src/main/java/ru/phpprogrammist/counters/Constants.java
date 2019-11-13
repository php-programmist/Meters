package ru.phpprogrammist.counters;

import android.util.SparseArray;
import android.util.SparseIntArray;


public class Constants {
    public static final int ELECTRO_TYPE = 1;
    public static final int WATER_TYPE = 2;
    public static final int GAS_TYPE = 3;
    public static SparseIntArray TYPE_COLOR_MAP;
    public static SparseArray<String> TYPE_SUFFIX_MAP;

    public static int getColorByType(int type) {
        TYPE_COLOR_MAP = new SparseIntArray();
        if (TYPE_COLOR_MAP.size() == 0) {
            TYPE_COLOR_MAP.put(ELECTRO_TYPE, R.color.colorElectro);
            TYPE_COLOR_MAP.put(WATER_TYPE, R.color.colorWater);
            TYPE_COLOR_MAP.put(GAS_TYPE, R.color.colorGas);
        }
        return TYPE_COLOR_MAP.get(type);
    }

    public static String getSuffixByType(int type) {
        TYPE_SUFFIX_MAP = new SparseArray<>();
        if (TYPE_SUFFIX_MAP.size() == 0) {
            TYPE_SUFFIX_MAP.put(ELECTRO_TYPE, "electro");
            TYPE_SUFFIX_MAP.put(WATER_TYPE, "water");
            TYPE_SUFFIX_MAP.put(GAS_TYPE, "gas");
        }
        return TYPE_SUFFIX_MAP.get(type);
    }
}
