package com.fathurJmartMR.jmart_android.model;

/**
 * Model class Product
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class Product extends Serializable{
    /**
     * Instance variable untuk class product
     */
    public int accountId;
    public ProductCategory category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public byte shipmentPlans;
    public int weight;

    /**
     * Override method untuk mengubah ke string
     * @return  name
     */
    @Override
    public String toString(){
        return name;
    }
}
