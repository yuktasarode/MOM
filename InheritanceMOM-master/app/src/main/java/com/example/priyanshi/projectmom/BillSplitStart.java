package com.example.priyanshi.projectmom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class  BillSplitStart extends AppCompatActivity {

    EditText etnumber;
    Button btnadd;
    public String  n;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bill_split_start);



    etnumber=(EditText)findViewById(R.id.etnumber);
    btnadd=(Button)findViewById(R.id.btnadd);

    btnadd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            n=etnumber.getText().toString();
            Intent intent=new Intent(getApplicationContext(),BillSplitMain.class);
            intent.putExtra("number",n);
            startActivity(intent);
        }
    });

        }

}
