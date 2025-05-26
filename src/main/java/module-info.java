module com.inventorymanagementsystem.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.inventorymanagementsystem.inventorymanagementsystem.controller to javafx.fxml;
    // Open model package for TableView PropertyValueFactory if models use JavaFX properties
    opens com.inventorymanagementsystem.inventorymanagementsystem.model to javafx.base;

    exports com.inventorymanagementsystem.inventorymanagementsystem;
}