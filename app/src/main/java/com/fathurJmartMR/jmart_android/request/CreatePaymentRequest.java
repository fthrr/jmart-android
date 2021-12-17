package com.fathurJmartMR.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Request class Create Payment
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class CreatePaymentRequest extends StringRequest {
    /**
     * Instance variable untuk class createPaymentRequest
     */
    private Map<String, String> params;

    /**
     * Put Method uutuk Create Payment Request
     * @param buyerId           buyer id
     * @param productId         product id
     * @param productCount      jumlah product
     * @param shipmentAddress   alamat shipment
     * @param shipmentPlan      shipment plan
     * @param listener          listener request
     * @param errorListener     error listener
     */
    public CreatePaymentRequest(String buyerId, String productId, String productCount, String shipmentAddress, String shipmentPlan,
                                Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.POST, "http://10.0.2.2:8080/payment/create", listener, errorListener);
        params = new HashMap<>();
        params.put("buyerId", buyerId);
        params.put("productId", productId);
        params.put("productCount", productCount);
        params.put("shipmentAddress", shipmentAddress);
        params.put("shipmentPlan", shipmentPlan);
    }

    /**
     * Mutator method untuk mengambil parameter
     * @return  parameter
     */
    public Map<String, String> getParams(){
        return params;
    }
}
