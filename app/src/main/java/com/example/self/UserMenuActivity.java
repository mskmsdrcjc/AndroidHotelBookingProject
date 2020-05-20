package com.example.self;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class UserMenuActivity extends AppCompatActivity {
    Button roomBooking,signOut,gallery,previousBooking;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        roomBooking = findViewById(R.id.roomBooking);
        signOut = findViewById(R.id.signOutB);
        gallery = findViewById(R.id.gallery);
        previousBooking = findViewById(R.id.previousBookings);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null && firebaseAuth != null){

                    firebaseAuth.signOut();

                    startActivity(new Intent(UserMenuActivity.this,MainActivity.class));
                }
            }
        });

        roomBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMenuActivity.this,RoomBookingActivity.class));
            }
        });

        previousBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UserMenuActivity.this,PreviousBookingsActivity.class));

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMenuActivity.this,GalleryActivity.class));
            }
        });
    }
}
