package com.ebc.waes.diff.business;

import com.ebc.waes.diff.domain.Comparison;
import com.ebc.waes.diff.domain.dto.ComparisonDiffDTO;
import com.ebc.waes.diff.domain.dto.SourceDTO;
import com.ebc.waes.diff.exception.ComparisonException;
import com.ebc.waes.diff.repository.ComparisonRepository;
import com.ebc.waes.diff.util.ComparisonUtils;
import org.apache.commons.lang3.StringUtils;
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
     * @return the {@link ComparisonDiffDTO} with the diffs values
     */
    public ComparisonDiffDTO getDiffResults(String id) {
        Comparison comparison  = repository.findById(id);
        validateComparison(comparison);
        return performDiff(ComparisonUtils.decodeBase64Text(comparison.getLeft()),
                            ComparisonUtils.decodeBase64Text(comparison.getRight()));
    }

    private ComparisonDiffDTO performDiff(String left, String right) {
        ComparisonDiffDTO comparisonDiff = new ComparisonDiffDTO();
        StringsComparator comparator = new StringsComparator(left, right);
        EditScript<Character> script = comparator.getScript();
        DiffVisitor<Character> visitor = new DiffVisitor<>();
        script.visit(visitor);
        comparisonDiff.setDiffs(visitor.getString());
        return comparisonDiff;
    }

    private class DiffVisitor<T> implements CommandVisitor<T> {
        private final StringBuilder builder;
        public DiffVisitor() {
            builder = new StringBuilder();
        }
        @Override
        public void visitInsertCommand(final T object) {
            builder.append("[+]"+object);
        }
        @Override
        public void visitKeepCommand(final T object) {
            builder.append(object);
        }
        @Override
        public void visitDeleteCommand(final T object) {
            builder.append("[-]"+object);
        }
        public String getString() {
            return builder.toString();
        }
    }

    private void validateComparison(Comparison entity) {
        if(entity == null
                || StringUtils.isEmpty(entity.getLeft())
                || StringUtils.isEmpty(entity.getRight()) ){
            throw new ComparisonException("The left and the right content must be provided!");
        }
    }

    /**
     * Create or Update a comparison and set the right side of the content
     * @param id the identifier of a comparison
     * @param source the {@link SourceDTO} with the text Base64 encoded
     */
    public void setDiffRightSide(String id, SourceDTO source) {
        Comparison entity = repository.findById(id);
        if(entity == null){
            entity = new Comparison();
            entity.setId(id);
        }
        entity.setRight(source.getContent());
        repository.persist(entity);
    }

    /**
     * Create or Update a comparison and set the left side of the content
     * @param id the identifier of a comparison
     * @param source the {@link SourceDTO} with the text Base64 encoded
     */
    public void setDiffLeftSide(String id, SourceDTO source) {
        Comparison entity = repository.findById(id);
        if(entity == null){
            entity = new Comparison();
            entity.setId(id);
        }
        entity.setLeft(source.getContent());
        repository.persist(entity);
    }
}
