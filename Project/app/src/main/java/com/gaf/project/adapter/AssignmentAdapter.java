package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.Assignment;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private List<Assignment> mListAssignment;

    public void setData(List<Assignment> list){
        this.mListAssignment = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment,parent,false);

        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {

        Assignment assignment =mListAssignment.get(position);

        if(assignment==null){
            return;
        }

        holder.AssignmentNo.setText(String.valueOf(assignment.getId()));
        holder.AssignmentClassName.setText(assignment.getClass_name());
        holder.AssignmentCourseName.setText(assignment.getCourse_name());
        holder.AssignmentTrainerName.setText(assignment.getTrainer_name());
        holder.AssignmentRegistrationCode.setText(assignment.getRegistration_code());
    }

    @Override
    public int getItemCount() {
        if (mListAssignment != null){
            return mListAssignment.size();
        }
        return 0;
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder{

        private TextView AssignmentNo, AssignmentCourseName, AssignmentClassName, AssignmentTrainerName, AssignmentRegistrationCode;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);

            AssignmentNo =itemView.findViewById(R.id.assignment_no);
            AssignmentCourseName =itemView.findViewById(R.id.assignment_course_name);
            AssignmentClassName =itemView.findViewById(R.id.assignment_class_name);
            AssignmentTrainerName =itemView.findViewById(R.id.assignment_trainer_name);
            AssignmentRegistrationCode =itemView.findViewById(R.id.assignment_registration_code);
        }
    }
}
