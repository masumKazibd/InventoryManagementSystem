package com.inventorymanagementsystem.inventorymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

public class ReportController {
    @FXML private TableView<?> reportTable;
    @FXML private DatePicker fromDatePicker;
    @FXML private DatePicker toDatePicker;

    @FXML
    private void handleGenerateReport() {
        System.out.println("Generate Report clicked");
    }

    @FXML
    private void handleExportToCSV() {
        System.out.println("Export to CSV clicked");
    }
}