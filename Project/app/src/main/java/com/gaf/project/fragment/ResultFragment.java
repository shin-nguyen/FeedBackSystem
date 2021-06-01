package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.service.ModuleService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//fragment to show statistics for admin, trainer in nav_result
public class ResultFragment extends Fragment {

    private Button btnShowOverview, btnViewComment, btnShowDetail;

    private ModuleService moduleService;
    private ClassService classService;
    private List<Module> moduleList;
    private List<Class> classList;
    private ArrayAdapter<Module> adapterModule;
    private ArrayAdapter<Class> adapterClass;

    private View view;
    private  String userRole;

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moduleService = ApiUtils.getModuleService();
        classService = ApiUtils.getClassService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_result, container, false);

        btnShowOverview = view.findViewById(R.id.btnShowOverview);
        btnViewComment = view.findViewById(R.id.btnViewComment);
        btnShowDetail = view.findViewById(R.id.btnShowDetail);

        //default hides btnViewComment
        btnViewComment.setVisibility(view.INVISIBLE);

        //default have fragment Result by pie chart, but no data
        Fragment fragPieChart = new ResultPieChartFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.statistics_fragment_container, fragPieChart).commit();

        //get Role of user
        userRole = SessionManager.getInstance().getUserRole();

        //set up spinner Class for role
        final Spinner spnClass = (Spinner) view.findViewById(R.id.spinner_class_name);
        //role: admin, display all classes
        if(userRole.equals(SystemConstant.ADMIN_ROLE)){

            Call<ClassResponse> callClass =  classService.loadListClass();
            new Thread(()-> {
                callClass.enqueue(new Callback<ClassResponse>() {
                    @Override
                    public void onResponse(Call<ClassResponse> call, Response<ClassResponse> response) {

                        if (response.isSuccessful()&& response.body()!=null){
                            classList = response.body().getClasss();
                            adapterClass =
                                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, classList);
                            spnClass.setAdapter(adapterClass);
                        }
                    }
                    @Override
                    public void onFailure(Call<ClassResponse> call, Throwable t) {
                        Log.e("Error",t.getLocalizedMessage());
                        showToast("Error");
                    }
                });}).run();
        }
        //role: trainer, display classes of this trainer
        else if(userRole.equals(SystemConstant.TRAINER_ROLE)){

            Call<ClassResponse> callClass =  classService.loadListClassByTrainer();
            new Thread(()-> {
                callClass.enqueue(new Callback<ClassResponse>() {
                    @Override
                    public void onResponse(Call<ClassResponse> call, Response<ClassResponse> response) {

                        if (response.isSuccessful()&& response.body()!=null){
                            classList = response.body().getClasss();
                            adapterClass =
                                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, classList);
                            spnClass.setAdapter(adapterClass);
                        }
                    }
                    @Override
                    public void onFailure(Call<ClassResponse> call, Throwable t) {
                        Log.e("Error",t.getLocalizedMessage());
                        showToast("Error");
                    }
                });}).run();

        }

        //set up spinner Module for role
        final Spinner spnModule = (Spinner) view.findViewById(R.id.spinner_module_name);
        //role: admin, display all modules
        if(userRole.equals(SystemConstant.ADMIN_ROLE)) {

            Call<ModuleResponse> callModule = moduleService.loadModuleAdmin();
            new Thread(() -> {
                callModule.enqueue(new Callback<ModuleResponse>() {
                    @Override
                    public void onResponse(Call<ModuleResponse> call, Response<ModuleResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            moduleList = response.body().getModules();
                            adapterModule =
                                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, moduleList);
                            spnModule.setAdapter(adapterModule);
                        }
                    }

                    @Override
                    public void onFailure(Call<ModuleResponse> call, Throwable t) {
                        Log.e("Error", t.getLocalizedMessage());
                        showToast("Error");
                    }
                });
            }).run();

        }
        //role: trainer, display modules of this trainer
        else if(userRole.equals(SystemConstant.TRAINER_ROLE)){

            Call<ModuleResponse> callModule = moduleService.loadModuleTrainer();
            new Thread(() -> {
                callModule.enqueue(new Callback<ModuleResponse>() {
                    @Override
                    public void onResponse(Call<ModuleResponse> call, Response<ModuleResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            moduleList = response.body().getModules();
                            adapterModule =
                                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, moduleList);
                            spnModule.setAdapter(adapterModule);
                        }
                    }

                    @Override
                    public void onFailure(Call<ModuleResponse> call, Throwable t) {
                        Log.e("Error", t.getLocalizedMessage());
                        showToast("Error");
                    }
                });
            }).run();

        }

        //button Show pieChart
        btnShowOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //hide btnViewComment if it's not hidden
                btnViewComment.setVisibility(view.INVISIBLE);

                //get 2 params: class and module are chosen
                Module module = (Module) spnModule.getSelectedItem();
                Class mClass = (Class) spnClass.getSelectedItem();

                //send 2 params: class and module are chosen
                Bundle bundle = new Bundle();
                bundle.putSerializable("class", mClass);
                bundle.putSerializable("module", module);

                //display fragment Result by pie chart
                Fragment fragPieChart = new ResultPieChartFragment();
                fragPieChart.setArguments(bundle);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.statistics_fragment_container, fragPieChart).commit();
            }
        });

        //button View Comment
        btnViewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //hide btnViewComment
                btnViewComment.setVisibility(view.INVISIBLE);

                //get 2 params: class and module are chosen
                Module module = (Module) spnModule.getSelectedItem();
                Class mClass = (Class) spnClass.getSelectedItem();

                //send 2 params: class and module are chosen
                Bundle bundle = new Bundle();
                bundle.putSerializable("class", mClass);
                bundle.putSerializable("module", module);

                //display fragment View all comments for class and module
                Fragment fragViewCmt = new ViewCommentFragment();
                fragViewCmt.setArguments(bundle);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.statistics_fragment_container, fragViewCmt).commit();
            }
        });

        //button show statistics by percent
        btnShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //display btnViewComment
                btnViewComment.setVisibility(view.VISIBLE);

                //get 2 params: class and module are chosen
                Module module = (Module) spnModule.getSelectedItem();
                Class mClass = (Class) spnClass.getSelectedItem();

                //send 2 params: class and module are chosen
                Bundle bundle = new Bundle();
                bundle.putSerializable("class", mClass);
                bundle.putSerializable("module", module);

                //display fragment Result by percent for class and module
                Fragment fragPercent = new ResultPercentFragment();
                fragPercent.setArguments(bundle);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.statistics_fragment_container, fragPercent).commit();
            }
        });
        return view;
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
}