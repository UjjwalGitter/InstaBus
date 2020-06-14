package com.ujjwalsingh.busticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StartPage extends Activity {
    CardView cardView;
    Toolbar toolbar;
    ImageView kane;
    TextView username;
    TextView date, toti,month,week;
    TextView availSeatthree, availSeatfive,availSeatsix;
    Button bthree, btfive, btsix;
   static Integer tic;
   static Integer ticfive;
   static Integer ticsix;
   LinearLayout l;
    FirebaseUser firebaseUser;
    DatabaseReference reference, demoRef, hasticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        toolbar =findViewById(R.id.sped);
        bthree=findViewById(R.id.bt);
        btfive=findViewById(R.id.bt2);
        btsix=findViewById(R.id.bt4);
        l=findViewById(R.id.custr);
        date=findViewById(R.id.latestdate);
        month=findViewById(R.id.month);
        week=findViewById(R.id.week);
        username = findViewById(R.id.sse);
        String datea=(String)android.text.format.DateFormat.format("dd", new Date());
        date.setText(datea);
        //Log.i("fert",date);
        String monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
        month.setText(monthname);
        String weewk =(String) android.text.format.DateFormat.format("EEEE", new Date());
        week.setText(weewk);
        Log.i("fern",monthname);
        //toolbar.setTitleTextColor(Color.parseColor("#FFF"));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(StartPage.this, SplashActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return false;
            }
        });
        toti = findViewById(R.id.toti);
        availSeatthree = findViewById(R.id.available);
        availSeatfive = findViewById(R.id.available2);
        availSeatsix = findViewById(R.id.available4);
        cardView = findViewById(R.id.desti);
        kane = findViewById(R.id.kane);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(StartPage.this, cardView);
                popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());
                popupMenu.show();
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(StartPage.this, l);
                popupMenu.getMenuInflater().inflate(R.menu.ujjwal, popupMenu.getMenu());
                popupMenu.show();
            }
        });

        kane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(StartPage.this, kane);
                popupMenu.getMenuInflater().inflate(R.menu.xolo, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(StartPage.this, SplashActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        return false;
                    }
                });
            }
        });

        reference = FirebaseDatabase.getInstance().getReference();

        demoRef = reference.child("Tickets");
        demoRef.child("remainingthree").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = Long.valueOf(dataSnapshot.getValue().toString());
                String v = Long.toString(value);
                Log.i("dsds", "ere" + String.valueOf(value));
                availSeatthree.setText(String.valueOf(value) + " Seats");
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });demoRef.child("remainingfive").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = Long.valueOf(dataSnapshot.getValue().toString());
                String v = Long.toString(value);
                Log.i("dsds", "ere" + String.valueOf(value));
                availSeatfive.setText(String.valueOf(value) + " Seats");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });demoRef.child("remainingsix").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = Long.valueOf(dataSnapshot.getValue().toString());
                String v = Long.toString(value);
                Log.i("dsds", "ere" + String.valueOf(value));
                availSeatsix.setText(String.valueOf(value) + " Seats");
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        hasticket = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        hasticket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                //Log.i("Sww",users.getId());
                String showname = users.getUsername();
                username.setText(showname);
                Integer three = users.getHasticketthree();
                Integer five = users.getHasticketfive();
                Integer six = users.getHasticketsix();
                    tic = three;
                    ticfive=five;
                    ticsix=six;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tic==0){
                  Intent intent = new Intent(StartPage.this,BuyTicketActivity.class);
                    intent.putExtra("button","three");
                    startActivity(intent);
               }else if (tic==1){
                   Toast.makeText(StartPage.this, "Only single ticket allowed to buy.", Toast.LENGTH_SHORT).show();
               }
            }
        });

        btfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ticfive==0){
                    Intent intentfive = new Intent(StartPage.this,BuyTicketActivity.class);
                    intentfive.putExtra("button","five");
                    startActivity(intentfive);
                }else if (ticfive==1){
                    Toast.makeText(StartPage.this, "Only single ticket allowed to buy.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btsix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ticsix==0){
                    Intent intentsix = new Intent(StartPage.this,BuyTicketActivity.class);
                    intentsix.putExtra("button","six");
                    startActivity(intentsix);
                }else if (ticsix==1){
                    Toast.makeText(StartPage.this, "Only single ticket allowed to buy.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.log:
                Log.i("sre","sd");
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(StartPage.this, SplashActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
