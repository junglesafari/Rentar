package com.example.myapplication.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.Tools;
import com.example.myapplication.utils.returningdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class registerActivity extends AppCompatActivity {

    private TextInputEditText mRegEmail;
    private TextInputEditText mCreatePassword;
    private String password1;
    private TextInputEditText mConfirmPassword;
    private String password2;
   // private TextInputEditText mDisplayname;

    private Button mRegButton;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private String user_email;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile( "^(?=.*[0-9]).{6,}$" );
    private ProgressDialog mprogressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        findfunction();

        //setting up custom toolbar
        custom_toolbar();

        mRegButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean validated_email=email_validator();
                Boolean validated_password_1=password_validator_1();
                Boolean validated_password_2=password_validator_2();
//                Boolean validated_user_name=user_showname_validator();


                if(validated_email&&
                        validated_password_1&&
                        validated_password_2&&
                        password1.equals( password2 )
                        ){

                    mprogressDialog=new ProgressDialog( registerActivity.this);
                    mprogressDialog.setTitle( "Registering user" );
                    mprogressDialog.setMessage( "Please wait while we create your account" );
                    mprogressDialog.setCanceledOnTouchOutside( false );
                    mprogressDialog.show();
                    returningdata r=new returningdata( user_email, password1);

                    signupNewUser( r );
                }

            }
        } );


    }


    private void custom_toolbar(){
        setSupportActionBar( mToolbar );
        getSupportActionBar().setTitle( "Create Account" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        Tools.setSystemBarColor(this, R.color.colorPrimary);

    }
    private void findfunction() {
        mRegButton = findViewById( R.id.creataccountbutton );
     //   mDisplayname = findViewById( R.id.display_name );
        mRegEmail = findViewById( R.id.reg_email );
        mCreatePassword = findViewById( R.id.reg_password );
        mConfirmPassword=findViewById( R.id.reg_password_confirm_password );
        mAuth = FirebaseAuth.getInstance();
        mToolbar = findViewById( R.id.register_toolbar );
    }
    private void signupNewUser(final returningdata r) {
        mAuth.createUserWithEmailAndPassword( r.regEmail, r.regPassword ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    mprogressDialog.dismiss();
                    sendEmailVerification();


                } else {
                    if (r.regPassword.length() < 8) {
                        Toast.makeText( getApplicationContext(), "password cannot be less than 8 digits ", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        } );


    }
    private boolean email_validator() {
        // user_name = email.getEditableText().toString().trim();
        user_email = mRegEmail.getEditableText().toString().trim();

        if (user_email.isEmpty()) {
            mRegEmail.setError( "field can't be empty" );
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher( user_email ).matches()) {
            mRegEmail.setError( "Please enter a valid email address" );
            return false;
        } else {

            mRegEmail.setError( null );
            return true;
        }


    }
    private boolean password_validator_1() {
        password1 = mCreatePassword.getEditableText().toString().trim();
        if (password1.isEmpty()) {
            mCreatePassword.setError( "field can't be empty" );
            return false;
        } else if (!PASSWORD_PATTERN.matcher( password1 ).matches()) {
            mCreatePassword.setError( "password length should be at least 6 digit containing at least 1 numeric character" );
            return false;
        } else {

            mCreatePassword.setError( null );
            return true;
        }


    }
    private boolean password_validator_2() {
        password2 = mConfirmPassword.getEditableText().toString().trim();
        if (password2.isEmpty()) {
            mConfirmPassword.setError( "field can't be empty" );
            return false;
        } else if (!PASSWORD_PATTERN.matcher( password2 ).matches()) {
            mConfirmPassword.setError( "password length should be at least 6 digit containing at least 1 numeric character"  );
            return false;
        } else {

            mConfirmPassword.setError( null );
            return true;
        }


    }
//    private boolean user_showname_validator() {
//
//        user_show_name = mDisplayname.getText().toString();
//        if (user_show_name.equals( "" )) {
//            mDisplayname.setError( "this field can not be empty" );
//            return false;
//        } else {
//            mDisplayname.setError( null );
//            return true;
//        }
//
//
//    }

    private void sendEmailVerification(){
        final FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            user.sendEmailVerification().addOnCompleteListener( new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText( registerActivity.this,"Registered successfully ,verification email has been sent to mail id.",Toast.LENGTH_SHORT ).show();
                    mAuth.signOut();
                    finish();
                    startActivity( new Intent( registerActivity.this,logInActivity.class ) );
                }
            } );


        }else {
            Toast.makeText( registerActivity.this,"Something went wrong, verification mail has not been sent. try after sometime.",Toast.LENGTH_SHORT ).show();

        }




    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
