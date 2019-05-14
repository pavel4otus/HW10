package ru.pavel2107.otus.hw10.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


//
// id - id комментария
// dateTime - время когда оставили
// name - имя пользователя
// comment - сам сомментарий
//

@Entity
@Table( name = "comments")
@NoArgsConstructor
@JsonIgnoreProperties( value = { "book"})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name="id", nullable = false)
    @Setter @Getter private Long id;

    @Column( name = "name")
    @Setter @Getter private String name;

    @Column( name = "datetime")
    @Setter @Getter private LocalDateTime dateTime;

    @Column( name = "comment")
    @Setter @Getter private String comment;

    @ManyToOne
    @JoinColumn( name="book_id")
    @Setter @Getter private Book book;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateTime=" + dateTime +
                ", comment='" + comment + '\'' +
                '}';
    }
}
