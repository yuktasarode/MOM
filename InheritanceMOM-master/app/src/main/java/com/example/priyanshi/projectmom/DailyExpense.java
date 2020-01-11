package com.example.priyanshi.projectmom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DailyExpense extends AppCompatActivity {

    private EditText food,trans,others;
    private TextView display;
    private Button calculate;
    private double displayamt;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    private double ans,eachdaytarget;
    DatabaseReference reff;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_expense);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance();

        reff=firebaseDatabase.getReference(firebaseUser.getUid());

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Member member=dataSnapshot.getValue(Member.class);
                ans=member.getAns();
                eachdaytarget=member.getEachdaytarget();
               // Toast.makeText(DailyExpense.this,"Ans:"+ans,Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DailyExpense.this,databaseError.getCode(),Toast.LENGTH_LONG).show();

            }
        });

        food=(EditText)findViewById(R.id.food);
        trans=(EditText)findViewById(R.id.trans);
        others=(EditText)findViewById(R.id.others);
        calculate=(Button)findViewById(R.id.btncalculate);
        display=(TextView)findViewById(R.id.display );

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayamt = Double.parseDouble(food.getText().toString().trim()) +
                        Double.parseDouble(trans.getText().toString().trim()) +
                        Double.parseDouble(others.getText().toString().trim());

                if(displayamt==ans)
                {
                    display.setText("Today's Limit reached.Tomorrow you can spend: "+displayamt);
                }
                else if(displayamt>ans)
                {
                    double additional=displayamt-ans;
                    eachdaytarget=Math.round(ans-additional);

                    display.setText("Oops!! Today's Limit exceeded.\nTomorrow you can spend: "+eachdaytarget);
                }
                else if(displayamt<ans){
                    double additional=Math.round(ans-displayamt);
                    eachdaytarget=Math.round(ans+additional);
                    display.setText("Congratulations!! You saved " +additional+"\n"+"Tomorrow you can spend: "+eachdaytarget);

                }

                Member member=new Member();
                member.setEachdaytarget(eachdaytarget);
                member.setAns(ans);
                reff.setValue(member);

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
                startActivity(new Intent(DailyExpense.this, MainActivity.class));
                Toast.makeText(DailyExpense.this, "Logout Successful", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
