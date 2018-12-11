package com.example.johan.yezasoft.Clases;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Comandas {
    private String idcomanda;
    private String idempleado;
    private String mesa;
    private String fecha;
    private String estado;
    private String reserva;
    private String factura;
    private String total;


    public Comandas(String idcomanda, String mesa, String fecha, String estado, String total) {
        this.idcomanda = idcomanda;
        this.mesa = mesa;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;

    }

    public Comandas() {

    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getIdcomanda() {
        return idcomanda;
    }

    public void setIdcomanda(String idcomanda) {
        this.idcomanda = idcomanda;
    }

    public String getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(String idempleado) {
        this.idempleado = idempleado;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        if (estado.equals("P")) {
            estado = "PENDIENTE";
        } else if (estado.equals("V")) {
            estado = "VISTO EN COCINA";

        } else if (estado.equals("L")) {
            estado = "LISTO EN COCINA";
        } else if (estado.equals("E")) {
            estado = "ENTREGADA";

        } else if (estado.equals("PG")) {
            estado = "PAGADO";
        }
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getReserva() {
        return reserva;
    }

    public void setReserva(String reserva) {
        this.reserva = reserva;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }



    public  static ArrayList<Comandas> getArrayList(String jsonarray){
        try {
            JSONArray jsonArray=new JSONArray(jsonarray);
            Comandas[] comandas =new Comandas[jsonArray.length()];
            ArrayList<Comandas> arrayList=new ArrayList<>();

            for (int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String idcomanda=jsonObject.getString("idcomanda");
                String mesa=jsonObject.getString("mesa");
                String fecha=jsonObject.getString("fecha");
                String estado=jsonObject.getString("estado");
                String total=jsonObject.getString("total");
                comandas[i]=new Comandas(idcomanda,mesa,fecha,estado,total);
                arrayList.add(comandas[i]);
            } return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
