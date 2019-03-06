package com.example.myapplication.activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activities.fragments.setAndupdateProfileFrag;
import com.example.myapplication.activities.fragments.userProfileFrag;
import com.example.myapplication.homeFrag;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements
        homeFrag.OnFragmentInteractionListener
        , userProfileFrag.OnFragmentInteractionListener
, setAndupdateProfileFrag.OnFragmentInteractionListener,
AdapterView.OnItemSelectedListener{
    private FirebaseAuth mAuth;
    private Toolbar mtoolbar;
    private ActionBar actionBar;
private TextView nav_email;
public String user_email = "developer.rentar@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        mAuth = FirebaseAuth.getInstance();
       if( mAuth.getCurrentUser()!=null){
           user_email= Objects.requireNonNull( mAuth.getCurrentUser() ).getEmail();
           //Toast.makeText( this,user_email+"",Toast.LENGTH_SHORT ).show();


       }

        initToolbar();
        initNavigationMenu();





        homeFrag homeFragment = new homeFrag();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace( R.id.contianerupcomingmovie,homeFragment );
        transaction.commit();

    }



    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //todo launch the activity if the user is not log in
        //updateUI(currentUser);
        if(currentUser == null){
            send_to_start();

        }


    }
    private void send_to_start(){

        Intent startIntent = new Intent( MainActivity.this,logInActivity.class );
        startActivity( startIntent );
        finish();
    }
    private void initToolbar() {
        mtoolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mtoolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Rentar");

        // Tools.setSystemBarColor(this);
    }

    private void initNavigationMenu() {
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);



        View headerView = nav_view.inflateHeaderView(R.layout.include_drawer_header_news);
       nav_email = headerView.findViewById(R.id.email);
        nav_email.setText( user_email );

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mtoolbar, R.string.navigation_drawer_close, R.string.navigation_drawer_open) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {

                if(item.getItemId() == R.id.log_out){
                    showCustomDialog();
                }else if(item.getItemId() == R.id.user_profile){



                    userProfileFrag userProfileFrag = new userProfileFrag();
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace( R.id.contianerupcomingmovie,userProfileFrag );
                    transaction.commit();


                }
                  Toast.makeText(getApplicationContext(), item.getTitle() + " Selected", Toast.LENGTH_SHORT).show();
                // actionBar.setTitle(item.getTitle());
                drawer.closeDrawers();

                return false;
            }
        });


    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_light);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((AppCompatButton) dialog.findViewById(R.id.bt_follow)).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Follow Clicked", Toast.LENGTH_SHORT).show();



                //logout code
                FirebaseAuth.getInstance().signOut();
                send_to_start();





            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //todo on spinner item selected
        Toast.makeText( getApplicationContext(),view.getId()+"Position"+position+"longID"+id+"",Toast.LENGTH_SHORT ).show();



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
         // todo on spinner item selected
    }
}
