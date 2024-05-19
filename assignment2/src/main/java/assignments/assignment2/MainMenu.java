package assignments.assignment2;
import java.util.*;
// import assignments.assignment1.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList; // list resto disimpan di list
    private static ArrayList<User> userList; // list user yang sudah mendaftar
    public static void main(String[] args) {
        // define ArrayList
        restoList = new ArrayList<Restaurant>();
        // define user yang terdaftar
        initUser();

        // main
        boolean programRunning = true; // loop controller
        while(programRunning){
            printHeader();
            startMenu();
            int command = input.nextInt();
            input.nextLine();

            // saat command == 1
            if(command == 1){
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                // validasi input login
                if(validateLoginUser(nama, noTelp) == false){
                    System.out.print("Pengguna dengan data tersebut tidak ditemukan!\n");
                    continue;
                }
                else {
                    System.out.println(String.format("Selamat Datang %s!", nama));
                }

                // saat user valid
                User userLoggedIn; 
                userLoggedIn = getUser(nama, noTelp); // get object user
                boolean isLoggedIn = true; // loop controller

                // role == customer
                if(userLoggedIn.role == "Customer"){
                    while (isLoggedIn){
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan(userLoggedIn);
                            case 2 -> handleCetakBill(userLoggedIn);
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan(userLoggedIn);
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }else{
                    // role == admin
                    while (isLoggedIn){
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            // program keluar
            }else if(command == 2){
                programRunning = false;
            }else{
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    }

    // validasi login user
    public static boolean validateLoginUser(String nama, String nomorTelepon){
        boolean found = false; // controller
        for(User user : userList){
            if(user.getNama().equals(nama)){
                if(user.getNomorTelepon().equals(nomorTelepon)){
                    found = true;
                }
            }
        }
        return found;
    }

    public static User getUser(String nama, String nomorTelepon){
        // implementasi method untuk mendapat user dari userList
        for(User user : userList){
            if(user.getNama().equals(nama) && user.getNomorTelepon().equals(nomorTelepon)){
                return user;
            }
        }
        return null;
    }

    public static Restaurant getResto(String nama){
        // implementasi method untuk mendapat resto dari restoList
        for(Restaurant resto : restoList){
            if(nama.equalsIgnoreCase(resto.getNama())){
                return resto;
            }
        }
        return null;
    }
    
    public static void handleBuatPesanan(User userLoggedIn){
        // implementasi method untuk handle ketika customer membuat pesanan
        System.out.println("----------Buat Pesanan----------");

        // loop controller 
        boolean running = true;
        StringBuffer outputOfValidation = new StringBuffer(); // output validasi ketika salah input
        while(running){
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();

            // validasi nama restoran
            if(getResto(namaRestoran) == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

            // validasi tanggal pemesanan
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalPemesanan = input.nextLine();
            if(OrderGenerator.validateInputTanggal(tanggalPemesanan, outputOfValidation) == false){
                System.out.println(outputOfValidation);
                continue;
            }

            // input order
            System.out.print("Jumlah Pesanan: ");
            int jumlahPesanan = Integer.parseInt(input.next());
            input.nextLine(); // menghabiskan sisa \n

            // validasi input order
            System.out.println("Order");
            ArrayList<Boolean> isValidList = new ArrayList<>(); // controller
            ArrayList<Menu> menuList = new ArrayList<Menu>(); // inisiasi menuList

            for(int i = 0; i < jumlahPesanan; i++){
                boolean isValid = false;
                String order = input.nextLine();
                Restaurant resto = getResto(namaRestoran); // get resto object
                for(Menu menu : resto.getMenu()){ // loop menu
                    if(order.equals(menu.getNamaMakanan())){ 
                        isValid = true; // saat menu ditemukan di resto
                        menuList.add(menu);
                    }
                }
                // saat ada setidaknya 1 false (1 menu salah)
                isValidList.add(isValid);
            }
            // saat menu tidak tersedia di resto
            if(isValidList.contains(false)){
                System.out.println("Mohon memesan menu yang tersedia di Restoran!\n");
                continue;
            }
            // ongkir
            String lokasi = userLoggedIn.getLokasi();
            int biayaOngkir = 0;
            switch (lokasi) {
                case "P":
                    biayaOngkir = 10000;
                    break;
                case "U":
                    biayaOngkir = 20000;
                    break; 
                case "T":
                    biayaOngkir = 35000;
                    break;
                case "S":
                    biayaOngkir = 40000;
                    break;
                case "B":
                    biayaOngkir = 60000;
                    break;
                case "-":
                    biayaOngkir = 0;
                    break;
            }

            // generate ID
            String orderID = OrderGenerator.generateOrderID(namaRestoran, tanggalPemesanan, userLoggedIn.getNomorTelepon());
            
            // order
            Order newOrder = new Order(orderID, tanggalPemesanan, biayaOngkir, getResto(namaRestoran), menuList);
            
            // add to order history user
            userLoggedIn.setOrderHistory(newOrder);
            
            //output
            System.out.println(String.format("Pesanan dengan ID %s diterima!", orderID));
            running = false;
        }
    }

    public static void handleCetakBill(User userLoggedIn){
        // implementasi method untuk handle ketika customer ingin cetak bill
        System.out.println("----------Cetak Bill----------");

        boolean running = true; // loop controller
        while (running) {
            System.out.print("Masukkan Order ID: ");
            String orderID = input.nextLine();

            // validasi Order ID
            boolean isValid = false; // controller

            // inisiasi objek class Order
            Order billOrder;
            billOrder = new Order(null, null, 0, null, null);
            for(Order order : userLoggedIn.getOrderHistory()){
                if(orderID.equals(order.getOrderID())){ // saat order ID ditemukan di order history user
                    billOrder = order;
                    isValid = true;
                }
            }
            // order ID tidak ditemukan
            if(isValid == false){
                System.out.println("Order ID tidak dapat ditemukan.");
                continue;
            }

            String tanggalPemesanan = billOrder.getTanggalPemesanan();
            String namaRestoran = billOrder.getRestaurant().getNama();
            String lokasiPengiriman = userLoggedIn.getLokasi();
            boolean orderFinished = billOrder.getOrderFinished();
            String statusPengiriman = "";
            if(orderFinished == true){
                statusPengiriman = "Finished";
            }
            else{
                statusPengiriman = "Not Finished";
            }

            // print
            System.out.println(String.format("\nBill: \nOrder ID: %s\nTanggal pemesanan: %s\nRestaurant: %s\nLokasi Pengiriman: %s\nStatus Pengiriman: %s",orderID, tanggalPemesanan, namaRestoran, lokasiPengiriman, statusPengiriman));
            // list menu dalam order
            ArrayList<Menu> menuOrdered = billOrder.getItems();

            // pesanan dan print
            System.out.println("Pesanan: ");
            for(Menu menu : menuOrdered){
                System.out.println(String.format("- %s %s", menu.getNamaMakanan(), String.valueOf(menu.getHarga())));
            }

            // biaya dan print
            int biayaOngkosKirim = billOrder.getBiayaOngkosKirim();
            double totalBiaya = 0;
            for(Menu menu : menuOrdered){
                totalBiaya += menu.getHarga();
            }
            totalBiaya += biayaOngkosKirim;
            System.out.println(String.format("Biaya Ongkos Kirim: Rp %s\nTotal Biaya: Rp %s", biayaOngkosKirim, totalBiaya));
            running = false;
        }
    }

    public static void handleLihatMenu(){
        // implementasi method untuk handle ketika customer ingin melihat menu
        System.out.println("----------Lihat Menu----------");

        boolean running = true;
        while(running){
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();

            // validasi input nama restaurant
            Restaurant resto;
            if(getResto(namaRestoran) == null){
                System.out.println("Restoran tidak terdaftar pada sistem.");
                continue;
            }
            else{
                resto = getResto(namaRestoran);
            }

            // sorting menu
            double currentLower;
            Menu currentMenu;
            ArrayList<Menu> restoMenu = resto.getMenu(); 
            ArrayList<Menu> copyOfRestoMenu = new ArrayList<>(restoMenu);
            ArrayList<Menu> sortedMenu = new ArrayList<Menu>();
            int size = restoMenu.size();

            for(int i = 0; i < size; i++){
                currentLower = copyOfRestoMenu.get(0).getHarga();
                currentMenu = null;
                for (Menu menu : copyOfRestoMenu){
                    if(menu.getHarga() < currentLower){
                        currentLower = menu.getHarga();
                        currentMenu = menu;
                    }
                }
                // jika index 0 terendah
                if(currentMenu == null){
                    sortedMenu.add(copyOfRestoMenu.get(0));
                    copyOfRestoMenu.remove(copyOfRestoMenu.get(0));
                    continue;
                }
                // jika index 0 bukan terendah
                sortedMenu.add(currentMenu);
                copyOfRestoMenu.remove(currentMenu);
            }
            // print menu
            System.out.println("Menu: ");
            int i = 1;
            for(Menu menu : sortedMenu){
                System.out.print(String.format("%s. %s %s\n", String.valueOf(i), menu.getNamaMakanan(), menu.getHarga()));
                ++i;
            }
            running = false;
        }
    }

    public static void handleUpdateStatusPesanan(User userLoggedIn){
        // implementasi method untuk handle ketika customer ingin update status pesanan
        System.out.println("----------Update Status Pesanan----------");
        boolean running = true;
        while(running){
            System.out.print("Order ID: ");
            String orderID = input.nextLine();

            // validasi Order ID
            boolean isValid = false;
            Order billOrder;
            billOrder = new Order(null, null, 0, null, null);
            for(Order order : userLoggedIn.getOrderHistory()){
                if(orderID.equals(order.getOrderID())){ // order ID ditemukan dalam order history user
                    billOrder = order;
                    isValid = true;
                }
            }
            if(isValid == false){
                System.out.println("Order ID tidak dapat ditemukan.");
                continue;
            }
            
            System.out.print("Status: ");
            String status = input.nextLine();

            if (status.equalsIgnoreCase("Selesai")){
                if(billOrder.getOrderFinished() == true){ // pesanan sudah selesai
                    System.out.println(String.format("Status pesanan dengan ID %s tidak berhasil diupdate!", orderID));
                }
                else{ // pesanan belum selesai
                    billOrder.setOrderFinished(true);
                    System.out.println(String.format("Status pesanan dengan ID %s berhasil diupdate!", orderID));
                }
            }
            running = false;
        }
    }

    public static void handleTambahRestoran(){
        // implementasi method untuk handle ketika admin ingin tambah restoran
        System.out.println("----------Tambah Restoran----------");
        boolean running = true; // loop controller

        while (running) {

            // nama
            System.out.print("Nama: ");
            String nama = input.nextLine();
            
            // validasi input nama restoran
            if(nama.length() < 4){
                System.out.println("Nama Restoran tidak valid!\n");
                continue;
            }
            // saat resto sudah terdaftar dan terdapat di restoList
            if(!(getResto(nama) == null)){
                System.out.println(String.format("Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda!\n", nama));
                continue;
            }
            // inisiasi objek restoran
            Restaurant newResto = new Restaurant(nama);

            // makanan
            System.out.print("Jumlah Makanan: ");
            int jumlahMakanan = Integer.parseInt(input.next());
            input.nextLine(); // buang sisa "\n"

            boolean isValid = true; // controller
            while(jumlahMakanan > 0){
                String namaHarga = input.nextLine();

                // nama makanan
                String[] namaDanHarga = namaHarga.split(" ");
                String[] namaMakananList = Arrays.copyOfRange(namaDanHarga, 0, namaDanHarga.length -1);
                String namaMakanan = String.join(" ", namaMakananList);
                
                // harga makanan
                String harga = namaDanHarga[namaDanHarga.length - 1];
                double hargaMakanan = 0;
                // validasi harga makanan
                try {
                    hargaMakanan = Double.parseDouble(harga);
                    Menu newMenu = new Menu(namaMakanan, hargaMakanan);
                    newResto.setMenu(newMenu);
                } catch (NumberFormatException e) {
                    isValid = false; // saat harga makanan bukan numeric
                }
                jumlahMakanan--;
            }

            // validasi input harga makanan
            if(isValid == false){
                System.out.println("Harga menu harus bilangan bulat!\n");
                continue;
            }
            // berhasil
            else{
                running = false;
                restoList.add(newResto);
                System.out.println(String.format("Restoran %s berhasil terdaftar.",nama));
            }
        }  
    }

    public static void handleHapusRestoran(){
        // implementasi method untuk handle ketika admin ingin hapus restoran
        System.out.println("----------Hapus Restoran----------");
        boolean found = false; // controller
        do {
            System.out.print("Nama Restoran: ");
            String deletedResto = input.nextLine();
   
            Iterator<Restaurant> iterator = restoList.iterator();
            while (iterator.hasNext()) {
                Restaurant resto = iterator.next();
                if(deletedResto.equalsIgnoreCase(resto.getNama())){
                    found = true; // saat nama resto ada di restoList
                    iterator.remove(); // safely remove the current element
                }
            }
            // saat nama resto ada di restoList dan terhapus
            if(found == true){
                System.out.println("Restoran berhasil dihapus.");
            }
            else{
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
            }
        }while(found == false);
    }

    public static void initUser(){
        // user yang terdaftar
       userList = new ArrayList<User>();
       userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
       userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
       userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
       userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
       userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

       userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
       userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
}