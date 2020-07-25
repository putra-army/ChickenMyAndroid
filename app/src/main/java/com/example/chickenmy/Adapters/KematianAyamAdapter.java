package com.example.chickenmy.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chickenmy.Databases.DashboardDatabase;
import com.example.chickenmy.Models.GridModel;
import com.example.chickenmy.R;

import java.text.DecimalFormat;
import java.util.List;

public class KematianAyamAdapter {
    private Context mContext;
    private Adapter mHomeAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<GridModel> gridModels, List<String> keys) {
        mContext = context;
        mHomeAdapter = new Adapter(gridModels, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mHomeAdapter);
    }

    class homeItemView extends RecyclerView.ViewHolder {

        private TextView tvTanggal, tvAyamMati, tvSuhu, tvHumi, tvAmonia;

        public homeItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.adapter_kematian_ayam, parent, false));

            tvTanggal   = itemView.findViewById(R.id.tv_tanggal_kematian);
            tvAyamMati  = itemView.findViewById(R.id.tv_kematian);
            tvSuhu      = itemView.findViewById(R.id.tv_kematian_temp);
            tvHumi      = itemView.findViewById(R.id.tv_kematian_humi);
            tvAmonia    = itemView.findViewById(R.id.tv_kematian_amonia);

        }

        public  void bind(final GridModel gridModel, String key) {

            tvTanggal.setText(String.valueOf(key));
            tvAyamMati.setText(gridModel.getA_berat() + " Ekor");
            tvSuhu.setText(gridModel.getD_temp() + "°C");
            tvHumi.setText(gridModel.getC_huma() + " %");
            tvAmonia.setText(gridModel.getB_amonia() + " ppm");

//            new DashboardDatabase().getTotalHari(new DashboardDatabase.DataJumlahHari() {
//                @Override
//                public void DataIsLoaded(Integer jumlahHari) {
//
//                    // 1 - 3 Hari
//                    if (jumlahHari < 3) {
//                        if (Double.parseDouble(gridModel.getD_temp()) < 29) {
//                            tvSuhu.setTextColor(Color.parseColor("#00567a"));
//                        } else if (Double.parseDouble(gridModel.getD_temp()) > 32) {
//                            tvSuhu.setTextColor(Color.parseColor("#bf0d00"));
//                        } else {
//                            tvSuhu.setTextColor(Color.parseColor("#000000"));
//                        }
//                    }
//
//                    // 3 - 5
//                    else if (jumlahHari < 6) {
//                        if (Double.parseDouble(gridModel.getD_temp()) < 27) {
//                            tvSuhu.setTextColor(Color.parseColor("#00567a"));
//                        } else if (Double.parseDouble(gridModel.getD_temp()) > 30) {
//                            tvSuhu.setTextColor(Color.parseColor("#bf0d00"));
//                        } else {
//                            tvSuhu.setTextColor(Color.parseColor("#000000"));
//                        }
//                    }
//
//                    // 6 - 8
//                    else if (jumlahHari < 9) {
//                        if (Double.parseDouble(gridModel.getD_temp()) < 25) {
//                            tvSuhu.setTextColor(Color.parseColor("#00567a"));
//                        } else if (Double.parseDouble(gridModel.getD_temp()) > 28) {
//                            tvSuhu.setTextColor(Color.parseColor("#bf0d00"));
//                        } else {
//                            tvSuhu.setTextColor(Color.parseColor("#000000"));
//                        }
//                    }
//
//                    // 6 - 8
//                    else if (jumlahHari <= 12) {
//                        if (Double.parseDouble(gridModel.getD_temp()) < 25) {
//                            tvSuhu.setTextColor(Color.parseColor("#00567a"));
//                        } else if (Double.parseDouble(gridModel.getD_temp()) > 27) {
//                            tvSuhu.setTextColor(Color.parseColor("#bf0d00"));
//                        } else {
//                            tvSuhu.setTextColor(Color.parseColor("#000000"));
//                        }
//                    }
//
//                    // > 12
//                    else {
//                        if (Double.parseDouble(gridModel.getD_temp()) < 24) {
//                            tvSuhu.setTextColor(Color.parseColor("#00567a"));
//                        } else if (Double.parseDouble(gridModel.getD_temp()) > 26) {
//                            tvSuhu.setTextColor(Color.parseColor("#bf0d00"));
//                        } else {
//                            tvSuhu.setTextColor(Color.parseColor("#000000"));
//                        }
//                    }
//                }
//            });
//
//            //Kelembaban
//            if (Double.parseDouble(gridModel.getC_huma()) > 75) {
//                tvHumi.setTextColor(Color.parseColor("#bf0d00"));
//            } else {
//                tvHumi.setTextColor(Color.parseColor("#000000"));
//            }
//
//            //amonia
//            if (Double.valueOf(gridModel.getB_amonia()) > 24) {
//                tvAmonia.setTextColor(Color.parseColor("#bf0d00"));
//            } else {
//                tvAmonia.setTextColor(Color.parseColor("#000000"));
//            }
        }
    }

    class Adapter extends RecyclerView.Adapter<homeItemView> {
        private List<GridModel> gridModels;
        private List<String> keys;

        public Adapter(List<GridModel> gridModels, List<String> keys) {
            this.gridModels = gridModels;
            this.keys = keys;
        }

        @NonNull
        @Override
        public homeItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new homeItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull homeItemView holder, int position) {
            holder.bind(gridModels.get(position), keys.get(position));
        }

        @Override
        public int getItemCount() {

            return gridModels.size();
        }
    }
}
