package com.proyectoa_pmdm_t2_pedrojimenez.fragments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectoa_pmdm_t2_pedrojimenez.DetalleActivity;
import com.proyectoa_pmdm_t2_pedrojimenez.R;
import com.proyectoa_pmdm_t2_pedrojimenez.retrofitData.Graph;

import java.util.ArrayList;

public class CentroAdapter extends RecyclerView.Adapter<CentroAdapter.CentroViewHolder> {
    private ArrayList<Graph> listaCentros;

    public CentroAdapter(ArrayList<Graph> listaCentros) {
        this.listaCentros = listaCentros;
    }

    @NonNull
    @Override
    public CentroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_item, parent, false);
        return new CentroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CentroViewHolder holder, int position) {
        Graph graph = listaCentros.get(position);
        holder.getTxtNom().setText(graph.getTitle());

        holder.getTxtNom().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetalleActivity.class);

                intent.putExtra("id", graph.getId());

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCentros.size();
    }

    public class CentroViewHolder extends RecyclerView.ViewHolder {
        TextView txtNom;
        public CentroViewHolder(View itemView) {
            super(itemView);
            txtNom = itemView.findViewById(R.id.txtNom);
        }

        public TextView getTxtNom() {
            return txtNom;
        }
    }

}
