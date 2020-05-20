package com.example.self;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoomBookingActivity extends AppCompatActivity {
    Button twoBedAcB,twoBednAcB,threeBednAcB,threeBedAcB,oneBedAcB,deluxeRoomB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_booking);
        twoBedAcB = findViewById(R.id.twoBedAcB);
        twoBednAcB = findViewById(R.id.twoBednAcB);
        threeBedAcB = findViewById(R.id.threeBedAcB);
        threeBednAcB = findViewById(R.id.threeBednAcB);
        oneBedAcB = findViewById(R.id.oneBedAcB);
        deluxeRoomB = findViewById(R.id.deluxeRoomB);

        twoBedAcB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomBookingActivity.this,CheckInForm.class);
                intent.putExtra("Type of room","Two beds Ac");
                startActivity(intent);
            }
        });
        twoBednAcB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomBookingActivity.this,CheckInForm.class);
                intent.putExtra("Type of room","Two beds Non Ac");
                startActivity(intent);
            }
        });
        threeBedAcB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomBookingActivity.this,CheckInForm.class);
                intent.putExtra("Type of room","Three beds Ac");
                startActivity(intent);
            }
        });
        threeBednAcB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomBookingActivity.this,CheckInForm.class);
                intent.putExtra("Type of room","Three beds Non Ac");
                startActivity(intent);
            }
        });
        oneBedAcB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomBookingActivity.this,CheckInForm.class);
                intent.putExtra("Type of room","One bed Ac");
                startActivity(intent);
            }
        });
        deluxeRoomB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomBookingActivity.this,CheckInForm.class);
                intent.putExtra("Type of room","Deluxe Room");
                startActivity(intent);
            }
        });
    }
}
