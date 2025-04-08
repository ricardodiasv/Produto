module com.example.produto {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.produto to javafx.fxml;
    exports com.example.produto;
}