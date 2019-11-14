package com.market.purchase.repositories;

import com.market.purchase.documents.HistoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryRepository extends MongoRepository<HistoryDocument, String> {
    Optional<HistoryDocument> findByLockId(String lockId);
}
