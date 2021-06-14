package com.example.swipes;


import android.content.Intent;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    Button buttonLogout;
    Button findUsers;
    TextView tv1,tv2,tv3,tv4;
    ImageView iv;
    DatabaseReference profileUser;
    String swipes_have,swipes_giving,name,email;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        currentUserId=firebaseAuth.getCurrentUser().getUid();
        profileUser= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);



        FirebaseUser user = firebaseAuth.getCurrentUser();

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        findUsers = (Button) findViewById(R.id.findUsers);
        tv1=(TextView)findViewById(R.id.profileName);
        tv2= (TextView)findViewById(R.id.profileEmail);
        tv3= (TextView) findViewById(R.id.profileSwipesHave);
        tv4= (TextView) findViewById(R.id.profileSwipesGiving);

        profileUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    swipes_have=dataSnapshot.child("swipes_have").getValue().toString();
                    swipes_giving=dataSnapshot.child("swipes_giving").getValue().toString();
                    name=dataSnapshot.child("name").getValue().toString();
                    email=dataSnapshot.child("email").getValue().toString();
                    tv1.setText(": "+name);
                    tv2.setText(": "+email);
                    tv3.setText(": "+swipes_have);
                    tv4.setText(": "+swipes_giving);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        buttonLogout.setOnClickListener(this);
    }

    public void onSearch (View v)
    {
        startActivity(new Intent(this, FireBaseUsers.class));

    }
    @Override
    public void onClick(View view) {
        if(view == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}