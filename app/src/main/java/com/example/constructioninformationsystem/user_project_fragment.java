package com.example.constructioninformationsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class user_project_fragment extends Fragment {
    TextView tvowner, tvtype, tvlocation;
    String location, projecttype, owner;
    ProgressDialog dialog;
    CardView cardView;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_project_fragment, container, false);
        Bundle bundle = this.getArguments();
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait..");
        dialog.show();
        tvlocation = view.findViewById(R.id.user_projectlocation);
        tvowner = view.findViewById(R.id.user_projectowner);
        tvtype = view.findViewById(R.id.user_projecttype);
        cardView=view.findViewById(R.id._user_cardview);
        String uid = Objects.requireNonNull(bundle).getString("userid");
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("project");
        reference2.child("location").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                try {
                    location = Objects.requireNonNull(task.getResult()).getValue().toString();
                } catch (Exception exception) {
                    dialog.dismiss();
                    cardView.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), exception.toString(), Toast.LENGTH_SHORT).show();
                   // error();
                }
                tvlocation.setText(location);
            }
        });
        reference2.child("projecttype").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                try {
                    projecttype = Objects.requireNonNull(task.getResult()).getValue().toString();
                    tvtype.setText(projecttype);
                } catch (Exception exception) {
                    dialog.dismiss();
                    cardView.setVisibility(View.INVISIBLE);

                }

            }
        });
        reference2.child("owner").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                try {
                    location = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getValue()).toString();
                    tvowner.setText(location);
                    dialog.dismiss();
                } catch (Exception exception) {
                    dialog.dismiss();
                    cardView.setVisibility(View.INVISIBLE);

                }


            }
        });

        return view;
    }

    public void error() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Project not found").setMessage("Your contractor has not been created your project yet")
                .setNegativeButton("OK", (dialogInterface, i) -> {
                });
        builder.show();
    }
}
