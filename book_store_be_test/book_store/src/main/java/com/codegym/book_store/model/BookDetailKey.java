package com.codegym.book_store.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BookDetailKey implements Serializable {

    @Column(name = "category_id")
    Long categoryId;

    @Column(name = "book_id")
    Long bookId;
}
