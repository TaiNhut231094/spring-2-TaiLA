package com.codegym.book_store.service;

import com.codegym.book_store.dto.projections.BookProjection;
import com.codegym.book_store.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    List<Book> findAll();

    Page<BookProjection> findAllPaginationAndSearch(Pageable pageable, String category, String title, String author);

    Optional<Book> findById(Long id);
}
