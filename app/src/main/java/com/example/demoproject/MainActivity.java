package com.example.demoproject;

import android.os.Bundle;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";



    FloatingActionButton fab;

    EditText editTextName, editTextId, editTextDocumentName;

    TextView textViewId, textViewName;
    Button buttonRead;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    DocumentReference ref = db.collection("Student").document("Data");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance());


        fab = findViewById(R.id.fab);
        editTextName = findViewById(R.id.editTextName);
        editTextId = findViewById(R.id.editTextId);
        editTextDocumentName = findViewById(R.id.editTextDocumentName);
        buttonRead = findViewById(R.id.buttonRead);

        textViewId = findViewById(R.id.textViewId);
        textViewName = findViewById(R.id.textViewName);


        CollectionReference dbCourses = db.collection("Courses");


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextName.getText().toString();
                int id = Integer.parseInt(editTextId.getText().toString());
                String documentName = editTextDocumentName.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("ID", id);
                hashMap.put("Name", name);

                ref.set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Data saved", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Data not saved", Toast.LENGTH_SHORT).show();

                    }
                });

//                Courses courses = new Courses("English","English Course","12Hours");
//
//                dbCourses.add(courses).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(MainActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, "Data not saved", Toast.LENGTH_SHORT).show();
//
//                    }
//                });

            }
        });

        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Object id = documentSnapshot.get("ID");
                            String name = documentSnapshot.getString("Name");
                            textViewId.setText("" + id);
                            textViewName.setText(name);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                        Toast.makeText(MainActivity.this, "Failed to retrieve", Toast.LENGTH_SHORT).show();

                    }
                });
//                db.collection("Courses").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                        if (!queryDocumentSnapshots.isEmpty()) {
//                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                            for (DocumentSnapshot d : list) {
//
//                                Courses hashMap = d.toObject(Courses.class);
//                                Toast.makeText(MainActivity.this, hashMap.getCourseName(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    }
//                });
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        ref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value.exists()) {
                    Object id = value.get("ID");
                    String name = value.getString("Name");
                    textViewId.setText("" + id);
                    textViewName.setText(name);
                }
            }
        });
    }
}