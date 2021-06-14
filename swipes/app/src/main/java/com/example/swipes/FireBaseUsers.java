package com.example.swipes;



import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBaseUsers extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView tv1;
    DatabaseReference ref;
    ArrayList<String> items=new ArrayList<String>();
    String result = "Name"+"       "+"Email"+"       "+"Swipes Giving"+"\n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebaseusers);
        firebaseAuth = FirebaseAuth.getInstance();
        tv1=(TextView)findViewById(R.id.userlist);

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        ref= FirebaseDatabase.getInstance().getReference().child("Users");
        ref.orderByChild("swipes_giving").startAt(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                items.add(result);
                for (DataSnapshot data:dataSnapshot.getChildren()){

                    result=result+data.child("name").getValue(String.class)+"       "+data.child("email").getValue(String.class)+"       "+data.child("swipes_giving").getValue(String.class)+"\n";
                }
                tv1.setText(result);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void onChat(View v){
        startActivity(new Intent(this,FriendlyMessage.class));
    }
    public void onBack(View view)
    {
        startActivity(new Intent(this, ProfileActivity.class));

    }
}