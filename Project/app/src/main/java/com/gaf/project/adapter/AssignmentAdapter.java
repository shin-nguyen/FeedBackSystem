package com.gaf.project.adapter;

import android.app.AlertDialog;
import android.content.Context;
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
    private Context mContext;

    public void setData(List<Assignment> list, Context context){
        this.mListAssignment = list;
        this.mContext= context;
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

        holder.assignmentNo.setText(String.valueOf(assignment.getModule().getModuleID()));
        holder.className.setText(String.valueOf(assignment.getmClass().getClassName()));
        holder.courseName.setText(String.valueOf(assignment.getModule().getModuleName()));
        holder.trainerName.setText(String.valueOf(assignment.getTrainer().getName()));
        holder.registrationCode.setText(assignment.getRegistrationCode());
    }

    @Override
    public int getItemCount() {
        if (mListAssignment != null){
            return mListAssignment.size();
        }
        return 0;
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder{

        private TextView assignmentNo, courseName, className, trainerName, registrationCode;
        private Button editAssignment, deleteAssignment;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);

            assignmentNo =itemView.findViewById(R.id.assignment_no);
            courseName =itemView.findViewById(R.id.assignment_course_name);
            className =itemView.findViewById(R.id.assignment_class_name);
            trainerName =itemView.findViewById(R.id.assignment_trainer_name);
            registrationCode =itemView.findViewById(R.id.assignment_registration_code);
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

                    AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                    View view=LayoutInflater.from(mContext).inflate(R.layout.warning_dialog,null);

                    builder.setView(view);

                    final AlertDialog warningDialog=builder.create();
                    warningDialog.show();

                    TextView warningContent = view.findViewById(R.id.txt_warning_content);
                    warningContent.setText("Do you want to delete this Assignment?");

                    Button btnCancel = view.findViewById(R.id.btn_cancel);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            warningDialog.dismiss();
                        }
                    });

                    Button btnYes = view.findViewById(R.id.btn_yes);
                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            warningDialog.dismiss();

                            AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                            View view=LayoutInflater.from(mContext).inflate(R.layout.successful_dialog,null);

                            builder.setView(view);

                            final AlertDialog successDialog=builder.create();
                            successDialog.show();

                            TextView warningContent = view.findViewById(R.id.txt_success_dialog_message);
                            warningContent.setText("Delete Success!");

                            Button btnCancel = view.findViewById(R.id.btn_success_confirm);
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    successDialog.dismiss();
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}
