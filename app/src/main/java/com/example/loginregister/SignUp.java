package com.example.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
EditText emailtext, passwordtext;
Button SingupButton;
TextView loginView;
FirebaseAuth fAuth;
ProgressBar progressBar;
ImageView imageView;
  //public static   final Pattern EMAIL_ADDRESS = Pattern.compile(
         //   "[a-zA-Z0-9\\+\\.\\_\\%\\+]{1,256}"+"\\@"+
              //      "[a-zA-Z0-9][a-zA-Z0-9]{0,64}"+"("+"\\."+"" +
               //     "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}"+ ")+");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailtext= findViewById(R.id.emailtext);
        passwordtext= findViewById(R.id.passtext);
        SingupButton = findViewById(R.id.SignupButton);
        loginView = findViewById(R.id.loginView);
        imageView=findViewById(R.id.imageView);

        fAuth=FirebaseAuth.getInstance();
        progressBar= findViewById(R.id.progressBar3);

       if (fAuth.getCurrentUser()!= null){
          startActivity(new Intent(getApplicationContext(), MainActivity.class));
               finish();
        }

        SingupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email= emailtext.getText().toString().trim();
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
              //  if(!EMAIL_ADDRESS.matcher(email).matches()){
                //    emailtext.setError("Please enter valid email");
              //  }



                progressBar.setVisibility(view.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(SignUp.this, "user created check email", Toast.LENGTH_LONG).show();

                                       startActivity(new Intent(getApplicationContext(), Login.class));
                                    }else{
                                        Toast.makeText(SignUp.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }

                            });
                        } else{
                            Toast.makeText(SignUp.this,"error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

             loginView.setOnClickListener(new View.OnClickListener() {
                @Override
                  public void onClick(View view) {
                     startActivity(new Intent(getApplicationContext(),Login.class));
                }
           });
      }
}
