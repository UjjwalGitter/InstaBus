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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    TextView email;
    Button reset;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        reset = findViewById(R.id.reset);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        progressBar = (ProgressBar)findViewById(R.id.spin_kite);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String semail = email.getText().toString();
                String[] arrOfStr = semail.split("@");
                if (TextUtils.isEmpty(semail)) {
                    Toast.makeText(ForgetPassword.this, "All credentials are required!", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    Log.i("semail", semail);
                    try {
                        if (arrOfStr.length==1){
                            Toast.makeText(ForgetPassword.this, "Enter valid Email", Toast.LENGTH_SHORT).show();
                        }
                        if (arrOfStr[1].equals("iiitdmj.ac.in")) {
                            Log.i("great","Derek");
                            progressBar.setVisibility(View.VISIBLE);

                            auth.sendPasswordResetEmail(semail)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.i("greatooo","ede");
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(ForgetPassword.this, "Password Reset link has been sent to your Email.", Toast.LENGTH_SHORT).show();

                                                Intent intent=new Intent(ForgetPassword.this,LoginActivity.class);
                                                startActivity(intent);
                                                finish();

                                            }else
                                                Log.i("greatnes ", " igtr");
                                        }
                                    });
                        } else {
                            Log.i("great","sd");
                            Toast.makeText(ForgetPassword.this, "Please use your college's email", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {

                    }

                }
            }
        });
    }
    @Override
    public void onBackPressed()
    {

        Intent intent=new Intent(ForgetPassword.this,LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
