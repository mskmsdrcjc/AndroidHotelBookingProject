package com.example.self;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class CheckInForm extends AppCompatActivity {


    public static String dayCIn;
    public static String dayCOut;
    public static String monthCIn;
    public static String monthCOut;
    public static String yearCIn;
    public static String yearCOut;
    public String dayDifference;

    public static EditText checkInDate;
    public static EditText checkOutDate;
    EditText noOfRoomsE;
    String noOfRoomsS;
    Button confirmB;
    String checkIn, checkOut;
    String typeOfRoom, uId, noOfDaysS;
    ImageButton checkInDatePickerB, checkOutDatePickerB;
    Date date1, date2,date3, date4;
    Date date = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_form);


        checkInDate = findViewById(R.id.checkInDate);
        checkOutDate = findViewById(R.id.checkOutDate);
        noOfRoomsE = findViewById(R.id.noOfRooms);
        confirmB = findViewById(R.id.confirmB);
        checkInDatePickerB = findViewById(R.id.checkInDatePickerB);
        checkOutDatePickerB = findViewById(R.id.checkOutDatePickerB);
        Intent intent = getIntent();
        typeOfRoom = intent.getStringExtra("Type of room");


        checkInDatePickerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment fragment = new MyDateFragment();
                fragment.show(getSupportFragmentManager(), "date picker");
            }
        });

        checkOutDatePickerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment fragment = new MyDateFragmentOut();
                fragment.show(getSupportFragmentManager(), "date picker");
            }
        });


        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkIn = checkInDate.getText().toString();
                checkOut = checkOutDate.getText().toString();
                noOfRoomsS = noOfRoomsE.getText().toString();
                date3 = returnDateCheckIn();
                date4 = returnDateCheckOut();

                if (TextUtils.isEmpty(checkIn)) {
                    Toast.makeText(CheckInForm.this, "Please enter check in date", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(checkOut)) {
                    Toast.makeText(CheckInForm.this, "Please enter check out date", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(noOfRoomsS)) {
                    Toast.makeText(CheckInForm.this, "Please enter number of rooms", Toast.LENGTH_SHORT).show();
                } else if (checkIn.equals(checkOut)) {
                    Toast.makeText(CheckInForm.this, "Check in and Check out dates cannot be same", Toast.LENGTH_SHORT).show();
                }else if (date4.before(date3)) {
                    Toast.makeText(CheckInForm.this, "Check out date is before the check in date\nPlease select another date", Toast.LENGTH_SHORT).show();
                }else if (date3.before(date)) {
                    Toast.makeText(CheckInForm.this, "Please choose another check in date", Toast.LENGTH_SHORT).show();
                }else {

                    noOfDaysS = datesDifference();
                    Intent intent = new Intent(CheckInForm.this, PaymentActivity.class);
                    intent.putExtra("No of rooms", noOfRoomsS);
                    intent.putExtra("Type of room", typeOfRoom);
                    intent.putExtra("No of days", dayDifference);
                    intent.putExtra("Check in date", checkIn);
                    intent.putExtra("Check out date", checkOut);
                    startActivity(intent);

                }


            }
        });


    }

    public static void checkInDateFunction(int year, int month, int dayOfMonth) {

        checkInDate.setText(dayOfMonth + "/" + month + "/" + year);

        dayCIn = String.valueOf(dayOfMonth);
        monthCIn = String.valueOf(month);
        yearCIn = String.valueOf(year);
    }

    public static void checkOutDateFunction(int year, int month, int dayOfMonth) {

        checkOutDate.setText(dayOfMonth + "/" + month + "/" + year);

        dayCOut = String.valueOf(dayOfMonth);
        monthCOut = String.valueOf(month);
        yearCOut = String.valueOf(year);

    }

    public String datesDifference() {
        try {
            //Dates to compare
            String CurrentDate = checkIn;
            String FinalDate = checkOut;

            Date date1;
            Date date2;

            SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");

            //Setting dates
            date1 = dates.parse(CurrentDate);
            date2 = dates.parse(FinalDate);

            Date date = new Date();

            //Comparing dates
            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);


            //Convert long to String
            dayDifference = Long.toString(differenceDates);

        } catch (Exception exception) {
            Log.e("DIDN'T WORK", "exception " + exception);
        }

        return dayDifference;
    }

    public Date returnDateCheckIn() {

        try {
            //Dates to compare
            String CurrentDate = checkIn;
            SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
            //Setting dates
            date1 = dates.parse(CurrentDate);
        } catch (Exception exception) {
            Log.e("DIDN'T WORK", "exception " + exception);
        }
        return date1;
    }
    public Date returnDateCheckOut() {

        try {
            //Dates to compare
            String CurrentDate = checkOut;
            SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
            //Setting dates
            date1 = dates.parse(CurrentDate);
        } catch (Exception exception) {
            Log.e("DIDN'T WORK", "exception " + exception);
        }
        return date1;
    }

}
