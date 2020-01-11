package com.example.priyanshi.projectmom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MonthlyGoals extends AppCompatActivity {

    EditText inc, save, househelp, phone, misc;
    Button extra,btndailyexp;
    Double ans;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    DatabaseReference reff;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_goals);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();

        reff=firebaseDatabase.getReference(firebaseUser.getUid());





        inc = (EditText) findViewById(R.id.inc);
        save = (EditText) findViewById(R.id.save);
        househelp = (EditText) findViewById(R.id.househelp);
        phone = (EditText) findViewById(R.id.phone);
        misc = (EditText) findViewById(R.id.misc);
        extra = (Button) findViewById(R.id.extra);
        btndailyexp = (Button) findViewById(R.id.btndailyexp);


       extra.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               double i = Double.parseDouble(inc.getText().toString());
               double s = Double.parseDouble(save.getText().toString());
               double f = Double.parseDouble(househelp.getText().toString());
               double t = Double.parseDouble(phone.getText().toString());
               double o = Double.parseDouble(misc.getText().toString());

               ans = ((i - s) - (f + t + o)) / 30;
              Toast.makeText(MonthlyGoals.this, ans.toString(), Toast.LENGTH_LONG).show();

               Member member=new Member(ans,ans );
               reff.setValue(member);
              // Toast.makeText(MonthlyGoals.this,"Data Saved",Toast.LENGTH_LONG).show();

           }
       });

        btndailyexp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MonthlyGoals.this,DailyExpense.class));
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenu: {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(MonthlyGoals.this, MainActivity.class));
                Toast.makeText(MonthlyGoals.this, "Logout Successful", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    }

