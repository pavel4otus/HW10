package ru.pavel2107.otus.hw10.repository.datajpa;

import org.springframework.data.repository.CrudRepository;
import ru.pavel2107.otus.hw10.domain.Author;


import java.util.List;


public interface AuthorRepository extends CrudRepository<Author, Long> {
    public List<Author> findByName( String name);
}

