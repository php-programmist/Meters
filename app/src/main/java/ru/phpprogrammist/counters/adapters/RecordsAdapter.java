package ru.phpprogrammist.counters.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.phpprogrammist.counters.R;
import ru.phpprogrammist.counters.data.Record;
import ru.phpprogrammist.counters.databinding.RecordItemBinding;
import androidx.databinding.DataBindingUtil;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.RecordsViewHolder> {

    private List<Record> records;
    public OnRecordClickListener onRecordClickListener;
    public interface OnRecordClickListener {
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecordItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.record_item, parent, false);
        return new RecordsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordsViewHolder holder, int position) {
        holder.bind(records.get(position));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    class RecordsViewHolder extends RecyclerView.ViewHolder{

        private RecordItemBinding binding;

        public RecordsViewHolder(@NonNull RecordItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onRecordClickListener != null) {
                        onRecordClickListener.onLongClick(getAdapterPosition());
                    }
                    return true;
                }
            });
        }
        public void bind(Record record) {
            binding.setRecord(record);
            binding.executePendingBindings();
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
