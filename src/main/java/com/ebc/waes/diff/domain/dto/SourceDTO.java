package com.ebc.waes.diff.domain.dto;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class SourceDTO{

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SourceDTO{" +
                "content='" + content + '\'' +
                '}';
    }
}
