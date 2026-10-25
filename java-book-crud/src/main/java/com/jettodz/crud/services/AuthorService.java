package com.jettodz.crud.services;

import com.jettodz.crud.entities.Author;
import java.util.List;

public interface AuthorService {
    Author getAuthorById(Integer id);
    List<Author> getAuthorsByIds(List<Integer> ids);
}
