package com.example.self;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity{

    Button makePayment;
    TextView amountTextView;
    String typeOfRoom,noOfRooms,noOfDays,cardNumberS,validThruS,cvvS,cardHolderNameS,checkInDate,checkOutDate,amountS;
    EditText cardNumber,validThru,cvv,cardHolderName;
    Double amount,noOfRoomsD,noOfDaysD;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("RoomBookings");

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        firebaseAuth = FirebaseAuth.getInstance();
        makePayment = findViewById(R.id.makePayment);
        cardHolderName = findViewById(R.id.cardHolderName);
        cardNumber = findViewById(R.id.cardNumber);
        validThru = findViewById(R.id.validThru);
        cvv = findViewById(R.id.cvv);
        amountTextView = findViewById(R.id.amountTextView);

        Intent intent = getIntent();
        checkInDate = intent.getStringExtra("Check in date");
        checkOutDate = intent.getStringExtra("Check out date");
        noOfRooms = intent.getStringExtra("No of rooms");
        typeOfRoom = intent.getStringExtra("Type of room");
        noOfDays = intent.getStringExtra("No of days");

        noOfRoomsD = Double.parseDouble(noOfRooms);
        noOfDaysD = Double.parseDouble(noOfDays);

        if (typeOfRoom.equals("Two beds Ac")){

            amount = 1299 * noOfDaysD * noOfRoomsD ;
            amountTextView.setText("Amount of Rs. "+amount+" will be deducted from your account.");
        }else if (typeOfRoom.equals("Two beds Non Ac")){

            amount = 999 * noOfDaysD * noOfRoomsD ;
            amountTextView.setText("Amount of Rs. "+amount+" will be deducted from your account.");

        }else if (typeOfRoom.equals("Three beds Ac")){

            amount = 2399 * noOfDaysD * noOfRoomsD ;
            amountTextView.setText("Amount of Rs. "+amount+" will be deducted from your account.");

        }else if (typeOfRoom.equals("Three beds Non Ac")){

            amount = 1999 * noOfDaysD * noOfRoomsD ;
            amountTextView.setText("Amount of Rs. "+amount+" will be deducted from your account.");

        }else if (typeOfRoom.equals("One bed Ac")){

            amount = 999 * noOfDaysD * noOfRoomsD ;
            amountTextView.setText("Amount of Rs. "+amount+" will be deducted from your account.");

        }else{

            amount = 3999 * noOfDaysD * noOfRoomsD ;
            amountTextView.setText("Amount of Rs. "+amount+" will be deducted from your account.");

        }

        makePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardNumberS = cardNumber.getText().toString();
                validThruS = validThru.getText().toString();
                cvvS = cvv.getText().toString();
                cardHolderNameS = cardHolderName.getText().toString();

                if (TextUtils.isEmpty(cardNumberS)) {
                    Toast.makeText(PaymentActivity.this, "Please enter your card number", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(validThruS)) {
                    Toast.makeText(PaymentActivity.this, "Please valid thru year", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(cvvS)) {
                    Toast.makeText(PaymentActivity.this, "Please enter cvv number", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(cardHolderNameS)) {
                    Toast.makeText(PaymentActivity.this, "Please enter card holder name", Toast.LENGTH_SHORT).show();
                }
                else {
                    currentUser = firebaseAuth.getCurrentUser();
                    assert currentUser != null;
                    amountS = String.valueOf(amount);
                    String uId = currentUser.getUid();
                    Map<String , Object> rbData = new HashMap<>();
                    rbData.put("Check In Date",checkInDate);
                    rbData.put("Check Out Date",checkOutDate);
                    rbData.put("No of rooms",noOfRooms);
                    rbData.put("Type of room",typeOfRoom);
                    rbData.put("Billing amount",amountS);
                    rbData.put("User Id",uId);
                    collectionReference.add(rbData);

                    startActivity(new Intent(PaymentActivity.this, ThanksActivity.class));
                }
            }
        });
    }

}
