package com.example.johan.yezasoft.Adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.johan.yezasoft.Clases.Plato;
import com.example.johan.yezasoft.Clases.Url;
import com.example.johan.yezasoft.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdaptadorMenuPlatos extends BaseAdapter {
    private  ArrayList<Plato> arrayList;
    private Context context;
    private JSONArray jsonArray;
    public AdaptadorMenuPlatos(ArrayList<Plato> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        jsonArray = new JSONArray();
    }

    @Override
    public int getCount() {
        if(arrayList.size()>0) return arrayList.size();
        else return 0;
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolderMenuPlato viewHolder;
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_menu_platos, viewGroup, false);
            viewHolder = new viewHolderMenuPlato(view);
            view.setTag(viewHolder);
        } else viewHolder = (viewHolderMenuPlato) view.getTag();

        Plato plato = (Plato) getItem(i);
        viewHolder.setNombre(plato.getNombre());
        viewHolder.setCantidad(plato.getDescripcion());
        viewHolder.setFoto(plato.getFoto());
        viewHolder.setValor(plato.getValor(), plato.getDescripcion());
        return view;
    }
}

class viewHolderMenuPlato{

    TextView lblnombre,lblcantidad,lblvalor;
    ImageView img_plato;
    public  viewHolderMenuPlato(View view){

        lblnombre = (TextView) view.findViewById(R.id.menu_plato_nombre);
        lblcantidad = (TextView) view.findViewById(R.id.menu_plato_cantidad);
        lblvalor = (TextView) view.findViewById(R.id.menu_plato_valor);
        img_plato=(ImageView) view.findViewById(R.id.img_menu_plato);

    }
    public  void setNombre(String nombre){this.lblnombre.setText(nombre);}
    public void setCantidad(String cantidad) {this.lblcantidad.setText("Cantidad: "+cantidad);}
    public  void setValor(String valor, String cantidad) {
        int subtotal = Integer.parseInt(valor)*Integer.parseInt(cantidad);
        this.lblvalor.setText("$ "+Integer.toString(subtotal));
    }
    public  void setFoto(String foto) {
        if(!foto.equals("")) Picasso.get().load(Url.url+foto).resize(70,70).centerCrop().into(this.img_plato);
    }
}