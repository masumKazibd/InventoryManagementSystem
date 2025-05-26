module com.inventorymanagementsystem.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.inventorymanagementsystem.inventorymanagementsystem to javafx.fxml;
    exports com.inventorymanagementsystem.inventorymanagementsystem;
}