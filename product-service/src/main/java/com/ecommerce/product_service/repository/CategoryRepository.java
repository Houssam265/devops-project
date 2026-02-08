package com.ecommerce.product_service.repository;

import com.ecommerce.product_service.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByName(String name);
    List<Category> findByParentId(String parentId);
    List<Category> findByParentIdIsNull();
    boolean existsByName(String name);
}
