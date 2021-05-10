package com.gaf.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gaf.project.authentication.AuthenticationRequest;
import com.gaf.project.authentication.AuthenticationResponse;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.service.AuthenticationService;
import com.gaf.project.utils.ApiUtils;

import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnSignIn;
    private CheckBox cbRememberMe;
    private Spinner pnRole;
    private AuthenticationService authenticationService;

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
        ArrayAdapter<CharSequence> roleAdapter = new ArrayAdapter<CharSequence>(getApplication(),
                R.layout.simple_spinner_item_role, role );

        roleAdapter.setDropDownViewResource(R.layout.simple_list_item_dropdown);
        pnRole.setAdapter(roleAdapter);
    }

    //get all view in activity
    public void addControls(){

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        cbRememberMe = findViewById(R.id.cb_remember_me);
        pnRole = findViewById(R.id.spinner_role);

        authenticationService = ApiUtils.getAuthenticationService();
    }
    //set event for views
    public void addEvent(){

        btnSignIn.setOnClickListener(v -> {

            final String username = edtEmail.getText().toString().trim();
            final String password = edtPassword.getText().toString().trim();

            //if the user has not entered the complete information
            if(TextUtils.isEmpty(username)) {
            }
            if(TextUtils.isEmpty(password)){
            }
            else {

                AuthenticationRequest authenticationRequest =
                        new AuthenticationRequest(username,password,"ADMIN");

                authenticationService.login(authenticationRequest)
                        .enqueue(new Callback<AuthenticationResponse>() {
                            @Override
                            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                                AuthenticationResponse authenticationResponse = response.body();

                                if(authenticationResponse!=null){
                                    SystemConstant.authenticationResponse = authenticationResponse;

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                                showToast("Error");
                            }
                        });
            }

//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
        });

    }
    public void showToast(String string){
        Toast.makeText(getApplication(),string,Toast.LENGTH_LONG).show();
    }

}