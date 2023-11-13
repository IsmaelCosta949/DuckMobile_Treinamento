package com.example.myapplication.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.ContatosViewHolder> {

    private List<HashMap<String, String>> contatosList = new ArrayList<>();

    public void setContatos(List<HashMap<String, String>> contatos) {
        this.contatosList = contatos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContatosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_profile, parent, false);
        return new ContatosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContatosViewHolder holder, int position) {
        HashMap<String, String> contato = contatosList.get(position);
        holder.textViewUserName.setText("Name: " + contato.get("userName"));
        holder.textViewUserEmail.setText("Email: " + contato.get("userEmail"));
    }

    @Override
    public int getItemCount() {
        return contatosList.size();
    }

    static class ContatosViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUserName;
        TextView textViewUserEmail;

        ContatosViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewUserEmail = itemView.findViewById(R.id.textViewUserEmail);
        }
    }
}
