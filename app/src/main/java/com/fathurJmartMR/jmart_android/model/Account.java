package com.fathurJmartMR.jmart_android.model;

/**
 * Model class Account
 *
 * @author Fathurrahman Irwansa
 * @version Final
 */
public class Account extends Serializable{
    public double balance;
    public String email;
    public String name;
    public String password;
    public Store store;

    /**
     * Constructor method dari class Account
     * @param name          account name
     * @param email         email account
     * @param password      password account
     * @param balance       balance account
     */
    public Account(String name, String email, String password, double balance){
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }
}
