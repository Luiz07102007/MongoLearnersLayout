package com.example.mongolearners;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AulasFragment extends Fragment implements AulasInterface {
    RecyclerView recAulas;
    FirebaseFirestore db;
    List<AulasModel> aulasModelList;
    AulasAdapter aulasAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aulas, container, false);

        db = FirebaseFirestore.getInstance();
        recAulas = view.findViewById(R.id.rec_aulas);

        recAulas.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        aulasModelList = new ArrayList<>();
        aulasAdapter = new AulasAdapter(getActivity(), aulasModelList, this);
        recAulas.setAdapter(aulasAdapter);

        db.collection("MongoCursos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AulasModel aulasModel = document.toObject(AulasModel.class);
                                aulasModelList.add(aulasModel);
                                aulasAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return view;
    }
    private void loadFragment(Fragment fragment, Bundle b){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setArguments(b);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onItemClick(int position) {
        Bundle b = new Bundle();
        b.putString("aula",aulasModelList.get(position).getNome().toString());
        b.putString("conteudo",aulasModelList.get(position).getConteudo().toString());
        loadFragment(new AulaExpandedFragment(), b);
    }
}