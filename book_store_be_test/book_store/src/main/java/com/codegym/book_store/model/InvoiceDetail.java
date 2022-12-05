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
@Table(name = "invoice_detail")
public class InvoiceDetail {
    @EmbeddedId
    private InvoiceDetailKey id;

    private Integer quantity;

    @ManyToOne
    @MapsId("book_id")
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private Book book;

    @ManyToOne
    @MapsId("invoice_id")
    @JoinColumn(name = "invoice_id")
    @JsonBackReference
    private Invoice invoice;
}
