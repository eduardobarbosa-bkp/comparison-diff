package com.ebc.waes.diff.model;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class SourceEntity {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SourceEntity{" +
                "content='" + content + '\'' +
                '}';
    }
}
