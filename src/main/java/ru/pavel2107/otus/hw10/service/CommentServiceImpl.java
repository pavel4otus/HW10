package ru.pavel2107.otus.hw10.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pavel2107.otus.hw10.domain.Comment;
import ru.pavel2107.otus.hw10.repository.datajpa.CommentRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository repository;

    @Autowired
    public CommentServiceImpl( CommentRepository repository){
        this.repository = repository;
    }


    @Override
    public Comment save(Comment comment) {
        return repository.save( comment);
    }

    @Override
    public Comment find(Long Id) {
        return repository.findById( Id).orElse( null);
    }

    @Override
    public List<Comment> findAll(Long bookId) {
        return repository.findByBookId( bookId);
    }
}
