package com.jettodz.crud.dtos.requests;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jettodz.crud.serialization.BookRequestJson;

@JsonSerialize(using = BookRequestJson.Serializer.class)
@JsonDeserialize(using = BookRequestJson.Deserializer.class)
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

    @Override
    public int hashCode() {
        return Objects.hash(title, isbn, languageId);
    }

    @Override 
    public String toString() {
        return String.format(
            "{\"title\": \"%s\", \"isbn\": \"%s\", \"languageId\": %d, \"numberOfPages\": %d, \"publicationDate\": \"%s\", \"authorIds\": \"%s\", \"publisherId\": %d}",
            title != null ? title : "null",
            isbn != null ? isbn : "null",
            languageId != null ? languageId : 0,
            numberOfPages != null ? numberOfPages : 0,
            publicationDate != null ? publicationDate.toString() : "null",
            authorIds != null ? authorIds.toString() : "null",
            publisherId != null ? publisherId : 0
        );
    }

}
