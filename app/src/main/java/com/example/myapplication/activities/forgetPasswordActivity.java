package com.example.myapplication.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPasswordActivity extends AppCompatActivity {
    android.support.design.widget.TextInputEditText resetMailId;
    android.support.v7.widget.AppCompatButton resetSubmitButton;
    TextView logInResetText;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forget_password );

        initToolbar();

        resetMailId = findViewById( R.id.resetMailId );
        resetSubmitButton = findViewById( R.id.resetSubmitButton );
        logInResetText = findViewById( R.id.logInResetText );
        resetMailId.setCursorVisible( false );
        mAuth = FirebaseAuth.getInstance();

        logInResetText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( forgetPasswordActivity.this,logInActivity.class ) );
                finish();
            }
        } );

        resetSubmitButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = resetMailId.getText().toString();
                resetMailId.setText( "" );

                if(TextUtils.isEmpty( email )){
                    Toast.makeText( forgetPasswordActivity.this,"Email field can not be empty",Toast.LENGTH_SHORT ).show();

                }else {
                    mAuth.sendPasswordResetEmail( email ).addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                customToast("Please check your mail ,we have sent a password reset link.");


                            }else {
                                customToast( "some error occurred ,please try after sometime." );
                            }
                        }
                    } );
                }

            }
        } );





    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_900);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void customToast(String text){
        LayoutInflater inflater=getLayoutInflater();
        View layout= inflater.inflate( R.layout.toast,(ViewGroup) findViewById( R.id.toast_layout_root ) );
        TextView toastText=layout.findViewById( R.id.toast_test );
        toastText.setText( text );

        Toast toast=new Toast( getApplicationContext() );
        // toast.setGravity( Gravity.CENTER_VERTICAL,0,0 );
        toast.setDuration( Toast.LENGTH_SHORT );
        toast.setView( layout );
        toast.show();
    }



}
