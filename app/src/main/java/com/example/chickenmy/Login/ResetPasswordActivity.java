package com.example.chickenmy.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chickenmy.Databases.DatabaseInit;
import com.example.chickenmy.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText edtPassword;
    private Button btnSetPassword;

    private DatabaseInit db = new DatabaseInit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edtPassword = findViewById(R.id.edt_reset_email);
        btnSetPassword = findViewById(R.id.btn_reset_password);

        btnSetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = String.valueOf(edtPassword.getText());

                db.mAuth.sendPasswordResetEmail(emailAddress);
                Toast.makeText(ResetPasswordActivity.this, "Periksa Email Anda",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
