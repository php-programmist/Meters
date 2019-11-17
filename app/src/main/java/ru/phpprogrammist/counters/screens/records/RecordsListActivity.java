package ru.phpprogrammist.counters.screens.records;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import ru.phpprogrammist.counters.helpers.MathHelper;
import ru.phpprogrammist.counters.R;
import ru.phpprogrammist.counters.screens.record.RecordActivity;
import ru.phpprogrammist.counters.screens.settings.SettingsActivity;
import ru.phpprogrammist.counters.adapters.RecordsAdapter;
import ru.phpprogrammist.counters.data.Record;
import ru.phpprogrammist.counters.pojo.Constants;
import ru.phpprogrammist.counters.pojo.Preferences;

public class RecordsListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRecords;
    private List<Record> records = new ArrayList<>();
    private RecordsAdapter adapter;
    private TextView textViewDifference;
    private TextView textViewPay;
    private SharedPreferences prefs;

    private RecordsListViewModel viewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_electro:
                    changeType(Constants.ELECTRO_TYPE);
                    return true;
                case R.id.navigation_water:
                    changeType(Constants.WATER_TYPE);
                    return true;
                case R.id.navigation_gas:
                    changeType(Constants.GAS_TYPE);
                    return true;
                case R.id.navigation_preferences:
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intent);
                    return false;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(RecordsListViewModel.class);
        recyclerViewRecords = findViewById(R.id.recyclerViewRecords);
        textViewDifference = findViewById(R.id.textViewDifference);
        textViewPay = findViewById(R.id.textViewPay);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        adapter = new RecordsAdapter(records);
        recyclerViewRecords.setLayoutManager(new LinearLayoutManager(this));
        changeType(viewModel.getRecordType().getId());
        recyclerViewRecords.setAdapter(adapter);

        setSwipeListner();
    }

    @Override
    protected void onStart() {
        super.onStart();
        calculate();
    }

    public void onClickAddRecord(View view) {
        Intent intent = new Intent(this, RecordActivity.class);
        intent.putExtra("recordType", viewModel.getRecordType().getId());
        startActivity(intent);
    }

    private void getData() {
        LiveData<List<Record>> recordsFromDB = viewModel.getAllByType();
        recordsFromDB.observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(@Nullable List<Record> recordsFromLiveData) {
                records = recordsFromLiveData;
                adapter.setRecords(recordsFromLiveData);
                calculate();
            }
        });
    }

    private void calculate(){
        int difference = 0;
        double pay = 0;
        Preferences preferences = viewModel.getPreferences();
        if(records != null && records.size() > 1){
            int nowReadings = records.get(0).getReadings();
            int lastReadings = records.get(1).getReadings();
            difference = Math.abs(nowReadings-lastReadings);
            int mutableDifference = difference;

            if (preferences.getCost_units() > 0){
                if (preferences.getPreferential_rate() && preferences.getPreferential_units() > 0 ){
                    pay = preferences.getPreferential_cost_units() * (difference > preferences.getPreferential_units()?preferences.getPreferential_units():difference);
                    mutableDifference -= preferences.getPreferential_units();
                }
                if (mutableDifference>0){
                    pay += preferences.getCost_units() * mutableDifference;
                }
                pay = MathHelper.round(pay,2);
            }
        }
        textViewDifference.setText(String.format(String.valueOf(getResources().getText(R.string.difference_label_s)),difference));
        textViewPay.setText(String.format(String.valueOf(getResources().getText(R.string.pay_label_s)),pay,preferences.getCurrency()));
    }

    private void changeType(int type) {
        viewModel.setRecordType(type);
        getData();
        recyclerViewRecords.setBackgroundColor(getResources().getColor(viewModel.getRecordType().getColor()));
    }


    private void remove(int position) {
        Record record = adapter.getRecords().get(position);
        viewModel.deleteRecord(record);
    }

    private void setSwipeListner() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewRecords);
    }

}
