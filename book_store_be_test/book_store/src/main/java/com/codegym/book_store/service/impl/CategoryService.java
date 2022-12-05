package com.codegym.book_store.service.impl;

import com.codegym.book_store.model.Category;
import com.codegym.book_store.repository.ICategoryRepository;
import com.codegym.book_store.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
