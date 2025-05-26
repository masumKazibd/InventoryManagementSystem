package com.inventorymanagementsystem.inventorymanagementsystem.service;

public class InventoryService {
//    private ObservableList<Product> products = FXCollections.observableArrayList();
//    private ObservableList<StockTransaction> transactions = FXCollections.observableArrayList();
//
//    private int nextProductId = 1;
//    private int nextTransactionId = 1;
//
//    public InventoryService() {
//        // Initialize with some sample data
//        addProduct(new Product(0, "Laptop", "High performance laptop", 999.99, 10));
//        addProduct(new Product(0, "Mouse", "Wireless mouse", 19.99, 50));
//
//        addTransaction(new StockTransaction(0, 1, "Laptop", 5, "IN", LocalDate.now().minusDays(3), "Initial stock"));
//        addTransaction(new StockTransaction(0, 2, "Mouse", 50, "IN", LocalDate.now().minusDays(2), "Initial stock"));
//    }
//
//    // Product methods
//    public ObservableList<Product> getProducts() {
//        return products;
//    }
//
//    public void addProduct(Product product) {
//        product.setId(nextProductId++);
//        products.add(product);
//    }
//
//    public void updateProduct(Product updatedProduct) {
//        for (int i = 0; i < products.size(); i++) {
//            if (products.get(i).getId() == updatedProduct.getId()) {
//                products.set(i, updatedProduct);
//                break;
//            }
//        }
//    }
//
//    public void deleteProduct(int productId) {
//        products.removeIf(product -> product.getId() == productId);
//    }
//
//    // Stock transaction methods
//    public ObservableList<StockTransaction> getTransactions() {
//        return transactions;
//    }
//
//    public void addTransaction(StockTransaction transaction) {
//        transaction.setId(nextTransactionId++);
//        transactions.add(transaction);
//
//        // Update product quantity
//        Product product = getProductById(transaction.getProductId());
//        if (product != null) {
//            if ("IN".equals(transaction.getTransactionType())) {
//                product.setQuantity(product.getQuantity() + transaction.getQuantity());
//            } else if ("OUT".equals(transaction.getTransactionType())) {
//                product.setQuantity(product.getQuantity() - transaction.getQuantity());
//            }
//        }
//    }
//
//    public Product getProductById(int productId) {
//        return products.stream()
//                .filter(p -> p.getId() == productId)
//                .findFirst()
//                .orElse(null);
//    }
//
//    public ObservableList<StockTransaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
//        return FXCollections.observableArrayList(
//                transactions.stream()
//                        .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
//                        .collect(Collectors.toList())
//        );
//    }
}