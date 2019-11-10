package ru.phpprogrammist.counters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;


import java.util.Calendar;
import java.util.Date;

public class RecordActivity extends AppCompatActivity {

    private EditText editTextReadings;
    private DatePicker datePicker;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        editTextReadings = findViewById(R.id.editTextReadings);
        datePicker = findViewById(R.id.datePicker);
        datePicker.setCalendarViewShown(false);
        datePicker.setSpinnersShown(true);
    }


    public void onClickSaveRecord(View view) {
        int readings = Integer.parseInt(editTextReadings.getText().toString().trim());
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        Date date = calendar.getTime();
        Record record = new Record(date,readings);
        viewModel.insertRecord(record);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
