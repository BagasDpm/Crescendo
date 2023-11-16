package com.example.crescendo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.crescendo.classmodel.users;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    TextView registerButton;
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.regissText);
        username = findViewById(R.id.etUsernameLogin);
        password = findViewById(R.id.etPasswordLogin);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(),"Username must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(),"Password must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Task<QuerySnapshot> query =db.collection("users")
                        .whereEqualTo("Username",username.getText().toString())
                        .whereEqualTo("Password",password.getText().toString())
                        .get();
                query.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        try {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            if(!documentSnapshot.getId().isEmpty()){
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }catch (Exception e){
                            Toast.makeText(getBaseContext(),"Data does not exist",Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(),"Error found: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}