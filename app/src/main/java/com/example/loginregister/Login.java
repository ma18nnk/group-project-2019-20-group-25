package com.example.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText emailtext, passwordtext;
    Button LoginButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    TextView Singup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        emailtext=findViewById(R.id.emailtext);
        passwordtext=findViewById(R.id.passtext);
        LoginButton=findViewById(R.id.SignupButton);
        progressBar=findViewById(R.id.progressBar);
        Singup=findViewById(R.id.Singup);
        fAuth= FirebaseAuth.getInstance();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= emailtext.getText().toString().trim();
                String password= passwordtext.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    emailtext.setError("Email required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    passwordtext.setError("password required");
                    return;
                }
                if (password.length()<6){
                    passwordtext.setError("password have to be 6 or more than 6 ");
                    return;
                }
                progressBar.setVisibility(view.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful()){
                            Toast.makeText(Login.this, "user Logged in", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(Login.this, "user error", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
        Singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
            }
        });
    }
}
