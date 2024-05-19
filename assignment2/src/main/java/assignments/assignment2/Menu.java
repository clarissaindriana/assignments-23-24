package assignments.assignment2;

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
    public String getNamaMakanan(){
        return namaMakanan;
    }
    public double getHarga(){
        return harga;
    }
}
