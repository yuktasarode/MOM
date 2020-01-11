package com.example.priyanshi.projectmom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RateUs extends AppCompatActivity {


    private RatingBar ratingBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        firebaseAuth= FirebaseAuth.getInstance();


        ratingBar=(RatingBar)findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               int rate=(int)rating;
                String message=null;

                switch(rate){
                    case 1:
                        message="Sorry to hear that! :(";
                        break;
                    case 2:
                        message="We will try to improve your experience";
                        break;
                    case 3:
                        message="Good Enough";
                        break;
                    case 4:
                        message="Great! Thank you";
                        break;
                    case 5:
                        message="Awesome! Thank you :)";
                        break;

                }

                Toast.makeText(RateUs.this,message,Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(RateUs.this, MainActivity.class));
                Toast.makeText(RateUs.this, "Logout Successful", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
