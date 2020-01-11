package com.example.priyanshi.projectmom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class BillSplitMain extends AppCompatActivity {

    EditText etamt,etnumber;
    int num,member;
    static int count = 0;
    double amount = 0d;
    Double perhead = 0d;
    LinearLayout ll;
    TextView display,value;
    Button btncalc,btnnext;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_bill_split_main);

        firebaseAuth= FirebaseAuth.getInstance();


        etamt = (EditText) findViewById(R.id.etamt);
        etnumber=(EditText)findViewById(R.id.etnumber);
        display = (TextView) findViewById(R.id.tvdisplay);
        value=(TextView)findViewById(R.id.tvvalue);
        btncalc = (Button) findViewById(R.id.btncalc);
        btnnext=(Button)findViewById(R.id.btnnext);

        ll = (LinearLayout) findViewById(R.id.ll);
        Intent intent = getIntent();
        String n = intent.getStringExtra("number");
        num = Integer.parseInt(n);

        final Double[] expense = new Double[num];
        for(int i=0;i<num;i++)
        {
            expense[i]=0d;
        }
        final CheckBox[] cba = new CheckBox[num];
        for (int i = 0; i < num; i++) {
            cba[i] = new CheckBox(this);
            ll.addView(cba[i]);
            cba[i].setText("Member " + (i + 1));
            cba[i].setId(i);


        }
        btncalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = etamt.getText().toString();
                amount = Double.parseDouble(a);
                member=Integer.parseInt(etnumber.getText().toString());
                count=0;


                for (int i = 0; i < num; i++) {
                    if (cba[i].isChecked()) {
                        count++;
                    }
                }
                perhead = amount / count;
                for (int i = 0; i < num; i++) {
                    if (cba[i].isChecked()) {
                        expense[i] = expense[i]+ perhead ;
                    }
                }



                display.setText("Amount is " + Math.round(expense[member-1])  );
                for(int i=0;i<num;i++)
                {
                    cba[i].setChecked(false);
                }

            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<num;i++)
                {
                    cba[i].setChecked(false);

                }
                etamt.getText().clear();
                display.setText("");

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
                startActivity(new Intent(BillSplitMain.this, MainActivity.class));
                Toast.makeText(BillSplitMain.this, "Logout Successful", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}




