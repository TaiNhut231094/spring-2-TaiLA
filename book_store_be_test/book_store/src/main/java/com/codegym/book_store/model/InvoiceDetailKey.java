package com.codegym.book_store.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class InvoiceDetailKey implements Serializable {

    @Column(name = "invoice_id")
    Long invoiceId;

    @Column(name = "book_id")
    Long bookId;
}
