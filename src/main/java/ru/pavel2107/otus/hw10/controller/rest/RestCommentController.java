package ru.pavel2107.otus.hw10.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.pavel2107.otus.hw10.domain.Comment;
import ru.pavel2107.otus.hw10.domain.CommentDTO;
import ru.pavel2107.otus.hw10.service.BookService;
import ru.pavel2107.otus.hw10.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
public class RestCommentController {

    private CommentService service;
    private BookService bookService;

    @Autowired
    public RestCommentController( CommentService service, BookService bookService){
        this.service = service;
        this.bookService = bookService;
    }

    @GetMapping( value = "/rest/comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comment> listComments(@PathVariable( value = "id") Long bookId){
        List<Comment> list = service.findAll( bookId);
        return list;
    }

    @PostMapping( value = "/rest/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void save( @RequestBody CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setName( commentDTO.getName());
        comment.setComment( commentDTO.getComment());
        comment.setDateTime( LocalDateTime.now());
        comment.setBook( bookService.find( commentDTO.getIdBook() ));
        comment = service.save( comment);
    }


}
