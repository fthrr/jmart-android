package com.fathurJmartMR.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Request class Register Store Request
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class RegisterStoreRequest extends StringRequest{
    /**
     * Instance variable untuk reqister store request
     */
    private Map<String, String> params;

    /**
     * Put method untuk class register class request
     * @param id                store id
     * @param name              store name
     * @param address           store address
     * @param phoneNumber       store phone number
     * @param listener          listener request
     * @param errorListener     error listener
     */
    public RegisterStoreRequest(
            int id,
            String name,
            String address,
            String phoneNumber,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener)
    {
        super(Method.POST, "http://10.0.2.2:8080/account/"+id+"/registerStore", listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("address", address);
        params.put("phoneNumber", phoneNumber);
    }

    /**
     * Mutator method untuk mengambil parameter
     * @return  parameter
     */
    public Map<String, String> getParams(){
        return params;
    }
}
