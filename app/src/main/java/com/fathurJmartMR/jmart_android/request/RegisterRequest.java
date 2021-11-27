package com.fathurJmartMR.jmart_android.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{
    private static final String URL = "http://localhost:8080/account/register";
    private Map<String, String> params;

    public RegisterRequest(String name, String email, String password,
                           Response.Listener<String> listener,
                           Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    public Map<String, String> getParams(){
        return params;
    }
}
