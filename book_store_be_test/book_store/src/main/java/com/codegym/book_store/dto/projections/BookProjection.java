package com.codegym.book_store.dto.projections;

import java.time.LocalDate;

public interface BookProjection {
    Long getId();

    String getTitle();

    Integer getNumberOfPages();

    String getSize();

    String getSummary();

    LocalDate getReleaseDate();

    String getImageUrl();

    Double getPrice();

    Integer getQuantity();

    String getAuthor();

    String getPublishingCompany();

    String getTranslator();

    String getCategory();
}
