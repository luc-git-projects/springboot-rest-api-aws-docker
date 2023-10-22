package br.com.lucotavio.restspringboot.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOK")
public class Book implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "LAUNCH_DATE")
    private LocalDate launchDate;

    @Column(name = "PRICE")
    private BigDecimal price ;

    @Column(name = "ISBN")
    private String isbn;

    @ToString.Exclude
    @OneToMany(mappedBy = "book", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<AuthorHasBook> authorHasBooks = new LinkedHashSet<>();

    public void addAuthorHasBooks(AuthorHasBook authorHasBook){
        authorHasBooks.add(authorHasBook);
        authorHasBook.setBook(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
