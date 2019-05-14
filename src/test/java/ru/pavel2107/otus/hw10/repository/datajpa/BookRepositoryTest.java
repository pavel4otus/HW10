package ru.pavel2107.otus.hw10.repository.datajpa;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pavel2107.otus.hw10.domain.Author;
import ru.pavel2107.otus.hw10.domain.Book;
import ru.pavel2107.otus.hw10.domain.Genre;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DisplayName( "Репозиторий книг")
@SpringBootTest
public class BookRepositoryTest {

    @Autowired private BookRepository   repository;
    @Autowired private GenreRepository  genreRepository;
    @Autowired private AuthorRepository authorRepository;


    static Genre  genre ;
    static Author author ;
    static Book   book ;

    @Before
    public  void beforeTests(){
        if( genre != null){
            return;
        }
        genre = new Genre();
        author = new Author();
        book = new Book();

        genre.setName( "FANTASY-TEST");
        genre = genreRepository.save( genre);

        author.setName( "KING-TEST");
        author = authorRepository.save( author);

        book.setName( "test");
        book.setIsbn( "123");
        book.setPublicationPlace( "place");
        book.setPublishingHouse( "hos");
        book.setPublicationYear(1200);

        book.setAuthor( author);
        book.setGenre( genre);

        book = repository.save( book);
    }


    @Test
    public void findByIsbn() {
        Book b = repository.findByIsbn( "123");
        assertEquals( "123", b.getIsbn());
    }

    @Test
    public void findByName() {
        List<Book> books = repository.findBookByName( "test");
        assertEquals( "test", books.get(0).getName());
        assertEquals( 1, books.size());
    }

    @Test
    public void findBookByAuthorId() {
        List<Book> books = repository.findBookByAuthorId( author.getId());
        assertNotEquals( 0, books.size());
    }

    @Test
    public void findBookByGenreId() {
        List<Book> books = repository.findBookByGenreId( genre.getId());
        assertNotEquals( 0, books.size());
    }


}