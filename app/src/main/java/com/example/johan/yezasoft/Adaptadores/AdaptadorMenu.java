package com.example.johan.yezasoft.Adaptadores;


import android.content.Context;
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
import com.loopj.android.http.AsyncHttpClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdaptadorMenu extends BaseAdapter {
    private Context context;
    private ArrayList<Plato> arrayList;
    private AsyncHttpClient cliente;

    public  AdaptadorMenu(Context context,AsyncHttpClient cliente,ArrayList<Plato> arrayList){

        this.context=context;
        this.arrayList=arrayList;
        this.cliente=cliente;

    }

    @Override
    public int getCount() {
        if (arrayList.size()>0) return arrayList.size();
        else
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  viwHolderMenu viwHolderMenu;

        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.item_platos,parent,false);
            viwHolderMenu=new viwHolderMenu(convertView);
            convertView.setTag(viwHolderMenu);
        }else{
            viwHolderMenu=(viwHolderMenu) convertView.getTag();
        }

        final Plato plato =(Plato) getItem(position);
        viwHolderMenu.setNombre(plato.getNombre());
        viwHolderMenu.setDescripcion(plato.getDescripcion());
        viwHolderMenu.setValor(plato.getValor());
        viwHolderMenu.setFoto(plato.getFoto());
        return convertView;
    }



}

class viwHolderMenu{

    TextView lblnombre,lblDescripcion,lblvalor;
    ImageView img_plato;
    public  viwHolderMenu(View view){

        lblnombre = (TextView) view.findViewById(R.id.lbl_nombre);
        lblDescripcion = (TextView) view.findViewById(R.id.lbl_descripcion);
        lblvalor = (TextView) view.findViewById(R.id.lbl_valor);
        img_plato=(ImageView) view.findViewById(R.id.img_plato);

    }
    public  void setNombre(String nombre){this.lblnombre.setText(nombre);}
    public  void setDescripcion(String descripcion){this.lblDescripcion.setText(descripcion);}
    public  void setValor(String valor) {this.lblvalor.setText("$ "+valor);}

    public  void setFoto(String foto) {if(!foto.equals("")){
        Log.i("FOTO",foto);
        Picasso.get().load(Url.url+foto).resize(70,70).centerCrop().into(this.img_plato);
    } }

}