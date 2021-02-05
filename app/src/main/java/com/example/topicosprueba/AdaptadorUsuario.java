package com.example.topicosprueba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorUsuario extends RecyclerView.Adapter<AdaptadorUsuario.UsuarioViewHolder> {

    Context context;
    List<Usuario> listaUsuarios;

    public AdaptadorUsuario(Context context, List<Usuario> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adap_usuario, null, false);
        return new UsuarioViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        holder.tvNota.setText(listaUsuarios.get(position).getNota());
        holder.tvNombre.setText(listaUsuarios.get(position).getNombre());
        holder.tvTelefono.setText(listaUsuarios.get(position).getTelefono());
        holder.tvEmail.setText(listaUsuarios.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView tvNota, tvNombre, tvTelefono, tvEmail;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNota = itemView.findViewById(R.id.tvNota);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}