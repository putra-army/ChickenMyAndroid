package com.example.chickenmy.Databases;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseInit {
    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;

    public FirebaseUser user;

    public FirebaseDatabase database;

    public DatabaseReference rerataS1;
    public DatabaseReference portable;
    public DatabaseReference users;
    public DatabaseReference GrafikGrid;
    public DatabaseReference setting;

    public DatabaseInit() {
        mAuth       = FirebaseAuth.getInstance();
        user        = mAuth.getCurrentUser();

        database    = FirebaseDatabase.getInstance();

        rerataS1    = database.getReference("kandang/fix1");
        portable    = database.getReference("kandang/portabel");
        users       = database.getReference("users");
        GrafikGrid  = database.getReference("grafik/grid");
        setting     = database.getReference("kandang/setting");
    }
}
