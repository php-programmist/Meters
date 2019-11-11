package ru.phpprogrammist.counters;

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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRecords;
    private final ArrayList<Record> records = new ArrayList<>();
    private RecordsAdapter adapter;

    private MainViewModel viewModel;

    private int recordType = Constants.ELECTRO_TYPE;


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
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerViewRecords = findViewById(R.id.recyclerViewRecords);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        adapter = new RecordsAdapter(records);
        recyclerViewRecords.setLayoutManager(new LinearLayoutManager(this));
        changeType(recordType);
        recyclerViewRecords.setAdapter(adapter);

        setSwipeListner();
    }

    public void onClickAddRecord(View view) {
        Intent intent = new Intent(this,RecordActivity.class);
        intent.putExtra("recordType",recordType);
        startActivity(intent);
    }

    private void getData() {
        LiveData<List<Record>> recordsFromDB = viewModel.getAllByType(recordType);
        recordsFromDB.observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(@Nullable List<Record> recordsFromLiveData) {
                adapter.setRecords(recordsFromLiveData);
            }
        });
    }

    private void changeType(int type){
        recordType = type;
        getData();
        recyclerViewRecords.setBackgroundColor(getResources().getColor(Constants.getColorByType(type)));
    }

    private void remove(int position) {
        Record record = adapter.getRecords().get(position);
        viewModel.deleteRecord(record);
    }
    
    private void setSwipeListner(){
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
