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
import com.gaf.project.model.Module;

import java.text.SimpleDateFormat;
import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder>{


    private ModuleAdapter.IClickItem iClickItem;
    public interface IClickItem<Module>{
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


        holder.moduleId.setText(String.valueOf(module.getModuleID()));
        holder.moduleName.setText(module.getModuleName());
        holder.moduleAdminId.setText(module.getAdmin().getName());
        holder.moduleStartDate.setText(dateFormat.format(module.getStartTime()));
        holder.moduleEndDate.setText(dateFormat.format(module.getEndTime()));
        holder.moduleFeedbackTitle.setText(module.getFeedback().getTitle());
        holder.moduldeFeedbackStartTime.setText(timeFormat.format(module.getFeedbackStartTime()));
        holder.moduldeFeedbackEndTime.setText(timeFormat.format(module.getFeedbackEndTime()));
    }

    @Override
    public int getItemCount() {
        if (mListModule != null){
            return mListModule.size();
        }
        return 0;
    }

    public class ModuleViewHolder extends RecyclerView.ViewHolder {

        private TextView moduleId, moduleName, moduleAdminId, moduleStartDate,moduleEndDate;
        private TextView moduleFeedbackTitle,moduldeFeedbackStartTime, moduldeFeedbackEndTime;
        private Button editButton, deleteButton;

        public ModuleViewHolder(@NonNull View view) {
            super(view);

            moduleId = view.findViewById(R.id.module_id);
            moduleName = view.findViewById(R.id.module_name);
            moduleAdminId = view.findViewById(R.id.module_admin_id);
            moduleStartDate = view.findViewById(R.id.module_start_date);
            moduleEndDate = view.findViewById(R.id.module_end_date);
            moduleFeedbackTitle = view.findViewById(R.id.module_feedback_title);
            moduldeFeedbackStartTime = view.findViewById(R.id.module_feedback_start_time);
            moduldeFeedbackEndTime = view.findViewById(R.id.module_feedback_end_time);
        }
    }
}

