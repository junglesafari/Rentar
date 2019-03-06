package com.example.myapplication.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.Tools;
import com.example.myapplication.utils.contract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class logInActivity extends AppCompatActivity {

    private ProgressBar progress_bar;
    private android.support.design.widget.TextInputEditText loginusername;
    private android.support.design.widget.TextInputEditText loginpassword;
    private FloatingActionButton loginfab;
    private TextView signupText;
    private TextView forgetpasswordText;
    private String email;
    private String password;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile( "^(?=.*[0-9]).{6,}$" );
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_log_in );
        findFunction();
        mAuth=FirebaseAuth.getInstance();
        onclick();


    }

    private void findFunction(){
        progress_bar = findViewById( R.id.progress_bar );
        loginusername = findViewById( R.id.loginusername );
        loginpassword = findViewById( R.id.loginuserpassword );
        loginfab = findViewById( R.id.loginfab );
        forgetpasswordText = findViewById( R.id.forgetpasswordText );
        signupText = findViewById( R.id.signupText );

    }

    private void searchAction() {
        progress_bar.setVisibility( View.VISIBLE);
        loginfab.setAlpha(0f);
        Boolean validated_email = email_validator();
        Boolean validated_password = password_validator();

        if (validated_email && validated_password) {
            login_user( loginusername.getEditableText().toString().trim(),loginpassword.getEditableText().toString().trim());
        }else {
            loginfab.setAlpha(1f);
        }



    }

    private void onclick(){

        loginfab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAction();
            }
        } );

        signupText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( logInActivity.this, registerActivity.class ) );
            }
        } );


        forgetpasswordText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( logInActivity.this, forgetPasswordActivity.class ) );
            }
        } );


    }

    private boolean password_validator() {
        password = loginpassword.getEditableText().toString().trim();
        if (password.isEmpty()) {
            loginpassword.setError( "field can't be empty" );
            return false;
        } else if (!PASSWORD_PATTERN.matcher( password ).matches()) {
            loginpassword.setError( "password length should be at least 6 digit containing at least 1 numeric character" );
            return false;
        } else {

            loginpassword.setError( null );
            return true;
        }


    }
    private boolean email_validator() {
        // user_name = email.getEditableText().toString().trim();
        email = loginusername.getEditableText().toString().trim();

        if (email.isEmpty()) {
            loginusername.setError( "field can't be empty" );
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher( email ).matches()) {
            loginusername.setError( "Please enter a valid email address" );
            return false;
        } else {

            loginusername.setError( null );
            return true;
        }


    }
    private void login_user(String email, final String password){
        mAuth.signInWithEmailAndPassword( email,password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    chekEmailVerification();

                } else {
                    loginfab.setAlpha(1f);
                    Toast.makeText( logInActivity.this,"Cannot sign in. Please check credentials",Toast.LENGTH_SHORT ).show();
                }






            }
        } );




    }
    private void chekEmailVerification(){
        FirebaseUser user = mAuth.getCurrentUser();
        Boolean emailFlag = user.isEmailVerified();
       if(emailFlag){
            finish();
            Intent intent = new Intent( logInActivity.this,MainActivity.class);
             startActivity( intent);
        }else {
            mAuth.signOut();
            Toast.makeText( logInActivity.this,"Please verify your email first",Toast.LENGTH_SHORT ).show();
        }



    }
}
