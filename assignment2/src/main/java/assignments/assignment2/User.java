package assignments.assignment2;
import java.util.*;

public class User {
    // attributes
    private String nama;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    public String role;
    private ArrayList<Order> orderHistory;

    // constructor
    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.orderHistory = new ArrayList<Order>();
    }
    // methods
    public String getNama(){
        return nama;
    }
    public String getNomorTelepon(){
        return nomorTelepon;
    }
    public String getEmail(){
        return email;
    }
    public String getLokasi(){
        return lokasi;
    }
    public ArrayList<Order> getOrderHistory(){
        return orderHistory;
    }
    public void setOrderHistory(Order newOrder){
        orderHistory.add(newOrder);
    }

}
