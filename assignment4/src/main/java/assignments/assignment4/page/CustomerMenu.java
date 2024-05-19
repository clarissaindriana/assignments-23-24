package assignments.assignment4.page;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import assignments.assignment3.assignment2.Menu;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.payment.DepeFoodPaymentSystem;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.assignment1.OrderGenerator;
import assignments.assignment3.assignment2.*;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter;
import assignments.assignment4.components.InterfacePack.CustomedRectangle;
import java.util.List;

public class CustomerMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ListView<String> listView = new ListView<>();
    private MainApp mainApp;
    private List<Restaurant> restoList = AdminMenu.restoList;
    private User user;

    public CustomerMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        this.printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();
    }

    @Override
    public Scene createBaseMenu() {
        // Implementasikan method ini untuk menampilkan menu untuk Customer
        VBox menuLayout = new VBox(20);

        // welcome user
        Label welcome = new Label(String.format("Welcome %s!", user.getNama()));
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        welcome.setTextFill(Color.WHITE);

        Rectangle rect = CustomedRectangle.RectangleCustom(300, 100);

        StackPane pane = new StackPane(rect, welcome);
        pane.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(welcome, Pos.CENTER);

        // buat pesanan
        Button buatPesananButton = new Button("Buat Pesanan");
        buatPesananButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        buatPesananButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Rectangle rectBuatPesanan = CustomedRectangle.RectangleCustom(180, 50);
        StackPane paneBuatPesanan = new StackPane(rectBuatPesanan, buatPesananButton);
        StackPane.setAlignment(buatPesananButton, Pos.CENTER);

        // cetak bill
        Button cetakBillButton = new Button("Cetak Bill");
        cetakBillButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        cetakBillButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Rectangle rectCetakBill = CustomedRectangle.RectangleCustom(180, 50);
        StackPane paneCetakBill = new StackPane(rectCetakBill, cetakBillButton);
        StackPane.setAlignment(paneCetakBill, Pos.CENTER);

        // bayar bill
        Button bayarBillButton = new Button("Bayar Bill");
        bayarBillButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        bayarBillButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Rectangle rectBayarBill = CustomedRectangle.RectangleCustom(180, 50);
        StackPane paneBayarBill = new StackPane(rectBayarBill, bayarBillButton);
        StackPane.setAlignment(paneBayarBill, Pos.CENTER);

        // cek saldo
        Button cekSaldoButton = new Button("Cek Saldo");
        cekSaldoButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        cekSaldoButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Rectangle rectCekSaldo = CustomedRectangle.RectangleCustom(180, 50);
        StackPane paneCekSaldo = new StackPane(rectCekSaldo, cekSaldoButton);
        StackPane.setAlignment(paneCekSaldo, Pos.CENTER);

        // logout
        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        logoutButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Rectangle rectLogout = CustomedRectangle.RectangleCustom(180, 50);
        StackPane paneLogout = new StackPane(rectLogout, logoutButton);
        StackPane.setAlignment(paneLogout, Pos.CENTER);

        // set on action
        buatPesananButton.setOnAction((ActionEvent event) -> {
            mainApp.setScene(addOrderScene);
            stage.show();
        });
        cetakBillButton.setOnAction((ActionEvent event) -> {
            mainApp.setScene(printBillScene);
            stage.show();
        });
        bayarBillButton.setOnAction((ActionEvent event) -> {
            mainApp.setScene(payBillScene);
            stage.show();
        });
        cekSaldoButton.setOnAction((ActionEvent event) -> {
            mainApp.setScene(cekSaldoScene);
            stage.show();
        });
        logoutButton.setOnAction((ActionEvent event) -> {
            mainApp.logout();
            stage.show();
        });

        // add to vbox
        menuLayout.getChildren().addAll(pane, paneBuatPesanan, paneCetakBill, paneBayarBill, paneCekSaldo, paneLogout);
        
        return new Scene(menuLayout, 400, 600);
    }

    private Scene createTambahPesananForm() {
        // Implementasikan method ini untuk menampilkan page tambah pesanan
        BorderPane border = new BorderPane();

        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);

        // restaurant
        Label restoName = new Label("Restaurant Name: ");
        restoName.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
        restoName.setTextFill(Color.WHITE);
        restoName.setPadding(new Insets(0,0,0,32));

        // combobox
        for(Restaurant restaurant : restoList){
            restaurantComboBox.getItems().add(restaurant.getNama());
        }
        restaurantComboBox.setStyle("-fx-background-color: rgb(217, 174, 173, 0.5); -fx-text-fill: white;");
        restaurantComboBox.setPrefWidth(180);
    
        // date
        Label date = new Label("Date (DD/MM/YYY): ");
        date.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
        date.setTextFill(Color.WHITE);
        date.setPadding(new Insets(0,0,0,32));

        TextField dateInput = new TextField();
        dateInput.setStyle("-fx-background-color: rgb(217, 174, 173, 0.5); -fx-text-fill: white;");

        // button menu
        Button menuButton = new Button("Menu");
        menuButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        menuButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        HBox button = new HBox(menuButton);
        button.setAlignment(Pos.CENTER);

        // add
        layout.add(restoName, 0, 0);
        layout.add(restaurantComboBox, 0, 1);
        layout.add(date, 0, 2);
        layout.add(dateInput, 0, 3);
        layout.add(button, 0, 4);
        layout.setAlignment(Pos.CENTER);

        Rectangle rectangle = CustomedRectangle.RectangleCustom(300, 200);
        StackPane topPane = new StackPane(rectangle, layout);
        topPane.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(layout, Pos.CENTER);

        // add
        border.setTop(topPane);

        // set on action
        menuButton.setOnAction((ActionEvent event) -> {
            // refresh
            listView.getItems().clear();
            // add vbox
            StackPane centerPane = handleViewMenuBuatPesanan(restaurantComboBox.getValue(),dateInput.getText().trim());
            border.setCenter(centerPane);

            mainApp.setScene(addOrderScene);
            stage.show();
            });

        return new Scene(border, 400, 600);
    }
    
    private Scene createBillPrinter(){
        // Implementasikan method ini untuk menampilkan page cetak bill
        BorderPane border = new BorderPane();

        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        
        // order ID
        Label orderID = new Label("Order ID: ");
        orderID.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
        orderID.setTextFill(Color.WHITE);
        orderID.setPadding(new Insets(0,0,0,50));

        TextField orderIdInput = new TextField();
        orderIdInput.setStyle("-fx-background-color: rgb(217, 174, 173, 0.5); -fx-text-fill: white;");

        // button
        Button printBillButton = new Button("Print Bill");
        printBillButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        printBillButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        kembaliButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        HBox printKembali = new HBox(printBillButton, kembaliButton);
        printKembali.setSpacing(10);
        printKembali.setPadding(new Insets(10,10,10,10));

        // add
        layout.add(orderID, 0, 0);
        layout.add(orderIdInput, 0, 1);
        layout.add(printKembali, 0,2);
        layout.setAlignment(Pos.CENTER);

        Rectangle rectangle = CustomedRectangle.RectangleCustom(200, 300);
        StackPane pane = new StackPane(rectangle, layout);
        pane.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(layout, Pos.CENTER);

        // set border
        border.setCenter(pane);

        // set on action
        printBillButton.setOnAction((ActionEvent event) -> {
            // validasi order ID
            Order order = CustomerSystemCLI.getOrderOrNull(orderIdInput.getText());
            if(order == null){
                showAlert("Alert", "Order ID Tidak Ditemukan!", "Masukkan Kembali Order ID." , AlertType.WARNING);
                mainApp.setScene(printBillScene);
                stage.show();
            }
            else { // order ID is true
                Scene billPrinter = new BillPrinter(stage, mainApp, user).getScene(order);
                mainApp.setScene(billPrinter);
                stage.show();
            }
            // refresh
            orderIdInput.clear();
        });
        kembaliButton.setOnAction((ActionEvent event) -> {
            // refresh
            orderIdInput.clear();
            // kembali ke CustomerMenu
            mainApp.setScene(this.scene);
            stage.show();
        });

        return new Scene(border, 400, 600);
    }

    private Scene createBayarBillForm() {
        // Implementasikan method ini untuk menampilkan page bayar bill
        BorderPane border = new BorderPane();

        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        
        // order id
        Label orderID = new Label("Order ID: ");
        orderID.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
        orderID.setTextFill(Color.WHITE);
        orderID.setPadding(new Insets(0,0,0,65));

        TextField orderIdInput = new TextField();
        orderIdInput.setStyle("-fx-background-color: rgb(217, 174, 173, 0.5); -fx-text-fill: white;");

        // combobox
        ComboBox<String> opsiPembayaranComboBox = new ComboBox<>();
        opsiPembayaranComboBox.getItems().addAll("Credit Card", "Debit");
        opsiPembayaranComboBox.setStyle("-fx-background-color: rgb(217, 174, 173, 0.5); -fx-text-fill: white;");
        opsiPembayaranComboBox.setPrefWidth(180);

        // button
        Button bayarButton = new Button("Bayar");
        bayarButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        bayarButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        kembaliButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        HBox bayarKembali = new HBox(bayarButton, kembaliButton);
        bayarKembali.setSpacing(10);
        bayarKembali.setPadding(new Insets(10,10,10,40));

        // add
        layout.add(orderID, 0, 0);
        layout.add(orderIdInput, 0, 1);
        layout.add(opsiPembayaranComboBox, 0, 2);
        layout.add(bayarKembali, 0,3);
        layout.setAlignment(Pos.CENTER);

        Rectangle rectangle = CustomedRectangle.RectangleCustom(200, 300);
        StackPane pane = new StackPane(rectangle, layout);
        pane.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(layout, Pos.CENTER);

        // set border
        border.setCenter(pane);

        // set on action
        bayarButton.setOnAction((ActionEvent event) -> {
            // validasi order ID
            Order order = CustomerSystemCLI.getOrderOrNull(orderIdInput.getText());
            if(order == null){
                showAlert("Alert", "Order ID Tidak Ditemukan!", "Masukkan Kembali Order ID." , AlertType.WARNING);
            }
            else { // order ID is true
                handleBayarBill(orderIdInput.getText(), opsiPembayaranComboBox.getValue());
            }
            mainApp.setScene(payBillScene);
            stage.show();

        });
        kembaliButton.setOnAction((ActionEvent event) -> {
            // refresh
            orderIdInput.clear();
            opsiPembayaranComboBox.setValue(null);
            // kembali ke Customer Menu
            mainApp.setScene(this.scene);
            stage.show();
        });

        return new Scene(border, 400,600);
    }

    private Scene createCekSaldoScene() {
        // Implementasikan method ini untuk menampilkan page cetak saldo
        BorderPane border = new BorderPane();

        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);

        // user dan saldo
        Label namaUser = new Label(this.user.getNama());
        namaUser.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
        namaUser.setTextFill(Color.WHITE);

        Rectangle rectUser = CustomedRectangle.RectangleCustom(200, 50);
        StackPane userPane = new StackPane(rectUser, namaUser);
        StackPane.setAlignment(namaUser, Pos.CENTER);

        Label saldo = new Label("");
        saldo.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
        saldo.setTextFill(Color.WHITE);

        Rectangle rectSaldo = CustomedRectangle.RectangleCustom(200, 50);
        StackPane saldoPane = new StackPane(rectSaldo, saldo);
        StackPane.setAlignment(saldo, Pos.CENTER);

        // button
        Button cekButton = new Button("Check Saldo");
        cekButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        cekButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        kembaliButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        HBox cekKembali = new HBox(cekButton, kembaliButton);
        cekKembali.setSpacing(10);
        cekKembali.setAlignment(Pos.CENTER);

        // add
        layout.add(userPane, 0, 0);
        layout.add(saldoPane, 0, 1);
        layout.add(cekKembali, 0, 2);
        layout.setAlignment(Pos.CENTER);

        Rectangle rectangle = CustomedRectangle.RectangleCustom(300, 300);
        StackPane centerPane = new StackPane(rectangle, layout);
        centerPane.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(layout, Pos.CENTER);

        // set border
        border.setCenter(centerPane);

        // set on action
        cekButton.setOnAction((ActionEvent event) -> {
            // set saldo
            saldo.setText((String.format("Saldo: Rp%s", String.valueOf(user.getSaldo()))));
            // refresh
            mainApp.setScene(cekSaldoScene);
            stage.show();
        });
        kembaliButton.setOnAction((ActionEvent event) -> {
            // refresh
            saldo.setText(null);
            // kembali ke CustomerMenu
            mainApp.setScene(this.scene);
            stage.show();
        });

        return new Scene(border, 400,600);
    }

    private StackPane handleViewMenuBuatPesanan(String namaRestaurant,String date){
        GridPane layoutMenu = new GridPane();
        layoutMenu.setHgap(10);
        layoutMenu.setVgap(10);

        // menu 
        Label menuLabel = new Label("Menu:");
        menuLabel.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
        menuLabel.setTextFill(Color.WHITE);
        
        // list view
        Restaurant restaurant = AdminMenu.getRestaurant(namaRestaurant);
        for(Menu menu : restaurant.getMenu()){
            listView.getItems().add(menu.getNamaMakanan());
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setPrefHeight(100);
        listView.setPrefWidth(200);

        // selected items
        Label selectedLabel = new Label("Selected Items: ");
        selectedLabel.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
        selectedLabel.setTextFill(Color.WHITE);

        ListView<String> selectedItemsArea = new ListView<>();
        selectedItemsArea.setPrefHeight(100);
        selectedItemsArea.setPrefWidth(200);

        // button
        Button buatPesananButton = new Button("Buat Pesanan");
        buatPesananButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        buatPesananButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        kembaliButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        HBox buatPesananAndKembali = new HBox(buatPesananButton, kembaliButton);
        buatPesananAndKembali.setSpacing(10);
        buatPesananAndKembali.setPadding(new Insets(10,10,10,10));
        
        // add
        layoutMenu.add(menuLabel, 0, 0);
        layoutMenu.add(listView, 0, 1);
        layoutMenu.add(selectedLabel, 0, 2);
        layoutMenu.add(selectedItemsArea, 0, 3);
        layoutMenu.add(buatPesananAndKembali, 0, 4);
        layoutMenu.setAlignment(Pos.CENTER);

        // rect
        Rectangle centerRectangle = CustomedRectangle.RectangleCustom(300, 320);
        StackPane centerPane = new StackPane(centerRectangle, layoutMenu);
        centerPane.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(layoutMenu, Pos.CENTER);

        // set on action
        listView.setOnMouseClicked(e -> { // selected items
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if(selectedItem != null){
                selectedItemsArea.getItems().add(selectedItem);
            }
        });
        buatPesananButton.setOnAction((ActionEvent event) -> {
            handleBuatPesanan(namaRestaurant, date, listView.getItems());
            // refresh
            listView.getItems().clear();
            selectedItemsArea.getItems().clear();
            mainApp.setScene(addOrderScene);
            stage.show();
        });
        kembaliButton.setOnAction((ActionEvent event) -> {
            // refresh
            listView.getItems().clear();
            selectedItemsArea.getItems().clear();
            // kembali ke CustomerMenu
            mainApp.setScene(this.scene);
            stage.show();
        });
        return centerPane;
    }

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        // Implementasi validasi isian pesanan

        // validate date
        boolean isValid = OrderGenerator.validateDate(tanggalPemesanan);
        if(isValid == false){
            showAlert("Alert", "Input Tanggal Tidak Valid!", "Masukkan Tanggal Pemesanan yang Valid.", AlertType.ERROR);
        }
        else if(menuItems.isEmpty()){
            showAlert("Alert", "Input Pesanan Tidak Valid!", "Masukkan Pesanan.", AlertType.ERROR);
        }
        else {
            Restaurant restaurant = AdminMenu.getRestaurant(namaRestoran);
            String orderID = OrderGenerator.generateOrderID(namaRestoran.toUpperCase(), tanggalPemesanan, this.user.getNomorTelepon());
            Order order = new Order(orderID, 
                                    tanggalPemesanan, 
                                    OrderGenerator.calculateDeliveryCost(this.user.getLokasi()), 
                                    restaurant, 
                                    CustomerSystemCLI.getMenuRequest(restaurant, menuItems));
            user.addOrderHistory(order);
            showAlert("Success", "Berhasil Membuat Pesanan!", String.format("Order dengan ID %s berhasil ditambahkan", orderID), AlertType.INFORMATION);
        }
    }

    private void handleBayarBill(String orderID, String pilihanPembayaran) {
        // Implementasi validasi pembayaran
        Order order = CustomerSystemCLI.getOrderOrNull(orderID);
        Restaurant resto = order.getRestaurant();
        long totalHarga = (long) order.getTotalHarga();
        DepeFoodPaymentSystem userPaymentSystem = user.getPaymentSystem();

        // validate order's status
        if (order.getOrderFinished() == true){
            showAlert("Alert", "Pembayaran Sudah Lunas", "Kembali ke Menu Awal", AlertType.WARNING);
            return;
        }
        // validate user's saldo
        if(user.getSaldo() < totalHarga){
            showAlert("Alert", "Pembayaran Gagal!", "Saldo Tidak Cukup.", AlertType.WARNING);
            return;
        }

        if(pilihanPembayaran.equals("Credit Card")){
            // validate user's payment system
            if(!(userPaymentSystem instanceof CreditCardPayment)){
                showAlert("Alert", "Pembayaran Gagal!", "User tidak memiliki metode pembayaran Credit Card", AlertType.WARNING);
                return;
            }
            // set Restaurant's and User's saldo while proccesing payment and printing the message
            resto.addSaldo(totalHarga);
            user.setSaldo(user.getSaldo() - userPaymentSystem.processPayment(totalHarga));
            showAlert("Succes", "Pembayaran Berhasil!", String.format("Berhasil Membayar Bill sebesar %s dengan Biaya Transaksi Sebesar %s.", String.valueOf(totalHarga), String.valueOf(totalHarga * 0.02)), AlertType.WARNING);

            // set order finished
            order.setOrderFinished(true);
        }  
        else if(pilihanPembayaran.equals("Debit")){
            // validate user's payment system
            if(!(userPaymentSystem instanceof DebitPayment)){
                showAlert("Alert", "Pembayaran Gagal!", "User tidak memiliki metode pembayaran Debit", AlertType.WARNING);  
                return;
            }

            // set Restauran's and User's saldo while processing payment and printing the message
            try{
                user.setSaldo(user.getSaldo() - userPaymentSystem.processPayment(totalHarga));
                resto.addSaldo(totalHarga);
                showAlert("Succes", "Pembayaran Berhasil!", String.format("Berhasil Membayar Bill sebesar %s.", String.valueOf(totalHarga)), AlertType.WARNING);
            }
            catch (IllegalArgumentException e){
                showAlert("Alert", "Pembayaran Gagal!", e.getMessage(), AlertType.WARNING);  
                return;
            }
            
            // set order finished
            order.setOrderFinished(true);
        }
    }

    @Override
    public Scene getScene(){
        return this.scene;
    }
}