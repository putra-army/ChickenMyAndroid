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

public class SettingKipasActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLevel1, edtLevel2, edtLevel3, edtLevel4;
    private ImageView btnBack;
    private Button btnSave;

    private DatabaseInit db = new DatabaseInit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_kipas);

        edtLevel1 = findViewById(R.id.edt_level1);
        edtLevel2 = findViewById(R.id.edt_level2);
        edtLevel3 = findViewById(R.id.edt_level3);
        edtLevel4 = findViewById(R.id.edt_level4);

        btnBack = findViewById(R.id.btn_back_tambah_kematian);
        btnSave = findViewById(R.id.btn_kematian_simpan);

        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        new SettingDatabase().getData(new SettingDatabase.DataStatus() {
            @Override
            public void DataIsLoaded(List<SettingModel> settingModels) {
                edtLevel1.setText(settingModels.get(0).getLevel1());
                edtLevel2.setText(settingModels.get(0).getLevel2());
                edtLevel3.setText(settingModels.get(0).getLevel3());
                edtLevel4.setText(settingModels.get(0).getLevel4());
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
            db.setting.child("level1").setValue(edtLevel1.getText().toString());
            db.setting.child("level2").setValue(edtLevel2.getText().toString());
            db.setting.child("level3").setValue(edtLevel3.getText().toString());
            db.setting.child("level4").setValue(edtLevel4.getText().toString());
            finish();
        }
    }
}
