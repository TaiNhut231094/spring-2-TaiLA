package com.codegym.book_store.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_detail")
public class BookDetail {

    @EmbeddedId
    private BookDetailKey id;

    @ManyToOne
    @MapsId("book_id")
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private Book book;

    @ManyToOne
    @MapsId("category_id")
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;
}
