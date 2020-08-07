package com.ms3.dse;

import org.springframework.data.repository.CrudRepository;

/**
 * This is a CRUD repository for user
 */
public interface UserRepository extends CrudRepository<User, Long> {
}

