package com.example.self;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    Button signInButton, signUpButton;
    AutoCompleteTextView email;
    EditText password;
    ProgressBar loginProgressBar;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginProgressBar = findViewById(R.id.login_progress);
        signInButton = findViewById(R.id.email_sign_in_button);
        signUpButton = findViewById(R.id.email_sign_up_button);
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginEmailPasswordUser(email.getText().toString(), password.getText().toString());

            }
        });

    }

    private void loginEmailPasswordUser(String email, String password) {

        loginProgressBar.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                loginProgressBar.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(LoginActivity.this, UserMenuActivity.class));
                            } else {
                                loginProgressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "Email and Password does not match", Toast.LENGTH_LONG).show();
                            }
                        }

                    });
        } else {
            if (TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                loginProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(LoginActivity.this, "Please enter the Email", Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)) {
                loginProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(LoginActivity.this, "Please enter the password", Toast.LENGTH_SHORT).show();
            } else {
                loginProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}