package com.example.chickenmy.Fragments.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chickenmy.Adapters.GridAdapter;
import com.example.chickenmy.Databases.DashboardDatabase;
import com.example.chickenmy.Databases.DatabaseInit;
import com.example.chickenmy.Databases.Fix1S1Database;
import com.example.chickenmy.Databases.PortableDatabase;
import com.example.chickenmy.GetDate;
import com.example.chickenmy.MainActivity;
import com.example.chickenmy.Models.GridModel;
import com.example.chickenmy.R;

import java.text.DecimalFormat;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView mTime, mWellcome;
    private TextView tvWindKandang, tvSuhuKandang, tvHumaKandang, tvAmoniaKandang;
    private TextView tvUpdateTgl, tvUpdateTime, tvHariKe;
    private ImageView imgStsSuhu, imgStsHumi, imgStsAmonia, imgStsAngin;
    private RecyclerView rvGrid;

    private int hourNow;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        GetDate getDate = new GetDate();

        mTime = root.findViewById(R.id.tv_home_welcome);
        mWellcome = root.findViewById(R.id.tv_home_hour);

        tvSuhuKandang    = root.findViewById(R.id.tv_tertinggi_mati);
        tvHumaKandang    = root.findViewById(R.id.tv_rerata_mati);
        tvWindKandang    = root.findViewById(R.id.tv_wind_kandang);
        tvAmoniaKandang  = root.findViewById(R.id.tv_terendah_mati);

        tvUpdateTgl     = root.findViewById(R.id.tv_update_tgl);
        tvUpdateTime    = root.findViewById(R.id.tv_update_time);

        tvHariKe        = root.findViewById(R.id.tv_hari_ke);

        imgStsSuhu      = root.findViewById(R.id.imgStsSuhu);
        imgStsHumi      = root.findViewById(R.id.imgStsHumi);
        imgStsAmonia    = root.findViewById(R.id.imgStsAmonia);
        imgStsAngin     = root.findViewById(R.id.imgStsAngin);

        rvGrid  = root.findViewById(R.id.rincian_grid_recycler);

        mTime.setText(getDate.getDateNow("E, dd MMMM yyyy"));
        hourNow = getDate.getTimeNow("HH");

        if (hourNow >= 3 && hourNow <= 10) {
            mWellcome.setText("Selamat Pagi");
        }
        if (hourNow >= 11 && hourNow <= 15) {
            mWellcome.setText("Selamat Siang");
        }
        if (hourNow >= 16 && hourNow <= 3) {
            mWellcome.setText("Selamat Malam");
        }

        new Fix1S1Database().getData(new Fix1S1Database.DataStatus() {
            @Override
            public void DataIsLoaded(final String hum, final String temp, String tgl, String time, String wind) {
                new DashboardDatabase().getTotalHari(new DashboardDatabase.DataJumlahHari() {
                    @Override
                    public void DataIsLoaded(Integer jumlahHari) {
                        tvHariKe.setText(jumlahHari.toString());

                        MainActivity.db.setting.child("harike").setValue(jumlahHari);
//
                        tvSuhuKandang.setText(String.valueOf(temp) + "°C");
//
//                        // 1 - 3 Hari
//                        if (jumlahHari < 3) {
//                            if (Double.parseDouble(temp) < 29) {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatbirusolid));
//                            } else if (Double.parseDouble(temp) > 32) {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatmerahsolid));
//                            } else {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatputihsolid));
//                            }
//                        }
//
//                        // 3 - 5
//                        else if (jumlahHari < 6) {
//                            if (Double.parseDouble(temp) < 28) {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatbirusolid));
//                            } else if (Double.parseDouble(temp) > 30) {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatmerahsolid));
//                            } else {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatputihsolid));
//                            }
//                        }
//
//                        // 6 - 8
//                        else if (jumlahHari < 9) {
//                            if (Double.parseDouble(temp) < 25) {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatbirusolid));
//                            } else if (Double.parseDouble(temp) > 28) {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatmerahsolid));
//                            } else {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatputihsolid));
//                            }
//                        }
//
//                        // 6 - 8
//                        else if (jumlahHari <= 12) {
//                            if (Double.parseDouble(temp) < 25) {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatbirusolid));
//                            } else if (Double.parseDouble(temp) > 28) {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatmerahsolid));
//                            } else {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatputihsolid));
//                            }
//                        }
//
//                        // > 12
//                        else {
//                            if (Double.parseDouble(temp) < 24) {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatbirusolid));
//                            } else if (Double.parseDouble(temp) > 28) {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatmerahsolid));
//                            } else {
//                                imgStsSuhu.setImageDrawable(getResources().getDrawable(R.drawable.bulatputihsolid));
//                            }
//                        }
                    }
                });

                tvHumaKandang.setText(String.valueOf(hum) + " %");
                tvUpdateTgl.setText(tgl);
                tvUpdateTime.setText(time);
                tvWindKandang.setText(wind + " m/s");

//                //Kelembaban
//                if (Double.parseDouble(hum) > 80) {
//                    imgStsHumi.setImageDrawable(getResources().getDrawable(R.drawable.bulatmerahsolid));
//                } else {
//                    imgStsHumi.setImageDrawable(getResources().getDrawable(R.drawable.bulatputihsolid));
//                }
//
//                //Kecepatan Angin
//                if (Double.parseDouble(wind) > 1.47) {
//                    imgStsAngin.setImageDrawable(getResources().getDrawable(R.drawable.bulatmerahsolid));
//                } else {
//                    imgStsAngin.setImageDrawable(getResources().getDrawable(R.drawable.bulatputihsolid));
//                }
            }

        });

        new PortableDatabase().getData(new PortableDatabase.DataStatus() {
            @Override
            public void DataIsLoaded(List<GridModel> gridModels) {
                new GridAdapter().setConfig(rvGrid, getActivity(), gridModels);
            }
        });

        new PortableDatabase().getDataAmonia(new PortableDatabase.DataAmonia() {
            @Override
            public void AmoniaValue(Double amonia) {
                DecimalFormat df = new DecimalFormat("#.##");

                tvAmoniaKandang.setText(String.valueOf(df.format(amonia)) + " ppm");

                if (Double.valueOf(amonia) > 24) {
                    imgStsAmonia.setImageDrawable(getResources().getDrawable(R.drawable.bulatmerahsolid));
                } else {
                    imgStsAmonia.setImageDrawable(getResources().getDrawable(R.drawable.bulatputihsolid));
                }
            }
        });

        return root;
    }
}