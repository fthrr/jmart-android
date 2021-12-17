package com.fathurJmartMR.jmart_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fathurJmartMR.jmart_android.model.Payment;
import com.fathurJmartMR.jmart_android.model.Product;
import com.google.gson.Gson;

import java.util.List;

/**
 * Adapter RecyclerView untuk Store Invoice Page
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class RecyclerViewInvoicesAdapter extends RecyclerView.Adapter<RecyclerViewInvoicesAdapter.ViewHolder> {
    private static final Gson gson = new Gson();
    private List<Payment> mData;
    private List<Product> invoiceProducts;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    // data is passed into the constructor
    RecyclerViewInvoicesAdapter(Context context, List<Payment> data, List<Product> invoiceProducts) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.invoiceProducts = invoiceProducts;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_buyer, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Payment paymentName = mData.get(position);
        Product productName = invoiceProducts.get(position);
        if(productName.toString().length() >= 38){
            holder.rv_tv_invoiceName.setTextSize(12.0f);
            holder.rv_tv_invoiceName.setMaxEms(10);
        }else{ }
        holder.rv_tv_invoiceName.setText(productName.toString());
        holder.rv_tv_invoiceStatus.setText(paymentName.history.get(paymentName.history.size() - 1).status.toString());
        holder.rv_tv_invoiceAmount.setText("x"+String.valueOf(paymentName.productCount));
        holder.rv_tv_invoiceTotalPrice.setText(String.valueOf(Math.round(productName.price * paymentName.productCount * 100.00)/100.00));
        switch (paymentName.shipment.plan){
            case 0:
                holder.rv_tv_invoiceShipmentPlan.setText(("INSTANT"));
                break;
            case 1:
                holder.rv_tv_invoiceShipmentPlan.setText(("SAME DAY"));
                break;
            case 2:
                holder.rv_tv_invoiceShipmentPlan.setText(("NEXT DAY"));
                break;
            case 4:
                holder.rv_tv_invoiceShipmentPlan.setText(("KARGO"));
                break;
            default:
                holder.rv_tv_invoiceShipmentPlan.setText(("REGULER"));
                break;
        }
        holder.rv_tv_productId.setText("Product ID: "+paymentName.productId);
        holder.rv_tv_invoiceBuyer.setText("Buyer ID: " + paymentName.buyerId);
        holder.rv_tv_invoiceShipmentAddress.setText(paymentName.shipment.address);
        holder.rv_image_productInvoice.setImageResource(ProductDetailActivity.getDrawableId(productName.category.toString()));
        if (paymentName.history.get(paymentName.history.size() - 1).status.toString() != "WAITING_CONFIRMATION") {
            holder.rv_btnAcceptInvoice.setVisibility(View.GONE);
            holder.rv_btnCancelTransaction.setVisibility(View.GONE);
        }
        if (paymentName.history.get(paymentName.history.size() - 1).status.toString() == "ON_PROGRESS") {
            holder.rv_btnSubmitTransaction.setVisibility(View.VISIBLE);
            holder.receipt.setVisibility(View.VISIBLE);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //Refresh the list by notify if list data has been updated
    public void refresh(List<Payment> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void refreshProducts(List<Product> invoiceProducts) {
        this.invoiceProducts = invoiceProducts;
        notifyDataSetChanged();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView rv_tv_invoiceName;
        TextView rv_tv_invoiceStatus;
        TextView rv_tv_invoiceAmount;
        TextView rv_tv_invoiceTotalPrice;
        TextView rv_tv_invoiceShipmentPlan;
        TextView rv_tv_productId;
        TextView rv_tv_invoiceBuyer;
        TextView rv_tv_invoiceShipmentAddress;
        ImageView rv_image_productInvoice;
        EditText receipt;
        Button rv_btnSubmitTransaction;
        Button rv_btnAcceptInvoice, rv_btnCancelTransaction;
        int id;

        ViewHolder(View itemView) {
            super(itemView);
            rv_tv_invoiceName = itemView.findViewById(R.id.rv_tv_invoiceName);
            rv_tv_invoiceStatus = itemView.findViewById(R.id.rv_tv_invoiceStatus);
            rv_tv_invoiceAmount = itemView.findViewById(R.id.rv_tv_invoiceAmount);
            rv_tv_invoiceTotalPrice = itemView.findViewById(R.id.rv_tv_invoiceTotalPrice);
            rv_tv_invoiceShipmentPlan = itemView.findViewById(R.id.rv_tv_invoiceShipmentPlan);
            rv_tv_productId = itemView.findViewById(R.id.rv_tv_productId);
            rv_tv_invoiceBuyer = itemView.findViewById(R.id.rv_tv_invoiceBuyerId);
            rv_tv_invoiceShipmentAddress = itemView.findViewById(R.id.rv_tv_invoiceShipmentAddress);
            rv_image_productInvoice = itemView.findViewById(R.id.rv_image_productInvoice);
            rv_btnAcceptInvoice = itemView.findViewById(R.id.rv_btnAcceptInvoice);
            rv_btnCancelTransaction = itemView.findViewById(R.id.rv_btnCancelTransaction);
            rv_btnSubmitTransaction = itemView.findViewById(R.id.rv_btnSubmitTransaction);
            receipt = itemView.findViewById(R.id.receipt);
            rv_btnAcceptInvoice.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    StringRequest cancelRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2:8080/payment/"+id+"/accept", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Toast.makeText(mInflater.getContext(), "Accept successful", Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(mInflater.getContext(), "Accept unsuccessful", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mInflater.getContext(), "Accept unsuccessful, can't connect to server", Toast.LENGTH_LONG).show();
                        }
                    });
                    RequestQueue queue = Volley.newRequestQueue(mInflater.getContext());
                    queue.add(cancelRequest);
                }
            });
            rv_btnCancelTransaction.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    StringRequest cancelRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2:8080/payment/"+id+"/cancel", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Toast.makeText(mInflater.getContext(), "Cancel successful", Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(mInflater.getContext(), "Cancel unsuccessful", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mInflater.getContext(), "Cancel unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    });
                    RequestQueue queue = Volley.newRequestQueue(mInflater.getContext());
                    queue.add(cancelRequest);
                }
            });
            rv_btnSubmitTransaction.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String receiptText = receipt.getText().toString();
                    StringRequest cancelRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2:8080/payment/"+id+"/submit?receipt="+receiptText, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Toast.makeText(mInflater.getContext(), "Submit successful", Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(mInflater.getContext(), "Submit unsuccessful", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mInflater.getContext(), "Submit unsuccessful, Can't connect to server", Toast.LENGTH_LONG).show();
                        }
                    });
                    RequestQueue queue = Volley.newRequestQueue(mInflater.getContext());
                    queue.add(cancelRequest);
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id).toString();
    }
    int getClickedItemId(int id){ return mData.get(id).id;}

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
