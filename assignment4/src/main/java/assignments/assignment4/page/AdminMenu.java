package assignments.assignment4.page;

import java.util.ArrayList;
import java.util.List;
import assignments.assignment3.assignment2.*;
import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.InterfacePack.CustomedRectangle;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class AdminMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private User user;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    protected static List<Restaurant> restoList = new ArrayList<>(); // punya class, bukan instance
    private MainApp mainApp; // Reference to MainApp instance
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ComboBox<String> menuItemsComboBox = new ComboBox<>();

    public AdminMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();
    }

    @Override
    public Scene createBaseMenu() {
        // Implementasikan method ini untuk menampilkan menu untuk Admin
        VBox menuLayout = new VBox(20);

        // welcome admin
        Label welcome = new Label(String.format("Welcome %s!", user.getNama()));
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        welcome.setTextFill(Color.WHITE);

        Rectangle rect = CustomedRectangle.RectangleCustom(300, 100);

        StackPane pane = new StackPane(rect, welcome);
        pane.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(welcome, Pos.CENTER);

        // tambah restoran
        Button tambahRestoButton = new Button("Tambah Restoran");
        tambahRestoButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        tambahRestoButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Rectangle rectTambahResto = CustomedRectangle.RectangleCustom(180, 50);
        StackPane paneTambahResto = new StackPane(rectTambahResto, tambahRestoButton);
        // paneTambahResto.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(tambahRestoButton, Pos.CENTER);

        // tambah menu restoran
        Button tambahMenuButton = new Button("Tambah Menu Restoran");
        tambahMenuButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        tambahMenuButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Rectangle rectTambahMenu = CustomedRectangle.RectangleCustom(180, 50);
        StackPane paneTambahMenu = new StackPane(rectTambahMenu, tambahMenuButton);
        StackPane.setAlignment(paneTambahMenu, Pos.CENTER);

        // lihat daftar restoran
        Button daftarRestoButton = new Button("Lihat Daftar Restoran");
        daftarRestoButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        daftarRestoButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Rectangle rectDaftarResto = CustomedRectangle.RectangleCustom(180, 50);
        StackPane paneDaftarResto = new StackPane(rectDaftarResto, daftarRestoButton);
        StackPane.setAlignment(paneDaftarResto, Pos.CENTER);

        // logout
        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        logoutButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Rectangle rectLogout = CustomedRectangle.RectangleCustom(180, 50);
        StackPane paneLogout = new StackPane(rectLogout, logoutButton);
        StackPane.setAlignment(paneLogout, Pos.CENTER);

        // add to vbox
        menuLayout.getChildren().addAll(pane, paneTambahResto, paneTambahMenu, paneDaftarResto, paneLogout);

        // set on action
        tambahRestoButton.setOnAction((ActionEvent event) -> {
            // set scene to addRestaurantSece
            mainApp.setScene(addRestaurantScene);
            stage.show();
        });
        tambahMenuButton.setOnAction((ActionEvent event) -> {
            // set scene to addMenuScene
            mainApp.setScene(addMenuScene);
            stage.show();
        });
        daftarRestoButton.setOnAction((ActionEvent event) -> {
            // set scene to viewRestaurantScene
            mainApp.setScene(viewRestaurantsScene);
            stage.show();
        });
        logoutButton.setOnAction((ActionEvent event) -> {
            // set scene to Login Scene
            mainApp.logout();
            stage.show();
        });
        
        return new Scene(menuLayout, 400, 600);
    }

    private Scene createAddRestaurantForm() {
        // Implementasikan method ini untuk menampilkan page tambah restoran
        BorderPane border = new BorderPane();

        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        
        // input restaurant 
        Label restoName = new Label("Restaurant Name");
        restoName.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
        restoName.setTextFill(Color.WHITE);
        restoName.setPadding(new Insets(0,0,0,32));

        TextField restoNameInput = new TextField();
        restoNameInput.setStyle("-fx-background-color: rgb(217, 174, 173, 0.5); -fx-text-fill: white;");

        // button
        Button submit = new Button("Submit");
        submit.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        submit.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Button kembali = new Button("Kembali");
        kembali.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        kembali.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        HBox submitKembali = new HBox(submit, kembali);
        submitKembali.setSpacing(10);
        submitKembali.setPadding(new Insets(10,10,10,10));

        // add grid
        layout.add(restoName, 0, 0);
        layout.add(restoNameInput, 0, 1);
        layout.add(submitKembali, 0,2);
        layout.setAlignment(Pos.CENTER);

        Rectangle rectangle = CustomedRectangle.RectangleCustom(200, 300);
        StackPane pane = new StackPane(rectangle, layout);
        pane.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(layout, Pos.CENTER);

        // set border
        border.setCenter(pane);

        // set on action
        submit.setOnAction((ActionEvent event) -> {
            handleTambahRestoran(restoNameInput.getText());
            // refresh
            restoNameInput.clear();
            mainApp.setScene(addRestaurantScene);
            stage.show();
        });
        kembali.setOnAction((ActionEvent event) -> {
            // refresh
            restoNameInput.clear();
            // kembali ke admin menu
            mainApp.setScene(this.scene);
            stage.show();
        });

        return new Scene(border, 400, 600);
    }

    private Scene createAddMenuForm() {
        // Implementasikan method ini untuk menampilkan page tambah menu restoran
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
        restaurantComboBox.setStyle("-fx-background-color: rgb(217, 174, 173, 0.5); -fx-text-fill: white;");
        restaurantComboBox.setPrefWidth(200);

        // menu item name
        Label menuName = new Label("Menu Item Name: ");
        menuName.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
        menuName.setTextFill(Color.WHITE);
        menuName.setPadding(new Insets(0,0,0,32));

        TextField menuNameInput = new TextField();
        menuNameInput.setStyle("-fx-background-color: rgb(217, 174, 173, 0.5); -fx-text-fill: white;");

        // price
        Label price = new Label("Price: ");
        price.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
        price.setTextFill(Color.WHITE);
        price.setPadding(new Insets(0,0,0,32));

        TextField priceInput = new TextField();
        priceInput.setStyle("-fx-background-color: rgb(217, 174, 173, 0.5); -fx-text-fill: white;");

        // button
        Button addMenuButton = new Button("Add Menu Item");
        addMenuButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        addMenuButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        kembaliButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        HBox addMenuAndKembali = new HBox(addMenuButton, kembaliButton);
        addMenuAndKembali.setSpacing(10);
        addMenuAndKembali.setPadding(new Insets(10,10,10,10));

        // add layout
        layout.add(restoName, 0, 0);
        layout.add(restaurantComboBox, 0, 1);
        layout.add(menuName, 0, 2);
        layout.add(menuNameInput, 0, 3);
        layout.add(price, 0, 4);
        layout.add(priceInput, 0, 5);
        layout.add(addMenuAndKembali, 0, 6);
        layout.setAlignment(Pos.CENTER);

        Rectangle rectangle = CustomedRectangle.RectangleCustom(200, 300);
        StackPane pane = new StackPane(rectangle, layout);
        pane.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(layout, Pos.CENTER);

        // set border
        border.setCenter(pane);
        
        // set on action
        addMenuButton.setOnAction((ActionEvent event) -> {
            handleTambahMenuRestoran(restaurantComboBox.getValue(), menuNameInput.getText(), priceInput.getText());
            // refresh
            menuNameInput.clear();
            priceInput.clear();
            mainApp.setScene(addMenuScene);
            stage.show();
        });
        kembaliButton.setOnAction((ActionEvent event) -> {
            // refresh
            menuNameInput.clear();
            priceInput.clear();
            restaurantComboBox.setValue(null);
            // kembali ke admin menu
            mainApp.setScene(this.scene);
            stage.show();
        });

        return new Scene(border, 400, 600);
    }
    
    
    private Scene createViewRestaurantsForm() {
        // Implementasikan method ini untuk menampilkan page daftar restoran
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
        menuItemsComboBox.setStyle("-fx-background-color: rgb(217, 174, 173, 0.5); -fx-text-fill: white;");
        menuItemsComboBox.setPrefWidth(180);

        // button
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        searchButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        kembaliButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        HBox searchAndKembali = new HBox(searchButton, kembaliButton);
        searchAndKembali.setSpacing(10);
        searchAndKembali.setPadding(new Insets(10,10,10,25));

        // add layout
        layout.add(restoName, 0, 0);
        layout.add(menuItemsComboBox, 0, 1);
        layout.add(searchAndKembali, 0, 2);
        layout.setAlignment(Pos.CENTER);

        Rectangle rectangle = CustomedRectangle.RectangleCustom(300, 150);
        StackPane topPane = new StackPane(rectangle, layout);
        topPane.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(layout, Pos.CENTER);

        // add
        border.setTop(topPane);

        // set on action
        searchButton.setOnAction((ActionEvent event) -> {
            // pane of restaurant's menus
            StackPane centerPane = handleViewRestaurant(menuItemsComboBox.getValue());
            border.setCenter(centerPane); // set border
        });
        kembaliButton.setOnAction((ActionEvent event) -> {
            // refresh
            menuItemsComboBox.getItems().clear();
            border.setCenter(null);
            // kembali ke AdminMenu
            mainApp.setScene(this.scene);
            stage.show();
        });

        return new Scene(border, 400, 600);
    }
    private StackPane handleViewRestaurant(String restoName){
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);

        // menu 
        Label menuLabel = new Label("Menu:");
        menuLabel.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
        menuLabel.setTextFill(Color.WHITE);
        layout.add(menuLabel, 0, 0);

        // list menu
        Restaurant restaurant = getRestaurant(restoName);
        ArrayList<Menu> sortedMenu = restaurant.sortMenu();
        int rowIndex = 0;
        for(Menu menu : sortedMenu){
            Label menuPrinted = new Label(String.format("%s - Rp%s", menu.getNamaMakanan(), String.valueOf(menu.getHarga())));
            menuPrinted.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
            menuPrinted.setTextFill(Color.WHITE);

            Rectangle rectMenu = CustomedRectangle.RectangleCustom(250, 50);
            StackPane paneMenu = new StackPane(rectMenu, menuPrinted);
            StackPane.setAlignment(menuPrinted, Pos.CENTER);
            
            // add menu to layout
            layout.add(paneMenu, 0, ++rowIndex);
        }
        layout.setAlignment(Pos.CENTER);

        Rectangle rectangle = CustomedRectangle.RectangleCustom(300, 300);
        StackPane centerPane = new StackPane(rectangle, layout);
        centerPane.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(layout, Pos.CENTER);
        
        return centerPane;
    }
    
    private boolean validateRestaurantName (String nama) {
        boolean isRestaurantNameValid = false;
        boolean isRestaurantExist = false;
        boolean isRestaurantNameLengthValid = false;
        if(restoList != null){
            isRestaurantExist = restoList.stream() // restaurant exist
                    .anyMatch(restoran -> restoran.getNama().equalsIgnoreCase(nama));
        }
        if(nama.length() >= 4){
            isRestaurantNameLengthValid = true;
        }
        // restaurant exist
        if (isRestaurantExist) {
            showAlert("Alert", "Nama Restoran tidak valid!", "Restoran sudah terdaftar.", AlertType.ERROR);
        } 
        // length invalid
        else if (!isRestaurantNameLengthValid) {
            showAlert("Alert", "Nama Restoran tidak valid!", "Minimal 4 karakter diperlukan.", AlertType.ERROR);
        } 
        else {
            isRestaurantNameValid = true;
        }
        return isRestaurantNameValid;
    }

    private void handleTambahRestoran(String nama) {
        // Implementasi validasi isian nama Restoran
        boolean isValid = validateRestaurantName(nama);
        if (isValid == true){
            Restaurant restaurant = new Restaurant(nama);
            // add resto to restoList
            restoList.add(restaurant);
            // message
            showAlert("Message", "Nama Restoran valid!", "Berhasil menambahkan Restoran.", AlertType.INFORMATION);
            // add combobox
            restaurantComboBox.getItems().add(nama);
            menuItemsComboBox.getItems().add(nama);
        }
    }
    protected static Restaurant getRestaurant(String nama){
        for(Restaurant resto : restoList){
            if(nama.equalsIgnoreCase(resto.getNama())){
                return resto;
            }
        }
        return null;
    }

    private boolean validateTambahMenuRestoran(String restaurant, String itemName, String price) {
        boolean isValid = true;
        Restaurant restoran = getRestaurant(restaurant);
        
        // validasi nama menu
        for(Menu menu : restoran.getMenu()){
            if(itemName.equalsIgnoreCase(menu.getNamaMakanan())){
                showAlert("Alert", "Item Menu tidak valid!", "Nama Menu sudah terdaftar.", AlertType.WARNING);
                isValid = false;
            }
        }

        // validasi input harga
        boolean isDigit = AdminSystemCLI.checkIsDigit(price);
        if(isDigit == false){
            showAlert("Alert", "Item Menu tidak valid!", "Price harus berupa bilangan bulat positif", AlertType.WARNING);
            isValid = false;
        }
        return isValid;
    }

    private void handleTambahMenuRestoran(String restaurant, String itemName, String price) {
        // Implementasi validasi isian menu Restoran
        Restaurant restoran = getRestaurant(restaurant);
        boolean isValid = validateTambahMenuRestoran(restaurant, itemName, price);
        if(isValid == true){
            restoran.addMenu(new Menu(itemName, Double.parseDouble(price)));
            showAlert("Message", "Item Menu valid!", "Berhasil menambahkan menu.", AlertType.INFORMATION);
        }
    }

    @Override
    public Scene getScene(){
        return this.scene;
    }
}
