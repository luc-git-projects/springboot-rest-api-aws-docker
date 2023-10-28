package br.com.lucotavio.restspringboot.model;

import br.com.lucotavio.restspringboot.dto.BookDto;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import static br.com.lucotavio.restspringboot.util.DataConverter.string_dd_mm_yyyy_withSlashToLocalDate;

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

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "PRICE")
    private BigDecimal price ;

    @Column(name = "LAUNCH_DATE")
    private LocalDate launchDate;

    public Book(BookDto bookDto) {
        id = bookDto.getKey();
        title = bookDto.getTitle();
        author = bookDto.getAuthor();
        price = convertStringToBigDecimal(bookDto.getPrice());
        launchDate = string_dd_mm_yyyy_withSlashToLocalDate(bookDto.getLaunchDate());
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

    private BigDecimal convertStringToBigDecimal(String stringNumber){
        stringNumber = stringNumber.replace(",", ".");
        boolean testIfIsNumber = isNumber(stringNumber);
        if(!testIfIsNumber){
            throw new ArithmeticException("Is not a number");
        }

        return new BigDecimal(stringNumber);
    }

    private boolean isNumber(String number){
        return NumberUtils.isParsable(number);
    }
}
