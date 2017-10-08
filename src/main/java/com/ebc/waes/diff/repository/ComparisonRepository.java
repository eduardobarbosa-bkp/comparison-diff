package com.ebc.waes.diff.repository;

import com.ebc.waes.diff.config.Configuration;
import com.ebc.waes.diff.exception.ComparisonException;
import com.ebc.waes.diff.model.ComparisonEntity;
import com.ebc.waes.diff.model.SourceEntity;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is responsible to store the data using the file system
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class ComparisonRepository {

    private Configuration configuration = Configuration.getInstance();

    /**
     * Find the comparison entity by id
     * @param id the identifier of a comparison entity
     * @return the {@link ComparisonEntity}, if the entity didn't exists return null.
     */
    public ComparisonEntity findById(String id){
        try {
            String recordDir = configuration.getStoreDir() + File.separator + id;
            Path path = Paths.get(recordDir);
            Path pathLeft = Paths.get(recordDir+File.separator+"left");
            Path pathRight = Paths.get(recordDir+File.separator+"right");
            if(!path.toFile().exists()){
                return null;
            }
            ComparisonEntity entity = new ComparisonEntity();
            entity.setId(id);
            if(pathLeft.toFile().exists()){
                SourceEntity left = new SourceEntity();
                left.setContent(FileUtils.readFileToString(pathLeft.toFile()));
                entity.setLeft(left);
            }
            if(pathRight.toFile().exists()){
                SourceEntity right = new SourceEntity();
                right.setContent(FileUtils.readFileToString(pathRight.toFile()));
                entity.setRight(right);
            }
            return entity;
        }  catch (IOException e){
            throw new ComparisonException(e.getMessage(), e);
        }
    }

    /**
     * Persist the comparison entity
     * @param entity the {@link ComparisonEntity}
     */
    public void persist(ComparisonEntity entity){
        try {
         if(StringUtils.isEmpty(entity.getId())){
             throw new ComparisonException("the field id is required!");
         }
        String pathDiff = configuration.getStoreDir() + File.separator + entity.getId();
        FileUtils.forceMkdir(Paths.get(pathDiff).toFile());
        if(entity.getLeft() != null) {
            FileUtils.write(Paths.get(pathDiff, "left").toFile(), entity.getLeft().getContent());
        }
        if(entity.getRight() != null) {
            FileUtils.write(Paths.get(pathDiff, "right").toFile(), entity.getRight().getContent());
        }
        }  catch (IOException e){
            throw new ComparisonException(e.getMessage(), e);
        }
    }

}
