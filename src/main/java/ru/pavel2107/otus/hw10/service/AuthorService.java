package ru.pavel2107.otus.hw10.service;

import ru.pavel2107.otus.hw10.domain.Author;

import java.util.List;

public interface AuthorService {
    Author save(Author author);
    void delete( Long ID);
    Author find( Long ID);
    List<Author> findByName(String name);
    List<Author> findAll();
}
