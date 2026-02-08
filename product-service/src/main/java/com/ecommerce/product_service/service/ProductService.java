package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.*;
import com.ecommerce.product_service.exception.ResourceNotFoundException;
import com.ecommerce.product_service.model.Category;
import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.model.ProductImage;
import com.ecommerce.product_service.repository.CategoryRepository;
import com.ecommerce.product_service.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findByIsActiveTrue(pageable)
                .map(this::toProductResponse);
    }
    
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return toProductResponse(product);
    }
    
    public Page<ProductResponse> searchProducts(String query, Pageable pageable) {
        return productRepository.searchProducts(query, pageable)
                .map(this::toProductResponse);
    }
    
    public Page<ProductResponse> getProductsByCategory(String categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable)
                .map(this::toProductResponse);
    }
    
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new RuntimeException("Product with SKU '" + request.getSku() + "' already exists");
        }
        
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
        
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(category);
        product.setCategoryId(request.getCategoryId());
        product.setSku(request.getSku());
        product.setIsActive(request.getIsActive());
        product.setSpecifications(request.getSpecifications() != null ? request.getSpecifications() : new java.util.HashMap<>());
        
        if (request.getImages() != null) {
            List<ProductImage> images = request.getImages().stream()
                    .map(img -> {
                        ProductImage productImage = new ProductImage();
                        productImage.setUrl(img.getUrl());
                        productImage.setIsPrimary(img.getIsPrimary());
                        return productImage;
                    })
                    .collect(Collectors.toList());
            product.setImages(images);
        }
        
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        
        product = productRepository.save(product);
        return toProductResponse(product);
    }
    
    @Transactional
    public ProductResponse updateProduct(String id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        if (request.getSku() != null && !request.getSku().equals(product.getSku())) {
            if (productRepository.existsBySku(request.getSku())) {
                throw new RuntimeException("Product with SKU '" + request.getSku() + "' already exists");
            }
            product.setSku(request.getSku());
        }
        
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
            product.setCategory(category);
            product.setCategoryId(request.getCategoryId());
        }
        if (request.getImages() != null) {
            List<ProductImage> images = request.getImages().stream()
                    .map(img -> {
                        ProductImage productImage = new ProductImage();
                        productImage.setUrl(img.getUrl());
                        productImage.setIsPrimary(img.getIsPrimary());
                        return productImage;
                    })
                    .collect(Collectors.toList());
            product.setImages(images);
        }
        if (request.getIsActive() != null) {
            product.setIsActive(request.getIsActive());
        }
        if (request.getSpecifications() != null) {
            product.setSpecifications(request.getSpecifications());
        }
        
        product.setUpdatedAt(LocalDateTime.now());
        product = productRepository.save(product);
        return toProductResponse(product);
    }
    
    @Transactional
    public void deleteProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
    
    @Transactional
    public ProductResponse updateStock(String id, StockUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        product.setStockQuantity(request.getStockQuantity());
        product.setUpdatedAt(LocalDateTime.now());
        product = productRepository.save(product);
        return toProductResponse(product);
    }
    
    private ProductResponse toProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setSku(product.getSku());
        response.setIsActive(product.getIsActive());
        response.setSpecifications(product.getSpecifications());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        
        if (product.getCategory() != null) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(product.getCategory().getId());
            categoryResponse.setName(product.getCategory().getName());
            categoryResponse.setDescription(product.getCategory().getDescription());
            categoryResponse.setParentId(product.getCategory().getParentId());
            categoryResponse.setCreatedAt(product.getCategory().getCreatedAt());
            response.setCategory(categoryResponse);
        }
        
        if (product.getImages() != null) {
            List<ProductImageResponse> imageResponses = product.getImages().stream()
                    .map(img -> {
                        ProductImageResponse imgResponse = new ProductImageResponse();
                        imgResponse.setUrl(img.getUrl());
                        imgResponse.setIsPrimary(img.getIsPrimary());
                        return imgResponse;
                    })
                    .collect(Collectors.toList());
            response.setImages(imageResponses);
        }
        
        return response;
    }
}
