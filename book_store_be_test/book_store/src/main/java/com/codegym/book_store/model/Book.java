package com.codegym.book_store.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer numberOfPages;

    private String size;

    @Column(columnDefinition = "text")
    private String summary;

    private LocalDate releaseDate;

    @Column(columnDefinition = "text")
    private String imageUrl;

    private Double price;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonBackReference
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publishing_company_id", referencedColumnName = "id")
    @JsonBackReference
    private PublishingCompany publishingCompany;

    @ManyToOne
    @JoinColumn(name = "translator_id", referencedColumnName = "id")
    @JsonBackReference
    private Translator translator;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<InvoiceDetail> invoiceDetailSet;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<BookDetail> bookDetailSet;
}

