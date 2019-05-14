package ru.pavel2107.otus.hw10.repository.datajpa;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pavel2107.otus.hw10.domain.Author;
import ru.pavel2107.otus.hw10.service.BookServiceImpl;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DisplayName( "Репозиторий авторов")
@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired AuthorRepository repository;


    @Test
    public void findByName() {
        Author author = new Author();
        author.setName( "king");
        repository.save( author);

        List<Author> list = repository.findByName( "king");
        assertEquals( "king", list.get(0).getName());
    }

    @Test
    public void save(){
        Author author = new Author();
        author.setName( "king");
        repository.save( author);

    }

}