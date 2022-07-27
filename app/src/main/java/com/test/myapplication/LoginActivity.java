package com.test.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.textfield.TextInputEditText;
import com.test.myapplication.databinding.ActivityLoginBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText edtusername;
    private TextInputEditText edtpassword;
    private Button ButtonLogin, ButtonRegister;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtusername = findViewById(R.id.edtusername);
        edtpassword = findViewById(R.id.edtpassword);
        ButtonLogin = findViewById(R.id.ButtonLogin);
        ButtonRegister = findViewById(R.id.ButtonRegister);
       // progressBar = findViewById(R.id.progressBar);

        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtusername.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "isi username terlebih dahulu", Toast.LENGTH_LONG).show();
                } else if (edtpassword.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "isi password terlebih dahulu", Toast.LENGTH_LONG).show();
                } else {
                   // progressBar.setVisibility(View.VISIBLE);
                    Logindata();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void Logindata(){
        String URL = "http://94.74.86.174:8080/api/login";
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
                                if (jsonObject.getString("statusCode").equals("2110")) {
                                    //String placesid = jsonObject.getString("places_id");
                                    //String nama = jsonObject.getString("full_name");
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    jsonObject.getString("statusCode").equals("401");
                                    Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
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
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };
            jsonObjReq.setTag("TAG");
            // Adding request to request queue
            queue.add(jsonObjReq);

            // Cancelling request
    /* if (queue!= null) {
    queue.cancelAll(TAG);
    } */

        }

    }
