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

public class adapter_user_material extends RecyclerView.Adapter<adapter_user_material.Viewholder> {
    List<String> listdate;
    List<String> listtype;
    List<String> listbrand;
    List<String> listprice;
    List<String> listquantity;
    Context context;
    String getdate,getuid,gettype;
DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
FirebaseAuth auth=FirebaseAuth.getInstance();
    public adapter_user_material(Context cx, List<String> listdate, List<String> list_materialtype, List<String> list_brand, List<String> list_price, List<String> list_quantity) {
        this.context = cx;
        this.listdate = listdate;
        this.listtype = list_materialtype;
        this.listbrand = list_brand;
        this.listprice = list_price;
        this.listquantity = list_quantity;
    }

    @NonNull
    @NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_user_viewmaterial, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull adapter_user_material.Viewholder holder, int position) {
        holder.tvdate.setText(listdate.get(position));
        holder.tvtype.setText(listtype.get(position));
        holder.tvbrand.setText(listbrand.get(position));
        holder.tvprice.setText(listprice.get(position));
        holder.tvquantity.setText(listquantity.get(position));

        holder.layout.setOnClickListener(view -> {
            getdate=listdate.get(position);
            gettype=listtype.get(position);
            Bundle bundle=new Bundle();
            bundle.putString("date",getdate);
            bundle.putString("type",gettype);
            User_editmaterial fragment=new User_editmaterial();
            fragment.setArguments(bundle);
            AppCompatActivity activity=(AppCompatActivity) context;
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main,fragment).commit();
        });
        holder.layout.setOnLongClickListener(view -> {
            getdate=listdate.get(position);
            gettype=listtype.get(position);
            getuid= Objects.requireNonNull(auth.getCurrentUser()).getUid();
            AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Delete Data");
            builder.setPositiveButton("Delete", (dialogInterface, i) -> {
                reference.child("users").child(getuid).child("project").child("material usage").child(getdate).child(gettype).removeValue().addOnSuccessListener(runnable -> {
                    Toast.makeText(context,"Successfully deleted", Toast.LENGTH_SHORT).show();
                });
            }).setNegativeButton("Cancel", (dialogInterface, i) -> {
            });
            builder.show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return listtype.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tvdate, tvtype, tvbrand, tvprice, tvquantity;
        ConstraintLayout layout;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvdate = itemView.findViewById(R.id.tv_material_date);
            tvtype = itemView.findViewById(R.id.tv_material_type);
            tvbrand = itemView.findViewById(R.id.tv_material_brand);
            tvprice = itemView.findViewById(R.id.tv_material_price);
            tvquantity = itemView.findViewById(R.id.tv_material_quantity);
            layout = itemView.findViewById(R.id.single_viewallMaterials);
        }
    }
}
