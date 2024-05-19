package assignments.assignment4.components.form;

import assignments.assignment3.MainMenu;
import assignments.assignment3.assignment2.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.InterfacePack.CustomedRectangle;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;

public class LoginForm {
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameInput;
    private TextField phoneInput;

    public LoginForm(Stage stage, MainApp mainApp) { // Pass MainApp instance to constructor
        this.stage = stage;
        this.mainApp = mainApp; // Store MainApp instance
    }

    private Scene createLoginForm() {
        // Implementasi method untuk menampilkan komponen form login
        BorderPane border = new BorderPane();

        // welcome
        StackPane pane = new StackPane();
        Text welcome = new Text("Welcome to Depe Food");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        welcome.setFill(Color.WHITE);
        
        Rectangle rect = CustomedRectangle.RectangleCustom(300, 100);
        pane.getChildren().addAll(rect, welcome);
        pane.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(welcome, Pos.CENTER);

        // input 
        Label nameLabel = new Label("Name:");
        nameInput = new TextField();
        nameLabel.setTextFill(Color.WHITE);
        nameInput.setStyle("-fx-background-color: rgb(217, 174, 173, 0.5); -fx-text-fill: white;");

        Label phoneNumberLabel = new Label("Phone Number:");
        phoneInput = new TextField();
        phoneNumberLabel.setTextFill(Color.WHITE);
        phoneInput.setStyle("-fx-background-color: rgb(217, 174, 173, 0.5); -fx-text-fill: white;");

        // button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // add grid
        grid.add(nameLabel, 0, 0);
        grid.add(nameInput, 0, 1);
        grid.add(phoneNumberLabel, 0, 2);
        grid.add(phoneInput, 0,3);
        grid.add(loginButton, 0, 4);
        grid.setAlignment(Pos.CENTER);

        Rectangle rectInput = CustomedRectangle.RectangleCustom(300, 300);
        StackPane paneInput = new StackPane(rectInput, grid);
        paneInput.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(grid, Pos.CENTER);

        // add border
        border.setTop(pane);
        border.setCenter(paneInput);

        // set on action
        loginButton.setOnAction((ActionEvent event) -> {
            // login
            handleLogin();
            // refresh
            nameInput.clear();
            phoneInput.clear();
        });
        return new Scene(border, 400, 600);
    }

    private void handleLogin() {
        // Implementasi validasi isian form login

        // userLoggedIn
        User userLoggedIn = MainMenu.getUser(nameInput.getText(), phoneInput.getText());
        if (userLoggedIn == null) {
            showAlert("Alert", "User Tidak Ditemukan!", "Input kembali Nama User dan Nomor Telepon.", AlertType.WARNING);
        } 
        else {
            // AdminMenu
            if(userLoggedIn.role.equals("Admin")){
                Scene adminMenu = new AdminMenu(stage, mainApp, userLoggedIn).getScene();
                mainApp.addScene("AdminMenu", adminMenu);
                mainApp.setScene(adminMenu);
                stage.show();
            }
            // CustomerMenu
            else{
                Scene customerMenu = new CustomerMenu(stage, mainApp, userLoggedIn).getScene();
                mainApp.addScene("CustomerMenu", customerMenu);
                mainApp.setScene(customerMenu);
                stage.show();
            }
            mainApp.setUser(userLoggedIn);
        }
    }

    protected void showAlert(String title, String header, String content, Alert.AlertType c){
        Alert alert = new Alert(c);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Scene getScene(){
        return this.createLoginForm();
    }

}
