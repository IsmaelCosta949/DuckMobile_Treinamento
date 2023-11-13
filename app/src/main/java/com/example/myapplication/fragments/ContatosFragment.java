package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContatosFragment extends Fragment {

    private RecyclerView recyclerViewContatos;
    private ContatosAdapter contatosAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contatos_fragment, container, false);

        recyclerViewContatos = view.findViewById(R.id.recyclerViewContatos);
        recyclerViewContatos.setLayoutManager(new LinearLayoutManager(getActivity()));
        contatosAdapter = new ContatosAdapter();
        recyclerViewContatos.setAdapter(contatosAdapter);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<HashMap<String, String>> userList = new ArrayList<>();
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String currentUserId = currentUser != null ? currentUser.getUid() : "";

                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        if (!currentUserId.equals(userId)) {
                            HashMap<String, String> user = new HashMap<>();
                            user.put("userName", String.valueOf(userSnapshot.child("userName").getValue()));
                            user.put("userEmail", String.valueOf(userSnapshot.child("userEmail").getValue()));
                            userList.add(user);
                        }
                    }
                    contatosAdapter.setContatos(userList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Trate o erro, se necessário
            }
        });

        return view;
    }
}
