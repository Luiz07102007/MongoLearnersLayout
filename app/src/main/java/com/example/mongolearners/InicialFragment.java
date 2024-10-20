package com.example.mongolearners;

import static android.app.PendingIntent.getActivity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;

public class InicialFragment extends Fragment {

    FirebaseAuth auth;
    DatabaseReference db;
    UserModel query;
    TextView textoFormatado, progresso;
    FrameLayout frameLayout;
    Float dias, quantidadeAulas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_inicial, container, false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        textoFormatado = v.findViewById(R.id.textView10);
        progresso = v.findViewById(R.id.textView11);
        frameLayout = v.findViewById(R.id.frameLayoutAulas);
        quantidadeAulas = 10F;


        db.child("Users").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {

                    query = task.getResult().getValue(UserModel.class);

                    Integer aulasMongo = query.getAulasMongo() - 1;

                    dias = quantidadeAulas - aulasMongo;
                    Float porcentagem  = (100 * aulasMongo)/quantidadeAulas;
                    progresso.setText("Track your Progress:\n"+porcentagem.intValue()+"% complete");


                    textoFormatado.setText("Hello, " + query.getName() + "!");
                    String texto2 = "\n\nIf you keep this effort you'll end the classes in " + dias.intValue() + " days";
                    String texto = textoFormatado.getText().toString();
                    Integer oi = dias.intValue();

                    SpannableString spannableString = new SpannableString(texto);
                    SpannableString spannableString2 = new SpannableString(texto2);
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.rgb(0, 200, 90));
                    ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.rgb(0, 200, 90));
                    spannableString.setSpan(colorSpan, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString2.setSpan(colorSpan2, 52, 57+ oi.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    SpannableStringBuilder combined = new SpannableStringBuilder();
                    combined.append(spannableString);
                    combined.append(spannableString2);

                    textoFormatado.setText(combined);


                }
            }
        });

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new AulasFragment());
            }
        });





        return v;
    }
    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}


