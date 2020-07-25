package com.example.chickenmy.Databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chickenmy.Models.GridModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GrafikBeratDatabase {

    public DatabaseInit db = new DatabaseInit();
    public List<String> keys = new ArrayList<>();
    public List<Integer> beratTodays = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Integer> beratToday, List<String> keys);
    }

    public void getData(final GrafikBeratDatabase.DataStatus dataStatus) {
        db.GrafikGrid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keys.clear();
                beratTodays.clear();
                for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                    db.GrafikGrid.child(ds.getKey()).child("RFID").orderByChild("idGrid").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                int beratToday = 0;
                                int jumData = 0;
                                for (final DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                    if (ds1.child("a_berat").exists()) {
                                        String berat = String.valueOf(ds1.child("a_berat").getValue(String.class));
                                        beratToday = beratToday + Integer.valueOf(berat);
                                        jumData++;
                                    }
                                }
                                if (jumData != 0) {
                                    beratToday = beratToday / jumData;
                                    keys.add(ds.getKey());
                                    beratTodays.add(beratToday);
                                    dataStatus.DataIsLoaded(beratTodays, keys);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
