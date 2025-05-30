package com.inventorymanagementsystem.inventorymanagementsystem.controller;

import com.inventorymanagementsystem.inventorymanagementsystem.model.Transaction;
import com.inventorymanagementsystem.inventorymanagementsystem.utils.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class ReportController {
    @FXML private DatePicker fromDatePicker;
    @FXML private DatePicker toDatePicker;
    @FXML private TableView<Transaction> reportTable;
    @FXML private TableColumn<Transaction, Integer> idColumn;
    @FXML private TableColumn<Transaction, String> productColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, Integer> quantityColumn;
    @FXML private TableColumn<Transaction, LocalDate> dateColumn;
    @FXML private TableColumn<Transaction, String> notesColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        productColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProductName()));
        typeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTransactionType()));
        quantityColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        dateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDate()));
        notesColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNotes()));

    }
    @FXML
    private void handleGenerateReport() {
        LocalDate from = fromDatePicker.getValue();
        LocalDate to = toDatePicker.getValue();

        if (from == null || to == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a valid date range.");
            return;
        }

        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        String sql = "SELECT t.id, p.name AS product_name, t.type, t.quantity, t.date, t.notes " +
                "FROM transactions t JOIN products p ON t.product_id = p.id " +
                "WHERE t.date BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(from));
            stmt.setDate(2, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();

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

            reportTable.setItems(transactions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExportToCSV() {
        ObservableList<Transaction> transactions = reportTable.getItems();
        if (transactions.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No data to export.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(reportTable.getScene().getWindow());

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                // Write header
                writer.println("ID,Product,Type,Quantity,Date,Notes");

                // Write data
                for (Transaction t : transactions) {
                    String line = String.format("%d,%s,%s,%d,%s,%s",
                            t.getId(),
                            t.getProductName().replaceAll(",", ""), // Avoid CSV errors
                            t.getTransactionType(),
                            t.getQuantity(),
                            t.getDate(),
                            t.getNotes().replaceAll(",", ""));
                    writer.println(line);
                }

                showAlert(Alert.AlertType.INFORMATION, "CSV file exported successfully!");

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Failed to export CSV file.");
            }
        }
    }



    private void showAlert(Alert.AlertType type, String content) {
        Alert alert = new Alert(type);
        alert.setContentText(content);
        alert.showAndWait();
    }


}