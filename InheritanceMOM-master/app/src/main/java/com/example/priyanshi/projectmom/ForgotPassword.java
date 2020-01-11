package com.example.priyanshi.projectmom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText passemail;
    private Button passreset;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        passemail=(EditText)findViewById(R.id.etPassReset);
        passreset=(Button)findViewById(R.id.btnpassreset);
        firebaseAuth= FirebaseAuth.getInstance();

        passreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteremail=passemail.getText().toString().trim();

                if(enteremail.equals("")){
                    Toast.makeText(ForgotPassword.this,"Enter Email Id",Toast.LENGTH_LONG).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(enteremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this,"Password Reset Email Sent",Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(ForgotPassword.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(ForgotPassword.this,"Error.Couldn't send email",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
