package com.codegym.book_store.service.impl;

import com.codegym.book_store.dto.projections.BookProjection;
import com.codegym.book_store.model.Book;
import com.codegym.book_store.repository.IBookRepository;
import com.codegym.book_store.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {

    @Autowired
    private IBookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<BookProjection> findAllPaginationAndSearch(Pageable pageable, String category, String title, String author) {
        return bookRepository.findAllPaginationSearch(pageable, category, title, author);
    }
}
