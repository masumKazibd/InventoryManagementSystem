package com.inventorymanagementsystem.inventorymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class StockController {
    @FXML private TableView<?> stockTable;
    @FXML private ComboBox<?> productComboBox;
    @FXML private TextField transactionIdField;
    @FXML private TextField typeField;
    @FXML private TextField transactionQuantityField;
    @FXML private DatePicker datePicker;
    @FXML private TextField notesField;

    @FXML
    private void handleStockIn() {
        System.out.println("Stock In clicked");
    }

    @FXML
    private void handleStockOut() {
        System.out.println("Stock Out clicked");
    }

    @FXML
    private void handleSaveTransaction() {
        System.out.println("Save Transaction clicked");
    }

    @FXML
    private void handleCancelTransaction() {
        System.out.println("Cancel Transaction clicked");
    }
}