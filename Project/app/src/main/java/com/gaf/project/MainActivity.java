package com.gaf.project;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.gaf.project.constant.SystemConstant;
import com.gaf.project.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        String userRole = SessionManager.getInstance().getUserRole();

        switch (userRole){
            case SystemConstant.ADMIN_ROLE:
                MenuItem menuItemAdmin= menu.getItem(10);
                menuItemAdmin.setVisible(false);
                break;
            case SystemConstant.TRAINER_ROLE:
                int[]arr_trainer = {5,6,8,10};
                for (int index : arr_trainer) {
                    MenuItem menuItemTrainer = menu.getItem(index);
                    menuItemTrainer.setVisible(false);
                }
                break;
            case SystemConstant.TRAINEE_ROLE:
                int[]arr_trainee = {2,5,6,7,8};
                for (int index : arr_trainee) {
                    MenuItem menuItemTrainer = menu.getItem(index);
                    menuItemTrainer.setVisible(false);
                }
                break;
            default:
                break;
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_homepage, R.id.nav_assignment, R.id.nav_class, R.id.nav_module,
                R.id.nav_enrollment,R.id.nav_feedback, R.id.nav_result, R.id.nav_question,
                R.id.nav_contact,R.id.nav_join,R.id.nav_log_out,
                R.id.add_feedback_fragment, R.id.add_assignment_fragment, R.id.add_question_fragment,
                R.id.edit_assignment_fragment,R.id.add_class_fragment,R.id.nav_trainee_home_fragment,R.id.detailClassFragment)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}