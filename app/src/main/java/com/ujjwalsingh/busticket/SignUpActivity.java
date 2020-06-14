package com.ujjwalsingh.busticket;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    TextView alread;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    Button buttonSignUp;
    boolean val;
    EditText editTextEmail,fullname, editTextPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        alread = findViewById(R.id.alread);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        alread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        editTextEmail = (EditText) findViewById(R.id.email);
        fullname = (EditText) findViewById(R.id.fullname);
        editTextPassword = (EditText) findViewById(R.id.password);
        buttonSignUp = findViewById(R.id.signup);
        progressBar = (ProgressBar)findViewById(R.id.spin_kite);
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                String[] arrOfStr = str.split("@");
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   if (val) {
                    String u_username = fullname.getText().toString();
                    String u_email = editTextEmail.getText().toString();
                    String str = editTextEmail.getText().toString();
                    String[] arrOfStr = str.split("@");
                    try {
                        if (arrOfStr[1].equals("iiitdmj.ac.in")) {
                            val = true;
                            buttonSignUp.setClickable(true);
                        } else {
                            editTextEmail.setError("Please use your college's email");
                            //  editTextEmail.
                            //buttonSignUp.setClickable(false);
                        }
                    }catch (Exception e){

                    }
                    String u_password = editTextPassword.getText().toString();
                    if (TextUtils.isEmpty(u_username) || TextUtils.isEmpty(u_email) || TextUtils.isEmpty(u_password)) {
                        Toast.makeText(SignUpActivity.this, "All credentials are required!", Toast.LENGTH_SHORT).show();
                    } else if (u_password.length() < 6) {
                        Toast.makeText(SignUpActivity.this, "Password must contain atleast 6 characters", Toast.LENGTH_SHORT).show();
                    } else {
                        if (val) {
                            register(u_username, u_email, u_password);

                        }else
                            Toast.makeText(SignUpActivity.this, "Enter valid Email", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    public void register(final String username, String email, String password){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    assert firebaseUser != null;
                    String userUId = firebaseUser.getUid();
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userUId);
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("id",userUId);
                    hashMap.put("hasticketfive",(0));
                    hashMap.put("hasticketsix",(0));
                    hashMap.put("hasticketthree",(0));
                    hashMap.put("username",username);

                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(SignUpActivity.this, "Registered successfully em", Toast.LENGTH_SHORT).show();
                            // Log.d(TAG, "Email sent.");
                        }else
                            Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    });

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "3rr3r3", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                }else{
                    Toast.makeText(SignUpActivity.this, "Unable to imgUrl with this username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void validit() {
        editTextEmail.setOnFocusChangeListener((view, b) -> {
            String str = editTextEmail.getText().toString();
            String[] arrOfStr = str.split("@");
            if (view.isFocused()) {
                Toast.makeText(this, "YEs", Toast.LENGTH_SHORT).show();
            }else {
                try {
                    if (arrOfStr[1].equals("iiitdmj.ac.in")) {
                        Toast.makeText(this, "lll", Toast.LENGTH_SHORT).show();
                   buttonSignUp.setClickable(true);
                    } else {

                        Toast.makeText(this, "kkk", Toast.LENGTH_SHORT).show();
                        editTextEmail.setError("Please use your college's email");
                       //  editTextEmail.
                        //buttonSignUp.setClickable(false);
                    }
                }catch (Exception e){
                }
                Toast.makeText(this, "NO", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(SignUpActivity.this,SplashActivity.class);
        startActivity(intent);
        finish();
    }
}
