package ru.pavel2107.otus.hw10.service;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pavel2107.otus.hw10.domain.*;
import ru.pavel2107.otus.hw10.service.BookService;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DisplayName( "Сервис книг")
@SpringBootTest
public class BookServiceImplTest {

    @Autowired BookService service;

    private Book createTestBook(){
        Book book = new Book();
        book.setName( "test");
        book.setIsbn( "123");
        book.setPublicationPlace( "place");
        book.setPublishingHouse( "hos");
        book.setPublicationYear(1200);

        Genre  genre  = new Genre();
        genre.setId( 1L);
        genre.setName( "FANTASY");

        Author author = new Author();
        author.setId( 1L);
        author.setName( "KING");

        book.setGenre( genre);
        book.setAuthor( author);
        return book;
    }

    @Test
    public void findByISBN() {
        Book book = createTestBook();
        book.setIsbn( "333");
        service.save( book);
        book = service.findByISBN( book.getIsbn());
        assertNotEquals( null, book);
    }

    @Test
    public void save() {
        Book book = createTestBook();
        book = service.save( book);
        assertEquals( "test", book.getName());
    }

    @Test
    public void delete() throws Exception{

        Book book = createTestBook();
        book = service.save( book);
        service.delete( book.getId());
        book = service.find( book.getId());
        assertNull( book);
    }

    @Test
    public void find() {
        Book book = createTestBook();
        book = service.save( book);
        book = service.find( book.getId());
        assertNotNull( book);
    }

    @Test
    public void findByName() {
        Book book = createTestBook();
        book = service.save( book);
        List<Book> list = service.findByName( book.getName());
        assertNotEquals( 0, list.size());
    }

    @Test
    public void findBookByAuthorId() {
        Book book = createTestBook();

        Author author = new Author();
        author.setId( 1L);

        book.setAuthor( author);
        service.save( book);
        List<Book> list = service.findBookByAuthorId( author.getId());
        assertNotEquals( 0, list.size());
    }

    @Test
    public void findAll() {
        Book book = createTestBook();
        service.save( book);
        List<Book>list = service.findAll();
        assertNotEquals( 0, list.size());
    }
}