package com.example.login_navview;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.legacy.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login_navview.databinding.ActivityNavDrawOptionsBinding;
import com.squareup.picasso.Picasso;

public class opciones extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavDrawOptionsBinding binding;
    ImageView P_imgProfile;
    TextView P_FullName;
    TextView P_txtMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String img = getIntent().getStringExtra("img");
        String nombres = getIntent().getStringExtra("nombres");
        String correo = getIntent().getStringExtra("correo");
        String rol = getIntent().getStringExtra("rol");


        binding = ActivityNavDrawOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavDrawOptions.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        P_imgProfile = (ImageView) headerView.findViewById(R.id.imgvProfile);
        P_FullName = (TextView) headerView.findViewById(R.id.txtFullName);
        P_txtMail = (TextView) headerView.findViewById(R.id.txtMail);

        Picasso.get()
                .load(img)
                .into(P_imgProfile);
        P_FullName.setText(nombres);
        P_txtMail.setText(correo);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.main_menu, R.id.manage_users, R.id.general_settings
            )
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav_draw_options);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

        Menu menuNav=navigationView.getMenu();
        MenuItem [] nav_items = new MenuItem[7];
        nav_items [0] = menuNav.findItem(R.id.main_menu);
        nav_items [1] = menuNav.findItem(R.id.general_settings);
        nav_items [2] = menuNav.findItem(R.id.manage_users);
        nav_items [3] = menuNav.findItem(R.id.my_posts);
        nav_items [4] = menuNav.findItem(R.id.new_release);
        nav_items [5] = menuNav.findItem(R.id.shop);
        nav_items [6] = menuNav.findItem(R.id.favorites);
        if(rol.equals("Admin") )
            for (int i = 3; i<nav_items.length; i++ )
                nav_items[i].setVisible(false);
        else if(rol.equals("Vendedor"))
        {
            for (int i = 0; i< nav_items.length-4; i++)
                nav_items[i].setVisible(false);
                for (int j = 5; j< nav_items.length; j++)
                    nav_items[j].setVisible(false);
        }else if(rol.equals("Cliente"))
            for (int i = 0; i<nav_items.length-2; i++)
                nav_items[i].setVisible(false);

        binding.appBarNavDrawOptions.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Enviar Correo", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_draw_options, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav_draw_options);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}