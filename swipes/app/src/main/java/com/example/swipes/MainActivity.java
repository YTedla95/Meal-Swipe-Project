package com.example.swipes;


import android.content.Intent;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName, editTextEmail, editTextPassword, editSwipesHave,editSwipesGiving;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.edit_text_name);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        editSwipesHave = findViewById(R.id.edit_number_swipes);
        editSwipesGiving=findViewById(R.id.edit_number_swipes_giving);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.button_register).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }

    private void registerUser() {
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String swipes_have = editSwipesHave.getText().toString().trim();
        final String swipes_giving=editSwipesGiving.getText().toString().trim();
        if (name.isEmpty()) {
            editTextName.setError("name is empty");
            editTextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("email is empty");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Not a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is empty");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password is too short");
            editTextPassword.requestFocus();
            return;
        }

        if (swipes_have.isEmpty()) {
            editSwipesHave.setError("please input a number");
            editSwipesHave.requestFocus();
            return;
        }
        if (swipes_giving.isEmpty()) {
            editSwipesHave.setError("please input a number");
            editSwipesGiving.requestFocus();
            return;
        }



        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            user user = new user(
                                    name,
                                    email,
                                    swipes_have,swipes_giving
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this,"Registered Successfully!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                    } else {
                                        //display a failure message
                                        Toast.makeText(MainActivity.this,"Unsuccessful",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_register)
        {
            registerUser();
        }
        if (id == R.id.loginlink)
        {
            startActivity(new Intent(this, LoginActivity.class));
        }

        /*switch (v.getId()) {
            case R.id.button_register:
                registerUser();
                break;
            case R.id.loginlink:
                startActivity(new Intent(this, LoginActivity.class));

        }*/
    }
}