package ru.pavel2107.otus.hw10.repository.datajpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pavel2107.otus.hw10.domain.Book;

import java.util.List;


public interface BookRepository extends CrudRepository<Book, Long> {
    Book findByIsbn( String Isbn);
    List<Book> findBookByName( String name);
    List<Book> findBookByAuthorId( Long id);
    List<Book> findBookByGenreId( Long id);
}
