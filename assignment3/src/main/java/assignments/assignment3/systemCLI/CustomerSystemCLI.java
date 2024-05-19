package assignments.assignment3.systemCLI;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import assignments.assignment3.assignment1.OrderGenerator;
import assignments.assignment3.assignment2.Menu;
import assignments.assignment3.assignment2.Order;
import assignments.assignment3.assignment2.Restaurant;
import assignments.assignment3.assignment2.User;
import assignments.assignment3.MainMenu;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.payment.DepeFoodPaymentSystem;

// Extends abstract class yang diberikan
public class CustomerSystemCLI extends UserSystemCLI{

    // mengoverride dari Abstract class
    protected boolean handleMenu(int choice){
        switch(choice){
            case 1 -> handleBuatPesanan();
            case 2 -> handleCetakBill();
            case 3 -> handleLihatMenu();
            case 4 -> handleBayarBill();
            case 5 -> handleCheckSaldo();
            case 6 -> {
                return false;
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    // mengoverride dari Abstract class
    protected void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public void handleBuatPesanan(){
        // Implementasi method untuk handle ketika customer membuat pesanan
        System.out.println("--------------Buat Pesanan----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim().toUpperCase();
            Restaurant restaurant = getRestaurantByName(restaurantName);
            if(restaurant == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalPemesanan = input.nextLine().trim();
            if(!OrderGenerator.validateDate(tanggalPemesanan)){
                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)");
                System.out.println();
                continue;
            }
            System.out.print("Jumlah Pesanan: ");
            int jumlahPesanan = Integer.parseInt(input.nextLine().trim());
            System.out.println("Order: ");
            List<String> listMenuPesananRequest = new ArrayList<>();
            for(int i=0; i < jumlahPesanan; i++){
                listMenuPesananRequest.add(input.nextLine().trim());
            }
            if(! validateRequestPesanan(restaurant, listMenuPesananRequest)){
                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                continue;
            };
            Order order = new Order(
                    OrderGenerator.generateOrderID(restaurantName, tanggalPemesanan, MainMenu.userLoggedIn.getNomorTelepon()),
                    tanggalPemesanan, 
                    OrderGenerator.calculateDeliveryCost(MainMenu.userLoggedIn.getLokasi()), 
                    restaurant, 
                    getMenuRequest(restaurant, listMenuPesananRequest));
            System.out.printf("Pesanan dengan ID %s diterima!", order.getOrderId());
            MainMenu.userLoggedIn.addOrderHistory(order);
            return;
        }
    }
    public static boolean validateRequestPesanan(Restaurant restaurant, List<String> listMenuPesananRequest){
        return listMenuPesananRequest.stream().allMatch(pesanan -> 
            restaurant.getMenu().stream().anyMatch(menu -> menu.getNamaMakanan().equals(pesanan))
        );
    }

    public static Menu[] getMenuRequest(Restaurant restaurant, List<String> listMenuPesananRequest){
        Menu[] menu = new Menu[listMenuPesananRequest.size()];
        for(int i=0;i<menu.length;i++){
            for(Menu existMenu : restaurant.getMenu()){
                if(existMenu.getNamaMakanan().equals(listMenuPesananRequest.get(i))){
                    menu[i] = existMenu;
                }
            }
        }
        return menu;
    }

    public static String getMenuPesananOutput(Order order){
        StringBuilder pesananBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols);
        for (Menu menu : order.getSortedMenu()) {
            pesananBuilder.append("- ").append(menu.getNamaMakanan()).append(" ").append(decimalFormat.format(menu.getHarga())).append("\n");
        }
        if (pesananBuilder.length() > 0) {
            pesananBuilder.deleteCharAt(pesananBuilder.length() - 1);
        }
        return pesananBuilder.toString();
    }

    public static String outputBillPesanan(Order order) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);
        return String.format("Bill:%n" +
                         "Order ID: %s%n" +
                         "Tanggal Pemesanan: %s%n" +
                         "Lokasi Pengiriman: %s%n" +
                         "Status Pengiriman: %s%n"+
                         "Pesanan:%n%s%n"+
                         "Biaya Ongkos Kirim: Rp %s%n"+
                         "Total Biaya: Rp %s%n",
                         order.getOrderId(),
                         order.getTanggal(),
                         MainMenu.userLoggedIn.getLokasi(),
                         !order.getOrderFinished()? "Not Finished":"Finished",
                         getMenuPesananOutput(order),
                         decimalFormat.format(order.getOngkir()),
                         decimalFormat.format(order.getTotalHarga())
                         );
    }

