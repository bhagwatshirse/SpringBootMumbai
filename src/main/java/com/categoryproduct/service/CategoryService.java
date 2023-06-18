package com.categoryproduct.service;


import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.categoryproduct.entity.Category;
import com.categoryproduct.entity.Product;
import com.categoryproduct.repository.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> updateCategory(Long id, Category updatedCategory) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            updatedCategory.setId(id);
            return Optional.of(categoryRepository.save(updatedCategory));
        }
        return Optional.empty();
    }

    public boolean deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
//    public Page<Category> getAllCategories(Pageable pageable) {
//        return categoryRepository.findAll(pageable);
//    }
    
    public Page<Category> getCategoryByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(pageable);
    }
  
}
