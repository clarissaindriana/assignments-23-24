package assignments.assignment2;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

    // banner
    public static void showBanner(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
    }

    // menu
    public static void showMenu(){
        System.out.println("-------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    // validasi input nama restoran
    public static Boolean validateInputNamaResto(String inputNamaRestoran, StringBuffer outputOfValidation){
        // validasi nama restoran
        if(inputNamaRestoran.length() < 4){
           outputOfValidation.replace(0, outputOfValidation.length(), "Nama Restoran tidak valid!\n");
           return false;
        }
        return true;
   }

   // validasi input tanggal pemesanan
   public static Boolean validateInputTanggal(String inputTanggalPemesanan, StringBuffer outputOfValidation){
        // validasi tanggal order
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try{
            LocalDate date = LocalDate.parse(inputTanggalPemesanan, inputFormatter);
            String formattedDate = date.format(outputFormatter);
            return true; 
        } 
        catch(DateTimeParseException exception) {
            outputOfValidation.replace(0, outputOfValidation.length(), "Tanggal pemesanan dalam format DD/MM/YYYY\n");
            return false;
        }
         
    }

    // validasi input nomor telepon
    public static Boolean validateInputNoTelp(String inputNoTelepon, StringBuffer outputOfValidation){
        // validasi nomor telepon
        try{
            Double.parseDouble(inputNoTelepon);
            return true;
        
        // gagal convert karena bukan numeric
        } catch(NumberFormatException e) {
            outputOfValidation.replace(0, outputOfValidation.length(), "Harap masukkan nomor telepon dalam bentuk bilangan bulat positif\n");
            return false;
        }
        
    }

    // mencari checksum 
    public static String checksum (String fourteenChars){
        String code39Characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int evenSum = 0;
        int oddSum = 0;
        for(int i = 0; i < fourteenChars.length(); i++){
            if (i % 2 == 0){
                char aChar = fourteenChars.charAt(i);
                int charValue = code39Characters.indexOf(aChar);
                evenSum += charValue;
            }
            else if (i % 2 == 1){
                char aChar = fourteenChars.charAt(i);
                int charValue = code39Characters.indexOf(aChar);
                oddSum += charValue;
            }
        }
        int evenChecksum = evenSum % 36;
        int oddChecksum = oddSum % 36;
        String checksum = Character.toString(code39Characters.charAt(evenChecksum)).concat(Character.toString(code39Characters.charAt(oddChecksum)));

        return checksum;
    }

    // method untuk generate order ID
    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        String output = "";
        
        // formatting 4 karakter pertama nama restoran
        String restoFourChars = namaRestoran.toUpperCase();
        restoFourChars = restoFourChars.replaceAll("\\s", "").substring(0,4);

        // formatting 8 karakter tanggal pemesanan
        String dateEightChars = tanggalOrder.replaceAll("/", "");

        // formatting 2 karakter dari nomor telepon
        int sum = 0;
        for(int i = 0; i < noTelepon.length(); i++){
            char digit = noTelepon.charAt(i);
            sum += Character.getNumericValue(digit);
        }
        int modulo = sum % 100;
        String moduloAsString = Integer.toString(modulo);
        if (moduloAsString.length() < 2){
            moduloAsString = "0" + moduloAsString;
        }

        // formatting 2 karakter checksum dari 14 karakter di atas
        String fourteenChars = (restoFourChars + dateEightChars + moduloAsString);
        String checksum = checksum(fourteenChars);
        

        // Order ID
        output = restoFourChars + dateEightChars + moduloAsString + checksum;
        return output;
    }

    // validasi input order ID
    public static Boolean validateInputOrderID(String inputOrderID, StringBuffer outputOfValidation){
        // validate Order ID
        if(inputOrderID.length() < 16){
            outputOfValidation.replace(0, outputOfValidation.length(), "Order ID minimal 16 karakter");
            return false;
        }
        else{
            // validasi checksum
            String fourteenChars = inputOrderID.substring(0,14);
            String validChecksum = checksum(fourteenChars);

            String validInputOrderID = fourteenChars + validChecksum;
            if(!(validInputOrderID.equals(inputOrderID))){
                outputOfValidation.replace(0, outputOfValidation.length(), "Silahkan masukkan Order ID yang valid!");
                return false;
            }
            return true;
        }
    }

    // validasi input lokasi pengiriman
    public static Boolean validateLokasi(String lokasi, StringBuffer outputOfValidation){
        String availableLocations = "PUTSB";
        Boolean ifLocationIsTrue = availableLocations.contains(lokasi.toUpperCase());
        if(!(ifLocationIsTrue)){
            outputOfValidation.replace(0, outputOfValidation.length(), "Harap masukkan lokasi pengiriman yang ada pada jangkauan");
            return false;
        }
        return true;
    }

    // generate bill
    public static String generateBill(String orderID, String lokasi){
        String output = "";
        String biayaOngkir = "";
        lokasi = lokasi.toUpperCase();
        switch (lokasi) {
            case "P":
                biayaOngkir = "Rp 10.000";
                break;
            case "U":
                biayaOngkir = "Rp 20.000";
                break; 
            case "T":
                biayaOngkir = "Rp 35.000";
                break;
            case "S":
                biayaOngkir = "Rp 40.000";
                break;
            case "B":
                biayaOngkir = "Rp 60.000";
                break;
        }
        // formatting tanggal pemesanan
        String inputTanggalPemesanan = orderID.substring(4, 12);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate date = LocalDate.parse(inputTanggalPemesanan, inputFormatter);
        String formattedDate = date.format(outputFormatter);

        // output
        output = String.format("Bill:\nOrder ID: %s\nTanggal Pemesanan: %s\nLokasi Pengiriman: %s\nBiaya Ongkos Kirim: %s\n", orderID, formattedDate, lokasi.toUpperCase(), biayaOngkir);
        return output;
    }


    // MAIN
        public static void main(String[] args){

        // show banner dan input user untuk memberi command
        showBanner();
        String inputUser;
        do {
            // menu
            showMenu();
            System.out.println("-------------------------");
            System.out.print("Pilihan menu: ");
            inputUser = input.next();

            // command 1
            if (inputUser.equals("1")){
                Boolean stateOfInput = true;
                StringBuffer outputOfValidation = new StringBuffer(); // output validasi ketika salah input
                String inputNamaRestoran;
                String inputTanggalPemesanan;
                String inputNoTelepon;

                while(stateOfInput == true){
                    outputOfValidation.replace(0, outputOfValidation.length(), "");

                    // nama resto
                    System.out.print("\nNama restoran: ");
                    inputNamaRestoran = input.next();
                    // validasi input
                    if(validateInputNamaResto(inputNamaRestoran, outputOfValidation) == false){
                        System.out.println(outputOfValidation);
                        continue;
                    }

                    // tanggal pemesanan
                    System.out.print("Tanggal pemesanan: ");
                    inputTanggalPemesanan = input.next();
                    // validasi input
                    if(validateInputTanggal(inputTanggalPemesanan, outputOfValidation) == false){
                        System.out.println(outputOfValidation);
                        continue;
                    }

                    // no telp
                    System.out.print("No. Telepon: ");
                    inputNoTelepon = input.next();
                    // validasi input
                    if(validateInputNoTelp(inputNoTelepon, outputOfValidation) == false){
                        System.out.println(outputOfValidation);
                        continue;
                    }
                    
                    // if all true
                    String methodOutput = String.format("Order ID %s diterima!", generateOrderID(inputNamaRestoran, inputTanggalPemesanan, inputNoTelepon));
                    System.out.println(methodOutput);
                    stateOfInput = false; //loop berhenti
                }
            }

            // command 2
            else if(inputUser.equals("2")){
                Boolean stateOfInput = true;
                StringBuffer outputOfValidation = new StringBuffer(); // output validasi saat salah input
                String inputOrderID;
                String lokasi;

                while(stateOfInput == true){
                    outputOfValidation.replace(0, outputOfValidation.length(), "");

                    // order ID
                    System.out.print("\nOrder ID: ");
                    inputOrderID = input.next();
                    // validasi
                    if(validateInputOrderID(inputOrderID, outputOfValidation) == false){
                        System.out.println(outputOfValidation);
                        continue;
                    }

                    // lokasi
                    System.out.print("Lokasi pengiriman: ");
                    lokasi = input.next();
                    // validasi 
                    if(validateLokasi(lokasi, outputOfValidation) == false){
                        System.out.println(outputOfValidation);
                        continue;
                    }

                    // if all true
                    System.out.println();
                    System.out.println(generateBill(inputOrderID, lokasi));
                    stateOfInput = false; //loop berhenti
                }
            }

            // command 3
            else if(inputUser.equals("3")){
                System.out.println("Terima kasih telah menggunakan DepeFood!");
            }
        } while(!(inputUser.equals("3")));
    }
}
