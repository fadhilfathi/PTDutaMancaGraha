package com.example.marbeelz.ptdutamancagraha;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import maes.tech.intentanim.CustomIntent;

public class LoginActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Users;
    EditText username, password;
    Button button_login;
    ImageView imageView;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String currentlogin = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        Users = database.getReference("Users");

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        button_login = (Button) findViewById(R.id.button_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(username.getText().toString(),
                        password.getText().toString());
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("currentlogin",0);
        String x = sharedPreferences.getString("logincurrent","");
        if (x.equals("admin")){
            Intent y = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(y);
            finish();
        }
        if (!x.equals("") && !x.equals("admin")){
            Intent z = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(z);
            finish();
        }
    }
    private void signIn(final String username, final String password) {
        Users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()){
                    if (!username.isEmpty()){
                        if (username.equals("admin")){
                            User login = dataSnapshot.child(username).getValue(User.class);
                            if (login.getPassword().equals(password)) {
                                SharedPreferences sharedPreferences = getSharedPreferences("currentlogin",0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("logincurrent",username);
                                editor.apply();
                                editor.commit();
                                Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                                Intent x = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(x);
                                finish();
                                CustomIntent.customType(LoginActivity.this, "fadein-to-fadeout");
                            }else{
                                Toast.makeText(LoginActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if (login.getPassword().equals(password)){
                            SharedPreferences sharedPreferences = getSharedPreferences("currentlogin",0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("logincurrent",username);
                            editor.apply();
                            editor.commit();
                            Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                            Intent x = new Intent(getApplicationContext(), UserActivity.class);
                            startActivity(x);
                            finish();
                            CustomIntent.customType(LoginActivity.this,"fadein-to-fadeout");
                        }else {
                            Toast.makeText(LoginActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                        }}
                    }else {
                        Toast.makeText(LoginActivity.this, "Username Tidak Terdaftar", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
