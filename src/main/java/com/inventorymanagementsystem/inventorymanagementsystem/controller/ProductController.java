package com.inventorymanagementsystem.inventorymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.awt.*;

public class ProductController {
    @FXML private TableView<?> productTable;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField descriptionField;
    @FXML private TextField priceField;
    @FXML private TextField quantityField;

    @FXML
    private void handleAddProduct() {
        System.out.println("Add Product clicked");
    }

    @FXML
    private void handleEditProduct() {
        System.out.println("Edit Product clicked");
    }

    @FXML
    private void handleDeleteProduct() {
        System.out.println("Delete Product clicked");
    }

    @FXML
    private void handleSaveProduct() {
        System.out.println("Save Product clicked");
    }

    @FXML
    private void handleCancelProduct() {
        System.out.println("Cancel Product clicked");
    }
}