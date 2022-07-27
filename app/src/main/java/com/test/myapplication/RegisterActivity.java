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

public class RegisterActivity extends AppCompatActivity {

    EditText edtemail, edtusername, edtpassword;
    Button ButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtemail = findViewById(R.id.edtemail);
        edtusername = findViewById(R.id.edtusername);
        edtpassword = findViewById(R.id.edtpassword);

        ButtonRegister = findViewById(R.id.ButtonRegister);

        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtusername.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "isi username terlebih dahulu", Toast.LENGTH_LONG).show();
                } else if (edtpassword.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "isi password terlebih dahulu", Toast.LENGTH_LONG).show();
                } else if (edtemail.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "isi email terlebih dahulu", Toast.LENGTH_LONG).show();
                } else {
                    Registerdata();
                }
            }
        });
    }

    private void Registerdata(){
        String URL = "http://94.74.86.174:8080/api/register";
        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("username", "fauzan");
        postParam.put("password", "12345");


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("RESPONSE", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if (jsonObject.getString("statusCode").equals("2000")) {
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                Toast.makeText(RegisterActivity.this, "register sudah berhasil", Toast.LENGTH_SHORT).show();
                            } else {
                                jsonObject.getString("statusCode").equals("401");
                                Toast.makeText(RegisterActivity.this, "username dan password salah", Toast.LENGTH_SHORT).show();
                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("TAG", "Error: " + error.getMessage());
            }
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        jsonObjReq.setTag("TAG");
        queue.add(jsonObjReq);
    }
}