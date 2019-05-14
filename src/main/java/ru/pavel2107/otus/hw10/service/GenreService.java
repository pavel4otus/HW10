package ru.pavel2107.otus.hw10.service;

import ru.pavel2107.otus.hw10.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre save( Genre genre);
    void delete( Long ID);
    Genre find(Long ID);
    List<Genre> findAll();

}
