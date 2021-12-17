package com.fathurJmartMR.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Request class Create Product
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class CreateProductRequest extends StringRequest {
    /**
     * Instance variable untuk CreateProductRequest
     */
    private static final String URL = "http://10.0.2.2:8080/product/create";
    private Map<String, String> params;

    /**
     * Put method untuk CreateProductRequest
     * @param accountId         account id
     * @param name              product name
     * @param weight            product weight
     * @param conditionUsed     product condition
     * @param price             product price
     * @param discount          product discount
     * @param category          product category
     * @param shipmentPlans     product shipment plan
     * @param listener          listener request
     * @param errorListener     error listener request
     */
    public CreateProductRequest(
            String accountId,
            String name,
            String weight,
            String conditionUsed,
            String price,
            String discount,
            String category,
            String shipmentPlans,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("accountId", accountId);
        params.put("name", name);
        params.put("weight", weight);
        params.put("conditionUsed", conditionUsed);
        params.put("price", price);
        params.put("discount", discount);
        params.put("category", category);
        params.put("shipmentPlans", shipmentPlans);
    }

    /**
     * Mutator method untuk mengambil parameter
     * @return  parameter
     */
    public Map<String, String> getParams(){
        return params;
    }
}
