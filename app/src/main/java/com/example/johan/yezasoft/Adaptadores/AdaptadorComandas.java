package com.example.johan.yezasoft.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johan.yezasoft.Clases.Comandas;
import com.example.johan.yezasoft.ComandaActivity;
import com.example.johan.yezasoft.R;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

public class AdaptadorComandas extends BaseAdapter {
    private Context context;
    private ArrayList<Comandas> arrayList;
    private AsyncHttpClient cliente;

    public  AdaptadorComandas(Context context,AsyncHttpClient cliente,ArrayList<Comandas> arrayList){

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
        final viwHolderComandas viwHolderComandas;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.item_comandasechas,parent,false);
            viwHolderComandas=new viwHolderComandas(convertView);
            convertView.setTag(viwHolderComandas);
        }else{
            viwHolderComandas=(com.example.johan.yezasoft.Adaptadores.viwHolderComandas) convertView.getTag();
        }
        final Comandas comandas=(Comandas) getItem(position);
        viwHolderComandas.setIdcomanda("COMANDA: "+comandas.getIdcomanda());
        viwHolderComandas.setMesa("MESA: "+comandas.getMesa());
        viwHolderComandas.setFecha("Fecha y Hora: "+comandas.getFecha());
        viwHolderComandas.setEstado("Estado: "+comandas.getEstado());
        viwHolderComandas.setTotal("TOTAL: "+comandas.getTotal());

    convertView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,ComandaActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("idcomanda",comandas.getIdcomanda());
            context.startActivity(intent);

        }
    });



        return convertView;
    }



}

class viwHolderComandas{
    TextView lbl_idcomanda,lbl_comanda_mesa,lbl_comanda_fecha,lbl_comanda_estado,lbl_comandas_total;
    public  viwHolderComandas(View view){
        lbl_idcomanda = (TextView) view.findViewById(R.id.lbl_comanda_idcomanda);
        lbl_comanda_mesa = (TextView) view.findViewById(R.id.lbl_comanda_mesa);
        lbl_comanda_fecha = (TextView) view.findViewById(R.id.lbl_comanda_fecha);
        lbl_comanda_estado = (TextView) view.findViewById(R.id.lbl_comanda_estado);
        lbl_comandas_total=(TextView) view.findViewById(R.id.lbl_comanda_valor);


    }
    public  void setIdcomanda(String idcomanda){this.lbl_idcomanda.setText(idcomanda);}
    public  void setMesa(String mesa){this.lbl_comanda_mesa.setText(mesa);}
    public  void setFecha(String fecha){this.lbl_comanda_fecha.setText(fecha);}
    public  void setEstado(String estado){this.lbl_comanda_estado.setText(estado);}
    public  void setTotal(String total){this.lbl_comandas_total.setText(total);}



}

