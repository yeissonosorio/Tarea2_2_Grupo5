package com.example.tarea2_2_grupo5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tarea2_2_grupo5.API.restapi;

import org.json.JSONObject;

public class Activitibuscar extends AppCompatActivity {
    EditText txtid;
    Button btnbuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitibuscar);

        txtid = (EditText) findViewById(R.id.txtid);
        btnbuscar = (Button) findViewById(R.id.btnbuscarid);


        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtid.getText().toString().length() != 0) {
                    search(txtid.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Campo vacio, por favor ponga un ID", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void search(String id){
        restapi restapi=new restapi();
        String url;
        url=restapi.url+"/"+id;

        RequestQueue queue= Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // JSON data retrieved successfully
                        try{
                            Log.e("w",""+response.length());
                            String[] datas=new String[1];

                            for (int i=0; i<1; i++) {
                                String id=response.getString("id");
                                String title=response.getString("title");
                                String body=response.getString("body");
                                String data=id+" - "+title+" - "+body;
                                datas[i]=data;
                            }
                            ListView listView=findViewById(R.id.list_id);
                            ArrayAdapter<String> adapter=new ArrayAdapter<>(Activitibuscar.this, R.layout.list_item_layout, datas);//adapter=adaptador
                            listView.setAdapter(adapter);

                        } catch (Exception e) {
                            Log.e("e",""+e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error occurred while making the request
                        error.printStackTrace();
                    }
                });

        queue.add(jsonObjectRequest);
    }
}