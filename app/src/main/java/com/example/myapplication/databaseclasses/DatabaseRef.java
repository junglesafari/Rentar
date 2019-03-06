package com.example.myapplication.databaseclasses;

import com.google.firebase.database.FirebaseDatabase;

public class DatabaseRef {
    private static FirebaseDatabase INSTANCE;

    public static FirebaseDatabase getDatabaseInstance(){

        if(INSTANCE == null){
            INSTANCE = FirebaseDatabase.getInstance();
            INSTANCE.setPersistenceEnabled( true );
        }

        return INSTANCE;
    }

}
