package com.ebay.candidates.productprocessor.repository;

import com.ebay.candidates.productprocessor.entity.ProductDo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductDo, Long> {
  ProductDo getById(Long id);
}
