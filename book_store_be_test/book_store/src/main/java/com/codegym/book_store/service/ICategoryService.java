package com.codegym.book_store.service;

import com.codegym.book_store.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
}
