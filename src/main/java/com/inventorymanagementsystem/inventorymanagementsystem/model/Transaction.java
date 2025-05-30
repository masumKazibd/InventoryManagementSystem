package com.inventorymanagementsystem.inventorymanagementsystem.model;

import java.time.LocalDate;

public class Transaction {
    private int id;
    private String productName;
    private String transactionType;
    private int quantity;
    private LocalDate date;
    private String notes;

    public Transaction(int id, String productName, String transactionType, int quantity, LocalDate date, String notes) {
        this.id = id;
        this.productName = productName;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.date = date;
        this.notes = notes;
    }

    public int getId() { return id; }
    public String getProductName() { return productName; }
    public String getTransactionType() { return transactionType; }
    public int getQuantity() { return quantity; }
    public LocalDate getDate() { return date; }
    public String getNotes() { return notes; }
}
