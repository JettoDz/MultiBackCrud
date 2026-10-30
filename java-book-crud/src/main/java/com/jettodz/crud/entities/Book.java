package com.jettodz.crud.entities;

import jakarta.persistence.Table;
import com.jettodz.crud.dtos.requests.BookRequest;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {

    public Book() {
    }

    public Book(Integer id, String title, String isbn, Language language, Integer numberOfPages, LocalDate publicationDate, List<Author> authors, Publisher publisher) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.language = language;
        this.numberOfPages = numberOfPages;
        this.publicationDate = publicationDate;
        this.authors = authors;
        this.publisher = publisher;
    }

    public Book(String title, String isbn, Language language, Integer numberOfPages, LocalDate publicationDate, List<Author> authors, Publisher publisher) {
        this.title = title;
        this.isbn = isbn;
        this.language = language;
        this.numberOfPages = numberOfPages;
        this.publicationDate = publicationDate;
        this.authors = authors;
        this.publisher = publisher;
    }

    public Book(BookRequest request, Language language, List<Author> authors, Publisher publisher) {
        this.title = request.getTitle();
        this.isbn = request.getIsbn();
        this.language = language;
        this.numberOfPages = request.getNumberOfPages();
        this.publicationDate = request.getPublicationDate();
        this.authors = authors;
        this.publisher = publisher;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer id;

    private String title;

    @Column(name = "isbn13")
    private String isbn;

    @OneToOne
    @JoinColumn(name = "language_id", referencedColumnName = "language_id")
    private Language language;

    private Integer numberOfPages;

    private LocalDate publicationDate;

    @ManyToMany
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @OneToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "publisher_id")
    private Publisher publisher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

}
