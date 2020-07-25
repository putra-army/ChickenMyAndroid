package com.example.chickenmy.Databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chickenmy.Models.GridModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PortableDatabase {
    DatabaseInit db = new DatabaseInit();
    public List<GridModel> gridModels = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<GridModel> gridModels);
    }

    public interface DataAmonia {
        void AmoniaValue(Double amonia);
    }

    public void getData(final PortableDatabase.DataStatus dataStatus) {
        db.portable.orderByChild("idGrid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gridModels.clear();
                for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                    final GridModel gridModel = ds.getValue(GridModel.class);
                    gridModels.add(gridModel);
                    dataStatus.DataIsLoaded(gridModels);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDataAmonia(final PortableDatabase.DataAmonia dataAmonia) {
        db.portable.orderByChild("idGrid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gridModels.clear();
                Double amoniaValues = 0.0;
                int jumData = 0;
                for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                    final GridModel gridModel = ds.getValue(GridModel.class);
                    if (gridModel.getB_amonia() != null) {
                        amoniaValues = amoniaValues + Double.valueOf(gridModel.getB_amonia());
                    }
                    jumData++;
                }
                if (jumData != 0) {
                    amoniaValues = amoniaValues / jumData;
                    dataAmonia.AmoniaValue(amoniaValues);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
