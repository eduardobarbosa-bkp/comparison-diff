package com.ebc.waes.diff.repository.impl;

import com.ebc.waes.diff.config.Configuration;
import com.ebc.waes.diff.domain.Comparison;
import com.ebc.waes.diff.domain.dto.SourceDTO;
import com.ebc.waes.diff.exception.ComparisonException;
import com.ebc.waes.diff.repository.ComparisonRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.inject.Alternative;
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
@Alternative
public class ComparisonFileSystemRepository implements ComparisonRepository{

    private Configuration configuration = Configuration.getInstance();

    /**
     * @see ComparisonRepository#findById(String)
     */
    @Override
    public Comparison findById(String id){
        try {
            String recordDir = configuration.getStoreDir() + File.separator + id;
            Path path = Paths.get(recordDir);
            Path pathLeft = Paths.get(recordDir+File.separator+"left");
            Path pathRight = Paths.get(recordDir+File.separator+"right");
            if(!path.toFile().exists()){
                return null;
            }
            Comparison entity = new Comparison();
            entity.setId(id);
            if(pathLeft.toFile().exists()){
                SourceDTO left = new SourceDTO();
                left.setContent(FileUtils.readFileToString(pathLeft.toFile()));
                entity.setLeft(left.getContent());
            }
            if(pathRight.toFile().exists()){
                SourceDTO right = new SourceDTO();
                right.setContent(FileUtils.readFileToString(pathRight.toFile()));
                entity.setRight(right.getContent());
            }
            return entity;
        }  catch (IOException e){
            throw new ComparisonException(e.getMessage(), e);
        }
    }

    /**
     * @see ComparisonRepository#persist(Comparison)
     */
    @Override
    public void persist(Comparison entity){
        try {
         if(StringUtils.isEmpty(entity.getId())){
             throw new ComparisonException("the field id is required!");
         }
        String pathDiff = configuration.getStoreDir() + File.separator + entity.getId();
        FileUtils.forceMkdir(Paths.get(pathDiff).toFile());
        if(StringUtils.isNotEmpty(entity.getLeft())) {
            FileUtils.write(Paths.get(pathDiff, "left").toFile(), entity.getLeft());
        }
        if(StringUtils.isNotEmpty(entity.getRight())) {
            FileUtils.write(Paths.get(pathDiff, "right").toFile(), entity.getRight());
        }
        }  catch (IOException e){
            throw new ComparisonException(e.getMessage(), e);
        }
    }

}
