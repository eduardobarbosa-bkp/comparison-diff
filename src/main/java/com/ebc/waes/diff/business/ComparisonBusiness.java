package com.ebc.waes.diff.business;

import com.ebc.waes.diff.exception.ComparisonException;
import com.ebc.waes.diff.model.ComparisonEntity;
import com.ebc.waes.diff.model.DifferEntity;
import com.ebc.waes.diff.model.SourceEntity;
import com.ebc.waes.diff.repository.ComparisonRepository;
import com.ebc.waes.diff.util.ComparisonUtils;
import org.apache.commons.text.diff.CommandVisitor;
import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * This class is responsible to provide the business logic for comparison
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
@Stateless
public class ComparisonBusiness {

    @Inject
    private ComparisonRepository repository;

    /**
     * Perform the comparison of the left and right side content,
     * as result provide the diffs between the left and right
     * @param id the identifier of a comparison
     * @return the {@link DifferEntity} with the diffs values
     */
    public DifferEntity getDiffResults(String id) {
        ComparisonEntity entity  = repository.findById(id);
        validateComparison(entity);
        return performDiff(
                ComparisonUtils.decodeBase64Text(entity.getLeft().getContent())
                ,ComparisonUtils.decodeBase64Text(entity.getRight().getContent()));
    }

    private DifferEntity performDiff(String left, String right) {
        DifferEntity differEntity = new DifferEntity();
        //If not of equal size just return that
        if(left.length() != right.length()){
            differEntity.setLeft(left);
            differEntity.setRight(right);
            return differEntity;
        }
        //If of same size provide insight in where the diffs are
        StringsComparator comparator = new StringsComparator(left, right);
        EditScript<Character> script = comparator.getScript();
        DiffVisitor<Character> visitor = new DiffVisitor<>();
        script.visit(visitor);

        differEntity.setLeft(left);
        differEntity.setRight(right);
        differEntity.setDiffs(visitor.getString());
        differEntity.setModifications(script.getModifications());
        return differEntity;
    }

    private class DiffVisitor<T> implements CommandVisitor<T> {
        private final StringBuilder builder;
        public DiffVisitor() {
            builder = new StringBuilder();
        }
        @Override
        public void visitInsertCommand(final T object) {
            builder.append("+"+object);
        }
        @Override
        public void visitKeepCommand(final T object) {
            builder.append(object);
        }
        @Override
        public void visitDeleteCommand(final T object) {
            builder.append("-"+object);
        }
        public String getString() {
            return builder.toString();
        }
    }

    private void validateComparison(ComparisonEntity entity) {
        if(entity == null
                || entity.getLeft() == null
                ||  entity.getRight() == null ){
            throw new ComparisonException("The left and the right content must be provided!");
        }
    }

    /**
     * Create or Update a comparison and set the right side of the content
     * @param id the identifier of a comparison
     * @param sourceEntity the {@link SourceEntity} with the text Base64 encoded
     */
    public void setDiffRightSide(String id, SourceEntity sourceEntity) {
        ComparisonEntity entity = repository.findById(id);
        if(entity == null){
            entity = new ComparisonEntity();
            entity.setId(id);
        }
        entity.setRight(sourceEntity);
        repository.persist(entity);
    }

    /**
     * Create or Update a comparison and set the left side of the content
     * @param id the identifier of a comparison
     * @param sourceEntity the {@link SourceEntity} with the text Base64 encoded
     */
    public void setDiffLeftSide(String id, SourceEntity sourceEntity) {
        ComparisonEntity entity = repository.findById(id);
        if(entity == null){
            entity = new ComparisonEntity();
            entity.setId(id);
        }
        entity.setLeft(sourceEntity);
        repository.persist(entity);
    }
}
