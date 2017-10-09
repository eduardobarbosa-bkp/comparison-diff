package com.ebc.waes.diff.repository;

import com.ebc.waes.diff.domain.Comparison;

/**
 * Created by eduardo.costa on 09/10/2017.
 */
public interface ComparisonRepository {

    /**
     * Find the comparison entity by id
     * @param id the identifier of a comparison entity
     * @return the {@link Comparison}, if the entity didn't exists return null.
     */
    Comparison findById(String id);

    /**
     * Persist the comparison entity
     * @param entity the {@link Comparison}
     */
    void persist(Comparison entity);
}
