package com.example.self;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PreviousBookingsActivity extends AppCompatActivity {

    TextView previousBookings;
    FirebaseAuth firebaseAuth ;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser currentUser ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String checkInDate,checkoutDate,noOfRooms,typeOfRoom,amount ;
    CollectionReference collectionReference = db.collection("RoomBookings");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_bookings);

        firebaseAuth = FirebaseAuth.getInstance();
        previousBookings = findViewById(R.id.previousBookings);
        currentUser = firebaseAuth.getCurrentUser();
        String uId = currentUser.getUid();

        collectionReference.whereEqualTo("User Id",uId).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        String text = "" ;
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                            checkInDate = documentSnapshot.getString("Check In Date");
                            checkoutDate = documentSnapshot.getString("Check Out Date");
                            noOfRooms = documentSnapshot.getString("No of rooms");
                            typeOfRoom = documentSnapshot.getString("Type of room");
                            amount = documentSnapshot.getString("Billing amount");


                            text += "Check in date : " + checkInDate + "\n" + "Check out date : " + checkoutDate + "\n"  + "Type of room : "
                                    + typeOfRoom + "\n" + "No of rooms : " +noOfRooms +"\n" +"Billing Amount : " + amount+"\n\n\n" ;
                        }

                        previousBookings.setText(text);


                    }
                });


    }
}
