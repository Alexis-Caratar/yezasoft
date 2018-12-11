package com.example.johan.yezasoft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.johan.yezasoft.Clases.Url;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txtusuario;
    private EditText txtclave;
    private Button btningresar;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    txtusuario=(EditText) findViewById(R.id.txtmesero);
    txtclave=(EditText) findViewById(R.id.txtclave);
    btningresar=(Button) findViewById(R.id.btningresar);
    btningresar.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btningresar:

              String url=Url.url;

                AsyncHttpClient clienteweb = new AsyncHttpClient();
                RequestParams parametros = new RequestParams();
                parametros.put("usuario", txtusuario.getText());
                parametros.put("clave", txtclave.getText());
                clienteweb.post(url + "mesero/validar.php", parametros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if (statusCode==200) {
                            String resultado = new String(responseBody);
                            ingresar(statusCode, resultado);
                        } else ingresar(statusCode, "Error al status code");
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        ingresar(statusCode, "Error");
                            }
                });
                break;
        }
    }

    private void ingresar(int statusCode, String respuesta) {
        if (statusCode==200){
            if(respuesta.equals("false")) {
                Toast.makeText(this, "Usuario o contraseña invalidos", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(respuesta);
                    JSONObject objetojson=jsonArray.getJSONObject(0);
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("usuario", objetojson.get("usuario").toString());
                    editor.putString("clave", objetojson.get("clave").toString());
                    editor.putString("correo", objetojson.get("correo").toString());
                    editor.putString("empleado", objetojson.get("empleado").toString());
                    editor.commit();
                    startActivity(new Intent(this, principalActivity.class)); finish();
                } catch (JSONException e) {
                    Toast.makeText(this, "PROBLEMA AL PROCESAR LOS DATOS", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

        }else Toast.makeText(this, "problema al conectando con el servidor ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        preferences = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        if(!preferences.getString("usuario", "").isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), principalActivity.class);
            startActivity(intent); finish();
        }
        super.onStart();
    }
}