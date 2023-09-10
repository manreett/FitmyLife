package com.example.fml_app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{

    private TextView register;
    private EditText email_log, password_log;
    private Button Login;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize views
        register = findViewById(R.id.registerLink);
        email_log = findViewById(R.id.email);
        password_log = findViewById(R.id.password);
        Login = findViewById(R.id.signIn);

        mAuth = FirebaseAuth.getInstance();

        //Send user to register page
        register.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, RegisterUser.class));
        });

        //Log user in
        Login.setOnClickListener(view1 -> {
            userLogin();
        });

    }

    private void userLogin() {
        String email = email_log.getText().toString().trim();
        String password = password_log.getText().toString().trim();

        //Input Validation
        if (email.isEmpty()){
            email_log.setError("Email is required!");
            email_log.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_log.setError("Enter a valid Email!");
            email_log.requestFocus();
            return;
        }
        if (password.isEmpty()){
            password_log.setError("Password is required!");
            password_log.requestFocus();
            return;
        }
        if (password.length() < 6){
            password_log.setError("Password must be at least 6 characters!");
            password_log.requestFocus();
            return;
        }

        //Retrieve user from FireBase
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                startActivity(new Intent(MainActivity.this,HomePage.class));
            }else{
                Toast.makeText(MainActivity.this, "Login Failed! Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}