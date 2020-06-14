package com.ujjwalsingh.busticket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static com.ujjwalsingh.busticket.StartPage.tic;
import static com.ujjwalsingh.busticket.StartPage.ticfive;
import static com.ujjwalsingh.busticket.StartPage.ticsix;

public class BuyTicketActivity extends AppCompatActivity {

    private String TAG = "SignUpActivity";
    String payeeAddress = "dulichand.bhadoria-1@oksbi";
    String payeeName = "Dulichand Bhadoria";
    String transactionNote = " ";
    String amount = "1";
    Integer resTic =0;
    String currencyUnit = "INR";
    String concat="";
    Intent intent = getIntent();
    String timing="3:30";
    TextView date;
    DatabaseReference reference, demoRef, tref, userref;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        UUID unicode = UUID.randomUUID();
        date=findViewById(R.id.ks);
        String datea=(String)android.text.format.DateFormat.format("dd", new Date())+" "+(String)android.text.format.DateFormat.format("MMMM", new Date());;
date.setText(datea);
        String ucode = unicode.toString();
        intent = getIntent();
        Log.i("ankit", intent.getStringExtra("button"));
        Object intentbutton = intent.getStringExtra("button");
        if (intentbutton.equals("three")){
            resTic=tic;
            concat="three";
        }else if (intentbutton.equals("five")){
            resTic=ticfive;
            concat="five";
        }else if (intentbutton.equals("six")){
            concat="six";
            resTic=ticsix;
        }
        String uc1 = ucode.substring(0,6);
        System.out.println(uc1);

        userref = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                String r = users.getUsername();
                transactionNote+=transactionNote+"  InstBus Payment                           "+"    "+uc1+" | ";

//                transactionNote+=transactionNote+"InstBus Payment                 "+"Username: "+r+" Ticket Id: "+uc1;
                reference = FirebaseDatabase.getInstance().getReference();
                demoRef = reference.child("Tickets");
                demoRef.child("remaining"+concat).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Integer value = Integer.valueOf(dataSnapshot.getValue().toString());
                        String v = Long.toString(value);
                        Log.i("dsds", "ere" + String.valueOf(value));
                        transactionNote+="Seat No. "+v;
                        //demoRef.child("remainingtickets").setValue(value-1);
                        // avti.setText(String.valueOf(value) + " Seats");
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                Log.i("puot",String.valueOf(users.getUsername()));
                //toti.setText(String.valueOf(value)+" Seats");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Button btnSubmit = (Button) findViewById(R.id.button);
       // reference = FirebaseDatabase.getInstance().getReference();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
                Uri uri = Uri.parse("upi://pay?pa="+payeeAddress+"&pn="+payeeName+"&tn="+transactionNote+
                        "&am="+amount+"&cu="+currencyUnit);
                Log.d(TAG, "onClick: uri: "+uri);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
                startActivityForResult(intent,1);
            }
        });


    }

    private void status(Integer status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("hasticket"+concat,status);
        transactionNote+=timing;
        reference.updateChildren(hashMap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "onActivityResult: requestCode: "+requestCode);
        Log.d(TAG, "onActivityResult: resultCode: "+resultCode);
        //txnId=UPI20b6226edaef4c139ed7cc38710095a3&responseCode=00&ApprovalRefNo=null&Status=SUCCESS&txnRef=undefined
        //txnId=UPI608f070ee644467aa78d1ccf5c9ce39b&responseCode=ZM&ApprovalRefNo=null&Status=FAILURE&txnRef=undefined

        if(data!=null) {
            Log.d(TAG, "onActivityResult: data: " + data.getStringExtra("response"));
            String res = data.getStringExtra("response");
            String search = "SUCCESS";
            if (res.toLowerCase().contains(search.toLowerCase())) {
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
            resTic=1;
                final Integer[] ks = {0};
            status(resTic);
                reference = FirebaseDatabase.getInstance().getReference();
                demoRef = reference.child("Tickets");
                demoRef.child("remaining"+concat).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Integer value = Integer.valueOf(dataSnapshot.getValue().toString());
                        String v = Long.toString(value);
                        Log.i("dsds", "ere" + String.valueOf(value));
                        transactionNote+="Seat No. "+v;
                        demoRef.child("remaining"+concat).setValue(value-1);
                       // avti.setText(String.valueOf(value) + " Seats");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                Intent intent = new Intent(BuyTicketActivity.this,StartPage.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
            }
        }

    }


}
