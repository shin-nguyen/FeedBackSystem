package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        void update(Class item);
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        holder.id.setText(String.valueOf(mClass.getClassID()));
        holder.name.setText(mClass.getClassName());
        holder.capacity.setText(mClass.getCapacity().toString());
        holder.startDate.setText(dateFormat.format(mClass.getStartTime()));
        holder.endDate.setText(dateFormat.format(mClass.getEndTime()));


        holder.btnEdit.setOnClickListener(v->{
            iClickItem.update(mClass);
        });
        holder.btnDelete.setOnClickListener(v->{
            iClickItem.delete(mClass);
        });
    }

    @Override
    public int getItemCount() {
        if (mListClass != null){
            return mListClass.size();
        }
        return 0;
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {

        private TextView id, name, capacity, startDate,endDate;
        private Button btnEdit, btnDelete, btnDetail;

        public ClassViewHolder(@NonNull View view) {
            super(view);
            initView(view);
        }

        private void initView(View view) {
            id = view.findViewById(R.id.txt_class_id);
            name = view.findViewById(R.id.txt_class_name);
            capacity = view.findViewById(R.id.txt_capacity);
            startDate = view.findViewById(R.id.txt_start_date);
            endDate = view.findViewById(R.id.txt_end_date);
            btnEdit = view.findViewById(R.id.btn_edit_class);
            btnDelete = view.findViewById(R.id.btn_delete_class);
            btnDetail = view.findViewById(R.id.btn_view_detail);

            String userRole = SessionManager.getInstance().getUserRole();
            if(userRole.equals(SystemConstant.ADMIN_ROLE)){
                btnDetail.setVisibility(View.GONE);
            }
            else {
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
            }
        }
    }
}

