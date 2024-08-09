package com.jettodz.java_book_crud.repositories;

import com.jettodz.java_book_crud.dtos.BookRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jettodz.java_book_crud.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE b.id = ?1")
    Book findBookById(Integer id);

}
