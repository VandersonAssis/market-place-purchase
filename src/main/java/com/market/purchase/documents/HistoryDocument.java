package com.market.purchase.documents;

import com.market.purchase.model.ProductLock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}