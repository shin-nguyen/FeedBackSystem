package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        holder.assignmentNo.setText(String.valueOf(assignment.getId()));
        holder.assignmentClassName.setText(assignment.getClass_name());
        holder.assignmentCourseName.setText(assignment.getCourse_name());
        holder.assignmentTrainerName.setText(assignment.getTrainer_name());
        holder.assignmentRegistrationCode.setText(assignment.getRegistration_code());
    }

    @Override
    public int getItemCount() {
        if (mListAssignment != null){
            return mListAssignment.size();
        }
        return 0;
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder{

        private TextView assignmentNo, assignmentCourseName, assignmentClassName, assignmentTrainerName, assignmentRegistrationCode;
        private Button editButton, deleteButton;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);

            assignmentNo =itemView.findViewById(R.id.assignment_no);
            assignmentCourseName =itemView.findViewById(R.id.assignment_course_name);
            assignmentClassName =itemView.findViewById(R.id.assignment_class_name);
            assignmentTrainerName =itemView.findViewById(R.id.assignment_trainer_name);
            assignmentRegistrationCode =itemView.findViewById(R.id.assignment_registration_code);
        }
    }
}
