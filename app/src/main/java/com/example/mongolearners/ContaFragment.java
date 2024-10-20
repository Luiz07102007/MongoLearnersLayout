package com.example.mongolearners;

import static android.graphics.Color.rgb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ContaFragment extends Fragment {
    FirebaseAuth auth;
    DatabaseReference db;
    UserModel query;
    EditText nome, email, senha;
    String nomeTv;
    Button editar, salvar, sair;
    Boolean tavaIgual;
    String enviaToast;
    TextView textoFormatado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_conta, container, false);




        nome = v.findViewById(R.id.edt_nomeComp);
        email = v.findViewById(R.id.edt_email);
        senha = v.findViewById(R.id.edt_senha);
        db = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        editar = v.findViewById(R.id.btn_editar);
        salvar = v.findViewById(R.id.btn_salvar);
        sair = v.findViewById(R.id.sair);
        textoFormatado = v.findViewById(R.id.textView8);

sair.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        auth.signOut();
        startActivity(new Intent(getContext(), LoginTela.class));
    }
});


        db.child("Users").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {

                    query = task.getResult().getValue(UserModel.class);



                    nome.setText(""+ query.getName());
                    textoFormatado.setText("Hello, "+ query.getName() + "!");
                    String texto = textoFormatado.getText().toString();

                    SpannableString spannableString = new SpannableString(texto);
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.rgb(0, 200, 90));
                    spannableString.setSpan(colorSpan, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textoFormatado.setText(spannableString);

                    email.setText(""+ query.getEmail());
                    senha.setText(""+ query.getSenha());
                }
            }
        });



        salvar.setBackgroundColor(rgb(0, 83, 37));

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nome.isEnabled()){
                    nome.setEnabled(true);
                    email.setEnabled(true);
                    senha.setEnabled(true);
                    salvar.setEnabled(true);
                    salvar.setBackgroundColor(rgb(1, 154, 70));

                }else {
                    nome.setEnabled(false);
                    email.setEnabled(false);
                    senha.setEnabled(false);
                    salvar.setEnabled(false);
                    salvar.setBackgroundColor(rgb(0, 83, 37));
                }
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enviaToast = "igual";
                db.child("Users").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            query = task.getResult().getValue(UserModel.class);
                        }
                    }
                });
                if (enviaToast == "igual"){ enviaToast = confereAlterado(nome, "name");}else{ confereAlterado(nome, "name");}
                if (enviaToast == "igual"){ enviaToast = confereAlterado(email, "email");}else{ confereAlterado(email, "email");}
                if (enviaToast == "igual"){ enviaToast = confereAlterado(senha, "senha");}else{ confereAlterado(senha, "senha");}


                if (enviaToast != "igual"){
                    Toast.makeText(getContext(), "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show();
                }
                nome.setEnabled(false);
                email.setEnabled(false);
                senha.setEnabled(false);
                salvar.setEnabled(false);
                salvar.setBackgroundColor(rgb(0, 83, 37));
            }
        });


        return v;
    }

    public String confereAlterado(EditText editText, String campo){
        switch (campo){
            case "name":
                if(editText.getText().toString() != query.getName()){
                    final HashMap<String, Object> salvarMap = new HashMap<>();
                    salvarMap.put(campo, editText.getText().toString());
                    db.child("Users").child(auth.getUid()).updateChildren(salvarMap);
                    return campo;

                }else {
                    return "igual";

                }

            case "email": if(editText.getText().toString() != query.getEmail().toString()){
                final HashMap<String, Object> salvarMap = new HashMap<>();
                salvarMap.put(campo, editText.getText().toString());
                auth.getCurrentUser().updateEmail(editText.getText().toString());
                db.child("Users").child(auth.getUid()).updateChildren(salvarMap);
                return campo;
            }else {
                return "igual";
            }

            case "senha": if(editText.getText().toString() != query.getSenha().toString()){
                final HashMap<String, Object> salvarMap = new HashMap<>();
                salvarMap.put(campo, editText.getText().toString());
                auth.getCurrentUser().updatePassword(editText.getText().toString());
                db.child("Users").child(auth.getUid()).updateChildren(salvarMap);
                return campo;
            }else {
                return "igual";
            }
            default:
                return "igual";
        }


    }

}