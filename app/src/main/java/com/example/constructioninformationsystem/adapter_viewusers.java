package com.example.constructioninformationsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class adapter_viewusers extends RecyclerView.Adapter<adapter_viewusers.Viewholder> {
    List<String> listname;
    List<String> listemail;
    List<String> listid;
    Context context;
    String id;
    admin_view_user objviewuser;

    public adapter_viewusers(Context cx, List<String> id, List<String> name, List<String> email) {
        this.listid = id;
        this.listname = name;
        this.listemail = email;
        this.context = cx;

    }

    @NonNull
    @NotNull
    @Override
    public adapter_viewusers.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_viewusers, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull adapter_viewusers.Viewholder holder, int position) {
        holder.tvname.setText(listname.get(position));
        holder.tvemail.setText(listemail.get(position));
        holder.layout.setOnClickListener(view -> {
            id = listid.get(position);
            AppCompatActivity activity = (AppCompatActivity) context;
            adminaction adminaction = new adminaction();
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            adminaction.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, adminaction).commit();

        });
    }

    @Override
    public int getItemCount() {
        return listname.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tvname, tvemail;
        ConstraintLayout layout;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tv_recycler_name);
            tvemail = itemView.findViewById(R.id.tv_recycler_email);
            layout = itemView.findViewById(R.id.single_viewalluser);
        }
    }
}
