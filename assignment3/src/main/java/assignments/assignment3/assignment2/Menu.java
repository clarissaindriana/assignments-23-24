package assignments.assignment3.assignment2;

public class Menu {
    
    // attributes
    private String namaMakanan;
    private double harga; 

    // constructor
    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }
    // methods
    public double getHarga() {
        return harga;
    }
    public String getNamaMakanan() {
        return namaMakanan;
    }
}
