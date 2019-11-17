package ru.phpprogrammist.counters.adapters;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

public class BindingsAdapter {
    @BindingAdapter("android:text")
    public static void setText(TextView view, int value) {
        if (value > 0){
            String old_value = view.getText().toString();
            String new_value = Integer.toString(value);
            if (!old_value.equals(new_value)){
                view.setText(new_value);
            }
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int getText(TextView view) {
        String value = view.getText().toString();
        if (value.isEmpty()){
            return 0;
        }
        return Integer.parseInt(value);
    }
}
