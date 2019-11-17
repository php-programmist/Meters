package ru.phpprogrammist.counters.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ru.phpprogrammist.counters.R;
import ru.phpprogrammist.counters.data.Record;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.RecordsViewHolder> {

    private List<Record> records;
    private OnRecordClickListener onRecordClickListener;
    interface OnRecordClickListener {
        void onRecordClick(int position);
        void onLongClick(int position);
    }

    public void setOnRecordClickListener(OnRecordClickListener onRecordClickListener) {
        this.onRecordClickListener = onRecordClickListener;
    }
    public RecordsAdapter(List<Record> records) {

        this.records = records;
    }

    @NonNull
    @Override
    public RecordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item,parent,false);
        return new RecordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordsViewHolder holder, int position) {
        Record record = records.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY", Locale.getDefault());
        holder.textViewDate.setText(dateFormat.format(record.getDate()));
        holder.textViewReadings.setText(String.format("%s", record.getReadings()));

    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    class RecordsViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewDate;
        private TextView textViewReadings;

        public RecordsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewReadings = itemView.findViewById(R.id.textViewReadings);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecordClickListener != null) {
                        onRecordClickListener.onRecordClick(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onRecordClickListener != null) {
                        onRecordClickListener.onLongClick(getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }

    public void setRecords(List<Record> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    public List<Record> getRecords() {
        return records;
    }
}
