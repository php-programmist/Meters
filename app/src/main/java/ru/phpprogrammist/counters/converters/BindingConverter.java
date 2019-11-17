package ru.phpprogrammist.counters.converters;

import androidx.databinding.BindingConversion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BindingConverter {
    @BindingConversion
    public static String convertDateToString(Date date) {
        if (date == null){
            return "0";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY", Locale.getDefault());
        return dateFormat.format(date);
    }

}
