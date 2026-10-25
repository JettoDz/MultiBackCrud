package com.jettodz.crud.dtos.requests;
import java.time.LocalDate;
import java.util.List;

public class BookRequest {

    private String title;
    private String isbn;
    private Integer languageId;
    private Integer numberOfPages;
    private LocalDate publicationDate;
    private List<Integer> authorIds;
    private Integer publisherId;

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

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
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

    /**
     * Sets the publication date from a string in ISO-8601 format (yyyy-MM-dd).
     */
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = LocalDate.parse(publicationDate);
    }

    public List<Integer> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Integer> authorIds) {
        this.authorIds = authorIds;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }
}
