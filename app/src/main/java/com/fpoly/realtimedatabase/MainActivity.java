package com.fpoly.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button btnPushData, btnGetData;
    private EditText edContent;
    private TextView tvResult;
    public static final String LOG = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPushData = findViewById(R.id.btnPushData);
        btnGetData = findViewById(R.id.btnGetData);
        tvResult = findViewById(R.id.tvResult);
        edContent = findViewById(R.id.edContent);
        btnPushData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("db/user/1/id");

                myRef.setValue(Integer.parseInt(edContent.getText().toString().trim()), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(MainActivity.this, "Push success", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readDataBase();
            }
        });


    }
    private void readDataBase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("content");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                tvResult.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, "False", Toast.LENGTH_SHORT).show();
            }
        });
    }
}