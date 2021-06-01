package com.gaf.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.authentication.AuthenticationRequest;
import com.gaf.project.authentication.AuthenticationResponse;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.fragment.ModuleFragment;
import com.gaf.project.model.Trainee;
import com.gaf.project.service.AuthenticationService;
import com.gaf.project.service.TraineeService;
import com.gaf.project.service.TrainerService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;
import com.gaf.project.viewmodel.AuthenticationViewModel;
import com.gaf.project.viewmodel.TraineeViewModel;

import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnSignIn;
    private CheckBox cbRememberMe;
    private Spinner pnRole;
    private TextView  errorPass, errorUserName;

    private TraineeViewModel traineeViewModel;
    private AuthenticationViewModel authenticationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        addValues();
        addEvent();
    }
    private void addValues() {
        edtEmail.setText("thao");
        edtPassword.setText("1234");

        String[] role = {SystemConstant.ADMIN_ROLE,SystemConstant.TRAINEE_ROLE,SystemConstant.TRAINER_ROLE};
        ArrayAdapter<CharSequence> roleAdapter = new ArrayAdapter<>(getApplication(),
                R.layout.simple_spinner_item_role, role );

        roleAdapter.setDropDownViewResource(R.layout.simple_list_item_dropdown);
        pnRole.setAdapter(roleAdapter);
    }

    //get all view in activity
    public void addControls(){
        traineeViewModel = new ViewModelProvider(this).get(TraineeViewModel.class);
        authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);

        getSupportActionBar().hide();
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        cbRememberMe = findViewById(R.id.cb_remember_me);
        pnRole = findViewById(R.id.spinner_role);
        errorUserName = findViewById(R.id.txt_error_username);
        errorPass = findViewById(R.id.txt_error_pass);
    }
    //set event for views
    public void addEvent(){

        btnSignIn.setOnClickListener(v -> {

            final String username = edtEmail.getText().toString().trim();
            final String password = edtPassword.getText().toString().trim();
            final Boolean remember = cbRememberMe.isChecked();
            final String role = pnRole.getSelectedItem().toString();

            Boolean flag = check(username,password);
            if (flag){
                AuthenticationRequest authenticationRequest =
                        new AuthenticationRequest(username,password,role,remember);

                authenticationViewModel.initData(authenticationRequest);
                authenticationViewModel.getAuthenticationResponseMutableLiveData().observe(this,
                        authenticationResponse -> {
                            setSession(authenticationResponse, username, role);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                );
            }
        });
    }

    private Boolean check(String username, String password) {
        Boolean flag =true;
        errorPass.setVisibility(View.GONE);
        errorUserName.setVisibility(View.GONE);

        //if the user has not entered the complete information
        if(TextUtils.isEmpty(username)) {
            errorUserName.setVisibility(View.VISIBLE);
            flag =false;
        }
        if(TextUtils.isEmpty(password)){
            errorPass.setVisibility(View.VISIBLE);
            flag =false;
        }
        return  flag;
    }

    private void setSession(AuthenticationResponse authenticationResponse, String username, String role) {

        SystemConstant.authenticationResponse = authenticationResponse;// cái này là sao chuyển dô session dc ko

        SessionManager.getInstance().setIsLogin(true);
        SessionManager.getInstance().setUserName(username);
        SessionManager.getInstance().setUserRole(role);

        if (role.equals(SystemConstant.TRAINEE_ROLE)){
            traineeViewModel.initData();
            traineeViewModel.getTraineeMutableLiveData().observe(this,trainee -> {
                SessionManager.getInstance().setTrainee(trainee);
            });
        }
//        edtPassword.setText("");
//        edtEmail.setText("");
    }


    public void showToast(String string){
        Toast.makeText(getApplication(),string,Toast.LENGTH_LONG).show();
    }

}