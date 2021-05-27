package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.PercentValue;

import java.util.List;

public class ResultPercentAdapter extends RecyclerView.Adapter<ResultPercentAdapter.ResultPercentViewHolder> {

    private List<PercentValue> mListValue;

    public void setData(List<PercentValue> list){
        this.mListValue = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultPercentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_percent,parent,false);

        return new ResultPercentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultPercentViewHolder holder, int position) {
        PercentValue mValue = mListValue.get(position);

        if(mValue==null){
            return;
        }

        holder.tvQuestionContent.setText(mValue.getQuestionContent());
        holder.tvPercent.setText(mValue.getPercent());
        holder.tvPercent1.setText(mValue.getPercent1());
        holder.tvPercent2.setText(mValue.getPercent2());
        holder.tvPercent3.setText(mValue.getPercent3());
        holder.tvPercent4.setText(mValue.getPercent4());
    }

    @Override
    public int getItemCount() {
        if (mListValue != null){
            return mListValue.size();
        }
        return 0;
    }

    public class ResultPercentViewHolder extends RecyclerView.ViewHolder {

        private TextView tvQuestionContent, tvPercent, tvPercent1, tvPercent2, tvPercent3, tvPercent4;

        public ResultPercentViewHolder(@NonNull View view) {
            super(view);

            tvQuestionContent = view.findViewById(R.id.tvQuestionContent);
            tvPercent = view.findViewById(R.id.tvPercent);
            tvPercent1 = view.findViewById(R.id.tvPercent1);
            tvPercent2 = view.findViewById(R.id.tvPercent2);
            tvPercent3 = view.findViewById(R.id.tvPercent3);
            tvPercent4 = view.findViewById(R.id.tvPercent4);

        }
    }
}
