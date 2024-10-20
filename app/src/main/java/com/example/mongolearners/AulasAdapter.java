package com.example.mongolearners;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class AulasAdapter  extends RecyclerView.Adapter<AulasAdapter.ViewHolder>{

    private final AulasInterface aulasInterface;
    private Context context;
    private List<AulasModel> aulasModelList;

    public AulasAdapter(Context context, List<AulasModel> aulasModelList, AulasInterface aulasInterface) {
        this.context = context;
        this.aulasModelList = aulasModelList;
        this.aulasInterface = aulasInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.aulas_card, parent, false), aulasInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nome.setText(aulasModelList.get(position).getNome());

    }

    @Override
    public int getItemCount() {
        return aulasModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView depImg;
        TextView nome;

        public ViewHolder(@NonNull View itemView, AulasInterface aulasInterface) {
            super(itemView);

            nome = itemView.findViewById(R.id.nome);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(aulasInterface != null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            aulasInterface.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
