package com.example.app_authe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.sql.DriverManager.println;
import com.example.app_authe.User;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

     Button b1;
     EditText editTextEmail;
     EditText editTextPassword;
     TextView textview;

    private ProgressDialog progressDialog;

    //for firebase auth
    private FirebaseAuth firebaseAuth;

    // database reference object
    DatabaseReference databaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");


        progressDialog = new ProgressDialog(this);

        b1=(Button)findViewById(R.id.button);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        textview=(TextView)findViewById(R.id.textview);

        b1.setOnClickListener(this);
        textview.setOnClickListener(this);

    }

    private void registerUser()
    {
        final String email=editTextEmail.getText().toString().trim();
       final  String password=editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(this,"Enter your Email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            //password is empty;
            Toast.makeText(this,"Enter your pasword",Toast.LENGTH_SHORT).show();
            return ;
        }
        //If validation is ok
        ///first we will show a progress bar

        progressDialog.setMessage("Registering User...");
        progressDialog.show();
             ///adding listener to check whether user has been registerd or not
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful())
                     {
                         progressDialog.dismiss();
                         //finish();

                         // kisi naye user ko push krne se phle ek id genetate krega
                         String id = databaseUsers.push().getKey();
                         User user= new User(id,email,password);
                         databaseUsers.child(id).setValue(user);
                         Toast.makeText(MainActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                     }
                     else
                     {
                         progressDialog.dismiss();

                         Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                         int q=1;
                     }
                    }
                });




    }

    @Override
    public void onClick(View view)
    {
        int k=7;
        if(view==b1)
        {
            println("Hello");
            registerUser();
        }
        if(view==textview)
        {
            //will open signin activity;
           // finish();
            println("bolo");
            k=1;
            Intent intent=new Intent(this,loginActivity.class);
            startActivity(intent);
        }
    }
}
