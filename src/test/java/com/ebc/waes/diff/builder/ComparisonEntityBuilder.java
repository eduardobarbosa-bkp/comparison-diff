package com.ebc.waes.diff.builder;

import com.ebc.waes.diff.domain.Comparison;
import com.ebc.waes.diff.test.TextContent;

import java.util.UUID;

/**
 * This class is a builder responsible to build a new instance of {@link Comparison} for using in test cases
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

    public Comparison build() {
        Comparison entity = new Comparison();
        entity.setId(id);
        entity.setLeft(left);
        entity.setRight(right);
        return entity;
    }


}
