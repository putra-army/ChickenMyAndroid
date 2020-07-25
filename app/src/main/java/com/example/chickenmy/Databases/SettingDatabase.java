package com.example.chickenmy.Databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chickenmy.Models.SettingModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SettingDatabase {

    private DatabaseInit db = new DatabaseInit();
    public List<SettingModel> settingModels = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<SettingModel> settingModels);
    }

    public void getData(final SettingDatabase.DataStatus dataStatus) {
        db.setting.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SettingModel settingModel = dataSnapshot.getValue(SettingModel.class);
                settingModels.add(settingModel);
                dataStatus.DataIsLoaded(settingModels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
