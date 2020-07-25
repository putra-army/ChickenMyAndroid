package com.example.chickenmy.Databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chickenmy.Models.SettingModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardDatabase {
    public DatabaseInit db = new DatabaseInit();
    public List<String> keys = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(float beratToday);
    }

    public interface DataStatusMati {
        void DataIsLoaded(Integer totalMati);
    }

    public interface DataTanggal {
        void DataIsLoaded(String tanggalMulai, String tanggalTerakhir);
    }

    public interface DataJumlahHari {
        void DataIsLoaded(Integer jumlahHari);
    }

    public void getDataBerat(final DashboardDatabase.DataStatus dataStatus) {
        db.GrafikGrid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keys.clear();
                for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                    db.GrafikGrid.child(ds.getKey()).child("RFID").orderByChild("idGrid").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                float beratToday = 0;
                                int jumData = 0;
                                for (final DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                    if (ds1.child("a_berat").exists()) {
                                        String berat = String.valueOf(ds1.child("a_berat").getValue(String.class));
                                        beratToday = beratToday + Float.valueOf(berat);
                                        jumData++;
                                    }
                                }
                                beratToday = (beratToday / jumData) / 1000;
                                dataStatus.DataIsLoaded(beratToday);
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

    public void getDataMati(final DashboardDatabase.DataStatusMati dataStatus) {
        db.GrafikGrid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int matiTotal = 0;
                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.child("mati").exists()) {
                            int mati = ds.child("mati").getValue(Integer.class);
                            matiTotal = matiTotal + mati;
                        }
                    }
                    dataStatus.DataIsLoaded(matiTotal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getTotalHari (final DashboardDatabase.DataJumlahHari dataJumlahHari) {
        db.GrafikGrid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int jumlahData = 0;
                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                        jumlahData++;
                    }
                    dataJumlahHari.DataIsLoaded(jumlahData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getTanggalMulaiDanAkhir (final DashboardDatabase.DataTanggal dataStatus) {
        db.GrafikGrid.limitToFirst(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                        db.GrafikGrid.limitToLast(1).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                Log.d("Tanggal Mulai", "ada");
                                for (final DataSnapshot ds1 : dataSnapshot1.getChildren()) {
                                    String tglMulai = ds.getKey();
                                    String tglAkhir = ds1.getKey();

                                    Log.d("Tanggal Mulai", tglMulai);
                                    dataStatus.DataIsLoaded(tglMulai, tglAkhir);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
