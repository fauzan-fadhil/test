package com.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edtNama;
    Button Btntambah, Btnliat, Btnhapus, Btnedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNama = findViewById(R.id.edtNama);
        Btntambah = findViewById(R.id.Btntambah);
        Btnliat = findViewById(R.id.Btnliat);
        Btnhapus = findViewById(R.id.Btnhapus);
        Btnedit = findViewById(R.id.Btnedit);

        Btnhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(MainActivity.this, Activitydelete.class));
            }
        });

        Btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AvtivityEdit.class));
            }
        });

        Btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNama.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "data tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {
                    Inputdata();
                }
            }
        });

        Btnliat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LihatData.class));
            }
        });
    }

    private void Inputdata(){
        String URL = "http://94.74.86.174:8080/api/checklist";
        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("name", "fauzan");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("RESPONSE", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if (jsonObject.getString("statusCode").equals("2000")) {
                                Toast.makeText(MainActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            } else {
                                jsonObject.getString("statusCode").equals("401");
                                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("TAG", "Error: " + error.getMessage());
                //hideProgressDialog();
            }
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                HashMap<String, String> headers = new HashMap<String, String>();
                String authValue = "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6W119.i2OVQdxr08dmIqwP7cWOJk5Ye4fySFUqofl-w6FKbm4EwXTStfm0u-sGhDvDVUqNG8Cc7STtUJlawVAP057Jlg";
                headers.put("Authorization", authValue);
                headers.put("Accept", "application/json; charset=UTF-8");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }};
        jsonObjReq.setTag("TAG");
        // Adding request to request queue
        queue.add(jsonObjReq);

        // Cancelling request
    /* if (queue!= null) {
    queue.cancelAll(TAG);
    } */

    }
}