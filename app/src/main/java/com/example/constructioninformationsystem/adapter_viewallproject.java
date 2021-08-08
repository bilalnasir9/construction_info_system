package com.example.constructioninformationsystem;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class adapter_viewallproject extends RecyclerView.Adapter<adapter_viewallproject.Viewholder> {
    List<String> listowner;
    List<String> listtype;
    List<String> listlocation;
    Context context;
    String id;

    public adapter_viewallproject(Context cx, List<String> owner, List<String> type, List<String> location) {
        this.listowner = owner;
        this.listtype = type;
        this.listlocation = location;
        this.context = cx;

    }

    @NonNull
    @NotNull
    @Override
    public adapter_viewallproject.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_viewprojects, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull adapter_viewallproject.Viewholder holder, int position) {
        holder.tvowner.setText(listowner.get(position));
        holder.tvtype.setText(listtype.get(position));
        holder.tvlocation.setText(listlocation.get(position));
    }

    @Override
    public int getItemCount() {
        return listowner.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tvowner, tvtype, tvlocation;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvowner = itemView.findViewById(R.id.tv_projects_owner);
            tvtype = itemView.findViewById(R.id.tv_projects_type);
            tvlocation = itemView.findViewById(R.id.tv_projects_locaton);
        }
    }
}
