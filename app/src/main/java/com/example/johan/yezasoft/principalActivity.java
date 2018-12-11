package com.example.johan.yezasoft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johan.yezasoft.Clases.Url;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class principalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView usuario = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtmesero);
        TextView correo = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtcorreo);
        usuario.setText(preferences.getString("usuario", ""));
        correo.setText(preferences.getString("correo", ""));
        if(getIntent().hasExtra("mnu_select")) {
            MenuItem menuItem = navigationView.getMenu().findItem(getIntent().getExtras().getInt("mnu_select"));
            onNavigationItemSelected(menuItem);
        }
        navigationView.setNavigationItemSelectedListener(this);
        //PARA QUE APARESCA DE PRIMERO EL FRAGMENTO
        getSupportActionBar().setTitle("MESAS");
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.txtcontenido,new MesasFragment()).commit();
        //FIN

        String url=Url.url;
        AsyncHttpClient clienteweb=new AsyncHttpClient();
        RequestParams parametros=new RequestParams();
        parametros.put("usuario",usuario);
        //Toast.makeText(this, ""+identificacioncandidato, Toast.LENGTH_SHORT).show();
        clienteweb.post(url + "sesionusuario.php", parametros, new AsyncHttpResponseHandler() {
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

    }

    private void ingresar(int statusCode, String respuesta) {
        EditText txtcorreo,txtusario;

        txtusario=(EditText) findViewById(R.id.txtmesero);

        if (statusCode==200){
            try {
                JSONArray arregloJson=new JSONArray(respuesta);
                for(int i = 0; i < arregloJson.length(); i++) {

                  //  txtusario(arregloJson.getJSONObject(respuesta).getString("nombres"));
                 //   Toast.makeText(this, "arreglo " + nombre, Toast.LENGTH_SHORT).show();

                }

            } catch (JSONException e) {
                Toast.makeText(this, "PROBLEMA AL PROCESAR LOS DATOS", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }else Toast.makeText(this, "__", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Uri uri = Uri.parse("http://yezasoft.000webhostapp.com/index.php?CONTENIDO=inicioWeb.php");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.+

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();
        getSupportActionBar().setTitle("MESAS");

        if (id == R.id.nav_camera) {
            getSupportActionBar().setTitle("MESAS");
            fragmentTransaction.replace(R.id.txtcontenido,new MesasFragment()).commit();
        } else if (id == R.id.nav_gallery) {
            getSupportActionBar().setTitle("COMANDAS");
            fragmentTransaction.replace(R.id.txtcontenido,new ComandasFragment()).commit();

        } else if (id == R.id.nav_slideshow) {
            getSupportActionBar().setTitle("MENU");
            fragmentTransaction.replace(R.id.txtcontenido,new MenuPlatosFragment()).commit();

        } else if (id == R.id.nav_manage) {

        }
         else if (id == R.id.nav_send) {
            preferences.edit().remove("usuario").remove("clave").remove("empleado").remove("correo").apply();
            onStart();
            /*Intent intent = new Intent(principalActivity.this, MainActivity.class);
            principalActivity.this.startActivity(intent);*/

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onStart() {
        preferences = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        if(preferences.getString("usuario", "").isEmpty()) {
            startActivity(new Intent(this, MainActivity.class)); finish();
        }
        super.onStart();
    }
}
