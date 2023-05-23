package com.jayrishi.firebase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    ArrayList<String> notes;
    ListView listView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btnDb);
        editText = findViewById(R.id.etNote);
        listView = findViewById(R.id.lv);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.item_row,R.id.lv,notes);
        listView.setAdapter(arrayAdapter);




        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        button.setOnClickListener(v -> {
            String note = editText.getText().toString();
            //upload the note to firebase
            dbRef.child("note").push().setValue(note);
           // dbRef.child("todo").push().setValue(note);
        });

        dbRef.child("note").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //called when new data is added to the "note" node
                String data = snapshot.getValue(String.class);
                notes.add(data);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //when existing data node is updated

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //when data of a sub node is removed

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //when position of a sub node changes

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //when the read operation failed

            }
        });
    }
}