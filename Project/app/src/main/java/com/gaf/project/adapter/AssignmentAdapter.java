package com.gaf.project.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.fragment.AddAssignmentFragment;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Class;
import com.gaf.project.utils.SessionManager;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private List<Assignment> mListAssignment;

    private AssignmentAdapter.IClickItem iClickItem;

    public interface IClickItem{
        void update(Assignment item);
        void delete(Assignment item);
    }
    public AssignmentAdapter(AssignmentAdapter.IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

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

        holder.assignmentNo.setText(String.valueOf(position));
        holder.className.setText(String.valueOf(assignment.getMClass().getClassName()));
        holder.courseName.setText(String.valueOf(assignment.getModule().getModuleName()));
        holder.trainerName.setText(String.valueOf(assignment.getTrainer().getName()));
        holder.registrationCode.setText(assignment.getRegistrationCode());

        holder.btnEdit.setOnClickListener(v->{
            iClickItem.update(assignment);
        });
        holder.btnDelete.setOnClickListener(v->{
            iClickItem.delete(assignment);
        });
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
        private Button btnEdit, btnDelete;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);

            assignmentNo =itemView.findViewById(R.id.assignment_no);
            courseName =itemView.findViewById(R.id.assignment_course_name);
            className =itemView.findViewById(R.id.assignment_class_name);
            trainerName =itemView.findViewById(R.id.assignment_trainer_name);
            registrationCode =itemView.findViewById(R.id.assignment_registration_code);
            btnEdit=itemView.findViewById(R.id.btn_edit_assignment);
            btnDelete=itemView.findViewById(R.id.btn_delete_assignment);

            String userRole = SessionManager.getInstance().getUserRole();
            if(!userRole.equals(SystemConstant.ADMIN_ROLE)){
                btnDelete.setVisibility(View.GONE);
                btnEdit.setVisibility(View.GONE);
            }
        }
    }
}
