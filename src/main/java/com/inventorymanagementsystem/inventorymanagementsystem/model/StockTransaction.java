package com.inventorymanagementsystem.inventorymanagementsystem.model;

import java.time.LocalDate;

public class StockTransaction {    private int productId;
    public enum TransactionType {
        IN, OUT
    }

    private String transactionId;
    private Product product;
    private TransactionType transactionType;
    private int quantity;
    private LocalDate transactionDate;
    private String notes;
    private int stockLevelAfterTransaction;

    public StockTransaction() {
    }

    public StockTransaction(String transactionId, Product product, TransactionType transactionType,
                            int quantity, LocalDate transactionDate, String notes, int stockLevelAfterTransaction) {
        this.transactionId = transactionId;
        this.product = product;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
        this.notes = notes;
        this.stockLevelAfterTransaction = stockLevelAfterTransaction;
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getStockLevelAfterTransaction() {
        return stockLevelAfterTransaction;
    }

    public void setStockLevelAfterTransaction(int stockLevelAfterTransaction) {
        this.stockLevelAfterTransaction = stockLevelAfterTransaction;
    }

    @Override
    public String toString() {
        return transactionType + " " + quantity + " units of " + product.getName() + " on " + transactionDate;
    }
}
