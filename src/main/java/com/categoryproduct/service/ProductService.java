package com.categoryproduct.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.categoryproduct.entity.Category;
import com.categoryproduct.entity.Product;
import com.categoryproduct.repository.ProductRepository;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            updatedProduct.setId(id);
            return Optional.of(productRepository.save(updatedProduct));
        }
        return Optional.empty();
    }

    public boolean deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
//    public Page<Product> getAllProduct(Pageable pageable) {
//        return productRepository.findAll(pageable);
//    	
//    	
//    	
//    }


    public Page<Product> getProductByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

}
	
	    