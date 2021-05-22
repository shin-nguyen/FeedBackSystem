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
import com.gaf.project.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder>{


    private ClassAdapter.IClickItem iClickItem;
    public interface IClickItem{
        void updateAndDetail(Class item);
        void delete(Class item);
    }
    public ClassAdapter(ClassAdapter.IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    private List<Class> mListClass;
    public void setData(List<Class> list){
        this.mListClass = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class,parent,false);

        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        Class mClass =mListClass.get(position);

        if(mClass==null){
            return;
        }
        String userRole = SessionManager.getInstance().getUserRole();

        holder.id.setText(String.valueOf(mClass.getClassID()));
        holder.name.setText(mClass.getClassName());

        if(userRole.equals(SystemConstant.ADMIN_ROLE)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            holder.capacity.setText(mClass.getCapacity().toString());
            holder.startDate.setText(dateFormat.format(mClass.getStartTime()));
            holder.endDate.setText(dateFormat.format(mClass.getEndTime()));

            holder.btnEdit.setOnClickListener(v->{
                iClickItem.updateAndDetail(mClass);
            });

            holder.btnDelete.setOnClickListener(v->{
                iClickItem.delete(mClass);
            });
        }
        else {
            holder.numberOfTrainee.setText(String.valueOf(mClass.getTrainees().size()));
            holder.btnDetail.setOnClickListener(v->{
                iClickItem.updateAndDetail(mClass);
            });

        }
    }

    @Override
    public int getItemCount() {
        if (mListClass != null){
            return mListClass.size();
        }
        return 0;
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {

        private TextView id, name, capacity, startDate,endDate,numberOfTrainee;
        private Button btnEdit, btnDelete, btnDetail;
        private LinearLayout linrNumberofTrainee, lnrInfoAdmin;

        public ClassViewHolder(@NonNull View view) {
            super(view);
            initView(view);
        }

        private void initView(View view) {
            id = view.findViewById(R.id.txt_class_id);
            name = view.findViewById(R.id.txt_class_name);

            btnDetail = view.findViewById(R.id.btn_view_detail);
            btnEdit = view.findViewById(R.id.btn_edit_class);
            btnDelete = view.findViewById(R.id.btn_delete_class);

            linrNumberofTrainee = view.findViewById(R.id.lnr_number_of_trainee);
            lnrInfoAdmin = view.findViewById(R.id.lnr_admin);

            String userRole = SessionManager.getInstance().getUserRole();
            if(userRole.equals(SystemConstant.ADMIN_ROLE)){
                capacity = view.findViewById(R.id.txt_capacity);
                startDate = view.findViewById(R.id.txt_start_date);
                endDate = view.findViewById(R.id.txt_end_date);

                linrNumberofTrainee.setVisibility(View.GONE);
                btnDetail.setVisibility(View.GONE);
            }
            else {
                numberOfTrainee = view.findViewById(R.id.txt_number_of_trainee);

                lnrInfoAdmin.setVisibility(View.GONE);
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
            }

        }
    }
}

