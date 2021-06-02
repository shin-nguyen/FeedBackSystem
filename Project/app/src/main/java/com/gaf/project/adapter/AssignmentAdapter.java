package com.gaf.project.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
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

import java.util.ArrayList;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> implements Filterable {

    private List<Assignment> mListAssignment;
    private List<Assignment> mListAssignmentOld;

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
        this.mListAssignmentOld = list;
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

        String paddingClass = convertToWhitespace(holder.tvClassName.getText().toString().trim(),"class");
        String paddingCourse = convertToWhitespace(holder.tvCourseName.getText().toString().trim(),"course");
        String paddingTrainer = convertToWhitespace(holder.tvTrainerName.getText().toString().trim(),"trainer");

        holder.assignmentNo.setText(String.valueOf(position));
        holder.className.setText(paddingClass + (assignment.getMClass().getClassName()));
        holder.courseName.setText(paddingCourse + (assignment.getModule().getModuleName()));
        holder.trainerName.setText(paddingTrainer + (assignment.getTrainer().getName()));
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

        private TextView assignmentNo, courseName, className, trainerName, registrationCode, tvCourseName, tvClassName, tvTrainerName;
        private Button btnEdit, btnDelete;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);

            assignmentNo =itemView.findViewById(R.id.assignment_no);
            courseName =itemView.findViewById(R.id.assignment_course_name);
            className =itemView.findViewById(R.id.assignment_class_name);
            trainerName =itemView.findViewById(R.id.assignment_trainer_name);
            registrationCode =itemView.findViewById(R.id.assignment_registration_code);
            tvCourseName =itemView.findViewById(R.id.txt_course_name);
            tvClassName =itemView.findViewById(R.id.txt_class_name);
            tvTrainerName =itemView.findViewById(R.id.txt_trainer_name);

            btnEdit=itemView.findViewById(R.id.btn_edit_assignment);
            btnDelete=itemView.findViewById(R.id.btn_delete_assignment);

            String userRole = SessionManager.getInstance().getUserRole();
            if(!userRole.equals(SystemConstant.ADMIN_ROLE)){
                btnDelete.setVisibility(View.GONE);
                btnEdit.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    mListAssignment=mListAssignmentOld;
                }else {
                    List<Assignment> assignmentList = new ArrayList<>();
                    for(Assignment assignment : mListAssignmentOld){
                        if(assignment.getMClass().getClassName().toLowerCase().contains(strSearch.toLowerCase())
                            ||assignment.getModule().getModuleName().toLowerCase().contains(strSearch.toLowerCase())
                            ||assignment.getTrainer().getName().toLowerCase().contains(strSearch.toLowerCase())
                            ||assignment.getRegistrationCode().toLowerCase().contains(strSearch.toLowerCase())){

                            assignmentList.add(assignment);

                        }

                        mListAssignment=assignmentList;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values=mListAssignment;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListAssignment=(List<Assignment>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public String convertToWhitespace (String string, String type){
        int length = string.length();

        if(type.equals("course")){
            length+=13;
        }

        if(type.equals("class")){
            length+=11;
        }

        if(type.equals("trainer")){
            length+=12;
        }

        String result="";
        for(int i=0;i<=length;i++){
            result+=" ";
        }
        return result;
    }
}
