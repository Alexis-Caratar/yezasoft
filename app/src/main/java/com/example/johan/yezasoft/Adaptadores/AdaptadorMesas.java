package com.example.johan.yezasoft.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johan.yezasoft.Clases.Mesas;
import com.example.johan.yezasoft.ComandaActivity;
import com.example.johan.yezasoft.MainActivity;
import com.example.johan.yezasoft.R;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

public class AdaptadorMesas extends RecyclerView.Adapter<AdaptadorMesas.Holder> {
    private Context context;
    private ArrayList<Mesas> arrayList;
    private AsyncHttpClient cliente;

    public AdaptadorMesas(Context context, ArrayList<Mesas> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mesas,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.nombre.setText(arrayList.get(i).getMesainicial());
        try {
            holder.linearLayout.setBackgroundColor(Color.parseColor(arrayList.get(i).getColor().trim().toString()));
            final Mesas mesas = (Mesas) arrayList.get(i);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ComandaActivity.class);
                    intent.putExtra("idmesa", mesas.getIdmesa());
                    intent.putExtra("mesainicial", mesas.getMesainicial());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }catch (Exception e ){
            Toast.makeText(context, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView nombre;
        LinearLayout linearLayout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.lbl_nombre);
            linearLayout=itemView.findViewById(R.id.layoutColor);
        }
    }

}

