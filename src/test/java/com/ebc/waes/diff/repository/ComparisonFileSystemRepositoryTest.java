package com.ebc.waes.diff.repository;

import com.ebc.waes.diff.builder.ComparisonEntityBuilder;
import com.ebc.waes.diff.config.Configuration;
import com.ebc.waes.diff.exception.ComparisonException;
import com.ebc.waes.diff.domain.Comparison;
import com.ebc.waes.diff.repository.impl.ComparisonFileSystemRepository;
import com.ebc.waes.diff.test.TextContent;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class ComparisonFileSystemRepositoryTest {

    private ComparisonFileSystemRepository repository = new ComparisonFileSystemRepository();

    private static final String ID_NOT_FOUND = UUID.randomUUID().toString();

    private String id;

    private Configuration configuration = Configuration.getInstance();

    @Before
    public void setup() throws Exception {
        File directory = Paths.get(configuration.getStoreDir()).toFile();
        if(directory.exists()) {
            FileUtils.cleanDirectory(directory);
        }
        id =  UUID.randomUUID().toString();
        String pathDiff = configuration.getStoreDir() + File.separator + id;
        FileUtils.forceMkdir(Paths.get(pathDiff).toFile());
        FileUtils.write(Paths.get(pathDiff, "left").toFile(), TextContent.SIMPLE_TEXT_CONTENT_BASE64);
        FileUtils.write(Paths.get(pathDiff, "right").toFile(), TextContent.SIMPLE_TEXT_CONTENT_BASE64);
    }

    @Test
    public void testFindByIdNotFound(){
        //given an id that doesn't match with any comparison
        //when find the entity by id
        Comparison entity = repository.findById(ID_NOT_FOUND);
        //then the entity must be null
        assertThat(entity, nullValue());
    }

    @Test
    public void testFindByIdValid(){
        //given an id that match with a comparison
        //when find the entity by id
        Comparison entity = repository.findById(this.id);
        //then the entity must not be null and the id must be the same
        assertThat(entity, notNullValue());
        assertThat(entity.getId(), equalTo(this.id));
        assertThat(entity.getLeft(), notNullValue());
        assertThat(entity.getRight(), notNullValue());
        assertThat(entity.getLeft(), equalTo(TextContent.SIMPLE_TEXT_CONTENT_BASE64));
        assertThat(entity.getRight(), equalTo(TextContent.SIMPLE_TEXT_CONTENT_BASE64));
    }


    @Test
    public void testPersist(){
        //given a valid comparison entity
        Comparison entity = ComparisonEntityBuilder.aInstance().build();
        //when persist the entity
        repository.persist(entity);
        //then the entity could be find in the database and should be the same
        Comparison entityDB = repository.findById(entity.getId());
        assertThat(entityDB, notNullValue());
        assertThat(entity.getId(), equalTo(entityDB.getId()));
    }


    @Test(expected = ComparisonException.class)
    public void testPersistNullId(){
        //given a invalid comparison entity with id null
        Comparison entity = ComparisonEntityBuilder.aInstance().id(null).build();
        //when persist the entity
        repository.persist(entity);
        //then should throw an exception
    }


}
