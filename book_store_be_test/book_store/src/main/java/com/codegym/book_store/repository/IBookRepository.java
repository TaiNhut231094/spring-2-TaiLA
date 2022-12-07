package com.codegym.book_store.repository;

import com.codegym.book_store.dto.projections.BookProjection;
import com.codegym.book_store.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface IBookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select b.id, b.title, b.number_of_pages as numberOfPages, b.size, b.summary, " +
            "            b.release_date as releaseDate, b.image_url as imageUrl, b.price, b.quantity, a.name " +
            "            as author, p.name as publishingCompany, t.name as translator from book b " +
            "            join category c on b.category_id = c.id " +
            "            join author a on b.author_id = a.id " +
            "            join publishing_company p on b.publishing_company_id = p.id " +
            "            join translator t on b.translator_id = t.id " +
            "            where c.name like %:category% and b.title like %:title% and a.name like %:author%", nativeQuery = true,
    countQuery = "select count(*) from (" +
            "            select b.id, b.title, b.number_of_pages as numberOfPages, b.size, b.summary, " +
            "            b.release_date as releaseDate, b.image_url as imageUrl, b.price, b.quantity, a.name " +
            "            as author, p.name as publishingCompany, t.name as translator from book b " +
            "            join category c on b.category_id = c.id " +
            "            join author a on b.author_id = a.id " +
            "            join publishing_company p on b.publishing_company_id = p.id " +
            "            join translator t on b.translator_id = t.id " +
            "            where c.name like %:category% and b.title like %:title% and a.name like %:author%) books")
    Page<BookProjection> findAllPaginationSearch(Pageable pageable, @Param("category") String category, @Param("title") String title, @Param("author") String author);

//    @Query(value = "select * from book where id = :id", nativeQuery = true)
//    Optional<Book> findById(@Param("id") Long id);
}