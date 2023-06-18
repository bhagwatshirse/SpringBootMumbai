package com.categoryproduct.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.categoryproduct.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	
}
