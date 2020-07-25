package com.example.chickenmy.Databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chickenmy.Models.GridModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GrafikMatiDatabase {

    public DatabaseInit db = new DatabaseInit();
    public List<String> keys = new ArrayList<>();
    public List<Integer> matiTodays = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Integer> matiToday, List<String> keys);
    }

    public void getData(final GrafikMatiDatabase.DataStatus dataStatus) {
        db.GrafikGrid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keys.clear();
                matiTodays.clear();
                for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                    db.GrafikGrid.child(ds.getKey()).child("mati").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int matiToday;
                            if (dataSnapshot.exists()) {
                                matiToday = dataSnapshot.getValue(Integer.class);
                                keys.add(ds.getKey());
                                matiTodays.add(matiToday);
                                dataStatus.DataIsLoaded(matiTodays, keys);
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
