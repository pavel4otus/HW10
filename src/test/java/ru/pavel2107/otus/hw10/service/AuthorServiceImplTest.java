package ru.pavel2107.otus.hw10.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pavel2107.otus.hw10.domain.Author;
import ru.pavel2107.otus.hw10.repository.datajpa.AuthorRepository;
import ru.pavel2107.otus.hw10.repository.datajpa.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DisplayName( "datajpa. Сервис авторов")
@SpringBootTest
public class AuthorServiceImplTest {

    @MockBean AuthorRepository authorRepository;
    @MockBean BookRepository bookRepository;

    AuthorService authorService;


    @Before
    public void before(){
        authorService = new AuthorServiceImpl( authorRepository);
    }



    @Test
    public void save() {
        Author author = new Author();
        author.setName( "KING-TEST");
        author.setId( 8L);

        when( authorRepository.save( author)).thenReturn( author);
        author = authorService.save( author);
        assertEquals( 8L , author.getId().longValue());
    }
    @Test
    public void find() {
        Author author = new Author();
        author.setName( "KING-TEST");
        author.setId( 88L);
        when( authorRepository.save( author)).thenReturn( author);
        Optional<Author> a = Optional.of( author);
        when( authorRepository.findById( 88L)).thenReturn( a);
        author = authorService.find( author.getId());
        assertEquals( 88L, author.getId().longValue());


    }

    @Test
    public void findByName() {
        Author author = new Author();
        author.setName( "KING-TEST-888");
        author.setId( 888L);
        when( authorRepository.save( author)).thenReturn( author);
        authorService.save( author);

        List<Author> list = new ArrayList<>();
        list.add( author);

        when( authorRepository.findByName( author.getName())).thenReturn( list);
        list = authorService.findByName( author.getName());
        assertEquals( 1, list.size());
    }

    @Test
    public void findAll() {

        Author author = new Author();
        author.setName( "KING-TEST-888");
        author.setId( 888L);
        when( authorRepository.save( author)).thenReturn( author);
        authorService.save( author);

        List<Author> list = new ArrayList<>();
        list.add( author);

        when( authorRepository.findAll()).thenReturn( list);
        list = (List<Author>)authorRepository.findAll();
        assertEquals( 1, list.size());


    }
}