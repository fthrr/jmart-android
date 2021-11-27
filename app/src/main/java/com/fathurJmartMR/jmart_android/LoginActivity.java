package com.fathurJmartMR.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fathurJmartMR.jmart_android.model.Account;
import com.fathurJmartMR.jmart_android.request.LoginRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity
implements Response.Listener<String>, Response.ErrorListener{

    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.emailLogin);
        etPassword = findViewById(R.id.passwordLogin);
        btnLogin = findViewById(R.id.loginButton);
        tvRegister = findViewById(R.id.registerText);

        btnLogin.setOnClickListener(this::onLoginClick);
        tvRegister.setOnClickListener(this::openRegisterActivity);
    }

    private void onLoginClick(View view){
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();

        LoginRequest req = new LoginRequest(email, pass, this, this);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(req);
    }

    private void openRegisterActivity(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void onResponse(String response){
        Intent intent = new Intent(this, MainActivity.class);
        try{
            JSONObject obj = new JSONObject(response);
            intent.putExtra("id", obj.getInt("id"));
        } catch (Exception e) {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, "Login Succcesful", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    public static Account getLoggedAccount(){
        return loggedAccount;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
    }
}