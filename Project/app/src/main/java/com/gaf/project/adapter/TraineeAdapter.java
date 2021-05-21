package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Class;
import com.gaf.project.model.Trainee;
import com.gaf.project.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.List;

public class TraineeAdapter extends RecyclerView.Adapter<TraineeAdapter.TraineeViewHolder>{

    private List<Trainee> mListTrainee;
    public void setData(List<Trainee> list){
        this.mListTrainee = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TraineeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trainee,parent,false);

        return new TraineeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TraineeViewHolder holder, int position) {
        Trainee mTrainee =mListTrainee.get(position);

        if(mTrainee==null){
            return;
        }

        holder.number.setText(String.valueOf(position));
        holder.id.setText(String.valueOf(mTrainee.getUserName()));
        holder.name.setText(mTrainee.getName());

    }

    @Override
    public int getItemCount() {
        if (mListTrainee != null){
            return mListTrainee.size();
        }
        return 0;
    }

    public class TraineeViewHolder extends RecyclerView.ViewHolder {

        private TextView id, name,number;

        public TraineeViewHolder(@NonNull View view) {
            super(view);
            initView(view);
        }

        private void initView(View view) {
            id = view.findViewById(R.id.txt_id_trainee);
            name = view.findViewById(R.id.txt_trainne_name);
            number = view.findViewById(R.id.txt_number);
        }
    }
}

