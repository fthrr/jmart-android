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
import com.fathurJmartMR.jmart_android.model.Store;
import com.fathurJmartMR.jmart_android.request.LoginRequest;
import com.fathurJmartMR.jmart_android.request.RegisterRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity Class untuk Login Page
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class LoginActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener{

    public static final Gson gson = new Gson();
    private static Account loggedAccount = null;

    private Button loginBtn;
    private TextView register;
    private EditText inEmail;
    private EditText inPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerText);
        inEmail = findViewById(R.id.emailLogin);
        inPass = findViewById(R.id.passwordLogin);

        register.setOnClickListener(this::onRegisterClick);
        loginBtn.setOnClickListener(this::onLoginClick);
    }

    private void onRegisterClick(View v){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void onLoginClick(View v){
        String email = inEmail.getText().toString();
        String pass = inPass.getText().toString();

        LoginRequest req = new LoginRequest(email, pass, this, this);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(req);
    }

    @Override
    public void onResponse(String response){
        Intent i = new Intent(this, MainActivity.class);
        try{
            JSONObject obj = new JSONObject(response);
            i.putExtra("id", obj.getInt("id"));
            loggedAccount = gson.fromJson(obj.toString(), Account.class);
        }
        catch(Exception e){
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show();
        startActivity(i);
    }

    @Override
    public void onErrorResponse(VolleyError error){
        Toast.makeText(this, "System Failure", Toast.LENGTH_LONG).show();
    }

    public static Account getLoggedAccount(){
        return loggedAccount;
    }

    public static void reloadLoggedAccount(String response){
        loggedAccount = gson.fromJson(response, Account.class);
    }

    public static void insertLoggedAccountStore(String response){
        Store newStore = gson.fromJson(response, Store.class);
        loggedAccount.store = newStore;
    }
}