package ru.phpprogrammist.counters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;



import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RecordActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ConstraintLayout constraintLayout;
    private EditText editTextReadings;
    private TextView textViewDate;
    private Date selectedDate;
    private DatePickerDialog dpd;
    private MainViewModel viewModel;
    private int recordType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        constraintLayout = findViewById(R.id.constraintLayout);
        editTextReadings = findViewById(R.id.editTextReadings);
        textViewDate = findViewById(R.id.textViewDate);

        Intent intent = getIntent();
        recordType = intent.getIntExtra("recordType",Constants.ELECTRO_TYPE);
        constraintLayout.setBackgroundColor(getResources().getColor(Constants.getColorByType(recordType)));
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                RecordActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        selectedDate = now.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY", Locale.getDefault());
        String date = dateFormat.format(selectedDate);
        textViewDate.setText(date);
    }

    public void onClickSaveRecord(View view) {
        int readings = Integer.parseInt(editTextReadings.getText().toString().trim());

        Record record = new Record(selectedDate,readings,recordType);
        viewModel.insertRecord(record);
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth);
        selectedDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY", Locale.getDefault());
        String date = dateFormat.format(selectedDate);

        textViewDate.setText(date);

    }

    public void onClickPickDate(View view) {
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }
}
