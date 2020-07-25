package com.example.chickenmy.Databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RincianGridDatabase {
    public DatabaseInit db = new DatabaseInit();
    public List<String> keys = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<String> keys);
    }

    public void getData(final RincianGridDatabase.DataStatus dataStatus) {
        db.GrafikGrid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot ds : dataSnapshot.getChildren()) {
//                    if (ds.getKey().child("RFID").exists()) {
                    Log.d("TAG", String.valueOf(ds));
                        keys.add(ds.getKey());
                        dataStatus.DataIsLoaded(keys);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
