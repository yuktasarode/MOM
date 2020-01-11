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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private EditText signuser,email,signpass,prof;
    private Button create;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth= FirebaseAuth.getInstance();

        signuser=(EditText)findViewById(R.id.signuser);
        signpass=(EditText)findViewById(R.id.signpass);
        email=(EditText)findViewById(R.id.email);
        prof=(EditText)findViewById(R.id.prof);
        create=(Button)findViewById(R.id.signup);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //Upload Data to Database
                    String user_email=email.getText().toString().trim();
                    String user_password=signpass.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                sendEmailVerification();

                            }
                            else
                            {
                                Toast.makeText(SignUp.this,"Registration Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    Boolean validate()
    {
        Boolean result=false;
        String e=email.getText().toString();
        String n=signuser.getText().toString();
        String p=signpass.getText().toString();

        if(e.isEmpty()||p.isEmpty()||n.isEmpty())
        {
            Toast.makeText(SignUp.this,"Enter data for all mandatory fields",Toast.LENGTH_LONG).show();

        }
        else
        {
            result=true;
        }
        return result;
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(SignUp.this,"Successfully Registered. Verfication mail sent",Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(SignUp.this,MainActivity.class));
                    }
                    else{
                        Toast.makeText(SignUp.this,"Error:Verfication mail not sent.Try again",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
