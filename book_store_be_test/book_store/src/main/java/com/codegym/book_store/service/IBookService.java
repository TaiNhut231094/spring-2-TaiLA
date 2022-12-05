package com.codegym.book_store.service;

import com.codegym.book_store.dto.projections.BookProjection;
import com.codegym.book_store.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookService {
    List<Book> findAll();

    Page<BookProjection> findAllPaginationAndSearch(Pageable pageable, String category, String title, String author);
}
