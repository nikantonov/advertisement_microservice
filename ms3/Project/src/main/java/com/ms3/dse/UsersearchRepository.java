package com.ms3.dse;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import com.ms3.dse.Usersearch;

/**
 * This is a CRUD repository for usersearch, with a explicit method for searching by their latest search.
 */
public interface UsersearchRepository extends CrudRepository<Usersearch, Long> {


    List<Usersearch> findBylatestsearch(String latestsearch);
}