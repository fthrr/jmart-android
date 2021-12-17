package com.fathurJmartMR.jmart_android.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Request class Register Request
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class RegisterRequest extends StringRequest{
    /**
     * Instance variable untuk register request
     */
    private static final String URL = "http://10.0.2.2:8080/account/register";
    private Map<String, String> params;

    /**
     * Put method untuk register account request
     * @param name              account name
     * @param email             email account
     * @param password          password account
     * @param listener          listener request
     * @param errorListener     error listener request
     */
    public RegisterRequest(
            String name,
            String email,
            String password,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
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
