package com.example.chickenmy.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chickenmy.Databases.DashboardDatabase;
import com.example.chickenmy.Models.GridModel;
import com.example.chickenmy.R;

import java.util.List;

public class GridAdapter {
    private Context mContext;
    private Adapter mHomeAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<GridModel> gridModels) {
        mContext = context;
        mHomeAdapter = new Adapter(gridModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mHomeAdapter);
    }

    class homeItemView extends RecyclerView.ViewHolder {

        private TextView tvIdGrid, tvGridBerat, tvGridTemp, tvGridHumi, tvGridAmonia;
        private TextView tvUpdateDate, tvUpdateTiem;
        private CardView mCardView;

        public homeItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.adapter_grid, parent, false));

            tvIdGrid = itemView.findViewById(R.id.tv_id_grid);
            tvGridBerat = itemView.findViewById(R.id.tv_grid_berat);
            tvGridTemp  = itemView.findViewById(R.id.tv_grid_temp);
            tvGridHumi  = itemView.findViewById(R.id.tv_grid_humi);
            tvGridAmonia= itemView.findViewById(R.id.tv_grid_amonia);

            tvUpdateDate= itemView.findViewById(R.id.grid_update_date);
            tvUpdateTiem= itemView.findViewById(R.id.grid_update_time);

        }

        public  void bind(final GridModel gridModel) {

            tvIdGrid.setText(String.valueOf(gridModel.getIdGrid()));
            tvGridBerat.setText(gridModel.getA_berat() + " Gram");
            tvGridTemp.setText(gridModel.getD_temp() + "°C");
            tvGridHumi.setText(gridModel.getC_huma() + " %");
            tvGridAmonia.setText(gridModel.getB_amonia() + " ppm");

            tvUpdateDate.setText(gridModel.getTanggal());
            tvUpdateTiem.setText(gridModel.getWaktu());

        }
    }

    class Adapter extends RecyclerView.Adapter<homeItemView> {
        private List<GridModel> gridModels;

        public Adapter(List<GridModel> gridModels) {
            this.gridModels = gridModels;
        }

        @NonNull
        @Override
        public homeItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new homeItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull homeItemView holder, int position) {
            holder.bind(gridModels.get(position));
        }

        @Override
        public int getItemCount() {

            return gridModels.size();
        }
    }
}
