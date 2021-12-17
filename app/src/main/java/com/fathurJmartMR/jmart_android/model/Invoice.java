package com.fathurJmartMR.jmart_android.model;

import java.util.Date;

/**
 * Model class account
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public abstract class Invoice extends Serializable{
    /**
     * Instance variable untuk kelas invoice
     */
    public int buyerId;
    public int complaintId;
    public final Date date;
    public int productId;
    public Rating rating;

    /**
     * enum class untuk invoice status
     */
    public enum Status{
        WAITING_CONFIRMATION,
        CANCELLED,
        ON_PROGRESS,
        ON_DELIVERY,
        COMPLAINT,
        FINISHED,
        FAILED,
        DELIVERED
    }

    /**
     * enum class untuk invoice Rating
     */
    public enum Rating{
        NONE,
        BAD,
        NEUTRAL,
        GOOD
    }

    /**
     * Record class untuk inisialisasi instance variable
     */
    class Record{
        public Date date;
        public String message;
        public Status status;
    }

    /**
     * Constructor method untuk invoice
     * @param buyerId       buyer id
     * @param productId     product id
     */
    protected Invoice(int buyerId, int productId){
        this.buyerId = buyerId;
        this.productId = productId;
        this.date = new Date();
        this.rating = Rating.NONE;
        this.complaintId = -1;
    }

}
