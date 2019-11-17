package ru.phpprogrammist.counters.screens.record;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.Calendar;
import java.util.Date;
import ru.phpprogrammist.counters.R;
import ru.phpprogrammist.counters.data.Record;
import ru.phpprogrammist.counters.databinding.ActivityRecordBinding;
import ru.phpprogrammist.counters.pojo.Constants;

public class RecordActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ConstraintLayout constraintLayout;

    private DatePickerDialog dpd;
    private RecordViewModel viewModel;
    private int recordTypeId;
    private ActivityRecordBinding binding;
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        viewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record);
        constraintLayout = findViewById(R.id.constraintLayout);

        Intent intent = getIntent();
        recordTypeId = intent.getIntExtra("recordTypeId", Constants.ELECTRO_TYPE);
        int recordId = intent.getIntExtra("recordId", 0);

        viewModel.setRecordType(recordTypeId);
        binding.setRecordType(viewModel.getRecordType());

        viewModel.getRecordById(recordId).observe(this,currentRecord -> {
            record = currentRecord;
            initDatePicker(record.getDate());
            binding.setRecord(record);
        });
    }

    public void onClickSaveRecord(View view) {
        viewModel.insertRecord(record);
        finish();
    }

    private void initDatePicker(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        dpd = DatePickerDialog.newInstance(
                RecordActivity.this,
                calendar.get(Calendar.YEAR), // Initial year selection
                calendar.get(Calendar.MONTH), // Initial month selection
                calendar.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth);
        record.setDate(calendar.getTime());
    }

    public void onClickPickDate(View view) {
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }
}
