package com.example.constructioninformationsystem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class admin_project_fragment extends Fragment {
    RecyclerView recyler;
    List<String> listowner = new ArrayList<>();
    List<String> listtype = new ArrayList<>();
    List<String> listlocation = new ArrayList<>();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_projects_fragment, container, false);
        recyler=view.findViewById(R.id.recycler_view_all_projects);
        TextView tvcheckproject=view.findViewById(R.id.tvprojectcheck);
        reference.child("contractor").child("projects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listowner.clear();
                listlocation.clear();
                listtype.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        String owner = Objects.requireNonNull(ds.child("owner").getValue()).toString();
                        String type = Objects.requireNonNull(ds.child("projecttype").getValue()).toString();
                        String location = Objects.requireNonNull(ds.child("location").getValue()).toString();
                        tvcheckproject.setVisibility(View.INVISIBLE);
                        recyler.setVisibility(View.VISIBLE);
                        listowner.add(owner);
                        listtype.add(type);
                        listlocation.add(location);
                    }catch (Exception exception){
                        Toast.makeText(getActivity(), exception.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
                adapter_viewallproject adapter=new adapter_viewallproject(getActivity(),listowner,listtype,listlocation);
                recyler.setAdapter(adapter);
                recyler.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return view;
    }
}