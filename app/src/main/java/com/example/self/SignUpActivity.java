package com.example.self;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    RadioButton maleRB,femaleRB;
    String email,password,username,mobile,address1,address2,gender;
    Button signUpButton;
    EditText username_account,email_account,password_account,phone_account,add1_account,add2_account;
    ProgressBar create_account_progress;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        maleRB = findViewById(R.id.maleRB);
        signUpButton = findViewById(R.id.email_sign_up_button_account);
        username_account = findViewById(R.id.username_account);
        email_account = findViewById(R.id.email_account);
        password_account = findViewById(R.id.password_account);
        phone_account = findViewById(R.id.phone_account);
        add1_account = findViewById(R.id.add1_account);
        add2_account = findViewById(R.id.add2_account);
        create_account_progress = findViewById(R.id.create_account_progress);
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser=firebaseAuth.getCurrentUser();

                if(currentUser != null){
                    //user is already logged in
                }else{
                    //no user yet...

                }
            }
        };
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(email_account.getText().toString())&&!TextUtils.isEmpty(password_account.getText().toString())&&
                        !TextUtils.isEmpty(username_account.getText().toString())&&!TextUtils.isEmpty(phone_account.getText().toString())&&
                        !TextUtils.isEmpty(add1_account.getText().toString())&&!TextUtils.isEmpty(add2_account.getText().toString())){

                     email = email_account.getText().toString();
                     password = password_account.getText().toString();
                     username = username_account.getText().toString();
                     mobile = phone_account.getText().toString();
                     address1 = add1_account.getText().toString();
                     address2 = add2_account.getText().toString();
                     if(maleRB.isChecked())
                         gender = "Male";
                     else
                         gender = "Female";

                    createUserEmailAccount(email,password,username,mobile,address1,address2,gender);
                }else{
                    Toast.makeText(SignUpActivity.this,"Empty fields not allowed!",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public void createUserEmailAccount(final String email, String password, final String username, final String mobile, final String address1, final String address2, final String gender){

        if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(mobile)&&
                !TextUtils.isEmpty(address1)&&!TextUtils.isEmpty(address2)){

            create_account_progress.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                currentUser = firebaseAuth.getCurrentUser();
                                assert currentUser != null;
                                String currentUserId = currentUser.getUid();

                                //Create a user map so that we can create a user in the User collection
                                Map<String,String>userObj = new HashMap<>();
                                userObj.put("UserId",currentUserId);
                                userObj.put("Username",username);
                                userObj.put("Email",email);
                                userObj.put("Mobile",mobile);
                                userObj.put("Address Line 1",address1);
                                userObj.put("Address Line 2",address2);
                                userObj.put("Gender",gender);

                                //Save into firestore
                                collectionReference.add(userObj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {

                                                documentReference.get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if(Objects.requireNonNull(task.getResult()).exists()){
                                                                    create_account_progress.setVisibility(View.INVISIBLE);
                                                                    startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                                                }else{
                                                                    create_account_progress.setVisibility(View.INVISIBLE);
                                                                }
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });



                            }else{
                             //SOmething went wrong
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

}