    public static Restaurant getRestaurantByName(String name){
        Optional<Restaurant> restaurantMatched = MainMenu.restoList.stream().filter(restoran -> restoran.getNama().toLowerCase().equals(name.toLowerCase())).findFirst();
        if(restaurantMatched.isPresent()){
            return restaurantMatched.get();
        }
        return null;
    }

    public void handleCetakBill(){
        // Implementasi method untuk handle ketika customer ingin cetak bill
        System.out.println("--------------Cetak Bill----------------");
        while (true) {
            System.out.print("Masukkan Order ID: ");
            String orderId = input.nextLine().trim();
            Order order = getOrderOrNull(orderId);
            if(order == null){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            System.out.println("");
            System.out.print(outputBillPesanan(order));
            return;
        }
    }
    public static Order getOrderOrNull(String orderId) {
        for (User user : MainMenu.userList) {
            for (Order order : user.getOrderHistory()) {
                if (order.getOrderId().equals(orderId)) {
                    return order;
                }
            }
        }
        return null;
    }

    public void handleLihatMenu(){
        // Implementasi method untuk handle ketika customer ingin melihat menu
        System.out.println("--------------Lihat Menu----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = getRestaurantByName(restaurantName);
            if(restaurant == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print(restaurant.printMenu());
            return;
        }
    }

    public void handleBayarBill(){
        // Implementasi method untuk handle ketika customer ingin melihat menu
        
        System.out.println("--------------Bayar Bill----------------");
        while (true) {

            // input Order ID
            System.out.print("Masukkan Order ID: ");
            String orderId = input.nextLine().trim();
            Order order = getOrderOrNull(orderId); // get the Order object

            // if order == null 
            if(order == null){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            // if order is finished
            else if (order.getOrderFinished() == true){
                System.out.println("Pesanan dengan ID ini sudah lunas!");
                return;
            }
            // print bill pesanan
            System.out.println("");
            System.out.print(outputBillPesanan(order));

            System.out.println("\nOpsi Pembayaran: ");
            System.out.println("1. Credit Card");
            System.out.println("2. Debit");

            // input payment method
            System.out.print("Pilih Metode Pembayaran: ");
            String paymentMethod = input.nextLine();
            
            // get the Restaurant, User, totalHarga, and userPaymentSystem
            User user = MainMenu.userLoggedIn;
            Restaurant resto = order.getRestaurant();
            long totalHarga = (long) order.getTotalHarga();
            DepeFoodPaymentSystem userPaymentSystem = user.getPaymentSystem();

            // validasi input
            if(user.getSaldo() < totalHarga){
                System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
                continue;
            }

            if(paymentMethod.equals("1")){
                // validate user's payment system
                if(!(userPaymentSystem instanceof CreditCardPayment)){
                    System.out.println("User belum memiliki metode pembayaran ini!\n");
                    continue;
                }
                // set Restaurant's and User's saldo while proccesing payment and printing the message
                resto.addSaldo(totalHarga);
                user.setSaldo(user.getSaldo() - userPaymentSystem.processPayment(totalHarga));

                // set order finished
                order.setOrderFinished(true);
            }  
            else if(paymentMethod.equals("2")){
                // validate user's payment system
                if(!(userPaymentSystem instanceof DebitPayment)){
                    System.out.println("User belum memiliki metode pembayaran ini!\n");
                    continue;
                }
    
                // set Restauran's and User's saldo while processing payment and printing the message
                try{
                    user.setSaldo(user.getSaldo() - userPaymentSystem.processPayment(totalHarga));
                    resto.addSaldo(totalHarga);
                }
                catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                    continue;
                }
                
                // set order finished
                order.setOrderFinished(true);
            }
            else{
                System.out.println("Masukkan Opsi Pembayaran yang tersedia");
                continue;
            }
            return; // break the loop
        }
    }
    public void handleCheckSaldo(){
        // Implementasi method untuk handle ketika customer ingin check saldo
        User user = MainMenu.userLoggedIn;
        long saldo = user.getSaldo();
        System.out.println("Sisa saldo sebesar Rp "+saldo);
    }
}
