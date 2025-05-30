package com.inventorymanagementsystem.inventorymanagementsystem.controller;

import com.inventorymanagementsystem.inventorymanagementsystem.model.Product;
import com.inventorymanagementsystem.inventorymanagementsystem.utils.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductController {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;

    @FXML
    private void handleAddProduct() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String price = priceField.getText();
        String quantity = quantityField.getText();

        String sql = "INSERT INTO products (name, description, price, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, Double.parseDouble(price));
            stmt.setInt(4, Integer.parseInt(quantity));

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product added successfully!");
            }

            nameField.clear();
            descriptionField.clear();
            priceField.clear();
            quantityField.clear();
            initialize();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add product.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void  initialize(){
        // Map table columns to Product fields
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        descriptionColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescription()));
        priceColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getUnitPrice()));
        quantityColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        // Load products into table
        loadProducts();
        productTable.setOnMouseClicked(event -> {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                idField.setText(String.valueOf(selectedProduct.getId()));
                nameField.setText(selectedProduct.getName());
                descriptionField.setText(selectedProduct.getDescription());
                priceField.setText(String.valueOf(selectedProduct.getUnitPrice()));
                quantityField.setText(String.valueOf(selectedProduct.getQuantity()));
            }
        });
    }
    @FXML
    private void handleEditProduct() {
        String idText = idField.getText();
        String name = nameField.getText();
        String description = descriptionField.getText();
        String priceText = priceField.getText();
        String quantityText = quantityField.getText();

        if (idText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a product to edit.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);

            String sql = "UPDATE products SET name=?, description=?, price=?, quantity=? WHERE id=?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, name);
                stmt.setString(2, description);
                stmt.setDouble(3, price);
                stmt.setInt(4, quantity);
                stmt.setInt(5, id);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Product updated successfully!");
                    loadProducts();
                }

                // Clear the form
                handleCancelProduct();
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid numbers for price and quantity.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update product.");
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a product to delete.");
            return;
        }

        // Optional: Confirm deletion
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this product?");
        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        int productId = selectedProduct.getId();
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product deleted successfully!");
                loadProducts();
                handleCancelProduct();  // Clear form
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete product.");
        }
    }


    @FXML
    private void handleSaveProduct() {
        System.out.println("Save Product clicked");
    }

    @FXML
    private void handleCancelProduct() {
        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        quantityField.clear();
        System.out.println("🔄 Product form cleared.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void loadProducts() {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                productList.add(product);
            }

            productTable.setItems(productList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}