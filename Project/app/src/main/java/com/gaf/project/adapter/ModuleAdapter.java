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
import com.gaf.project.model.Module;
import com.gaf.project.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder>{


    private ModuleAdapter.IClickItem iClickItem;
    public interface IClickItem{
        void update(Module  item);
        void delete(Module item);
    }
    public ModuleAdapter(ModuleAdapter.IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    private List<Module> mListModule;
    public void setData(List<Module> list){
        this.mListModule = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_module,parent,false);

        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module module =mListModule.get(position);

        if(module==null){
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");


        holder.id.setText(String.valueOf(module.getModuleID()));
        holder.name.setText(module.getModuleName());
        holder.adminId.setText(module.getAdmin().getName());
        holder.startDate.setText(dateFormat.format(module.getStartTime()));
        holder.endDate.setText(dateFormat.format(module.getEndTime()));
        holder.feedbackTitle.setText(module.getFeedback().getTitle());
        holder.feedbackStartTime.setText(timeFormat.format(module.getFeedbackStartTime()));
        holder.feedbackEndTime.setText(timeFormat.format(module.getFeedbackEndTime()));

        holder.btnEdit.setOnClickListener(v->{
            iClickItem.update(module);
        });
        holder.btnDelete.setOnClickListener(v->{
            iClickItem.delete(module);
        });
    }

    @Override
    public int getItemCount() {
        if (mListModule != null){
            return mListModule.size();
        }
        return 0;
    }

    public class ModuleViewHolder extends RecyclerView.ViewHolder {

        private TextView id, name, adminId, startDate, endDate;
        private TextView feedbackTitle, feedbackStartTime, feedbackEndTime;
        private Button btnEdit, btnDelete;

        public ModuleViewHolder(@NonNull View view) {
            super(view);

            id = view.findViewById(R.id.module_id);
            name = view.findViewById(R.id.module_name);
            adminId = view.findViewById(R.id.module_admin_id);
            startDate = view.findViewById(R.id.module_start_date);
            endDate = view.findViewById(R.id.module_end_date);
            feedbackTitle = view.findViewById(R.id.module_feedback_title);
            feedbackStartTime = view.findViewById(R.id.module_feedback_start_time);
            feedbackEndTime = view.findViewById(R.id.module_feedback_end_time);

            btnEdit = view.findViewById(R.id.btn_edit_module);
            btnDelete = view.findViewById(R.id.btn_delete_module);

            String userRole = SessionManager.getInstance().getUserRole();
            if(!userRole.equals(SystemConstant.ADMIN_ROLE)){
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
            }

        }
    }
}

