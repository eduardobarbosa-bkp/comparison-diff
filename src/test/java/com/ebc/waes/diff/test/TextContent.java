package com.ebc.waes.diff.test;

import java.util.Base64;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public interface TextContent {

    String SIMPLE_TEXT_CONTENT_PLAN = "Analyzes two files and show the lines that are different.";
    String SIMPLE_TEXT_CONTENT_BASE64 = Base64.getEncoder().encodeToString(SIMPLE_TEXT_CONTENT_PLAN.getBytes());
    String SIMPLE_TEXT_LEFT_CONTENT_PLAN = "Analyzes two files and show the lines that are different.LEFT";
    String SIMPLE_TEXT_LEFT_CONTENT_BASE64 = Base64.getEncoder().encodeToString(SIMPLE_TEXT_LEFT_CONTENT_PLAN.getBytes());
    String SIMPLE_TEXT_RIGHT_CONTENT_PLAN = "Analyzes two files and show the lines that are different.RIGHT";
    String SIMPLE_TEXT_RIGHT_CONTENT_BASE64 = Base64.getEncoder().encodeToString(SIMPLE_TEXT_RIGHT_CONTENT_PLAN.getBytes());
}
