package com.codegym.book_store.controller;

import com.codegym.book_store.dto.projections.BookProjection;
import com.codegym.book_store.model.Book;
import com.codegym.book_store.model.Category;
import com.codegym.book_store.repository.IBookRepository;
import com.codegym.book_store.service.IBookService;
import com.codegym.book_store.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("api/public/book")
@CrossOrigin(origins = "*")
@RestController
public class BookRestController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;

//    @GetMapping(value = "")
//    public ResponseEntity<List<Book>> getAllBook() {
//        List<Book> books = bookService.findAll();
//        if (books.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(books, HttpStatus.OK);
//    }

    @GetMapping(value = "/search")
    public ResponseEntity<Page<BookProjection>> goListBook(@PageableDefault(size = 8) Pageable pageable,
                                                           @RequestParam Optional<String> category,
                                                           @RequestParam Optional<String> title,
                                                           @RequestParam Optional<String> author) {
        String keyCategory = category.orElse("");
        String keyTitle = title.orElse("");
        String keyAuthor = author.orElse("");

        Page<BookProjection> books = bookService.findAllPaginationAndSearch(pageable, keyCategory, keyTitle, keyAuthor);
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping(value = "/category")
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> categories = categoryService.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id) {
        Book book = this.bookService.findById(id).orElse(null);

        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
