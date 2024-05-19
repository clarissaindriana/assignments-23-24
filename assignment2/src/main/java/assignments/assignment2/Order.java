package assignments.assignment2;
import java.util.*;

public class Order {
    // attributes
    private String orderID;
    private String tanggalPemesanan;
    private int biayaOngkosKirim;
    private Restaurant restaurant;
    private ArrayList<Menu> items = new ArrayList<Menu>();
    private boolean orderFinished;


    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, ArrayList<Menu> items){
        this.orderID = orderId;
        this.tanggalPemesanan = tanggal;
        this.biayaOngkosKirim = ongkir;
        this.restaurant = resto;
        this.items = items;
    
    }
    
    // methods
    public String getOrderID(){
        return orderID;
    }
    public String getTanggalPemesanan(){
        return tanggalPemesanan;
    }
    public int getBiayaOngkosKirim(){
        return biayaOngkosKirim;
    }
    public Restaurant getRestaurant(){
        return restaurant;
    }
    public ArrayList<Menu> getItems(){
        return items;
    }
    public boolean getOrderFinished(){
        return orderFinished;
    }
    public void setOrderFinished(boolean isFinished){
        this.orderFinished = isFinished;
    }
}

