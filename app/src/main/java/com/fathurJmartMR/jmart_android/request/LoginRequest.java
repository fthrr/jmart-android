package com.fathurJmartMR.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Request class Login Request
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class LoginRequest extends StringRequest {
    /**
     * Instance variable untuk class login Request
     */
    private static final String URL = "http://10.0.2.2:8080/account/login";
    private Map<String, String> params;

    /**
     * Put method untuk login request
     * @param email             email login
     * @param password          password login
     * @param listener          listener request
     * @param errorListener     error listener request
     */
    public LoginRequest(
            String email,
            String password,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    /**
     * Mutator method untuk mengambil parameter
     * @return  parameter
     */
    public Map<String, String> getParams(){
        return params;
    }
}
