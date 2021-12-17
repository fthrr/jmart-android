package com.fathurJmartMR.jmart_android.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Model class Payment
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class Payment extends Invoice{
    /**
     * Instance variable untuk class payment
     */
    public ArrayList<Record> history = new ArrayList<>();
    public int productCount;
    public Shipment shipment;

    /**
     * Inner class untuk payment record
     */
    public static class Record{
        /**
         * Instance variable untuk class record
         */
        public final Date date;
        public String message;
        public Status status;

        /**
         * Constructor method untuk record
         * @param status    record status
         * @param message   message status
         */
        public Record(Status status, String message){
            date = new Date();
            status = status;
            message = message;
        }
    }

    /**
     * Constructor method untuk payment
     * @param buyerId       buyer id
     * @param productId     product id
     * @param productCount  jumlah product
     * @param shipment      shipment method
     */
    public Payment(int buyerId, int productId, int productCount, Shipment shipment){
        super(buyerId, productId);
        this.productCount = productCount;
        this.shipment = shipment;
    }
}
