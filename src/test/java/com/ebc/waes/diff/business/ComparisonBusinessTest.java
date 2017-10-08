package com.ebc.waes.diff.business;

import com.ebc.waes.diff.builder.ComparisonEntityBuilder;
import com.ebc.waes.diff.exception.ComparisonException;
import com.ebc.waes.diff.model.ComparisonEntity;
import com.ebc.waes.diff.model.DifferEntity;
import com.ebc.waes.diff.repository.ComparisonRepository;
import com.ebc.waes.diff.test.TextContent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class ComparisonBusinessTest {

    private static final String ID_NOT_FOUND = UUID.randomUUID().toString();
    private static final String ID_VALID = UUID.randomUUID().toString();
    @InjectMocks
    private ComparisonBusiness business;
    @Mock
    private ComparisonRepository repository;

    @Test(expected = ComparisonException.class)
    public void testGetDiffResultsWithoutSetFiles() {
        business.getDiffResults(ID_NOT_FOUND);
    }

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.doReturn(null).when(repository).findById(ID_NOT_FOUND);
        ComparisonEntity entity = ComparisonEntityBuilder.aInstance().id(ID_VALID)
                .left(TextContent.SIMPLE_TEXT_LEFT_CONTENT_BASE64)
                .right(TextContent.SIMPLE_TEXT_RIGHT_CONTENT_BASE64)
                .build();
        Mockito.doReturn(entity).when(repository).findById(ID_VALID);
    }

    @Test
    public void testGetDiffResultsValid() {
        //given an id that match with a comparison
        //when call getDiffResults
        DifferEntity entity = business.getDiffResults(ID_VALID);
        //then return the diffs between the left and left side
        assertThat(entity, notNullValue());
    }

    @Test(expected = ComparisonException.class)
    public void testGetDiffResultsNotFound() {
        //given an id that doesn't match with any comparison
        //when call getDiffResults
        business.getDiffResults(ID_NOT_FOUND);
        //then should throw an exception
    }

    @Test
    public void testSetDiffRightSide() {
        //given a valid comparison entity
        ComparisonEntity entity = ComparisonEntityBuilder.aInstance().build();
        //when call setDiffLeftSide
        business.setDiffLeftSide(entity.getId(), entity.getLeft());
        //then call the repository to persist the comparison
        Mockito.verify(repository, Mockito.times(1)).persist(entity);
    }

    @Test
    public void testSetDiffLeftSide() {
//given a valid comparison entity
        ComparisonEntity entity = ComparisonEntityBuilder.aInstance().build();
        //when call setDiffRightSide
        business.setDiffRightSide(entity.getId(), entity.getRight());
        //then call the repository to persist the comparison
        Mockito.verify(repository, Mockito.times(1)).persist(entity);
    }


}
