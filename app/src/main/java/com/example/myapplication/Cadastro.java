package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastro extends AppCompatActivity {
    private static final String TAG = "Cadastro";

    private EditText emailEditText;
    private EditText nomeEditText;
    private EditText passwordEditText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nomeEditText = findViewById(R.id.nome);
        emailEditText = findViewById(R.id.editTextTextEmailAddress2);
        passwordEditText = findViewById(R.id.editTextTextPassword);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void cadastrar(View view) {
        final String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        final String nome = nomeEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(nome)) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            user.updateProfile(new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nome)
                                            .build())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Nome do usuário adicionado ao perfil.");

                                                // Salve os dados do usuário no Firebase Realtime Database
                                                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
                                                String userId = user.getUid();

                                                usersRef.child(userId).child("userName").setValue(nome);
                                                usersRef.child(userId).child("userEmail").setValue(email);
                                            }
                                        }
                                    });

                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(Cadastro.this, "Cadastro bem-sucedido", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Cadastro.this, Login.class));
                            finish();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Cadastro.this, "Falha no cadastro. Verifique os dados e tente novamente.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void changePageLogin(View view) {
        finish();
    }
}
