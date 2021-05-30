package com.gaf.project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.fragment.JoinFragment;
import com.gaf.project.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

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
                for (int menuItemIndex = 0; menuItemIndex < menu.size(); menuItemIndex++) {
                    MenuItem menuItem= menu.getItem(menuItemIndex);
                    if(menuItem.getItemId() == R.id.nav_join){
                        menuItem.setVisible(false);
                    }
                }
                break;
            case SystemConstant.TRAINER_ROLE:
                for (int menuItemIndex = 0; menuItemIndex < menu.size(); menuItemIndex++) {
                    MenuItem menuItem= menu.getItem(menuItemIndex);
                    if( menuItem.getItemId() == R.id.nav_enrollment||
                        menuItem.getItemId() == R.id.nav_feedback||
                        menuItem.getItemId() == R.id.nav_question||
                        menuItem.getItemId() == R.id.nav_join){

                        menuItem.setVisible(false);
                    }
                }
                break;
            case SystemConstant.TRAINEE_ROLE:
                for (int menuItemIndex = 0; menuItemIndex < menu.size(); menuItemIndex++) {
                    MenuItem menuItem= menu.getItem(menuItemIndex);
                    if( menuItem.getItemId() == R.id.nav_assignment||
                        menuItem.getItemId() == R.id.nav_enrollment||
                        menuItem.getItemId() == R.id.nav_feedback||
                        menuItem.getItemId() == R.id.nav_result||
                        menuItem.getItemId() == R.id.nav_question){

                        menuItem.setVisible(false);
                    }
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
                R.id.edit_assignment_fragment,R.id.add_class_fragment,R.id.nav_trainee_home_fragment,R.id.detailClassFragment,
                R.id.addModuleFragment,
                R.id.detailEnrollmentFragment,
                R.id.editEnrollmentFragment)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                if (item.getItemId() == R.id.nav_join){
                    DialogFragment dialogFragment = JoinFragment.newInstance();
                    dialogFragment.setCancelable(false);
                    dialogFragment.show(getSupportFragmentManager(), "join");
                }
                NavigationUI.onNavDestinationSelected(item, navController);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}