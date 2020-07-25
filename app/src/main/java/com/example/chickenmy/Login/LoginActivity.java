package com.example.chickenmy.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chickenmy.Databases.DatabaseInit;
import com.example.chickenmy.MainActivity;
import com.example.chickenmy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseInit db = new DatabaseInit();
    private EditText etEmail, etPass;
    private Button btnLogin;
    private TextView tvResetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.edt_reset_email);
        etPass  = findViewById(R.id.login_et_password);

        btnLogin= findViewById(R.id.btn_reset_password);

        tvResetPass = findViewById(R.id.tv_reset_password);

        btnLogin.setOnClickListener(this);
        tvResetPass.setOnClickListener(this);

        db.mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    protected void onStart(){
        super.onStart();

        db.mAuth.addAuthStateListener(db.mAuthListener);

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_reset_password) {
            logIn();
        }
        if (i == R.id.tv_reset_password) {
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
        }
    }

    //fungsi signin untuk mengkonfirmasi data pengguna yang sudah mendaftar sebelumnya
    private void logIn() {
        Log.d("TAG", "logIn");
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        db.mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "logIn:onComplete:" + task.isSuccessful());
                        //hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(LoginActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // membuat User admin baru
        writeNewAdmin(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Required");
            result = false;
        } else {
            etEmail.setError(null);
        }

        if (TextUtils.isEmpty(etPass.getText().toString())) {
            etPass.setError("Required");
            result = false;
        } else {
            etPass.setError(null);
        }

        return result;
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewAdmin(String userId, String name, String email) {
//        String userModel = new UserModel.;

        db.users.child(userId).child("email").setValue(email);
    }
}
