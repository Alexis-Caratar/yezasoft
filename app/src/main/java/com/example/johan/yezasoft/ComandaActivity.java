package com.example.johan.yezasoft;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johan.yezasoft.Adaptadores.AdaptadorMenuPlatos;
import com.example.johan.yezasoft.Clases.Plato;
import com.example.johan.yezasoft.Clases.Url;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ComandaActivity extends AppCompatActivity {
    public SharedPreferences login_preferences, preferences;
    private FloatingActionButton fab;
    private TextView txtmesa,txtmesero;
    private ListView listView;
    private AsyncHttpClient client;
    private Toolbar toolbar;
    private JSONArray platos_comanda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);
        client = new AsyncHttpClient();
        client.setTimeout(1000000);
        toolbar = (Toolbar) findViewById(R.id.toolbar_comanda);
        setSupportActionBar(toolbar);
        platos_comanda = new JSONArray();
        login_preferences = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        preferences = this.getSharedPreferences("comanda", MODE_PRIVATE);
        final ArrayList<Plato> listItems = new ArrayList<Plato>();
        listView = (ListView) findViewById(R.id.lista_menu_platos);
        final AdaptadorMenuPlatos adaptadorMenuPlatos = new AdaptadorMenuPlatos(listItems, ComandaActivity.this);
        fab = (FloatingActionButton) findViewById(R.id.fab_comanda);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(ComandaActivity.this);
                final View view = layoutInflater.inflate(R.layout.formulario_comandas, null);
                final Spinner spinner_menu = (Spinner) view.findViewById(R.id.spinner_menu);
                final Spinner spinner_plato = (Spinner) view.findViewById(R.id.spinner_plato);
                final EditText txtcantidad = (EditText) view.findViewById(R.id.txtcantidad);
                final AlertDialog.Builder formulario = new AlertDialog.Builder(v.getContext());
                formulario.setCancelable(false);
                formulario.setTitle("Adicionar plato");

                formulario.setView(view);
                client.post(Url.url + "mesero/listarmenus.php", new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(ComandaActivity.this, throwable.getMessage().trim(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {

                        try {
                            final JSONArray jsonArray = new JSONArray(responseString);
                            final List<String> arrayList = new ArrayList<String>();
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayList.add(object.get("nombre").toString());
                            }
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ComandaActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList);
                            spinner_menu.setAdapter(adapter);

                            spinner_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    RequestParams params = new RequestParams();
                                    try {
                                      //  Toast.makeText(ComandaActivity.this, jsonArray.getJSONObject(i).get("idmenu").toString(), Toast.LENGTH_SHORT).show();
                                        params.put("idmenu", jsonArray.getJSONObject(i).get("idmenu").toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    client.post(Url.url + "mesero/listarmenu_platos.php", params, new TextHttpResponseHandler() {
                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                            Toast.makeText(ComandaActivity.this, throwable.getMessage().trim(), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                            spinner_plato.setEnabled(true);

                                            try {
                                                final JSONArray jsonArray = new JSONArray(responseString);
                                                preferences.edit().putString("platosJSON", jsonArray.toString()).commit();
                                                List<String> arrayList = new ArrayList<String>();
                                                for(int i = 0; i < jsonArray.length(); i++) {
                                                    JSONObject object = jsonArray.getJSONObject(i);
                                                    arrayList.add(object.get("nombre").toString()+" - $ "+object.get("valor").toString());
                                                }
                                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ComandaActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList);
                                                spinner_plato.setAdapter(adapter);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            spinner_plato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                    try {
                                                        JSONArray jsonArray1 = new JSONArray(preferences.getString("platosJSON", ""));
                                                        preferences.edit().putString("platoseleccionado", jsonArray1.getJSONObject(i).toString()).commit();
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> adapterView) {

                                                }
                                            });
                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        } catch (JSONException e) {
                            Toast.makeText(ComandaActivity.this, e.getMessage().trim(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                formulario.setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String txtplato = preferences.getString("platoseleccionado", "");
                        String cantidad = txtcantidad.getText().toString().trim();
                        if(!txtplato.isEmpty()&&(!cantidad.isEmpty()||cantidad.equals("0"))) {
                            try {
                                JSONObject object = new JSONObject(preferences.getString("platoseleccionado", ""));
                                object.put("cantidad", cantidad);
                                platos_comanda.put(object);
                              //  Toast.makeText(ComandaActivity.this, platos_comanda.toString(), Toast.LENGTH_SHORT).show();
                                preferences.edit().putString("platos_comanda", platos_comanda.toString()).apply();
                                Plato plato = new Plato(object.get("idplato").toString(), object.get("nombre").toString(), cantidad, object.get("valor").toString(), object.get("foto").toString());
                                listItems.add(plato);
                                adaptadorMenuPlatos.notifyDataSetChanged();
                                dialogInterface.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            dialogInterface.cancel();
                            Toast.makeText(ComandaActivity.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                formulario.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }); formulario.show();
            }
        });
        txtmesa=(TextView) findViewById(R.id.txtmesa);
        txtmesero=(TextView) findViewById(R.id.txtmesero);
        txtmesero.setText(preferences.getString("usuario", ""));
        txtmesa.setText(getIntent().getExtras().get("mesainicial").toString());
        listView.setAdapter(adaptadorMenuPlatos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_comanda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mnu_registrar_comanda) {
            RequestParams params = new RequestParams();
            params.put("platosJSON", preferences.getString("platos_comanda", ""));
            params.put("mesero", login_preferences.getString("empleado", ""));
            params.put("idmesa",getIntent().getExtras().get("idmesa").toString());

            client.post(Url.url + "mesero/Registrarfactura.php",params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(ComandaActivity.this, throwable.getMessage().trim(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if(responseString.isEmpty()) {
                        Intent intent = new Intent(ComandaActivity.this, principalActivity.class);
                        intent.putExtra("mnu_select", R.id.nav_camera);
                        startActivity(intent);
                    } else Log.i("RESPUESTA", responseString);
                }
            });
        }
        return true;
    }

    @Override
    protected void onStart() {
        preferences = this.getSharedPreferences("comanda",MODE_PRIVATE);
        preferences.edit().remove("platos_comanda").remove("platosJSON").remove("platoseleccionado").apply();
        super.onStart();
    }
}