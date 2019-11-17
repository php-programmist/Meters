package ru.phpprogrammist.counters.pojo;
import ru.phpprogrammist.counters.R;

import android.util.SparseArray;

public class RecordTypes {
    private SparseArray<RecordType> types = new SparseArray<>();

    public RecordTypes() {
        types.put(Constants.ELECTRO_TYPE,new RecordType(Constants.ELECTRO_TYPE,R.color.colorElectro,"electro",R.string.units_kwatt));
        types.put(Constants.WATER_TYPE,new RecordType(Constants.WATER_TYPE,R.color.colorWater,"water",R.string.units_m3));
        types.put(Constants.GAS_TYPE,new RecordType(Constants.GAS_TYPE,R.color.colorGas,"gas",R.string.units_m3));
    }

    public SparseArray<RecordType> getTypes() {
        return types;
    }
}
