package com.ms3.dse;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * This is a CRUD repository to work with advertisements from database and search them by a key word
 */
public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {
    List<Advertisement> findBytitle(String title);
}

