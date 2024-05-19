package assignments.assignment3.assignment2;
import java.util.ArrayList;
import assignments.assignment3.payment.DepeFoodPaymentSystem;

public class User {
    
    // attributes
    private String nama;
    private String nomorTelepon;
    private String email;
    public String role;
    private String lokasi;
    private ArrayList<Order> orderHistory;
    private DepeFoodPaymentSystem payment;
    private long saldo;

    // constructor
    public User(String nama, String nomorTelepon, String email, String lokasi, String role, DepeFoodPaymentSystem paymentSystem, long saldo){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.payment = paymentSystem;
        this.saldo = saldo;
        orderHistory = new ArrayList<>();
    }

    // methods
    public long getSaldo(){
        return saldo;
    }
    public void setSaldo(long amount){
        this.saldo = amount;
    }
    public DepeFoodPaymentSystem getPaymentSystem(){
        return payment;
    }
    public void setPayment(DepeFoodPaymentSystem paymentSystem){
        this.payment = paymentSystem;
    }
    public String getEmail() {
        return email;
    }
    public String getNama() {
        return nama;
    }
    public String getLokasi() {
        return lokasi;
    }
    public String getNomorTelepon() {
        return nomorTelepon;
    }
    public void addOrderHistory(Order order){
        orderHistory.add(order);
    }
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }
    public boolean isOrderBelongsToUser(String orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format("User dengan nama %s dan nomor telepon %s", nama, nomorTelepon);
    }

}
