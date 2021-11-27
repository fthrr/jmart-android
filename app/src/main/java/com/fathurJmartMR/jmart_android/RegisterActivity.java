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
import com.fathurJmartMR.jmart_android.request.RegisterRequest;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity
        implements Response.Listener<String>, Response.ErrorListener{

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.nameRegister);
        etEmail = findViewById(R.id.emailRegister);
        etPassword = findViewById(R.id.passwordRegister);
        btnRegister = findViewById(R.id.registerButton);

        btnRegister.setOnClickListener(this::onRegisterClick);
    }

    private void onRegisterClick(View view){
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        RegisterRequest req = new RegisterRequest(name ,email, password, this, this);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(req);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Register Failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        Intent intent = new Intent(this, MainActivity.class);
        try{
            JSONObject obj = new JSONObject(response);
            if(obj != null) {
                Toast.makeText(this, "Register successful", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Register Failed", Toast.LENGTH_LONG).show();
        }
    }
}