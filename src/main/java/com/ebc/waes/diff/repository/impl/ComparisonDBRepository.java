package com.ebc.waes.diff.repository.impl;

import com.ebc.waes.diff.domain.Comparison;
import com.ebc.waes.diff.repository.ComparisonRepository;

import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * This class is responsible to store the data using the data base
 * @version 1.0
 * @author eduardo.costa
 * @since 07/10/2017
 */
@Default
public class ComparisonDBRepository implements ComparisonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @see ComparisonRepository#findById(String)
     */
    @Override
    public Comparison findById(String id) {
        return  entityManager.find(Comparison.class, id);
    }

    /**
     * @see ComparisonRepository#persist(Comparison)
     */
    @Override
    public void persist(Comparison entity) {
        entityManager.merge(entity);
    }
}
