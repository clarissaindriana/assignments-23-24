package assignments.assignment2;
import java.util.*;

public class Restaurant {
    // attributes
    private String nama;
    private ArrayList<Menu> menu;

    // constructor
    public Restaurant(String nama){
        this.nama = nama;
        this.menu = new ArrayList<Menu>();
    }

    // method
    public String getNama(){
        return nama;
    }
    public ArrayList<Menu> getMenu(){
        return menu;
    }
    public void setMenu(Menu newMenu){
        this.menu.add(newMenu);
    }
}
