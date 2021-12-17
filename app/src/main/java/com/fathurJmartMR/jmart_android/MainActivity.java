package com.fathurJmartMR.jmart_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fathurJmartMR.jmart_android.model.Account;
import com.fathurJmartMR.jmart_android.model.Product;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity Class untuk Main Page
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {
    public static final String EXTRA_PRODUCTID = "com.fathurJmartMR.jmart_android.EXTRA_PRODUCTID";
    private static final Gson gson = new Gson();
    RecyclerViewAdapter adapter;
    private TabLayout mainTabLayout;
    private CardView cv_product;
    private Button btnPrev;
    private Button btnNext;
    private Button btnGo;
    private EditText et_page;
    private int page;
    private CardView cv_filter;
    private EditText et_productName;
    private EditText et_lowestPrice;
    private EditText et_highestPrice;
    private CheckBox cb_new;
    private CheckBox cb_used;
    private Button btnApply;
    private Button btnClear;
    private Spinner spinner_filterCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);

        mainTabLayout = findViewById(R.id.mainTabLayout);
        cv_product = findViewById(R.id.cv_product);
        cv_filter = findViewById(R.id.cv_filter);
        //Tab Selector Listener
        mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        cv_product.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        cv_filter.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        cv_product.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        cv_filter.setVisibility(View.INVISIBLE);
                        break;
                }
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { } //Reselect Tab unused.
        });
        //Request to Fetch Product Lists
        List<Product> productNames = new ArrayList<>();                     //ArrayList to store products
        page = 0;                                                           //Default page = 0
        fetchProduct(productNames, page, queue, true);         //Method to fetch GET request products
        RecyclerView recyclerView = findViewById(R.id.rv_Products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, productNames);     //Using custom MyRecyclerViewAdapter
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); //Add divider to each row

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnGo   = findViewById(R.id.btnGo);
        et_page = findViewById(R.id.et_page);
        et_page.setText("1");
        btnPrev.setOnClickListener(new View.OnClickListener() {             //Handle pagination buttons
            @Override
            public void onClick(View v) {
                if(page > 0){
                    page--;
                    fetchProduct(productNames, page, queue, true);  //Update the list with paginated products
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                fetchProduct(productNames, page, queue, true);
            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    page = Integer.parseInt(et_page.getText().toString());
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Please input a valid page number.", Toast.LENGTH_LONG).show();
                    page = 0;
                }
                page--;
                fetchProduct(productNames, page, queue, true);
            }
        });
        //Filter CardView
        et_productName = findViewById(R.id.et_productName);
        et_lowestPrice = findViewById(R.id.et_lowestPrice);
        et_highestPrice = findViewById(R.id.et_highestPrice);
        spinner_filterCategory = findViewById(R.id.spinner_filterCategory);
        cb_new = findViewById(R.id.cb_new);
        cb_used = findViewById(R.id.cb_used);
        String checkCondition;
        if(cb_new.isChecked()){
            checkCondition = cb_new.getText().toString();
        }
        if(cb_used.isChecked()){
            checkCondition = cb_used.getText().toString();
        }
        btnApply = findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = et_productName.getText().toString();
                String lowestPrice= et_lowestPrice.getText().toString();
                String highestPrice = et_highestPrice.getText().toString();
                String category = spinner_filterCategory.getSelectedItem().toString();
                StringRequest filterRequest = new StringRequest(Request.Method.GET, "http://10.0.2.2:8080/product/getFiltered?pageSize=10&accountId="+LoginActivity.getLoggedAccount().id+"&search="+productName+"&minPrice="+lowestPrice+"&maxPrice="+highestPrice+"&category="+category, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Use reader to read json response of filtered products
                        JsonReader reader = new JsonReader(new StringReader(response));
                        try {
                            //Clear the previous list
                            productNames.clear();
                            reader.beginArray();
                            while(reader.hasNext()){
                                //Add the products from reader to the arraylist
                                productNames.add(gson.fromJson(reader, Product.class));
                            }
                            //Refresh/update displaying the list
                            adapter.refresh(productNames);
                            reader.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Filter product unsuccessful, error occurred", Toast.LENGTH_LONG).show();
                        }
                        //After filtering, move back to display the product tab (set product visible, set Filter invisible)
                        cv_product.setVisibility(View.VISIBLE);
                        cv_filter.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Filtering Succesful", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Filtering error, check again.", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(filterRequest);
            }
        });
        //Clear the input fields
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Testing Clear", Toast.LENGTH_SHORT).show();
                et_productName.setText("");
                et_lowestPrice.setText("");
                et_highestPrice.setText("");
                cb_new.setChecked(false);
                cb_used.setChecked(false);
                spinner_filterCategory.setSelection(0);
            }
        });
    }
    //Fetch Products Request Method
    public void fetchProduct(List<Product> productNames, int page, RequestQueue queue, boolean refreshAdapter){
        StringRequest fetchProductsRequest = new StringRequest(Request.Method.GET, "http://10.0.2.2:8080/product/page?page="+page+"&pageSize=10", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonReader reader = new JsonReader(new StringReader(response));
                try {
                    productNames.clear();
                    reader.beginArray();
                    while(reader.hasNext()){
                        productNames.add(gson.fromJson(reader, Product.class));
                    }
                    if(refreshAdapter){
                        adapter.refresh(productNames);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Fetch product unsuccessful, error occurred", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Fetch product unsuccessful, error occurred", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(fetchProductsRequest);
    }
    //RecycleView Item ClickListener
    @Override
    public void onItemClick(View view, int position) {
        int clickedItemId = adapter.getClickedItemId(position);
        Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
        intent.putExtra(EXTRA_PRODUCTID, clickedItemId);
        startActivity(intent);
    }
    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate with the menu created in menu_main
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        //Check if account has no store yet, dont display the add product menu item
        if(LoginActivity.getLoggedAccount().store == null){
            menu.getItem(1).setVisible(false);
        }
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        List<Product> productNames = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        fetchProduct(productNames, page, queue, true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        fetchProduct(productNames, page, queue, true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addBox:
                startActivity(new Intent(this, CreateProductActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(this, AboutMeActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}