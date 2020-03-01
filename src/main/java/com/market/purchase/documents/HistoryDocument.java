package com.market.purchase.documents;

import com.market.purchase.model.ProductLock;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "history")
public class HistoryDocument {
    @Id
    private String id;
    private String customerId;
    private String productId;
    private String lockId;
    private Integer quantity;
    private OrderStatus orderStatus;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private HistoryDocument(ProductLock productLock) {
        this.customerId = "to_be_implemented";
        this.productId = productLock.getIdProduct();
        this.lockId = productLock.getLockId();
        this.quantity = productLock.getQuantity();
        this.orderStatus = OrderStatus.valueOf(productLock.getOrderStatus().toString());
    }

    public static HistoryDocument build(ProductLock productLock) {
        return new HistoryDocument(productLock);
    }

    public enum OrderStatus {
        PENDING,
        PROCESSING,
        PAYMENT_NOT_AUTHORIZED,
        FINISHED
    }

    public String getId() {
        return id;
    }

    public HistoryDocument setId(String id) {
        this.id = id;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public HistoryDocument setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public HistoryDocument setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getLockId() {
        return lockId;
    }

    public HistoryDocument setLockId(String lockId) {
        this.lockId = lockId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public HistoryDocument setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public HistoryDocument setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public HistoryDocument setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public HistoryDocument setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }
}