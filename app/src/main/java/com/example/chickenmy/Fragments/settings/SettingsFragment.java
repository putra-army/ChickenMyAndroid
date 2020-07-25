package com.example.chickenmy.Fragments.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.chickenmy.Databases.SettingDatabase;
import com.example.chickenmy.Login.LoginActivity;
import com.example.chickenmy.Models.SettingModel;
import com.example.chickenmy.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class SettingsFragment extends Fragment implements View.OnClickListener{

    TextView tvLogout, tvKirimData, tvKipas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        tvLogout    = root.findViewById(R.id.tv_loguot);
        tvKirimData = root.findViewById(R.id.tv_pengaturan_suhu);
        tvKipas     = root.findViewById(R.id.tv_pengaturan_kipas);

        tvLogout.setOnClickListener(this);
        tvKirimData.setOnClickListener(this);
        tvKipas.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_loguot) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
        if (i == R.id.tv_pengaturan_suhu) {
            startActivity(new Intent(getActivity(), SettingDataKirimActivity.class));
        }
        if (i == R.id.tv_pengaturan_kipas) {
            startActivity(new Intent(getActivity(), SettingKipasActivity.class));
        }
    }
}