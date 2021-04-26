package com.springboot.api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.api.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	



}
