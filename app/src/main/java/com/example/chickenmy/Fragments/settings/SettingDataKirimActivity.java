package com.example.chickenmy.Fragments.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.chickenmy.Databases.DatabaseInit;
import com.example.chickenmy.Databases.SettingDatabase;
import com.example.chickenmy.Models.SettingModel;
import com.example.chickenmy.R;

import java.util.List;

public class SettingDataKirimActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSuhu, edtHumi, edtKec;
    private ImageView btnBack;
    private Button btnSave;

    private DatabaseInit db = new DatabaseInit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_data_kirim);

        edtSuhu = findViewById(R.id.edt_level1);
        edtHumi = findViewById(R.id.edt_level2);
        edtKec  = findViewById(R.id.edt_level3);

        btnBack = findViewById(R.id.btn_back_tambah_kematian);
        btnSave = findViewById(R.id.btn_kematian_simpan);

        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        new SettingDatabase().getData(new SettingDatabase.DataStatus() {
            @Override
            public void DataIsLoaded(List<SettingModel> settingModels) {
                edtSuhu.setText(settingModels.get(0).getRangeTemp());
                edtHumi.setText(settingModels.get(0).getRangeHumi());
                edtKec.setText(settingModels.get(0).getRangeWind());
            }
        });

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_back_tambah_kematian) {
            finish();
        }
        if (i == R.id.btn_kematian_simpan) {
            db.setting.child("rangeTemp").setValue(edtSuhu.getText().toString());
            db.setting.child("rangeHumi").setValue(edtHumi.getText().toString());
            db.setting.child("rangeWind").setValue(edtKec.getText().toString());
            finish();

        }
    }
}
