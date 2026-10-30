package com.jettodz.crud.daos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jettodz.crud.entities.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}
