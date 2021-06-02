package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.Class;
import com.gaf.project.model.Enrollment;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Question;
import com.gaf.project.model.Trainee;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentAdapter extends RecyclerView.Adapter<EnrollmentAdapter.EnrollmentViewHolder> implements Filterable {

    private List<Enrollment> mListEnrollment;
    private List<Enrollment> mListEnrollmentOld;

    private EnrollmentAdapter.IClickItem iClickItem;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.equals("All")){
                    mListEnrollment = mListEnrollmentOld;
                }else {
                    List<Enrollment> enrollmentList = new ArrayList<>();
                    for(Enrollment enrollment : mListEnrollmentOld){
                        if(enrollment.getClassName().toLowerCase().contains(strSearch.toLowerCase())){
                            enrollmentList.add(enrollment);
                        }
                        mListEnrollment = enrollmentList;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListEnrollment;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListEnrollment = (List<Enrollment>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface IClickItem{
        void detail(Enrollment item);
        void update(Enrollment item);
        void delete(Enrollment item);
    }

    public EnrollmentAdapter(EnrollmentAdapter.IClickItem iClickItem){
        this.iClickItem = iClickItem;
    }

    public void setData(List<Enrollment> list){
        this.mListEnrollment = list;
        this.mListEnrollmentOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EnrollmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enrollment,parent,false);

        return new EnrollmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnrollmentViewHolder holder, int position) {

        Enrollment enrollment = mListEnrollment.get(position);

        if (enrollment == null)
            return;

        holder.classId.setText(enrollment.getMClass().getClassID().toString());
        holder.className.setText(enrollment.getMClass().getClassName());
        holder.traineeId.setText(enrollment.getTrainee().getUserName());
        holder.traineeName.setText(enrollment.getTrainee().getName());

        holder.detailButton.setOnClickListener(v -> {
            iClickItem.detail(enrollment);
        });
        holder.editButton.setOnClickListener(v -> {
            iClickItem.update(enrollment);
        });
        holder.deleteButton.setOnClickListener(v -> {
            iClickItem.delete(enrollment);
        });
    }

    @Override
    public int getItemCount() {
        if (mListEnrollment != null)
            return mListEnrollment.size();
        return 0;
    }

    public class EnrollmentViewHolder extends RecyclerView.ViewHolder{

        private TextView traineeId, traineeName, classId, className;
        private Button detailButton, editButton, deleteButton;
        public EnrollmentViewHolder(@NonNull View itemView) {
            super(itemView);

            traineeId = itemView.findViewById(R.id.txt_trainee_id);
            traineeName = itemView.findViewById(R.id.txt_trainee_name);
            classId = itemView.findViewById(R.id.txt_class_id);
            className = itemView.findViewById(R.id.txt_class_name);

            detailButton = itemView.findViewById(R.id.btn_detail_enrollment);
            editButton = itemView.findViewById(R.id.btn_edit_enrollment);
            deleteButton = itemView.findViewById(R.id.btn_delete_enrollment);


        }
    }
}
