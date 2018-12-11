package com.example.johan.yezasoft.Clases;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Plato {

    String idplato;
    String nombre;
    String descripcion;
    String valor;
    String foto;

    public Plato(){}

    public Plato(String idplato, String nombre, String descripcion, String valor, String foto) {
        this.idplato = idplato;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valor = valor;
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getIdplato() {
        return idplato;
    }

    public String getNombre() {
        return nombre;
    }

    public String getValor() {return this.valor;}

    public  static ArrayList<Plato> getArrayList(String jsonarray){

        try {
            JSONArray jsonArray=new JSONArray(jsonarray);
            Plato[] platos =new Plato[jsonArray.length()];
            ArrayList<Plato> arrayList=new ArrayList<>();

            for (int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String idplato=jsonObject.getString("idplato");
                String nombre=jsonObject.getString("nombre");
                String descripcion=jsonObject.getString("descripcion");
                String valor=jsonObject.getString("valor");
                String foto=jsonObject.getString("foto");
                platos[i]=new Plato(idplato,nombre,descripcion,valor,foto);
                arrayList.add(platos[i]);
            } return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
