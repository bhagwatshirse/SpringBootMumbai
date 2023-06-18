package com.categoryproduct.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.categoryproduct.entity.Category;
import com.categoryproduct.entity.Product;
import com.categoryproduct.repository.ProductRepository;
import com.categoryproduct.service.CategoryService;
import com.categoryproduct.service.ProductService;



@RestController
@RequestMapping("/api/products")
public class ProductController {
	
    private final ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product updatedProduct) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            updatedProduct.setId(id);
            productRepository.save(updatedProduct);
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
//    @GetMapping("/page/{page}")
//    public ResponseEntity<Page<Product>> getAllProduct(@RequestParam int page,
//                                                           @RequestParam int size) {
//        Pageable pageable = PageRequest.of(page, 2);
//        Page<Product> productPage =((ProductService) productRepository).getAllProduct(pageable);
//         return ResponseEntity.ok(productPage);
//       
//    }

   
    @GetMapping("/allPro/{page}")
    public ResponseEntity<List<Product>> productByPage(@PathVariable Integer page, @RequestParam(defaultValue = "3") Integer size) {
    	 // Limit the maximum page number to 2
        if (page > 2) {
            page = 2;
        }
        Page<Product> productPage = productService.getProductByPage(page, size);
        List<Product> productList = productPage.getContent();
        if (productList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productList);
       
}

    
}