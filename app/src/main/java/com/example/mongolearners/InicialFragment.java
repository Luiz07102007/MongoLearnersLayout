package com.example.mongolearners;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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

public class InicialFragment extends Fragment {

    FirebaseAuth auth;
    DatabaseReference db;
    UserModel query;
    TextView textoFormatado;
    FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_inicial, container, false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        textoFormatado = v.findViewById(R.id.textView10);
        frameLayout = v.findViewById(R.id.frameLayoutAulas);


        db.child("Users").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {

                    query = task.getResult().getValue(UserModel.class);

                    textoFormatado.setText("Hello, "+ query.getName() + "!");
                    String texto2 = "\n\nIf you keep this effort you'll end the classes in 10 days";
                    String texto = textoFormatado.getText().toString();

                    SpannableString spannableString = new SpannableString(texto);
                    SpannableString spannableString2 = new SpannableString(texto2);
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.rgb(0, 200, 90));
                    ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.rgb(0, 200, 90));
                    spannableString.setSpan(colorSpan, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString2.setSpan(colorSpan2, 52, 59, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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

            }
        });

        return v;
    }
}