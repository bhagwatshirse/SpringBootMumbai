package com.categoryproduct.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
import com.categoryproduct.repository.CategoryRepository;
import com.categoryproduct.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable ("id") Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @RequestBody Category updatedCategory) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            updatedCategory.setId(id);
            categoryRepository.save(updatedCategory);
            return ResponseEntity.ok(updatedCategory);
        }
        return ResponseEntity.notFound().build();
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
//    @GetMapping("/page/{page}")
//    public ResponseEntity<Page<Category>> getAllCategories(@RequestParam int page,
//                                                           @RequestParam int size) {
//        Pageable pageable = PageRequest.of(page, 3);
//        Page<Category> categoryPage = ((CategoryService) categoryRepository).getAllCategories(pageable);
//        return ResponseEntity.ok(categoryPage);
//    }
    @GetMapping("/allCat/{page}")
    public ResponseEntity<List<Category>> categoryByPage(@PathVariable Integer page, @RequestParam(defaultValue = "3") Integer size) {
    	 // Limit the maximum page number to 3
        if (page > 3) {
            page = 3;
        }
        Page<Category> categoryPage = categoryService.getCategoryByPage(page, size);
        List<Category> categoryList = categoryPage.getContent();
        if (categoryList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryList);
    }

    }
