package com.example.chickenmy.Databases;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fix1S1Database {

    DatabaseInit db = new DatabaseInit();

    public void getData(final Fix1S1Database.DataStatus dataStatus) {
        db.rerataS1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String hum = (String) dataSnapshot.child("rerata").child("hum").getValue();
                    String temp = (String) dataSnapshot.child("rerata").child("temp").getValue();
                    String tgl = (String) dataSnapshot.child("update").child("tanggal").getValue();
                    String time = (String) dataSnapshot.child("update").child("waktu").getValue();
                    String wind = (String) dataSnapshot.child("W1").child("speed").getValue();

                    dataStatus.DataIsLoaded(hum, temp, tgl, time, wind);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface DataStatus {
        void DataIsLoaded(String hum, String temp, String tgl, String time, String wind);
    }

}
