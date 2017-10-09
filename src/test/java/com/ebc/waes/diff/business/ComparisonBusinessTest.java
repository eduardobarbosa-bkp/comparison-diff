package com.ebc.waes.diff.business;

import com.ebc.waes.diff.builder.ComparisonEntityBuilder;
import com.ebc.waes.diff.domain.dto.SourceDTO;
import com.ebc.waes.diff.exception.ComparisonException;
import com.ebc.waes.diff.domain.Comparison;
import com.ebc.waes.diff.domain.dto.ComparisonDiffDTO;
import com.ebc.waes.diff.repository.impl.ComparisonFileSystemRepository;
import com.ebc.waes.diff.test.TextContent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Base64;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class ComparisonBusinessTest {

    private static final String ID_NOT_FOUND = UUID.randomUUID().toString();
    @InjectMocks
    private ComparisonBusiness business;
    @Mock
    private ComparisonFileSystemRepository repository;

    @Test(expected = ComparisonException.class)
    public void testGetDiffResultsWithoutSetFiles() {
        business.getDiffResults(ID_NOT_FOUND);
    }

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.doReturn(null).when(repository).findById(ID_NOT_FOUND);

    }

    private void mockComparisonEntityResult( Comparison entity) {
        Mockito.doReturn(entity).when(repository).findById(entity.getId());
    }

    @Test
    public void testGetDiffResults() {
        //given an id that match with a comparison
        Comparison entity = ComparisonEntityBuilder.aInstance().id(UUID.randomUUID().toString())
                .left(TextContent.SIMPLE_TEXT_LEFT_CONTENT_BASE64)
                .right(TextContent.SIMPLE_TEXT_RIGHT_CONTENT_BASE64)
                .build();
        mockComparisonEntityResult(entity);
        //when call getDiffResults
        ComparisonDiffDTO diff = business.getDiffResults(entity.getId());
        //then return the diffs between the left and left side
        assertThat(diff, notNullValue());
        assertThat(diff.getDiffs(), equalTo(TextContent.DIFF_LEFT_TO_RIGHT));
    }

    @Test
    public void testGetDiffResultsWithEqualContent() {
        //given an id that match with a comparison with the same content in left and right side
        Comparison entity = ComparisonEntityBuilder.aInstance().id(UUID.randomUUID().toString())
                .left(TextContent.SIMPLE_TEXT_CONTENT_BASE64)
                .right(TextContent.SIMPLE_TEXT_CONTENT_BASE64)
                .build();
        mockComparisonEntityResult(entity);
        //when call getDiffResults
        ComparisonDiffDTO diff = business.getDiffResults(entity.getId());
        //then return no diffs between the left and left side
        assertThat(diff, notNullValue());
        assertThat(diff.getDiffs(), equalTo(TextContent.SIMPLE_TEXT_CONTENT_PLAN));
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
        Comparison entity = ComparisonEntityBuilder.aInstance().build();
        //when call setDiffLeftSide
        SourceDTO source = new SourceDTO();
        source.setContent(entity.getLeft());
        business.setDiffLeftSide(entity.getId(), source);
        //then call the repository to persist the comparison
        Mockito.verify(repository, Mockito.times(1)).persist(entity);
    }

    @Test
    public void testSetDiffLeftSide() {
        //given a valid comparison entity
        Comparison entity = ComparisonEntityBuilder.aInstance().build();
        //when call setDiffRightSide
        SourceDTO source = new SourceDTO();
        source.setContent(entity.getRight());
        business.setDiffRightSide(entity.getId(), source);
        //then call the repository to persist the comparison
        Mockito.verify(repository, Mockito.times(1)).persist(entity);
    }


}
