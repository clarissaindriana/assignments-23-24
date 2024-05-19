package assignments.assignment4.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import assignments.assignment3.assignment2.*;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.InterfacePack.CustomedRectangle;

public class BillPrinter {
    private Stage stage;
    private MainApp mainApp;
    private User user;

    public BillPrinter(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
    }

    private Scene createBillPrinterForm(Order order){
        // Implementasi untuk menampilkan komponen hasil cetak bill
         BorderPane border = new BorderPane();

        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        
        // output bill
        Label outputBill = new Label(outputBillPesanan(order));
        outputBill.setFont((Font.font("Arial", FontWeight.BOLD, 12)));
        outputBill.setTextFill(Color.WHITE);

        // button
        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle("-fx-background-color: #A87676; -fx-text-fill: white;");
        kembaliButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        HBox button = new HBox(kembaliButton);
        button.setAlignment(Pos.CENTER);

        // add grid
        layout.add(outputBill, 0, 0);
        layout.add(button, 0, 1);
        layout.setAlignment(Pos.CENTER);

        Rectangle rectangle = CustomedRectangle.RectangleCustom(300, 300);
        StackPane pane = new StackPane(rectangle, layout);
        pane.setPadding(new Insets(20,20,20,20));
        StackPane.setAlignment(layout, Pos.CENTER);

        // add border
        border.setCenter(pane);

        // set on action
        kembaliButton.setOnAction((ActionEvent event) -> {
            // kembali ke Scene CustomerMenu
            mainApp.setScene(mainApp.getScene("CustomerMenu"));
            stage.show();
        });
        return new Scene(border, 400, 600);
    }

    private String outputBillPesanan(Order order) {
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
                            this.user.getLokasi(),
                            !order.getOrderFinished()? "Not Finished":"Finished",
                            CustomerSystemCLI.getMenuPesananOutput(order),
                            decimalFormat.format(order.getOngkir()),
                            decimalFormat.format(order.getTotalHarga())
                            );
    }

    public Scene getScene(Order order) {
        return this.createBillPrinterForm(order);
    }

    // Class ini opsional
    public class MenuItem {
        private final StringProperty itemName;
        private final StringProperty price;

        public MenuItem(String itemName, String price) {
            this.itemName = new SimpleStringProperty(itemName);
            this.price = new SimpleStringProperty(price);
        }

        public StringProperty itemNameProperty() {
            return itemName;
        }

        public StringProperty priceProperty() {
            return price;
        }

        public String getItemName() {
            return itemName.get();
        }

        public String getPrice() {
            return price.get();
        }
    }
}
