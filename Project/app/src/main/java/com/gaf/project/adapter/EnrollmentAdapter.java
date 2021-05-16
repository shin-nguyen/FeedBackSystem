package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.Class;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Trainee;

import java.util.List;

public class EnrollmentAdapter extends RecyclerView.Adapter<EnrollmentAdapter.EnrollmentViewHolder> {

    private List<Class> mListClass;
    private List<Trainee> mListTrainee;

    public void setClassData(List<Class> list){
        this.mListClass = list;
        notifyDataSetChanged();
    }

    public void setTraineeData(List<Trainee> list){
        this.mListTrainee = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public EnrollmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback,parent,false);

        return new EnrollmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnrollmentViewHolder holder, int position) {

        Class clazz = mListClass.get(position);
        Trainee trainee = mListTrainee.get(position);

        if (clazz == null || trainee == null)
            return;

        holder.classId.setText(clazz.getClassID());
        holder.className.setText(clazz.getClassName());
        holder.traineeId.setText(trainee.getUserName());
        holder.traineeName.setText(trainee.getName());
    }

    @Override
    public int getItemCount() {
        if (mListClass != null && mListTrainee != null)
            return mListClass.size() * mListTrainee.size();
        return 0;
    }

    public class EnrollmentViewHolder extends RecyclerView.ViewHolder{

        private TextView traineeId, traineeName, classId, className;
        private Button detailButton, editButton, deleteButton;
        public EnrollmentViewHolder(@NonNull View itemView) {
            super(itemView);

            traineeId = itemView.findViewById(R.id.trainee_id);
            traineeName = itemView.findViewById(R.id.trainee_name);
            classId = itemView.findViewById(R.id.class_id);
            className = itemView.findViewById(R.id.class_name);
        }
    }

}
