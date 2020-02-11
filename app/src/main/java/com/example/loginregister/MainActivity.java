package com.example.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
private ActionBarDrawerToggle nToggle;
private DrawerLayout mDrawerLayout;
//Database mydb;
 Button ButtonE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//Event= (CostomCalendarView) findViewById(R.id.Event);
      mDrawerLayout=(DrawerLayout) findViewById(R.id.menu_home);
        nToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.opean,R.string.close);
        //mydb= new Database(this);
        ButtonE= findViewById(R.id.ButtonEvent);

        ButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           startActivity(new Intent((MainActivity.this), MainCalActiv.class));

            }
        });


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
        if(nToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


}
