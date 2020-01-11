package com.example.priyanshi.projectmom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText uname,pass;
    private Button login,signup;
    private TextView forgotpass;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();


        uname=(EditText)findViewById(R.id.loguser);
        pass=(EditText)findViewById(R.id.logpass);
        login=(Button) findViewById(R.id.login);
        signup=(Button) findViewById(R.id.logcreate);
        forgotpass=(TextView)findViewById(R.id.forgotpass) ;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(uname.getText().toString(),pass.getText().toString());

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ForgotPassword.class));
            }
        });

    }


    void validate(String username,String password)
    {
        firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                    checkEmailVerification();

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Login Unsuccessful",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();

        if(emailflag){
            finish();
            startActivity(new Intent(MainActivity.this,Homepage.class));
        }

        else
        {
            Toast.makeText(MainActivity.this,"Verify Email",Toast.LENGTH_LONG).show();
            firebaseAuth.signOut();
        }
    }


}
