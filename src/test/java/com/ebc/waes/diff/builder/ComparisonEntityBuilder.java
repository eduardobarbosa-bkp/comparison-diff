package com.ebc.waes.diff.builder;

import com.ebc.waes.diff.model.ComparisonEntity;
import com.ebc.waes.diff.model.DifferEntity;
import com.ebc.waes.diff.model.SourceEntity;
import com.ebc.waes.diff.test.TextContent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This class is a builder responsible to build a new instance of {@link ComparisonEntity} for using in test cases
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class ComparisonEntityBuilder {

    private String id = UUID.randomUUID().toString();

    private String left = TextContent.SIMPLE_TEXT_CONTENT_BASE64;

    private String right = TextContent.SIMPLE_TEXT_CONTENT_BASE64;

    private ComparisonEntityBuilder() {
    }

    public static ComparisonEntityBuilder aInstance() {
        return new ComparisonEntityBuilder();
    }

    public ComparisonEntityBuilder id(String id) {
        this.id = id;
        return this;
    }

    public ComparisonEntityBuilder left(String left) {
        this.left = left;
        return this;
    }

    public ComparisonEntityBuilder right(String right) {
        this.right = right;
        return this;
    }

    public ComparisonEntity build() {
        ComparisonEntity entity = new ComparisonEntity();
        entity.setId(id);
        SourceEntity sourceEntityLeft = new SourceEntity();
        sourceEntityLeft.setContent(left);
        entity.setLeft(sourceEntityLeft);
        SourceEntity sourceEntityRight = new SourceEntity();
        sourceEntityRight.setContent(right);
        entity.setRight(sourceEntityRight);
        return entity;
    }


}
