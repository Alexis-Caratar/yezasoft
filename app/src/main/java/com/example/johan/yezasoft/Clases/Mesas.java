package com.example.johan.yezasoft.Clases;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Mesas {
    String idmesa;
    String area;
    String color;
    String mesainicial;


    public Mesas() {
    }

    public Mesas(String idmesa, String area, String color, String mesainicial, String mesafinal) {
        this.idmesa = idmesa;
        this.area = area;
        this.color = color;
        this.mesainicial = mesainicial;

    }

    public String getIdmesa() {
        return idmesa;
    }

    public String getArea() {
        return area;
    }

    public String getColor() {
        return color;
    }

    public String getMesainicial() {
        return mesainicial;
    }



    public void setIdmesa(String idmesa) {
        this.idmesa = idmesa;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setMesainicial(String mesainicial) {
        this.mesainicial = mesainicial;
    }




}
