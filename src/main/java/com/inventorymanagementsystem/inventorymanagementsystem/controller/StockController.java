package com.inventorymanagementsystem.inventorymanagementsystem.controller;

import com.inventorymanagementsystem.inventorymanagementsystem.model.Product;
import com.inventorymanagementsystem.inventorymanagementsystem.model.Transaction;
import com.inventorymanagementsystem.inventorymanagementsystem.utils.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class StockController {
    @FXML private TableView<Transaction> stockTable;
    @FXML private TableColumn<Transaction, Integer> idColumn;
    @FXML private TableColumn<Transaction, String> productColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, Integer> quantityColumn;
    @FXML private TableColumn<Transaction, String> dateColumn;
    @FXML private TableColumn<Transaction, String> notesColumn;

    @FXML private TextField transactionIdField;
    @FXML private ComboBox<Product> productComboBox;
    @FXML private TextField typeField;
    @FXML private TextField transactionQuantityField;
    @FXML private DatePicker datePicker;
    @FXML private TextField notesField;

    private String currentType = "";

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        productColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProductName()));
        typeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTransactionType()));
        quantityColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        dateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDate().toString()));
        notesColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNotes()));

        loadProducts();
        loadTransactions();
    }
    @FXML
    private void handleStockIn() {
        currentType = "IN";
        typeField.setText("IN");
    }

    @FXML
    private void handleStockOut() {
        currentType = "OUT";
        typeField.setText("OUT");
    }
    @FXML
    private void handleRefresh() {
        loadProducts();
        showAlert(Alert.AlertType.INFORMATION, "Refreshed", "Product list refreshed successfully!");
    }


    @FXML
    private void handleSaveTransaction() {
        Product selectedProduct = productComboBox.getValue();
        if (selectedProduct == null || currentType.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please select a product and type.");
            return;
        }

        try {
            int quantity = Integer.parseInt(transactionQuantityField.getText());
            LocalDate date = datePicker.getValue();
            String notes = notesField.getText();

            String sql = "INSERT INTO transactions (product_id, type, quantity, date, notes) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, selectedProduct.getId());
                stmt.setString(2, currentType);
                stmt.setInt(3, quantity);
                stmt.setDate(4, Date.valueOf(date));
                stmt.setString(5, notes);

                stmt.executeUpdate();
            }

            // Update product quantity
            int updatedQty = currentType.equals("IN") ? selectedProduct.getQuantity() + quantity : selectedProduct.getQuantity() - quantity;
            String updateProductSql = "UPDATE products SET quantity = ? WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(updateProductSql)) {
                stmt.setInt(1, updatedQty);
                stmt.setInt(2, selectedProduct.getId());
                stmt.executeUpdate();
            }

            showAlert(Alert.AlertType.INFORMATION, "Success", "Transaction saved!");
            loadTransactions();
            clearForm();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Please enter a valid number for quantity.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save transaction.");
        }
    }

    private void loadTransactions() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        String sql = "SELECT t.id, p.name AS product_name, t.type, t.quantity, t.date, t.notes FROM transactions t JOIN products p ON t.product_id = p.id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("id"),
                        rs.getString("product_name"),
                        rs.getString("type"),
                        rs.getInt("quantity"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("notes")
                ));
            }
            stockTable.setItems(transactions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelTransaction() {
        clearForm();
    }
    private void clearForm() {
        transactionIdField.clear();
        productComboBox.setValue(null);
        typeField.clear();
        transactionQuantityField.clear();
        datePicker.setValue(null);
        notesField.clear();
        currentType = "";
    }

    void loadProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        String sql = "SELECT * FROM products";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
            productComboBox.setItems(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}