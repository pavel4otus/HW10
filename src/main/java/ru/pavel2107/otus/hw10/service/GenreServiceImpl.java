package ru.pavel2107.otus.hw10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pavel2107.otus.hw10.domain.Genre;
import ru.pavel2107.otus.hw10.repository.datajpa.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {


    private GenreRepository repository;

    @Autowired
    public GenreServiceImpl(GenreRepository repository){
        this.repository = repository;
    }

    @Override
    public Genre save(Genre genre) {
        return repository.save( genre);
    }

    @Override
    public void delete(Long ID) {
        Genre g = find( ID);
        if( g != null) {
            repository.delete( g);
        }
    }

    @Override
    public Genre find(Long ID) {
        return repository.findById( ID).orElse( null);
    }

    @Override
    public List<Genre> findAll() {
        return (List<Genre>)repository.findAll();
    }
}
