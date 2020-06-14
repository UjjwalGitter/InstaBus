package com.ujjwalsingh.busticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView newuser;
    EditText email, password;
    Button signup;
    TextView forget;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        newuser = findViewById(R.id.newuser);
        email = findViewById(R.id.email);
        signup = findViewById(R.id.login);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);

        forget = findViewById(R.id.forgetpass);
        mAuth = FirebaseAuth.getInstance();
        password = findViewById(R.id.password);
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("Str", "sds");
                String u_email = email.getText().toString();
                String u_password = password.getText().toString();
                if (TextUtils.isEmpty(u_email) || TextUtils.isEmpty(u_password)) {
                    Toast.makeText(LoginActivity.this, "All credentials are required!", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(u_email, u_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.i("Strs", "sds");

                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(LoginActivity.this, StartPage.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.i("Strad", "sds");
                                    Toast.makeText(LoginActivity.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "Failed to Login with these credentials", Toast.LENGTH_SHORT).show();
                                Log.i("Sastr", "sds");

                            }

                        }
                    });
                }
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();

    }
}
