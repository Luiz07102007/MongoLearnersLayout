package com.example.mongolearners;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroTela extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    EditText edtEmail, edtSenha, edtNome;
    Button cadastro;
    TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_tela);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


            // Inflate the layout for this fragment
            auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            edtEmail = findViewById(R.id.edt_emailCadastro);
            edtSenha = findViewById(R.id.edt_senhaCadastro);
            edtNome = findViewById(R.id.edt_nome);
            cadastro = findViewById(R.id.btn_cadastro);
            tv_login = findViewById(R.id.tv_login);

            cadastro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createUser();
                }
            });

            tv_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CadastroTela.this, LoginTela.class));
                }
            });


    }

    private void createUser() {
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();
        String nome  = edtNome.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(CadastroTela.this, "Email está vazio", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(senha)){
            Toast.makeText(CadastroTela.this, "Senha está vazia", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            UserModel userModel = new UserModel(nome, email, senha);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);

                            Toast.makeText(CadastroTela.this, "Cadastro concluído", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CadastroTela.this, "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}