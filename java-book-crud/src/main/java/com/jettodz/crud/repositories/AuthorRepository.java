package com.jettodz.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jettodz.crud.entities.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
