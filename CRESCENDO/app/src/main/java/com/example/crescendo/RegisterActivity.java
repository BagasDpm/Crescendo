package com.example.crescendo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    TextView username, password, confpass, LoginButton;
    AppCompatButton regisButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.etUsernameRegis);
        password=findViewById(R.id.etPasswordRegis);
        confpass=findViewById(R.id.etConfirmPassword);
        regisButton=findViewById(R.id.regisButton);
        LoginButton=findViewById(R.id.LoginText);
        regisButton.setOnClickListener(e->{
            if (username.getText().toString().isEmpty()){
                Toast.makeText(this,"Username must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.getText().toString().isEmpty()){
                Toast.makeText(this,"Password must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            if (confpass.getText().toString().isEmpty()){
                Toast.makeText(this,"Confirmed Password must be filled", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.getText().toString().equals(confpass.getText().toString())){
                Toast.makeText(this,"Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, String> user = new HashMap<>();
            user.put("Username",username.getText().toString());
            user.put("Password",password.getText().toString());

            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getBaseContext(),"Successfully registered",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getBaseContext(),"Something went wrong, "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoRegis=new Intent(getBaseContext(),LoginActivity.class);
                startActivity(backtoRegis);
                finish();
            }
        });


    }
}