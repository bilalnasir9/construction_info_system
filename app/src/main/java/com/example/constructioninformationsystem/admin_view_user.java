package com.example.constructioninformationsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.widget.Toast.*;

public class admin_view_user extends Fragment {
    RecyclerView recycler_viewallusers;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    List<String> listid = new ArrayList<>();
    List<String> listname = new ArrayList<>();
    List<String> listemail = new ArrayList<>();
    adapter_viewusers objadapter;
    ProgressDialog pd;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_admin_view_user, container, false);
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait..");
        recycler_viewallusers = view.findViewById(R.id.recycler_view_all_user);
        loadactivity();
        return view;
    }


    public void loadactivity() {
        pd.show();
        reference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listid.clear();
                listname.clear();
                listemail.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String key = ds.getKey();
                    try {
                    String name = Objects.requireNonNull(ds.child("name").getValue()).toString();
                    String email = Objects.requireNonNull(ds.child("email").getValue()).toString();
                       listid.add(key);
                       listname.add(name);
                       listemail.add(email);
                   }catch (Exception e){
                       Toast.makeText(getActivity(), e.toString(), LENGTH_SHORT).show();
                   }

                }
                objadapter = new adapter_viewusers(getActivity(), listid, listname, listemail);
                recycler_viewallusers.setAdapter(objadapter);
                recycler_viewallusers.setLayoutManager(new LinearLayoutManager(getActivity()));
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.dismiss();
            }
        });
    }


}