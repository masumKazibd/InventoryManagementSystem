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
}
