package com.fathurJmartMR.jmart_android.request;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Request class Register Store Request
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class RequestFactory {
    /**
     * Instance variable untuk class request Factory
     */
    private static final String URL_FORMAT_ID = "http://10.0.2.2:8080/%s/%d";
    private static final String URL_FORMAT_PAGE = "http://10.0.2.2:8080/%s/page?page=%s&pageSize=%s";

    /**
     * Method String Request untuk mendapatkan data berdasarkan ID
     * @param parentURI         URI Utama
     * @param id                id
     * @param listener          listener
     * @param errorListener     error listener
     * @return StringRequest    new object
     */
    public static StringRequest getById(
            String parentURI,
            int id,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener)
    {
        String url = String.format(URL_FORMAT_ID, parentURI, id);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }

    /**
     * Method String Request untuk mendapatkan data berdasarkan Page
     * @param parentURI     URI utama
     * @param page          page number
     * @param pageSize      ukuran page
     * @param listener      listener
     * @param errorListener error listener
     * @return  StringReqeust   new object
     */
    public static StringRequest getPage(
            String parentURI,
            int page,
            int pageSize,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener)
    {
        String url = String.format(URL_FORMAT_PAGE, parentURI, page, pageSize);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }
}
