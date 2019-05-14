package ru.pavel2107.otus.hw10.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name ="genre")
@NoArgsConstructor
@JsonIgnoreProperties( value = { "books"})
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name="id", nullable = false)
    @Setter @Getter private Long id;

    @Column( name = "name")
    @Setter @Getter private String name;

    @JsonIgnore
    @OneToMany( mappedBy = "genre", fetch = FetchType.LAZY)
    @Setter @Getter private List<Book> books;

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
