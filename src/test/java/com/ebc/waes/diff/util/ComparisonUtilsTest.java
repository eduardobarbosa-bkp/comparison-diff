package com.ebc.waes.diff.util;

import com.ebc.waes.diff.test.TextContent;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class ComparisonUtilsTest {

    @Test
    public void testDecodeBase64Content() {
        String content = ComparisonUtils.decodeBase64Text(TextContent.SIMPLE_TEXT_CONTENT_BASE64);
        assertThat(content, equalTo(TextContent.SIMPLE_TEXT_CONTENT_PLAN));
    }

    @Test
    public void testDecodeBase64ContentEmpty() {
        String content = ComparisonUtils.decodeBase64Text("");
        assertThat(content, equalTo(""));
    }


    @Test
    public void testEncodeBase64Content() {
        String content = ComparisonUtils.encodeBase64Text(TextContent.SIMPLE_TEXT_CONTENT_PLAN);
        assertThat(content, equalTo(TextContent.SIMPLE_TEXT_CONTENT_BASE64));
    }

    @Test
    public void testEncodeBase64ContentEmpty() {
        String content = ComparisonUtils.encodeBase64Text("");
        assertThat(content, equalTo(""));
    }


}
