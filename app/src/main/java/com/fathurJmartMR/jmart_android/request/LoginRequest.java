package com.fathurJmartMR.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    private static final String URL = "http://localhost:8080/account/login";
    private Map<String, String> params;

    public LoginRequest(String email, String password,
                        Response.Listener<String> listener,
                        Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email", email);
        params.put("passwprd", password);
    }

    public Map<String, String> getParams(){
        return params;
    }
}
