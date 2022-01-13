package com.example.login_navview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String url = "https://my-json-server.typicode.com/AlexAndrangoCatota/jsonNavigationView/roles?user=";
    RequestQueue rqueue;
    EditText usuario, pass;
    Button login;
    String nombres;
    String img;
    String rol;
    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rqueue = Volley.newRequestQueue(this);
        usuario = (EditText)findViewById(R.id.txtUser);
        pass = (EditText)findViewById(R.id.txtpass);
        login = (Button)findViewById(R.id.btnLogIn);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                requestJSON(usuario.getText().toString().trim(), pass.getText().toString().trim());
            }
        });
    }

    private void requestJSON(String user, String password)
    {
        Intent in = new Intent(this, opciones.class);
       JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
               Request.Method.GET,
               url + user + "&pass=" + password,
               null,
               new Response.Listener<JSONArray>() {
                   @Override
                   public void onResponse(JSONArray response) {
                       if(response.length() <= 0)
                       {
                           Toast.makeText(getApplicationContext(), "credenciales incorrectas", Toast.LENGTH_SHORT).show();
                       }else
                       {
                           int size = response.length();
                           for (int i = 0; i<size; i++)
                           {
                               try {
                                   JSONObject jsObject = new JSONObject(response.get(i).toString());
                                   nombres = jsObject.getString("nombres");
                                   img = jsObject.getString("img");
                                   rol = jsObject.getString("rol");
                                   correo = jsObject.getString("correo");
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                           in.putExtra("nombres", nombres);
                           in.putExtra("img", img);
                           in.putExtra("rol", rol);
                           in.putExtra("correo", correo);
                           startActivity(in);
                       }
                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {

                   }
               }
       );
       rqueue.add(jsonArrayRequest);
    }
}