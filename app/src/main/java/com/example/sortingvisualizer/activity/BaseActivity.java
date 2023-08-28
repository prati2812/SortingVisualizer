package com.example.sortingvisualizer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

import com.example.sortingvisualizer.R;

public abstract class BaseActivity extends AppCompatActivity {

    public Context context;
    public LayoutInflater layoutInflater;

    public DrawerLayout drawerLayout_main;
    public ViewStub viewStub_main;
    public ViewStub viewStub_menu_left;
    public ViewStub viewStub_menu_right;

    public View view_main;
    public View view_menu_left;
    public View view_menu_right;


    public int Layout_main;
    public int Layout_left;
    public int Layout_right;

    public boolean isConfigured = false;

    protected void configure(int Layout_main, int Layout_left, int Layout_right){
        this.Layout_main = Layout_main;
        this.Layout_left = Layout_left;
        this.Layout_right = Layout_right;

        isConfigured = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base);

        context = this;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        drawerLayout_main = findViewById(R.id.drawerLayout_main);
        viewStub_main = findViewById(R.id.view_stub_main);
        viewStub_menu_left = findViewById(R.id.view_stub_menu_left);
        viewStub_menu_right = findViewById(R.id.view_stub_menu_right);

        viewStub_main.setLayoutResource(Layout_main);
        viewStub_menu_left.setLayoutResource(Layout_left);
        viewStub_menu_right.setLayoutResource(Layout_right);

    }

    public boolean isDrawerOpen(int id){
        if(id == 0){
            return (drawerLayout_main.isDrawerOpen(GravityCompat.START)) || (drawerLayout_main.isDrawerOpen(GravityCompat.END));
        }
        else if(id == 1){
            return drawerLayout_main.isDrawerOpen(GravityCompat.START);
        }
        else if(id == 2){
            return drawerLayout_main.isDrawerOpen(GravityCompat.START);
        }
        return false;
    }

    public void openDrawer(int id){
        if(id == 0){
            drawerLayout_main.openDrawer(GravityCompat.START);
            drawerLayout_main.openDrawer(GravityCompat.END);
        }
        else if(id == 1){
            drawerLayout_main.openDrawer(GravityCompat.START);
        } else if(id == 2) {
            drawerLayout_main.openDrawer(GravityCompat.END);
        }
    }

    public void closeDrawer(int id){
        if(id == 0){
            drawerLayout_main.closeDrawer(GravityCompat.START);
            drawerLayout_main.closeDrawer(GravityCompat.END);
        }
        else if(id == 1){
            drawerLayout_main.closeDrawer(GravityCompat.START);
        }
        else if(id == 2){
            drawerLayout_main.closeDrawer(GravityCompat.END);
        }
    }

    @Override
    public void onBackPressed() {
        if(isDrawerOpen(0)){
            closeDrawer(0);
        }

    }

    protected abstract void initPseudoCode();
    protected abstract void initViews();
    protected abstract void initNavigation();
    protected abstract void initToolTipTexts();
}