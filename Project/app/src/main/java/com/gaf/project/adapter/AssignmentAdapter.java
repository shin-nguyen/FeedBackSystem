package com.gaf.project.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.MainActivity;
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

        holder.assignmentNo.setText("1");
        holder.assignmentClassName.setText(String.valueOf(assignment.getmClass().getClassName()));
        holder.assignmentCourseName.setText(String.valueOf(assignment.getModule().getModuleName()));
        holder.assignmentTrainerName.setText(String.valueOf(assignment.getTrainer().getName()));
        holder.assignmentRegistrationCode.setText(assignment.getRegistrationCode());
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
        private Button editAssignment, deleteAssignment;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);

            assignmentNo =itemView.findViewById(R.id.assignment_no);
            assignmentCourseName =itemView.findViewById(R.id.assignment_course_name);
            assignmentClassName =itemView.findViewById(R.id.assignment_class_name);
            assignmentTrainerName =itemView.findViewById(R.id.assignment_trainer_name);
            assignmentRegistrationCode =itemView.findViewById(R.id.assignment_registration_code);
            editAssignment=itemView.findViewById(R.id.btn_edit_assignment);
            deleteAssignment=itemView.findViewById(R.id.btn_delete_assignment);

            editAssignment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            deleteAssignment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayDeleteDialog();
                }
            });
        }

        public void displayDeleteDialog(){
            AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());

            alert.setTitle("Details");
            alert.setMessage("Enter your basic details");
            alert.setCancelable(false);


            alert.setNegativeButton(Html.fromHtml("<font color='#26AC33'>CANCEL</font>"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alert.setPositiveButton(Html.fromHtml("<font color='#26AC33'>ADD</font>"), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = alert.create();
            dialog.show();
        }
    }
}
