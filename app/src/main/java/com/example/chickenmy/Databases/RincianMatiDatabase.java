package com.example.chickenmy.Databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chickenmy.Fragments.dashboard.RincianMatiActivity;
import com.example.chickenmy.Models.GridModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RincianMatiDatabase {
    private DatabaseInit db = new DatabaseInit();
    private List<String> keys = new ArrayList<>();
    private List<GridModel> gridModels = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(int mati, int tertinggi, int rerata, int terendah);
    }

    public interface DataMatiHarian {
        void DataISLoaded(List<GridModel> gridModels, List<String> keys);
    }

    public void getData(final RincianMatiDatabase.DataStatus dataStatus) {
        db.GrafikGrid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int matiTotal = 0;
                    int tertinggi = 0;
                    int terendah = 999;
                    float rerata = 0;
                    int jumData = 0;
                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.child("mati").exists()) {
                            int mati = ds.child("mati").getValue(Integer.class);
                            matiTotal = matiTotal + mati;
                            if (mati > tertinggi) {
                                tertinggi = mati;
                            }
                            if (mati < terendah && mati != 0) {
                                terendah = mati;
                            }
                            jumData++;
                        }

                    }
                    rerata = (float)matiTotal / (float)jumData;
                    Log.d("rerata", String.valueOf(rerata));
                    dataStatus.DataIsLoaded(matiTotal, tertinggi, (Integer)Math.round(rerata), terendah);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDataMatiHarian(final RincianMatiDatabase.DataMatiHarian dataStatus) {
        db.GrafikGrid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                    db.GrafikGrid.child(ds.getKey()).child("RFID").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
//                                int berat = 0, amonia = 0;
                                Double suhu = 0.0, humi = 0.0, amonia = 0.0;
                                int jumData = 0;

                                if (ds.child("mati").exists()) {
                                    for (final DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                        GridModel gridModel = ds1.getValue(GridModel.class);

                                        suhu = suhu + Double.valueOf(gridModel.getD_temp());
                                        humi = humi + Double.valueOf(gridModel.getC_huma());
                                        amonia = amonia + Double.valueOf(gridModel.getB_amonia());

                                        jumData++;
                                    }

                                    int mati = ds.child("mati").getValue(Integer.class);
                                    GridModel gridModel = new GridModel();

                                    DecimalFormat df = new DecimalFormat("#.##");

                                    gridModel.setA_berat(String.valueOf(mati));
                                    gridModel.setD_temp(String.valueOf(df.format(suhu / jumData)));
                                    gridModel.setC_huma(String.valueOf(df.format(humi / jumData)));
                                    gridModel.setB_amonia(String.valueOf(df.format(amonia / jumData)));

                                    gridModels.add(gridModel);
                                    keys.add(ds.getKey());

                                    dataStatus.DataISLoaded(gridModels, keys);
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
