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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity Class untuk Register Page
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class RegisterActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener{

    private Button registerBtn;
    private TextView login;
    private EditText inName;
    private EditText inEmail;
    private EditText inPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerBtn = findViewById(R.id.registerButton);
        inName = findViewById(R.id.nameRegister);
        inEmail = findViewById(R.id.emailRegister);
        inPass = findViewById(R.id.passwordRegister);
        login = findViewById(R.id.loginText);

        registerBtn.setOnClickListener(this::onRegisterClick);
        login.setOnClickListener(this::onLoginClick);
    }

    private void onLoginClick(View v){
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void onRegisterClick(View v){
        String name = inName.getText().toString();
        String email = inEmail.getText().toString();
        String pass = inPass.getText().toString();

        RegisterRequest req = new RegisterRequest(name, email, pass, this, this);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(req);
    }

    @Override
    public void onResponse(String response){
        Intent i = new Intent(this, LoginActivity.class);
        try{
            JSONObject obj = new JSONObject(response);
            if(obj != null){
                Toast.makeText(this, "Register Successful", Toast.LENGTH_LONG).show();
                startActivity(i);
            }
        }
        catch(JSONException e){
            Toast.makeText(this, "Register Failed", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error){
        Toast.makeText(this, "System Failure", Toast.LENGTH_LONG).show();
    }
}