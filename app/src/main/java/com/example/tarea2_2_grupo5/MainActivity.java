package com.example.tarea2_2_grupo5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tarea2_2_grupo5.API.restapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    restapi rest_api=new restapi();
    ListView lista;

    Button btnbuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.list);
        btnbuscar=findViewById(R.id.btnbuscar);

        String url=rest_api.url;
        RequestQueue queue= Volley.newRequestQueue(this);

        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONArray jsonArray=new JSONArray(response);
                            String[] datas=new String[jsonArray.length()];

                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject career_object=jsonArray.getJSONObject(i);
                                String id=career_object.getString("id");
                                String title=career_object.getString("title");
                                String body=career_object.getString("body");
                                String data=id+" - "+title+" - "+body;
                                datas[i]=data;
                            }

                            ListView listView=findViewById(R.id.list);
                            ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this, R.layout.list_item_layout, datas);
                            listView.setAdapter(adapter);

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("W",""+error);
            }
        });
        queue.add(request);

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Activitibuscar.class);
                startActivity(intent);
            }
        });


    }


}