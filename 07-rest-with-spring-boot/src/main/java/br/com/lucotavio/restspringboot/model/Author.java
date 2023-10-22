package br.com.lucotavio.restspringboot.model;

import br.com.lucotavio.restspringboot.dto.AuthorDto;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import static br.com.lucotavio.restspringboot.util.DataConverter.string_dd_mm_yyyy_withSlashToLocalDate;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTHOR")
public class Author implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    public Author(AuthorDto authorDto){
        id = authorDto.getKey();
        name = authorDto.getName();
        country = authorDto.getCountry();
        birthDate = string_dd_mm_yyyy_withSlashToLocalDate(authorDto.getBirthDate());
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<AuthorHasBook> authorHasBooks = new LinkedHashSet<>();

    public void addAuthorHasBook(AuthorHasBook authorHasBook){
        this.authorHasBooks.add(authorHasBook);
        authorHasBook.setAuthor(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
