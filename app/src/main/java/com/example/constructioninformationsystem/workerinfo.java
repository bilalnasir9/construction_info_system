package com.example.constructioninformationsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class workerinfo extends Fragment {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ProgressDialog pd;
    String uid;
    List<String> list_worker_expertise = new ArrayList<>();
    List<String> list_worker_name = new ArrayList<>();
    List<String> list_worker_adress = new ArrayList<>();
    List<String> list_worker_contact = new ArrayList<>();
    List<String> list_worker_datetime = new ArrayList<>();
    adapter_user_workers objadapter;
    RecyclerView recyclerView;
    adapter_user_workers obj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workerinfo, container, false);
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait..");
        pd.show();
        uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        recyclerView = view.findViewById(R.id.recycler_worker_info);
        FloatingActionButton btn_add_worker = view.findViewById(R.id.float_add_worker);
        reference.child("users").child(uid).child("project").child("workers").get().addOnCompleteListener(task -> {
            try {
                String id = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getValue()).toString();
                getdata();
            } catch (Exception exception) {
                pd.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Record not found").setMessage("Your have not added any worker's data")
                        .setNegativeButton("OK", (dialogInterface, i) -> {
                        });
                builder.show();
            }
        });
        btn_add_worker.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new addworker()).commit();
        });
        return view;
    }

    private void getdata() {
        reference.child("users").child(uid).child("project").child("workers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list_worker_expertise.clear();
                list_worker_adress.clear();
                list_worker_contact.clear();
                list_worker_name.clear();
                list_worker_datetime.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
//                        String expertise = ds.getKey();
                        String datetime = ds.getKey();
                        assert datetime != null;
                        reference.child("users").child(uid).child("project").child("workers").child(datetime).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot ds2 : snapshot.getChildren()){
                        try {
                            String expertise = ds2.getKey();
                            String name = Objects.requireNonNull(ds2.child("name").getValue()).toString();
                            String adress = Objects.requireNonNull(ds2.child("address").getValue()).toString();
                            String contact = Objects.requireNonNull(ds2.child("contact").getValue()).toString();
                            list_worker_datetime.add(datetime);
                            list_worker_expertise.add(expertise);
                            list_worker_name.add(name);
                            list_worker_adress.add(adress);
                            list_worker_contact.add(contact);
                        }catch (Exception exception){
                            exception.printStackTrace();
                        }
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    obj = new adapter_user_workers(getActivity(),list_worker_datetime, list_worker_expertise, list_worker_name, list_worker_contact, list_worker_adress);
                    recyclerView.setAdapter(obj);
                    pd.dismiss();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

                    }
                    catch (Exception exception){
                        pd.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Record not found").setMessage("Your have not added any worker's data")
                                .setNegativeButton("OK", (dialogInterface, i) -> {
                                });
                        builder.show();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}