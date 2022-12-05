package com.codegym.book_store.repository;

import com.codegym.book_store.dto.projections.BookProjection;
import com.codegym.book_store.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IBookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select b.id, b.title, b.number_of_pages as numberOfPages, b.size, b.summary, " +
            " b.release_date as releaseDate, b.image_url as imageUrl, b.price, b.quantity, a.name " +
            " as author, p.name as publishingCompany, t.name as translator from book_detail bd " +
            " join book b on bd.book_id = b.id " +
            " join category c on bd.category_id = c.id " +
            " join author a on b.author_id = a.id " +
            " join publishing_company p on b.publishing_company_id = p.id " +
            " join translator t on b.translator_id = t.id " +
            " where c.name like %:category% and b.title like %:title% and a.name like %:author% " +
            " group by b.id", nativeQuery = true,
    countQuery = "select count(*) from (" +
            "            select b.id, b.title, b.number_of_pages as numberOfPages, b.size, b.summary, " +
            "            b.release_date as releaseDate, b.image_url as imageUrl, b.price, b.quantity, a.name " +
            "            as author, p.name as publishingCompany, t.name as translator from book_detail bd " +
            "            join book b on bd.book_id = b.id " +
            "            join category c on bd.category_id = c.id " +
            "            join author a on b.author_id = a.id " +
            "            join publishing_company p on b.publishing_company_id = p.id " +
            "            join translator t on b.translator_id = t.id " +
            "            where c.name like %:category% and b.title like %:title% and a.name like %:author% " +
            "            group by b.id) books")
    Page<BookProjection> findAllPaginationSearch(Pageable pageable, @Param("category") String category, @Param("title") String title, @Param("author") String author);
}