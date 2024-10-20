package com.example.mongolearners;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AulaExpandedFragment extends Fragment {
    String titulo, conteudo;
    TextView tv_titulo, tv_conteudo;
    Button concluir;
    DatabaseReference db;
    FirebaseAuth auth;
    UserModel query;
    Integer aulasMongo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_aula_expanded, container, false);
        tv_titulo = v.findViewById(R.id.titulo);
        tv_conteudo = v.findViewById(R.id.conteudo);
        concluir = v.findViewById(R.id.btn_concluir);
        db = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        Bundle b = this.getArguments();

        if (b.getString("aula")!= null && b.getString("conteudo") != null){
            titulo = b.getString("aula");
            conteudo = b.getString("conteudo");
        }

        tv_titulo.setText(titulo);
        tv_conteudo.setText(conteudo);

        concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.child("Users").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            query = task.getResult().getValue(UserModel.class);
                            aulasMongo = 0 + query.getAulasMongo();
                            aulasMongo += 1;
                            final HashMap<String, Object> salvarMap = new HashMap<>();
                            salvarMap.put("aulasMongo",aulasMongo);
                            db.child("Users").child(auth.getUid()).updateChildren(salvarMap);
                        }
                    }
                });



            }
        });

        return v;
    }
}