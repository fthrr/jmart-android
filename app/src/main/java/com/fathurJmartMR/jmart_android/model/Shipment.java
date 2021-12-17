package com.fathurJmartMR.jmart_android.model;

/**
 * Model class Shipment
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class Shipment {
    /**
     * Instance variable untuk class shipment
     */
    public String address;
    public int cost;
    public byte plan;
    public String receipt;

    /**
     * Constructor Method untuk class shipment
     * @param address   alamat shipment
     * @param cost      cost shipment
     * @param plan      plan shipment
     * @param receipt   receipt shipment
     */
    public Shipment(String address, int cost, byte plan, String receipt){
        this.address = address;
        this.cost = cost;
        this.plan = plan;
        this.receipt = receipt;
    }
}
