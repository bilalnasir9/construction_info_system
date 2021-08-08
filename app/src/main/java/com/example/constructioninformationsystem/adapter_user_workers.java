package com.example.constructioninformationsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class adapter_user_workers extends RecyclerView.Adapter<adapter_user_workers.viewholder> {

    List<String> listexpertise, listname, listcontact, listaddress, listdatetime;
    Context context;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    public adapter_user_workers(FragmentActivity activity, List<String> list_worker_datetime, List<String> list_worker_expertise, List<String> list_worker_name, List<String> list_worker_contact, List<String> list_worker_adress) {
        this.context = activity;
        this.listexpertise = list_worker_expertise;
        this.listname = list_worker_name;
        this.listcontact = list_worker_contact;
        this.listaddress = list_worker_adress;
        this.listdatetime = list_worker_datetime;
    }

    @NonNull
    @NotNull
    @Override
    public adapter_user_workers.viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_user_viewworkers, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull adapter_user_workers.viewholder holder, int position) {
        holder.tvexpertise.setText(listexpertise.get(position));
        holder.tvname.setText(listname.get(position));
        holder.tvcontact.setText(listcontact.get(position));
        holder.tvaddress.setText(listaddress.get(position));
        holder.tvdatetime.setText(listdatetime.get(position));
        holder.layout.setOnClickListener(view -> {
            String exp = listexpertise.get(position);
            String date=listdatetime.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("expertise", exp);
            bundle.putString("datetime", date);
            User_editWorker fragment = new User_editWorker();
            fragment.setArguments(bundle);
            AppCompatActivity activity = (AppCompatActivity) context;
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment).commit();
        });
        holder.layout.setOnLongClickListener(view -> {
            String expertise = listexpertise.get(position);
            String datetime = listdatetime.get(position);
            String getuid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
            AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Delete Data");
            builder.setPositiveButton("Delete", (dialogInterface, i) -> {
                reference.child("users").child(getuid).child("project").child("workers").child(datetime).child(expertise).removeValue().addOnSuccessListener(runnable -> {
                    Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
                });
            }).setNegativeButton("Cancel", (dialogInterface, i) -> {
            });
            builder.show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return listexpertise.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;
        TextView tvexpertise, tvname, tvcontact, tvaddress, tvdatetime;

        public viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvexpertise = itemView.findViewById(R.id.tv_worker_expertise);
            tvname = itemView.findViewById(R.id.tv_worker_name);
            tvcontact = itemView.findViewById(R.id.tv_worker_contact);
            tvaddress = itemView.findViewById(R.id.tv_worker_address);
            tvdatetime = itemView.findViewById(R.id.tv_worker_date_time);
            layout = itemView.findViewById(R.id.single_viewallworkers);

        }
    }
}
