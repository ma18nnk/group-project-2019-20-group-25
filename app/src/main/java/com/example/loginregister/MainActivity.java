package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      int id=item.getItemId();
      if (id==R.id.menu_home){
            Toast.makeText(this, "Home Clicked",Toast.LENGTH_SHORT).show();
        }
        if (id==R.id.Logout){
            Toast.makeText(this, "Log out Clicked",Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
        if (id==R.id.EditProfile){
            Toast.makeText(this, "Edit Profile Clicked",Toast.LENGTH_SHORT).show();
            startActivity(new Intent (this, EditProfile.class));
        }
        if (id==R.id.help){
            Toast.makeText(this, "Help Clicked",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


}
